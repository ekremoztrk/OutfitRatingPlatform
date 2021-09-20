package model.domain;

import java.util.*;
import model.data_access.implemantation.InputOutput;
import model.data_access.implemantation.OutfitRepository;
import model.data_access.implemantation.UserRepository;
import model.utilities.Observer;
import model.utilities.Subject;

public class Model implements Observer, Subject {

	private UserRepository userRepo;
	private OutfitRepository outfitRepo;
	private boolean changed;
	private List<Observer> observers;
	private List<Subject> subjects;
	private InputOutput io;
	
	public Model() {
		io = new InputOutput();
		this.userRepo = new UserRepository(io);
		this.outfitRepo = new OutfitRepository(io, this.userRepo);
		setChanged(false);
		setObservers(new ArrayList<Observer>());
		setSubjects(new ArrayList<Subject>());
//
//		User user1 = new User(1, "tugkantuglular", "1234");
//		User user2 = new User(2, "dilekozturk", "1234");
//		User user3 = new User(3, "serhatcaner", "1234");
//		User user4 = new User(4, "armanokluoglu", "1234");
//		User user5 = new User(5, "ekremozturk", "1234");
//		User user6 = new User(6, "elonmusk", "1234");
//
//		user1.addFollower(user2);
//		user1.addFollower(user3);
//		user1.addFollower(user4);
//		user1.addFollower(user5);
//		user2.addFollower(user4);
//		user2.addFollower(user5);
//		user3.addFollower(user1);
//		user3.addFollower(user6);
//		user5.addFollower(user1);
//		user6.addFollower(user1);
//
//		user2.addFollowing(user1);
//		user3.addFollowing(user1);
//		user4.addFollowing(user1);
//		user5.addFollowing(user1);
//		user4.addFollowing(user2);
//		user5.addFollowing(user2);
//		user1.addFollowing(user3);
//		user6.addFollowing(user3);
//		user1.addFollowing(user5);
//		user1.addFollowing(user6);
//
//		Comment com1 = new Comment(1, "Denemeeeee", user1, new Date());
//		Comment com2 = new Comment(2, "YORUUm", user5, new Date());
//		Comment com3 = new Comment(3, "yorum deneme", user3, new Date());
//		Comment com4 = new Comment(4, "testtesttest", user4, new Date());
//
//		Outfit outfit = new Outfit(1, Brand.GAP, Type.HOODIE, Occasion.CASUAL, Gender.UNISEX, Arrays.asList(Size.S, Size.M, Size.L), Color.ORANGE, Arrays.asList(com1, com2, com3, com4));
//		Outfit outfit2 = new Outfit(2, Brand.BURBERRY, Type.SCARF, Occasion.ELEGANT, Gender.UNISEX, Arrays.asList(Size.M), Color.TAN, new ArrayList<>());
//		Outfit outfit3 = new Outfit(3, Brand.CALVINKLEIN, Type.COAT, Occasion.CASUAL, Gender.WOMEN, Arrays.asList(Size.XS, Size.S, Size.M, Size.L, Size.XL), Color.WHITE, new ArrayList<>());
//		Outfit outfit4 = new Outfit(4, Brand.FENDI, Type.BLOUSE, Occasion.ELEGANT, Gender.WOMEN, Arrays.asList(Size.S, Size.M, Size.L), Color.BLACK, new ArrayList<>());
//		Outfit outfit5 = new Outfit(5, Brand.GUCCI, Type.BELT, Occasion.ELEGANT, Gender.UNISEX, Arrays.asList(Size.M), Color.BLACK, new ArrayList<>());
//		Outfit outfit6 = new Outfit(6, Brand.ARMANI, Type.PANTS, Occasion.PARTY, Gender.MEN, Arrays.asList(Size.XS, Size.S, Size.M, Size.L, Size.XL), Color.PINK, new ArrayList<>());
//		Outfit outfit7 = new Outfit(7, Brand.ROLEX, Type.WATCH, Occasion.CASUAL, Gender.MEN, Arrays.asList(Size.M), Color.GRAY, new ArrayList<>());
//		Outfit outfit8 = new Outfit(8, Brand.ADIDAS, Type.SWEATPANTS, Occasion.SPORTY, Gender.UNISEX, Arrays.asList(Size.S, Size.M, Size.L), Color.BLACK, new ArrayList<>());
//		Outfit outfit9 = new Outfit(9, Brand.CONVERSE, Type.FOOTWEAR, Occasion.CASUAL, Gender.UNISEX, Arrays.asList(Size.XS, Size.S, Size.M, Size.L, Size.XL), Color.RED, new ArrayList<>());
//		Outfit outfit10 = new Outfit(10, Brand.ZARA, Type.SWIMSUIT, Occasion.SPORTY, Gender.WOMEN, Arrays.asList(Size.XS, Size.S, Size.M, Size.L, Size.XL), Color.PATTERNED, new ArrayList<>());
//		Outfit outfit11 = new Outfit(11, Brand.HERMES, Type.BRACELET, Occasion.ELEGANT, Gender.WOMEN, Arrays.asList(Size.M), Color.ROSEGOLD, new ArrayList<>());
//		Outfit outfit12 = new Outfit(12, Brand.NIKE, Type.FOOTWEAR, Occasion.SPORTY, Gender.MEN, Arrays.asList(Size.XS, Size.S, Size.M, Size.L, Size.XL), Color.GREEN, new ArrayList<>());
//		Outfit outfit13 = new Outfit(13, Brand.LEVIS, Type.JEANS, Occasion.CASUAL, Gender.WOMEN, Arrays.asList(Size.S, Size.M, Size.L), Color.BLUE, new ArrayList<>());
//		Outfit outfit14 = new Outfit(14, Brand.PRADA, Type.DRESS, Occasion.ELEGANT, Gender.WOMEN, Arrays.asList(Size.S, Size.M, Size.L), Color.BLACK, new ArrayList<>());
//		Outfit outfit15 = new Outfit(15, Brand.RAYBAN, Type.GLASSES, Occasion.CASUAL, Gender.UNISEX, Arrays.asList(Size.M), Color.GOLD, new ArrayList<>());
//
//		Collection collection = new Collection(1, "collection1", user1, new Date(1618923364000L), Arrays.asList(outfit, outfit2));
//		Collection collection2 = new Collection(2, "collection2", user2, new Date(1618836964000L), Arrays.asList(outfit3, outfit));
//		Collection collection3 = new Collection(3, "collection3", user3, new Date(1618750564000L), Arrays.asList(outfit4, outfit3));
//		Collection collection4 = new Collection(4, "collection4", user4, new Date(1619009764000L), Arrays.asList(outfit, outfit2, outfit3));
//		Collection collection5 = new Collection(5, "collection5", user5, new Date(1619096164000L), Arrays.asList(outfit5, outfit2, outfit6));
//		Collection collection6 = new Collection(6, "collection6", user6, new Date(1618919764000L), Arrays.asList(outfit6, outfit2, outfit, outfit3));
//
//		user1.addCollection(collection);
//		user2.addCollection(collection2);
//		user3.addCollection(collection3);
//		user4.addCollection(collection4);
//		user5.addCollection(collection5);
//		user6.addCollection(collection6);
//
//		outfit.like(user1);
//		outfit2.like(user2);
//		outfit2.like(user3);
//		outfit2.like(user4);
//		outfit3.like(user5);
//		outfit3.like(user6);
//		outfit4.like(user1);
//		outfit4.like(user2);
//		outfit5.like(user3);
//		outfit6.like(user4);
//
//		outfit.dislike(user2);
//		outfit.dislike(user3);
//		outfit2.dislike(user1);
//		outfit4.dislike(user6);
//
//		userList = Arrays.asList(user1, user2, user3, user4, user5, user6);
//		outfits = Arrays.asList(outfit, outfit2, outfit3, outfit4, outfit5, outfit6, outfit7, outfit8, outfit9, outfit10, outfit11, outfit12, outfit13, outfit14, outfit15);
//
//		io.xmlOutput(userList);
//		io.outputOutfits(outfits);
//
//		io.inputUsers();
//		io.inputOutfits();

		registerAll();
	}

	private void registerAll() {
		List<User> users = userRepo.findAll();
		List<Outfit> outfits = outfitRepo.findAll();
		for (User user : users) {
			user.register(this);
			addSubject(user);
		}
		for (Outfit outfit : outfits) {
			outfit.register(this);
			addSubject(outfit);
		}
	}

	public void outputData(){
		userRepo.outputUsers();
		outfitRepo.outputOutfits();
	}
	public User login(String username, String password) throws IllegalArgumentException, IllegalStateException {
		User user = userRepo.findUserByUsername(username);
		if (!user.getPassword().equals(password)) {
			throw new IllegalArgumentException("Invalid password.");
		}
		return user;
	}

	public Collection createCollectionForUser(String name, User user) {
		Collection collection = new Collection(name, user);
		user.addCollection(collection);
		return collection;
	}

	public void addOutfitToCollection(int outfitId, Collection collection) throws IllegalStateException {
		Outfit outfit = outfitRepo.findOutfitById(outfitId);
		collection.addOutfit(outfit);
		outputData();
	}

	public void removeOutfitFromCollection(int outfitId, Collection collection) throws IllegalStateException {
		Outfit outfit = outfitRepo.findOutfitById(outfitId);
		collection.removeOutfit(outfit);
		outputData();
	}

	public void likeOutfitAsUser(Outfit outfit, User user) {
		outfit.like(user);
		outputData();
	}

	public void dislikeOutfitAsUser(Outfit outfit, User user) {
		outfit.dislike(user);
		outputData();
	}

	public Comment commentOnOutfitAsUser(Outfit outfit, String commentContent, User user) {
		Comment comment = new Comment(commentContent, user, new Date());
		outfit.addComment(comment);
		outputData();
		return comment;
	}

	public void removeCommentOnOutfit(Outfit outfit, Comment comment) {
		outfit.removeComment(comment);
		outputData();
	}

	public void followUserAsUser(int userId, User user) {
		User userToFollow = userRepo.findUserById(userId);
		user.addFollowing(userToFollow);
		userToFollow.addFollower(user);
		outputData();
	}

	public void unfollowUserAsUser(int userId, User user) {
		User userToUnfollow = userRepo.findUserById(userId);
		user.removeFollowing(userToUnfollow);
		userToUnfollow.removeFollower(user);
		outputData();
	}

	public List<User> getFollowingsOfUser(int userId) {
		User user = userRepo.findUserById(userId);
		return user.getFollowings();
	}

	public List<User> getFollowersOfUser(int userId) {
		User user = userRepo.findUserById(userId);
		return user.getFollowers();
	}

	public List<Collection> getCollectionsOfFollowingsOfUserChronologically(User user) {
		List<User> followings = user.getFollowings();
		List<Collection> collections = new ArrayList<>();
		for (User following : followings) {
			collections.addAll(following.getCollections());
		}
		Collections.sort(collections, new Comparator<Collection>() {
			public int compare(Collection col1, Collection col2) {
				return col2.getCreationDate().compareTo(col1.getCreationDate());
			}
		});
		return collections;

	}

	public List<Outfit> getAllOutfits() {
		return outfitRepo.findAll();
	}

	public Outfit getMostLikedOutfit() {
		return outfitRepo.getOutfitWithMostLikes();
	}

	public Outfit getMostDislikedOutfit() {
		return outfitRepo.getOutfitWithMostDislikes();
	}

	public List<User> getAllUsers(){ return userRepo.findAll();}

	public User getMostFollowedUser() {
		return userRepo.getUserWithMostFollowers();
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public List<Observer> getObservers() {
		return observers;
	}

	public void setObservers(List<Observer> observers) {
		this.observers = observers;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	// SUBJECT METHODS

	@Override
	public void register(Observer obj) {
		if (obj == null) {
			throw new NullPointerException("The given observer is null.");
		}
		List<Observer> observers = getObservers();
		if (!observers.contains(obj)) {
			observers.add(obj);
			setObservers(observers);
		}
	}

	@Override
	public void unregister(Observer obj) {
		if (obj == null) {
			throw new NullPointerException("The given observer is null.");
		}
		List<Observer> observers = getObservers();
		if (!observers.contains(obj)) {
			observers.remove(obj);
			setObservers(observers);
		}
	}

	@Override
	public void notifyObservers() {
		if (!isChanged()) {
			return;
		}
		setChanged(false);
		List<Observer> observers = getObservers();
		for (Observer observer : observers) {
			observer.update();
		}
	}

	// OBSERVER METHODS

	@Override
	public void update() {
		setChanged(true);
		notifyObservers();
	}

	@Override
	public void addSubject(Subject sub) {
		this.subjects.add(sub);
	}

	@Override
	public void removeSubject(Subject sub) {
		this.subjects.remove(sub);
	}
}
