package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.domain.Model;
import view.LoginFrame;

public class LoginController {

	private SessionManager session;
	private Model model;
	private LoginFrame view;

	public LoginController(Model model, LoginFrame view, SessionManager session) {
		this.model = model;
		this.view = view;
		this.session = session;
		
		view.addLoginActionListener(new LoginListener());
	}
	
    class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = "";
            String password = "";
            try {
            	username = view.getUsername();
            	password = String.valueOf(view.getPassword());
            	session.setCurrentUser(model.login(username, password));
                session.homePage();
            } catch (IllegalArgumentException e1) {
            	view.showMessage(e1.getMessage());
            } catch (IllegalStateException e2) {
            	view.showMessage(e2.getMessage());
            } 
        }
    }
}
