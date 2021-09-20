package app;

import controller.MainController;
import model.domain.Model;
import view.FrameManager;
import view.MainFrame;

public class OutfitRating {
	FrameManager fm;
	FrameManager fm2;

	public OutfitRating() {
		this.fm = new FrameManager();
		this.fm2 = new FrameManager();
	}
	
	@SuppressWarnings("unused")
	public void start() {
		Model model = new Model();
        MainFrame view = new MainFrame(this.fm);
        MainController controller = new MainController(model, this.fm);
		MainController controller2 = new MainController(model, this.fm2);
	}
}