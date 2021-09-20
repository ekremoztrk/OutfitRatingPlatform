package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import model.domain.Collection;
import model.domain.Model;
import model.domain.User;
import view.AllUsersFrame;

public class AllUsersController {

	private SessionManager session;
	private AllUsersFrame view;
	private Model model;

	public AllUsersController(Model model, AllUsersFrame view, SessionManager session) {
		this.session = session;
		this.view = view;
		this.model = model;

		setSidebarListeners();
		setContentListeners();
	}

	private void setSidebarListeners() {
		view.addOpenProfileActionListener(new OpenUserListener(session.getCurrentUser()));
		view.addLogoutActionListener(new LogoutListener());
		view.addOutfitsActionListener(new OpenOutfitsListener());
		view.addStatisticsActionListener(new OpenStatisticsListener());
		view.addHomeActionListener(new OpenHomeListener());
	}

	private void setContentListeners() {
		List<User> users = model.getAllUsers();
		for (User user : users) {
			view.addOpenUserActionListener(new OpenUserListener(user), user.getUsername());
		}
	}

	class OpenUserListener implements ActionListener {
		private User user;

		public OpenUserListener(User user) {
			this.user = user;
		}

		public void actionPerformed(ActionEvent e) {
			session.userPage(user);
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

	class OpenStatisticsListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			session.statisticsPage();
		}
	}
	class OpenHomeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			session.homePage();
		}
	}


	class OpenOutfitsListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			session.outfitsPage();
		}
	}

	class LogoutListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			session.loginPage();
		}
	}
}
