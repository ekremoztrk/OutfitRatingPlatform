package controller;

import model.domain.Model;
import model.domain.User;
import view.MainFrame;

public class OutfitRatingController {

	private User currentUser;
	private Model model;
	private MainFrame view;

	public OutfitRatingController(Model model, MainFrame view) {
		this.model = model;
		this.view = view;
	}
}
