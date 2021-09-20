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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import model.domain.Model;
import model.domain.Outfit;

public class OutfitsFrame extends JFrame {

	private static final long serialVersionUID = -4853864434524144396L;
	private Model model;
	private FrameManager fm;

	private JPanel mainPanel;
	private JPanel leftSide;
	private JPanel content;

	private JButton profilePageButton;
	private JButton homePageButton;
	private JButton allUsersPageButton;
	private JButton statisticsPageButton;
	private JButton logoutButton;

	private List<JButton> outfitButtons;

	public OutfitsFrame(Model model, FrameManager fm) {
		this.fm = fm;
		this.outfitButtons = new ArrayList<>();
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
		getFrameManager().setNewPanel(mainPanel, "outfits");
	}

	public void setLeftSide() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);

		JLabel titleLabel = new JLabel("Outfit Rating MVC", JLabel.CENTER);
		titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		titleLabel.setFont(new Font(titleLabel.getFont().getName(), titleLabel.getFont().getStyle(), 30));

		JLabel pageLabel = new JLabel("All Outfits", JLabel.CENTER);
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
		outfitsPageButton.setEnabled(false);

		JButton allUsersPageButton = new JButton("List All Users");
		allUsersPageButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		allUsersPageButton.setPreferredSize(new Dimension(100, 50));
		this.allUsersPageButton = allUsersPageButton;

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
		Model model = (Model) this.model;
		List<Outfit> outfits = model.getAllOutfits();

		JPanel cards = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(10, 10, 10, 10);
		for (Outfit outfit : outfits) {
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			JButton outfitButton = new JButton();
			JLabel brand = new JLabel(outfit.getBrand().toString());
			JLabel color = new JLabel(outfit.getColor().toString());
			JLabel type = new JLabel(outfit.getType().toString());

			ImageIcon icon = outfit.getImage();
			if (icon == null) {
				outfitButton.setText("This outfit does not have an image.");
			}

			outfitButton.setName("" + outfit.getId());
			outfitButton.setIcon(icon);
			outfitButton.setBackground(java.awt.Color.WHITE);
			outfitButtons.add(outfitButton);

			panel.setPreferredSize(new Dimension(350, 400));
			outfitButton.setPreferredSize(new Dimension(350, 350));

			panel.add(outfitButton);
			panel.add(brand);
			panel.add(color);
			panel.add(type);

			cards.add(panel, gbc);
		}

		content.removeAll();
		content.add(new JScrollPane(cards));
		getFrameManager().setNewPanel(mainPanel, "outfits");
	}

	public void addOpenOutfitActionListener(ActionListener actionListener, String outfitId) {
		for (JButton jButton : outfitButtons) {
			if (jButton.getName().equals(outfitId)) {
				jButton.addActionListener(actionListener);
			}
		}
	}
	
	public void addAllUsersActionListener(ActionListener actionListener) {
		allUsersPageButton.addActionListener(actionListener);
	}
	
	public void addOpenProfileActionListener(ActionListener actionListener) {
		profilePageButton.addActionListener(actionListener);
	}

	public void addStatisticsActionListener(ActionListener actionListener) {
		statisticsPageButton.addActionListener(actionListener);
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
}
