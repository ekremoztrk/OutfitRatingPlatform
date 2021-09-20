package controller;

import model.domain.Collection;
import model.domain.Model;
import model.domain.Outfit;
import model.domain.User;
import view.*;

@SuppressWarnings("unused")
public class SessionManager {
	private User currentUser;
	private FrameManager fm;
	private Model model;

	public SessionManager(User currentUser, FrameManager fm, Model model) {
		this.setCurrentUser(currentUser);
		this.fm = fm;
		this.model = model;
	}

	public void loginPage() {
		LoginFrame loginView = new LoginFrame(fm);
		LoginController loginController = new LoginController(model, loginView, this);
	}

	public void homePage() {
		HomeFrame homeView = new HomeFrame(model, fm, currentUser);
		HomeController homeController = new HomeController(model, homeView, this);
	}

	public void userPage(User user) {
		UserFrame userView = new UserFrame(fm, currentUser, user);
		UserController userController = new UserController(model, userView, this, user);
	}

	public void allUsersPage() {
		AllUsersFrame userView = new AllUsersFrame(model, fm, currentUser);
		AllUsersController userController = new AllUsersController(model, userView, this);
	}

	public void collectionPage(Collection collection) {
		CollectionFrame collectionView = new CollectionFrame(fm, currentUser, collection);
		CollectionController collectionController = new CollectionController(model, collectionView, this, collection);
	}

	public void outfitPage(Outfit outfit) {
		OutfitFrame outfitView = new OutfitFrame(fm, outfit, currentUser);
		OutfitController outfitController = new OutfitController(model, outfitView, this, outfit);
	}

	public void outfitsPage() {
		OutfitsFrame outfitsView = new OutfitsFrame(model, fm);
		OutfitsController outfitsController = new OutfitsController(model, outfitsView, this);
	}

	public void statisticsPage() {
		StatisticsFrame statisticsView = new StatisticsFrame(fm, model);
		StatisticsController statisticsControler = new StatisticsController(statisticsView, this);
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
}