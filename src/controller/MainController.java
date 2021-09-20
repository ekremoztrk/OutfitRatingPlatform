package controller;

import model.domain.Model;
import view.FrameManager;

public class MainController {

	private Model model;
	private FrameManager fm;

	public MainController(Model model, FrameManager fm) {
		this.model = model;
		this.fm = fm;
		
		SessionManager session = new SessionManager(null, this.fm, this.model);
		session.loginPage();
	}

}
