package scenes;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import Main.Main;
import helper.LoadSave;
import static Main.GameStates.*;

/* Class used to explain rules of the game */
public class Tutorial extends GameScene implements SceneMethods {

	private BufferedImage[] guides;
	private int tutorialScreen;

	public Tutorial(Main game) {
		super(game);

		guides = new BufferedImage[5];
		loadTutorialImages();
		tutorialScreen = 0;

	}

	public void update() {

	}

	private void loadTutorialImages() {

		for (int i = 0; i < 5; i++) {
			guides[i] = LoadSave.getTutorial(i);
		}

	}

	@Override
	public void render(Graphics g) {

		drawTutorial(g);

	}

	private void drawTutorial(Graphics g) {
		g.drawImage(guides[tutorialScreen], 0, 0, 640, 810, null);
	}

	private void nextTutorialScreen() {

		tutorialScreen++;

		if (tutorialScreen >= 5)
			tutorialScreen = 0;
	}

	@Override
	public void mouseClicked(int x, int y) {

	}

	@Override
	public void leftClick() {

	}

	@Override
	public void mouseMoved(int x, int y) {

	}

	@Override
	public void mousePressed(int x, int y) {

	}

	@Override
	public void mouseReleased(int x, int y) {

	}

	@Override
	public void mouseDragged(int x, int y) {

	}

	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			nextTutorialScreen();

		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			SetGameState(MENU);

		}
	}

}
