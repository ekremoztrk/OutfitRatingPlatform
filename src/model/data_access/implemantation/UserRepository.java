package model.data_access.implemantation;

import java.util.List;

import model.data_access.UserRepositoryInterface;
import model.domain.User;

public class UserRepository implements UserRepositoryInterface {

	private InputOutput io;
	private List<User> userList;

	public UserRepository(InputOutput io) {
		this.io = io;
		userList = io.inputUsers();
	}

	public void outputUsers(){
		io.xmlOutput(userList);
	}

	public List<User> findAll(){
		return userList;
	}
	
	public void setUsers(List<User> users) {
		this.userList = users;
	}

	public User findUserByUsername(String username) {
		for (User user : userList) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		throw new IllegalStateException("No user with the given username exists.");
	}

	public User findUserById(int userId) {
		for(User user:userList){
			if(user.getId()==userId)
				return user;
		}
		return null;
	}

	public User getUserWithMostFollowers() {
		User mostFollowedUser = null;
		for (User user: userList) {
			if (mostFollowedUser == null) {
				mostFollowedUser = user;
			} else {
				if (mostFollowedUser != null && user.getFollowers().size() > mostFollowedUser.getFollowers().size()) {
					mostFollowedUser = user;
				}
			}
		}
		return mostFollowedUser;
	}
	
}