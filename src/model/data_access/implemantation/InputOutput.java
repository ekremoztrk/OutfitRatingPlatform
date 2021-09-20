package model.data_access.implemantation;

import model.data_access.InputOutputInterface;
import model.domain.Collection;
import model.domain.Outfit;
import model.domain.User;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

public class InputOutput implements InputOutputInterface {

	private List<User> users;
	private List<Outfit> outfits;
	private List<List<UserWithCollection>> collectionsList;
	private static FileWriter file;

	public List<User> getUsers() {
		return users;
	}

	public List<Outfit> getOutfits() {
		return outfits;
	}

	public void outputOutfits(List<Outfit> outfits) {
		List<JSONObject> allOutfitsJSON = new ArrayList<>();
		JSONObject outfitsJsonList = new JSONObject();
		for (Outfit outfit : outfits) {
			allOutfitsJSON.add(IOParser.outfitToJSON(outfit));
		}
		outfitsJsonList.put("Outfits", allOutfitsJSON);
		try {
			file = new FileWriter("outfits.json");
			file.write(outfitsJsonList.toString());

		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			try {
				file.flush();
				file.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<Outfit> inputOutfits(UserRepository userRepository) {
		JSONParser jsonParser = new JSONParser();
		List<Outfit> outfitsList = new ArrayList<>();
		try (FileReader reader = new FileReader("outfits.json")) {
			Object obj = jsonParser.parse(reader);
			org.json.simple.JSONObject outfits = (org.json.simple.JSONObject) obj;
			org.json.simple.JSONArray entries = (org.json.simple.JSONArray) outfits.get("Outfits");

			entries.forEach(entry -> outfitsList
					.add(IOParser.parseOutfitJson((org.json.simple.JSONObject) entry, new UserRepository(this))));
			this.outfits = outfitsList;
			matchUsersAndCollections(collectionsList, userRepository);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return outfitsList;
	}

	public void xmlOutput(List<User> users) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			// add elements to Document
			Element rootElement = doc.createElementNS("CENG431", "Users");
			// append root element to document
			doc.appendChild(rootElement);

			// append first child element to root element
			for (User user : users)
				rootElement.appendChild(IOParser.toUserXMLNode(user,doc));

			// for output to file, console
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			// for pretty print
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);

			// write to console or file
			//StreamResult console = new StreamResult(System.out);
			StreamResult file = new StreamResult(new File("users.xml"));

			// write data
			//transformer.transform(source, console);
			transformer.transform(source, file);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<User> inputUsers() {
		List<User> users = new ArrayList<>();
		List<UserWithUsers> followersList = new ArrayList<>();
		List<UserWithUsers> followingsList = new ArrayList<>();
		List<List<UserWithCollection>> collectionsList = new ArrayList<>();
		try {
			File inputFile = new File("users.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			// doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("User");

			Node nNode = nList.item(0);
			int length = nNode.getChildNodes().getLength();
			for (int i = 0; i < length; i++) {
				if (nNode != null && nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					int id = Integer.valueOf(eElement.getAttribute("id"));
					String userName = eElement.getElementsByTagName("UserName").item(0).getTextContent();
					String password = eElement.getElementsByTagName("Password").item(0).getTextContent();

					User user = new User(id, userName, password, new ArrayList<>(), new ArrayList<>(),
							new ArrayList<>());
					Node followers = eElement.getElementsByTagName("Followers").item(0);
					Node followings = eElement.getElementsByTagName("Followings").item(0);
					Node collections = eElement.getElementsByTagName("Collections").item(0);

					UserWithUsers followersObject = nodeToUserWithUsers(id, followers);
					UserWithUsers followingsObject = nodeToUserWithUsers(id, followings);
					List<UserWithCollection> userWithCollections = nodeToUserWithCollection(id, collections);

					users.add(user);
					followersList.add(followersObject);
					followingsList.add(followingsObject);
					collectionsList.add(userWithCollections);

					nNode = nNode.getNextSibling().getNextSibling();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.users = users;
		matchUsersAndFollowers(followersList);
		matchUsersAndFollowings(followingsList);
		this.collectionsList = collectionsList;
		return this.users;
	}

	private UserWithUsers nodeToUserWithUsers(int userId, Node node) {
		NodeList nList = node.getChildNodes();
		List<Integer> relatedUsers = new ArrayList<>();
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				int id = Integer.valueOf(eElement.getAttribute("id"));
				relatedUsers.add(id);
			}
		}
		return new UserWithUsers(userId, relatedUsers);
	}

	private List<UserWithCollection> nodeToUserWithCollection(int userId, Node node) {
		NodeList nList = node.getChildNodes();
		List<UserWithCollection> userWithCollections = new ArrayList<>();

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			List<Integer> outfitIdsList = new ArrayList<>();
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				int id = Integer.valueOf(eElement.getAttribute("id"));
				String name = eElement.getElementsByTagName("Name").item(0).getTextContent();

				String outfitIds = eElement.getElementsByTagName("OutfitIds").item(0).getTextContent();
				String[] values = outfitIds.split(" ");
				if(values.length > 0) {
					for (String s : values)
						try {
							outfitIdsList.add(Integer.valueOf(s));
						} catch (NumberFormatException e) {
							outfitIdsList = new ArrayList<>();
						}
				}
				UserWithCollection userWithCollection = new UserWithCollection(userId, id, name, outfitIdsList);
				userWithCollections.add(userWithCollection);
			}
		}
		return userWithCollections;

	}

	class UserWithUsers {

		int userId;
		List<Integer> connectedUserIds;

		public UserWithUsers(int userId, List<Integer> connectedUserIds) {
			this.userId = userId;
			this.connectedUserIds = connectedUserIds;
		}
	}

	class UserWithCollection {

		int userId;
		int collectionId;
		String collectionName;
		List<Integer> outfitIds;

		public UserWithCollection(int userId, int collectionId, String collectionName, List<Integer> outfitIds) {
			this.userId = userId;
			this.collectionId = collectionId;
			this.collectionName = collectionName;
			this.outfitIds = outfitIds;
		}
	}

	private void matchUsersAndFollowers(List<UserWithUsers> followersList) {
		for (UserWithUsers user : followersList) {
			User followed = findUserById(user.userId);
			for (int id : user.connectedUserIds) {
				User follower = findUserById(id);
				followed.addFollower(follower);
			}
		}
	}

	private void matchUsersAndFollowings(List<UserWithUsers> followingsList) {
		for (UserWithUsers user : followingsList) {
			User following = findUserById(user.userId);
			for (int id : user.connectedUserIds) {
				User followed = findUserById(id);
				following.addFollowing(followed);
			}
		}

	}

	private void matchUsersAndCollections(List<List<UserWithCollection>> lists, UserRepository userRepository) {
		for (List<UserWithCollection> usersCollections : lists) {
			for (UserWithCollection userCollections : usersCollections) {
				User user = userRepository.findUserById(userCollections.userId);
				int collectionId = userCollections.collectionId;
				Collection collection = new Collection(collectionId, userCollections.collectionName, user);
				for (int outfitId : userCollections.outfitIds) {
					Outfit outfit = findOutfitById(outfitId);
					collection.addOutfit(outfit);
				}
				user.addCollection(collection);
			}
		}
	}

	private User findUserById(int id) {
		for (User user : users) {
			if (user.getId() == id)
				return user;
		}
		return null;
	}

	private Outfit findOutfitById(int id) {
		for (Outfit outfit : outfits) {
			if (outfit.getId() == id)
				return outfit;
		}
		return null;
	}
}