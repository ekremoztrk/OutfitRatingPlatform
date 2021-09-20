package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import model.domain.*;
import model.utilities.Observer;
import model.utilities.Subject;
import view.CollectionFrame;

public class CollectionController implements Observer {

	private SessionManager session;
	private Subject model;
	private CollectionFrame view;
	private Collection collection;

	public CollectionController(Model model, CollectionFrame view, SessionManager session, Collection collection) {
		this.session = session;
		this.model = model;
		this.view = view;
		this.collection = collection;

		collection.register(this);

		setSidebarListeners();
		setContentListeners();
	}

	private void setSidebarListeners() {
		view.addOpenProfileActionListener(new OpenUserListener(session.getCurrentUser()));
		view.addLogoutActionListener(new LogoutListener());
		view.addHomeActionListener(new OpenHomeListener());
		view.addOpenOutfitsActionListener(new OpenOutfitsListener());
		view.addAllUsersActionListener(new OpenAllUserListener());
		view.addStatisticsActionListener(new OpenStatisticsListener());
	}

	private void setContentListeners() {
		List<Outfit> outfits = collection.getOutfits();
		for (Outfit outfit : outfits) {
			view.addOpenOutfitActionListener(new OpenOutfitListener(outfit), "" + outfit.getId());
		}
		view.addAddOutfitActionListener(new AddOutfitListener());
		for(Outfit outfit:outfits)
			view.addRemoveOutfitFromCollectionActionListener(new RemoveOutfitFromCollectionListener(outfit),outfit.getId());
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

	class AddOutfitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			List<Outfit> allOutfits = ((Model) model).getAllOutfits();
			String[] choices = new String[allOutfits.size()];
			for (int i = 0; i < allOutfits.size(); i++) {
				Outfit outfit = allOutfits.get(i);
				choices[i] = outfit.getId() + ". " + outfit.getBrand() + " " + outfit.getColor() + " "
						+ outfit.getType();
			}
			Object response = (view.showInputMessage("Choose an outfit to add:", choices));
			if (response == null || response == "") {
				return;
			}
			String str = response.toString();
			String outfitId = str.substring(0, str.indexOf("."));
			try {
				((Model) model).addOutfitToCollection(Integer.parseInt(outfitId), collection);
			} catch (IllegalStateException e1) {
				view.showMessage(e1.getMessage());
				return;
			}
		}
	}

	class OpenOutfitListener implements ActionListener {
		private Outfit outfit;

		public OpenOutfitListener(Outfit outfit) {
			this.outfit = outfit;
		}

		public void actionPerformed(ActionEvent e) {
			session.outfitPage(outfit);
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
	
	class OpenAllUserListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			session.allUsersPage();
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
	class RemoveOutfitFromCollectionListener implements ActionListener {
		private Outfit outfit;

		public RemoveOutfitFromCollectionListener(Outfit outfit) {
			this.outfit = outfit;
		}

		public void actionPerformed(ActionEvent e) {
			((Model) model).removeOutfitFromCollection(outfit.getId(), collection);
		}
	}
}
