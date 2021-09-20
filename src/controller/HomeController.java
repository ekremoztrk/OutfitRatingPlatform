package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import model.domain.Collection;
import model.domain.Model;
import model.domain.User;
import model.utilities.Observer;
import model.utilities.Subject;
import view.HomeFrame;

public class HomeController implements Observer {

	private SessionManager session;
	private HomeFrame view;
	private Subject model;

	public HomeController(Model model, HomeFrame view, SessionManager session) {
		this.session = session;
		this.view = view;
		this.model = model;
		
		model.register(this);

		setSidebarListeners();
		setContentListeners();
	}

	private void setSidebarListeners() {
		view.addOpenProfileActionListener(new OpenUserListener(session.getCurrentUser()));
		view.addLogoutActionListener(new LogoutListener());
		view.addOutfitsActionListener(new OpenOutfitsListener());
		view.addAllUsersActionListener(new OpenAllUserListener());
		view.addStatisticsActionListener(new OpenStatisticsListener());
	}

	private void setContentListeners() {
		List<Collection> collections = ((Model) model).getCollectionsOfFollowingsOfUserChronologically(session.getCurrentUser());
		for (Collection collection : collections) {
			view.addOpenCollectionActionListener(new OpenCollectionListener(collection), collection.getName());
			view.addOpenUserActionListener(new OpenUserListener(collection.getCreator()),
					collection.getCreator().getUsername());
		}
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

	class OpenOutfitsListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			session.outfitsPage();
		}
	}

	class OpenAllUserListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			session.allUsersPage();
		}
	}

	class LogoutListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			session.loginPage();
		}
	}
}
