package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.domain.Model;
import model.domain.Outfit;
import model.domain.User;
import model.utilities.Observer;
import model.utilities.Subject;

public class StatisticsFrame extends JFrame implements Observer {

	private static final long serialVersionUID = -4853864434524144396L;
	private Subject sub;
	private FrameManager fm;

	private JPanel mainPanel;
	private JPanel leftSide;
	private JPanel content;

	private JButton homePageButton;
	private JButton profilePageButton;
	private JButton outfitsPageButton;
	private JButton allUsersPageButton;
	private JButton logoutButton;

	public StatisticsFrame(FrameManager fm, Model model) {
		this.fm = fm;
		this.sub = model;

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 2));

		JPanel leftSide = new JPanel();
		leftSide.setLayout(new GridBagLayout());
		leftSide.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.leftSide = leftSide;

		JPanel content = new JPanel();
		this.content = content;

		mainPanel.add(leftSide);
		mainPanel.add(content);
		this.mainPanel = mainPanel;

		setLeftSide();
		setContent();
		getFrameManager().setNewPanel(mainPanel, "statistics");
	}

	public void setLeftSide() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);

		JLabel titleLabel = new JLabel("Outfit Rating MVC", JLabel.CENTER);
		titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		titleLabel.setFont(new Font(titleLabel.getFont().getName(), titleLabel.getFont().getStyle(), 30));

		JLabel pageLabel = new JLabel("Statistics", JLabel.CENTER);
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
		this.allUsersPageButton = allUsersPageButton;

		JButton statisticsPageButton = new JButton("Statistics");
		statisticsPageButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		statisticsPageButton.setPreferredSize(new Dimension(100, 50));
		statisticsPageButton.setEnabled(false);

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

	public void setContent() {
		Model model = (Model) sub;
		Outfit mostLikedOutfit = model.getMostLikedOutfit();
		String mostLiked = mostLikedOutfit.getBrand() + " " + mostLikedOutfit.getType() + " ("
				+ mostLikedOutfit.getLikes() + " likes)";
		Outfit mostDislikedOutfit = model.getMostDislikedOutfit();
		String mostDisliked = mostDislikedOutfit.getBrand() + " " + mostDislikedOutfit.getType() + " ("
				+ mostDislikedOutfit.getDislikes() + " dislikes)";
		User mostFollowedUser = model.getMostFollowedUser();
		String mostFollowed = mostFollowedUser.getUsername() + " (" + mostFollowedUser.getFollowers().size()
				+ " followers)";

		content.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(50, 5, 5, 5);

		JLabel mostLikedLabel = new JLabel("Most liked outfit: " + mostLiked);
		mostLikedLabel.setFont(new Font(mostLikedLabel.getFont().getName(), mostLikedLabel.getFont().getStyle(), 15));
		JLabel mostDislikedLabel = new JLabel("Most disliked outfit: " + mostDisliked);
		mostDislikedLabel
				.setFont(new Font(mostDislikedLabel.getFont().getName(), mostDislikedLabel.getFont().getStyle(), 15));
		JLabel mostFollowedLabel = new JLabel("Most followed user: " + mostFollowed);
		mostFollowedLabel
				.setFont(new Font(mostFollowedLabel.getFont().getName(), mostFollowedLabel.getFont().getStyle(), 15));

		content.removeAll();
		content.add(mostLikedLabel, gbc);
		content.add(mostDislikedLabel, gbc);
		content.add(mostFollowedLabel, gbc);
		getFrameManager().setNewPanel(mainPanel, "statistics");
	}

	public void addAllUsersActionListener(ActionListener actionListener) {
		allUsersPageButton.addActionListener(actionListener);
	}
	
	public void addOpenUserActionListener(ActionListener actionListener) {
		profilePageButton.addActionListener(actionListener);
	}

	public void addOutfitsActionListener(ActionListener actionListener) {
		outfitsPageButton.addActionListener(actionListener);
	}

	public void addHomeActionListener(ActionListener actionListener) {
		homePageButton.addActionListener(actionListener);
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

	@Override
	public void update() {
		setContent();
	}

	@Override
	public void addSubject(Subject sub) {
		this.sub = sub;
	}

	@Override
	public void removeSubject(Subject sub) {
		this.sub = null;
	}
}
