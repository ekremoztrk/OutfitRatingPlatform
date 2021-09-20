package model.domain;


import model.utilities.Observer;
import model.utilities.Subject;
import java.util.ArrayList;
import java.util.List;

public class User implements Subject {

	private int id;
	private String username;
	private String password;
	private List<User> followers;
	private List<User> followings;
	private List<Collection> collections;
	private boolean changed;
	private List<Observer> observers;

	public User(int id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.followers = new ArrayList<>();
		this.followings = new ArrayList<>();
		this.collections = new ArrayList<>();
		this.changed = false;
		this.observers = new ArrayList<Observer>();
	}
	
	public User(int id, String username, String password, List<User> followers, List<User> followings,
			List<Collection> collections) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.followers = followers;
		this.followings = followings;
		this.collections = collections;
		this.changed = false;
		this.observers = new ArrayList<Observer>();
	}

	public void addFollower(User user) {
		List<User> followers = getFollowers();
		followers.add(user);
		setFollowers(followers);
	}

	public void removeFollower(User user) {
		List<User> followers = getFollowers();
		followers.remove(user);
		setFollowers(followers);
	}

	public User findFollowerById(int id) {
		List<User> followers = getFollowers();
		for (User user : followers) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}

	public void addFollowing(User user) {
		List<User> followings = getFollowings();
		followings.add(user);
		setFollowings(followings);
	}

	public void removeFollowing(User user) {
		List<User> followings = getFollowings();
		followings.remove(user);
		setFollowings(followings);
	}

	public User findFollowingById(int id) {
		List<User> followings = getFollowings();
		for (User user : followings) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}

	public void addCollection(Collection collection) {
		List<Collection> collections = getCollections();
		collections.add(collection);
		setCollections(collections);
	}

	public void removeCollection(Collection collection) {
		List<Collection> collections = getCollections();
		collections.remove(collection);
		setCollections(collections);
	}

	public Collection findCollectionById(int id) {
		List<Collection> collections = getCollections();
		for (Collection collection : collections) {
			if (collection.getId() == id) {
				return collection;
			}
		}
		return null;
	}
	
	@Override
	public boolean equals(Object o) {
		return getId() == ((User) o).getId();
	}

	// GETTERS AND SETTERS

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<User> getFollowers() {
		return followers;
	}

	public void setFollowers(List<User> followers) {
		setChanged(true);
		notifyObservers();
		this.followers = followers;
	}

	public List<User> getFollowings() {
		return followings;
	}

	public void setFollowings(List<User> followings) {
		setChanged(true);
		notifyObservers();
		this.followings = followings;
	}

	public List<Collection> getCollections() {
		return collections;
	}

	public void setCollections(List<Collection> collections) {
		setChanged(true);
		notifyObservers();
		this.collections = collections;
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
	
	// OBSERVATION METHODS
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
}
