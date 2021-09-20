package model.data_access.implemantation;

import model.domain.Collection;
import model.domain.Comment;
import model.domain.Outfit;
import model.domain.User;
import model.utilities.*;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.swing.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IOParser {

    public static Node toUserXMLNode(User user, Document doc) {
        Element userElem = doc.createElement("User");
        userElem.setAttribute("id", String.valueOf(user.getId()));
        userElem.appendChild(getUserElements(doc, "UserName", user.getUsername()));
        userElem.appendChild(getUserElements(doc, "Password", user.getPassword()));

        Element node = doc.createElement("Followers");
        if(!user.getFollowers().isEmpty()){
            for(User follower:user.getFollowers()){
                node.appendChild(toSimpleUserXMLNode(follower,doc));
            }
        }
        Element node2 = doc.createElement("Followings");
        if(!user.getFollowings().isEmpty()){
            for(User following:user.getFollowings()){
                node2.appendChild(toSimpleUserXMLNode(following,doc));
            }
        }
        Element node3 = doc.createElement("Collections");
        if(!user.getCollections().isEmpty()){
            for(Collection collection:user.getCollections()){
                node3.appendChild(toXMLCollectionNode(collection,doc));
            }
        }
        userElem.appendChild(node);
        userElem.appendChild(node2);
        userElem.appendChild(node3);
        return userElem;
    }

    private static Node toSimpleUserXMLNode(User user, Document doc) {
        Element userElem = doc.createElement("User");

        userElem.setAttribute("id", String.valueOf(user.getId()));
        userElem.appendChild(getUserElements(doc, "UserName", user.getUsername()));
        userElem.appendChild(getUserElements(doc, "Password", user.getPassword()));

        return userElem;
    }

    private static Node getUserElements(Document doc, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

    public static JSONObject outfitToJSON(Outfit outfit){
        JSONObject outfitJSON = new JSONObject();
        outfitJSON.put("Id", outfit.getId());
        outfitJSON.put("Brand", outfit.getBrand());
        outfitJSON.put("Type", outfit.getType());
        outfitJSON.put("Occasion", outfit.getOccasion());
        outfitJSON.put("Gender", outfit.getGender());
        outfitJSON.put("Sizes", outfit.getSizes());
        outfitJSON.put("Color", outfit.getColor());
        outfitJSON.put("Likes", outfit.getLikes());
        outfitJSON.put("Dislikes", outfit.getDislikes());

        List<Integer> likedUserIds = new ArrayList<>();
        if(outfit.getLikedUsers()!=null){
            for(User user:outfit.getLikedUsers()){
                likedUserIds.add(user.getId());
            }
        }
        List<Integer> dislikedUserIds = new ArrayList<>();
        if(outfit.getDislikedUsers()!=null){
            for(User user:outfit.getDislikedUsers()){
                dislikedUserIds.add(user.getId());
            }
        }

        outfitJSON.put("LikedUserIds", likedUserIds);
        outfitJSON.put("DislikedUserIds", dislikedUserIds);

        List<JSONObject> commentsList = new ArrayList<>();
        if(outfit.getComments()!=null){
            for(Comment comment:outfit.getComments()){
                commentsList.add(toCommentJSON(comment));
            }
        }
        outfitJSON.put("Comments", commentsList);
        return outfitJSON;
    }

    @SuppressWarnings("unchecked")
	public static Outfit parseOutfitJson(org.json.simple.JSONObject outfitJSON, UserRepository repository) {
        int id = ((Long) outfitJSON.get("Id")).intValue();
        Brand brand = Brand.valueOf((String) outfitJSON.get("Brand"));
        Type type = Type.valueOf((String) outfitJSON.get("Type"));
        Occasion occasion = Occasion.valueOf((String) outfitJSON.get("Occasion"));
        Gender gender = Gender.valueOf((String) outfitJSON.get("Gender"));
        Color color = Color.valueOf((String) outfitJSON.get("Color"));

        List<User> likedUsers = new ArrayList<>();
        List<User> dislikedUsers = new ArrayList<>();

        org.json.simple.JSONArray likedUserIds = (org.json.simple.JSONArray) outfitJSON.get("LikedUserIds");
        org.json.simple.JSONArray dislikedUserIds = (org.json.simple.JSONArray) outfitJSON.get("DislikedUserIds");
        likedUserIds.forEach(entry -> likedUsers.add(repository.findUserById(((Long) entry).intValue())));
        dislikedUserIds.forEach(entry -> dislikedUsers.add(repository.findUserById(((Long) entry).intValue())));


        List<Size> sizes = new ArrayList<>();
        org.json.simple.JSONArray sizesJson = (org.json.simple.JSONArray) outfitJSON.get("Sizes");
        sizesJson.forEach(entry -> sizes.add(Size.valueOf((String) entry)));

        List<Comment> comments = new ArrayList<>();
        org.json.simple.JSONArray commentsJSON = (org.json.simple.JSONArray) outfitJSON.get("Comments");
        commentsJSON.forEach(entry -> {
            try {
                comments.add(parseCommentJson((org.json.simple.JSONObject) entry, repository));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        Outfit outfit = new Outfit(id,brand,type,occasion,gender,sizes,color,comments);
        outfit.setLikedUsers(likedUsers);
        outfit.setDislikedUsers(dislikedUsers);
        outfit.setImage(new ImageIcon("assets/" + id + ".jpg"));
        return outfit;
    }

    // JSON METHODS

    public JSONObject toCollectionJSON(Collection collection){
        JSONObject collectionJSON = new JSONObject();
        collectionJSON.put("Id", collection.getId());
        collectionJSON.put("Name", collection.getName());
        List<JSONObject> outfitsJSON = new ArrayList<>();
        for(Outfit outfit:collection.getOutfits()){
            outfitsJSON.add(IOParser.outfitToJSON(outfit));
        }
        collectionJSON.put("Outfits", outfitsJSON);

        return collectionJSON;
    }

    // XML METHODS

    public static Node toXMLCollectionNode(Collection collection, Document doc) {
        Element collectionElem = doc.createElement("Collection");
        //set id attribute
        collectionElem.setAttribute("id", String.valueOf(collection.getId()));
        //create name element
        collectionElem.appendChild(getOutfitElements(doc, "Name", collection.getName()));
        String outfitIds="";
        for(Outfit outfit:collection.getOutfits()){
            outfitIds+=outfit.getId()+" ";
        }
        if(outfitIds.length() > 0) {
        	outfitIds = outfitIds.substring(0, outfitIds.length() - 1);
        }
        collectionElem.appendChild(getOutfitElements(doc,"OutfitIds",outfitIds));

        return collectionElem;
    }

    private static Node getOutfitElements(Document doc, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

    // JSON METHODS

    public static JSONObject toCommentJSON(Comment comment){
        JSONObject commentJson = new JSONObject();
        commentJson.put("Id", comment.getId());
        commentJson.put("Content", comment.getContent());
        commentJson.put("AuthorId", comment.getAuthor().getId());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        commentJson.put("Date", dateFormat.format(comment.getDate()));
        return commentJson;
    }

    public static Comment parseCommentJson(org.json.simple.JSONObject commentJSON, UserRepository repository) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        int id = ((Long) commentJSON.get("Id")).intValue();
        String content = (String) commentJSON.get("Content");
        int userId = ((Long) commentJSON.get("AuthorId")).intValue();
        String dateStr = (String) commentJSON.get("Date");
        Date date=dateFormat.parse(dateStr);

        User user = repository.findUserById(userId);

        return new Comment(id,content,user,date);
    }
}
