package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.domain.User;
import view.StatisticsFrame;

public class StatisticsController {

	private SessionManager session;
	private StatisticsFrame view;

	public StatisticsController(StatisticsFrame view, SessionManager session) {
		this.session = session;
		this.view = view;
		
		setSidebarListeners();
	}
	
	private void setSidebarListeners() {
		view.addHomeActionListener(new OpenHomeListener());
		view.addLogoutActionListener(new LogoutListener());
		view.addOutfitsActionListener(new OpenOutfitsListener());
		view.addAllUsersActionListener(new OpenAllUserListener());
		view.addOpenUserActionListener(new OpenUserListener(session.getCurrentUser()));
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
