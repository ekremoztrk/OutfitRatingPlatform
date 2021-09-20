package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import model.domain.*;
import model.utilities.Observer;
import model.utilities.Subject;
import view.UserFrame;

public class UserController implements Observer {

	private SessionManager session;
	private UserFrame view;
	private Subject model;
	private User user;

	public UserController(Model model, UserFrame view, SessionManager session, User user) {
		this.model = model;
		this.session = session;
		this.view = view;
		this.user = user;

		user.register(this);
		setSidebarListeners();
		setContentListeners();
	}
	
	private void setSidebarListeners() {
		view.addOpenProfileActionListener(new OpenUserListener());
		view.addLogoutActionListener(new LogoutListener());
		view.addOpenOutfitsActionListener(new OpenOutfitsListener());
		view.addHomeActionListener(new OpenHomeListener());
		view.addStatisticsActionListener(new OpenStatisticsListener());
		view.addAllUsersActionListener(new OpenAllUserListener());
	}
	
	private void setContentListeners() {
		List<Collection> collections = user.getCollections();
		for (Collection collection : collections) {
			view.addOpenCollectionActionListener(new OpenCollectionListener(collection), collection.getName());
		}
		view.addCreateCollectionActionListener(new CreateCollectionListener());
		view.addUnfollowUserActionListener(new UnfollowUserListener());
		view.addFollowUserActionListener(new FollowUserListener());
	}

	@Override
	public void update() {
		setContentListeners();
	}

	@Override
	public void addSubject(Subject sub) {
		this.model = sub;
	}

	@Override
	public void removeSubject(Subject sub) {
		this.model = null;

	}

	class OpenOutfitsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	session.outfitsPage();
        }
    }
    
    class OpenCollectionListener implements ActionListener {
    	private Collection collection;
    	
    	public OpenCollectionListener(Collection collection) {
    		this.collection = collection;
    	}
    	
        public void actionPerformed(ActionEvent e) {
        	session.collectionPage(collection);
        }
    }
	class OpenAllUserListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			session.allUsersPage();
		}
	}
    
    class CreateCollectionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	String newCollectionName = view.showInputDialog("Enter the name of the collection:");
        	if (newCollectionName == null || newCollectionName == "")  {
        		return;
        	}
        	newCollectionName = newCollectionName.trim();
			((Model) model).createCollectionForUser(newCollectionName, session.getCurrentUser());
        }
    }
	class UnfollowUserListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			((Model) model).unfollowUserAsUser(user.getId(), session.getCurrentUser());
		}
	}

	class FollowUserListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			((Model) model).followUserAsUser(user.getId(), session.getCurrentUser());
		}
	}

    class OpenUserListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	session.userPage(session.getCurrentUser());
        }
    }
    
    class OpenHomeListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		session.homePage();
    	}
    }
    
    class OpenStatisticsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	session.statisticsPage();
        }
    }
    
    class LogoutListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	session.loginPage();
        }
    }
}
