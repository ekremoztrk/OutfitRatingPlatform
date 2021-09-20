package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import model.domain.Model;
import model.domain.User;

public class AllUsersFrame extends JFrame {

	private static final long serialVersionUID = -4853864434524144396L;
	private Model model;
	private FrameManager fm;
	private User currentUser;

	private JPanel mainPanel;
	private JPanel leftSide;
	private JPanel content;

	private JButton profilePageButton;
	private JButton homePageButton;
	private JButton outfitsPageButton;
	private JButton statisticsPageButton;
	private JButton logoutButton;

	private List<JButton> userButtons;
	private List<JButton> collectionButtons;

	public AllUsersFrame(Model model, FrameManager fm, User currentUser) {
		this.currentUser = currentUser;
		this.fm = fm;
		this.userButtons = new ArrayList<>();
		this.collectionButtons = new ArrayList<>();
		this.model = model;

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 2));

		JPanel leftSide = new JPanel();
		leftSide.setLayout(new GridBagLayout());
		leftSide.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.leftSide = leftSide;

		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		this.content = content;

		mainPanel.add(this.leftSide);
		mainPanel.add(this.content);
		this.mainPanel = mainPanel;

		setLeftSide();
		setCards();
		getFrameManager().setNewPanel(mainPanel, "all_users");
	}

	public void setLeftSide() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);

		JLabel titleLabel = new JLabel("Outfit Rating MVC", JLabel.CENTER);
		titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		titleLabel.setFont(new Font(titleLabel.getFont().getName(), titleLabel.getFont().getStyle(), 30));

		JLabel pageLabel = new JLabel("All Users", JLabel.CENTER);
		pageLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		pageLabel.setFont(new Font(pageLabel.getFont().getName(), pageLabel.getFont().getStyle(), 20));

		JButton homePageButton = new JButton("Homepage");
		homePageButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		homePageButton.setPreferredSize(new Dimension(100, 50));
		this.homePageButton = homePageButton;

		JButton profilePageButton = new JButton("My Profile");
		profilePageButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		profilePageButton.setPreferredSize(new Dimension(100, 50));
		this.profilePageButton = profilePageButton;

		JButton outfitsPageButton = new JButton("List All Outfits");
		outfitsPageButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		outfitsPageButton.setPreferredSize(new Dimension(100, 50));
		this.outfitsPageButton = outfitsPageButton;

		JButton allUsersPageButton = new JButton("List All Users");
		allUsersPageButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		allUsersPageButton.setPreferredSize(new Dimension(100, 50));
		allUsersPageButton.setEnabled(false);

		JButton statisticsPageButton = new JButton("Statistics");
		statisticsPageButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		statisticsPageButton.setPreferredSize(new Dimension(100, 50));
		this.statisticsPageButton = statisticsPageButton;

		JButton logoutButton = new JButton("Logout");
		logoutButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		logoutButton.setPreferredSize(new Dimension(100, 50));
		this.logoutButton = logoutButton;

		leftSide.add(titleLabel, gbc);
		leftSide.add(pageLabel, gbc);
		leftSide.add(homePageButton, gbc);
		leftSide.add(profilePageButton, gbc);
		leftSide.add(outfitsPageButton, gbc);
		leftSide.add(allUsersPageButton, gbc);
		leftSide.add(statisticsPageButton, gbc);
		leftSide.add(logoutButton, gbc);
	}

	public void setCards() {
		List<User> users = new ArrayList<>(model.getAllUsers());
		users.remove(currentUser);

		JPanel cards = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.insets = new Insets(20, 10, 10, 10);
		for (User user : users) {
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			String userName = user.getUsername();
			JButton userButton = new JButton(userName);
			userButtons.add(userButton);
			panel.setPreferredSize(new Dimension(350, 70));
			userButton.setPreferredSize(new Dimension(150, 50));
			panel.add(userButton);
			cards.add(panel, gbc);
		}

		content.removeAll();
		content.add(new JScrollPane(cards));
		getFrameManager().setNewPanel(mainPanel, "all_users");
	}

	public void addOpenCollectionActionListener(ActionListener actionListener, String collectionName) {
		for (JButton jButton : collectionButtons) {
			if (jButton.getText().equals(collectionName)) {
				jButton.addActionListener(actionListener);
			}
		}
	}

	public void addOpenUserActionListener(ActionListener actionListener, String userName) {
		for (JButton jButton : userButtons) {
			if (jButton.getText().equals(userName)) {
				jButton.addActionListener(actionListener);
			}
		}
	}

	public void addOpenProfileActionListener(ActionListener actionListener) {
		profilePageButton.addActionListener(actionListener);
	}

	public void addHomeActionListener(ActionListener actionListener) {
		homePageButton.addActionListener(actionListener);
	}

	public void addOutfitsActionListener(ActionListener actionListener) {
		outfitsPageButton.addActionListener(actionListener);
	}

	public void addStatisticsActionListener(ActionListener actionListener) {
		statisticsPageButton.addActionListener(actionListener);
	}

	public void addLogoutActionListener(ActionListener actionListener) {
		logoutButton.addActionListener(actionListener);
	}

	public FrameManager getFrameManager() {
		return this.fm;
	}

	public void showMessage(String message) {
		JOptionPane.showMessageDialog(getFrameManager().getFrame(), message);
	}
}
