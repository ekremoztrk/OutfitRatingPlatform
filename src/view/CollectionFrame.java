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
import model.domain.Collection;
import model.domain.Outfit;
import model.domain.User;
import model.utilities.Observer;
import model.utilities.Subject;

public class CollectionFrame extends JFrame implements Observer {

	private static final long serialVersionUID = -4853864434524144396L;
	private Subject collection;
	private FrameManager fm;
	private User currentUser;

	private JPanel mainPanel;
	private JPanel leftSide;
	private JPanel content;

	private JButton addOutfitButton;

	private JButton profilePageButton;
	private JButton homePageButton;
	private JButton outfitsPageButton;
	private JButton allUsersPageButton;
	private JButton statisticsPageButton;
	private JButton logoutButton;

	private List<JButton> outfitButtons;
	private List<JButton> removeOutfitButtons;
	private List<OutfitPanel> outfitPanels;

	public CollectionFrame(FrameManager fm, User currentUser, Collection collection) {
		this.fm = fm;
		this.currentUser = currentUser;
		this.outfitButtons = new ArrayList<>();
		this.collection = collection;
		this.removeOutfitButtons = new ArrayList<>();
		this.outfitPanels = new ArrayList<>();

		collection.register(this);

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
		getFrameManager().setNewPanel(mainPanel, "collection");
	}

	public void setLeftSide() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);

		JLabel titleLabel = new JLabel("Outfit Rating MVC", JLabel.CENTER);
		titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		titleLabel.setFont(new Font(titleLabel.getFont().getName(), titleLabel.getFont().getStyle(), 30));

		JLabel pageLabel = new JLabel("Collection", JLabel.CENTER);
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
		Collection collection = (Collection) this.collection;
		List<Outfit> outfits = collection.getOutfits();

		JButton addOutfitButton = new JButton("Add Outfit To Collection");
		addOutfitButton.setPreferredSize(new Dimension(200, 50));
		addOutfitButton.setBackground(Color.PINK);
		this.addOutfitButton = addOutfitButton;

		JPanel cards = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(10, 10, 10, 10);

		JLabel collectionName = new JLabel("Collection Name: " + collection.getName());
		JLabel creatorName = new JLabel("Creator Name: " + collection.getCreator().getUsername());
		JLabel creationDate = new JLabel("Creation Date: " + collection.getCreationDate());

		cards.add(collectionName, gbc);
		cards.add(creatorName, gbc);
		cards.add(creationDate, gbc);
		if (collection.getCreator().equals(currentUser)) {
			cards.add(addOutfitButton, gbc);
		}

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
			if(currentUser.equals(collection.getCreator())){
				JButton removeOutfitButton = new JButton("🗑");
				panel.add(removeOutfitButton);
				removeOutfitButtons.add(removeOutfitButton);
				OutfitPanel outfitPanel = new OutfitPanel(outfit,removeOutfitButton);
				outfitPanels.add(outfitPanel);
			}

			cards.add(panel, gbc);
		}

		content.removeAll();
		content.add(new JScrollPane(cards));
		getFrameManager().setNewPanel(mainPanel, "collection");
	}

	public void addOpenOutfitActionListener(ActionListener actionListener, String outfitId) {
		for (JButton jButton : outfitButtons) {
			if (jButton.getName().equals(outfitId)) {
				jButton.addActionListener(actionListener);
			}
		}
	}
	public void addRemoveOutfitFromCollectionActionListener(ActionListener actionListener, int outfitId) {
		for (OutfitPanel outfitPanel : outfitPanels) {
			if (outfitPanel.outfit.getId() == outfitId) {
				outfitPanel.removeButton.addActionListener(actionListener);
			}
		}
	}
	public void addAddOutfitActionListener(ActionListener actionListener) {
		addOutfitButton.addActionListener(actionListener);
	}
	
	public void addAllUsersActionListener(ActionListener actionListener) {
		allUsersPageButton.addActionListener(actionListener);
	}

	public void addOpenProfileActionListener(ActionListener actionListener) {
		profilePageButton.addActionListener(actionListener);
	}

	public void addOpenOutfitsActionListener(ActionListener actionListener) {
		outfitsPageButton.addActionListener(actionListener);
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

	public Object showInputMessage(String message, Object[] choices) {
		return JOptionPane.showInputDialog(getFrameManager().getFrame(), message, null, JOptionPane.QUESTION_MESSAGE,
				null, choices, choices[0]);
	}

	@Override
	public void update() {
		setCards();
	}

	@Override
	public void addSubject(Subject sub) {
		this.collection = sub;
	}

	@Override
	public void removeSubject(Subject sub) {
		this.collection = null;
	}

	private class OutfitPanel{
		private Outfit outfit;
		private JButton removeButton;

		public OutfitPanel(Outfit outfit, JButton removeButton) {
			this.outfit = outfit;
			this.removeButton = removeButton;
		}
	}
}
