package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import model.domain.Comment;
import model.domain.Model;
import model.domain.Outfit;
import model.domain.User;
import model.utilities.Observer;
import model.utilities.Subject;
import view.OutfitFrame;

public class OutfitController implements Observer {

	private SessionManager session;
	private Subject model;
	private Outfit outfit;
	private OutfitFrame view;
	private List<Comment> comments;

	public OutfitController(Model model, OutfitFrame view, SessionManager session, Outfit outfit) {
		this.model = model;
		this.view = view;
		this.session = session;
		this.outfit = outfit;
		this.comments = outfit.getComments();

		outfit.register(this);
		setSidebarListeners();
		setContentListeners();
	}

	private void setSidebarListeners() {
		view.addOpenProfileActionListener(new OpenUserListener(session.getCurrentUser()));
		view.addLogoutActionListener(new LogoutListener());
		view.addOpenOutfitsActionListener(new OpenOutfitsListener());
		view.addHomeActionListener(new OpenHomeListener());
		view.addAllUsersActionListener(new OpenAllUserListener());
		view.addStatisticsActionListener(new OpenStatisticsListener());
	}

	private void setContentListeners() {
		view.addLikeOutfitActionListener(new LikeOutfitListener());
		view.addDislikeOutfitActionListener(new DislikeOutfitListener());
		view.addCommentOnOutfitActionListener(new AddCommentOnOutfitListener());
		for (Comment comment : comments) {
			view.addRemoveCommentOnOutfitActionListener(new RemoveCommentOnOutfitListener(comment), comment.getId());
		}
	}

	class LikeOutfitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			((Model) model).likeOutfitAsUser(outfit, session.getCurrentUser());
		}
	}

	class DislikeOutfitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			((Model) model).dislikeOutfitAsUser(outfit, session.getCurrentUser());
		}
	}

	class AddCommentOnOutfitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			((Model) model).commentOnOutfitAsUser(outfit, view.getComment(), session.getCurrentUser());
			view.clearComment();
		}
	}

	class RemoveCommentOnOutfitListener implements ActionListener {
		private Comment comment;

		public RemoveCommentOnOutfitListener(Comment comment) {
			this.comment = comment;
		}

		public void actionPerformed(ActionEvent e) {
			((Model) model).removeCommentOnOutfit(outfit, comment);
		}
	}

	class OpenAllUserListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			session.allUsersPage();
		}
	}
	
	class OpenOutfitsListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			session.outfitsPage();
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
}
