package scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static Main.GameStates.*;
import static helper.Constants.Enemies.*;
import Main.Main;
import helper.LoadSave;
import userInterface.Button;
import userInterface.Slider;

public class Settings extends GameScene implements SceneMethods {

	private BufferedImage wizardImg;
	private Slider sSound;
	private int difficultyLevel;
	private Button bMenu, bEasy, bNormal, bHard;
	private int[][] lvl;

	public Settings(Main game) {

		super(game);
		initButtons();
		difficultyLevel = NORMAL;
		sSound = new Slider("Volume", 100, 100, 400, 20);
		lvl = LoadSave.GetLevelDataE("editView");
		wizardImg = LoadSave.getSpriteAtlas().getSubimage(32 * 3, 32 * 3, 32, 32);

	}

	public void update() {
		// There was a plan....
	}

	@Override
	public void render(Graphics g) {

		drawLevel(g);

		drawWizzard(g);
		sSound.draw(g);
		bMenu.draw(g);

		bEasy.drawSettingsB(g, EASY);
		bNormal.drawSettingsB(g, NORMAL);
		bHard.drawSettingsB(g, HARD);

		drawText(g);

	}

	private void drawWizzard(Graphics g) {

		// Simple decoration
		if (difficultyLevel == EASY) {
			g.drawImage(wizardImg, 130, 255, 64, 64, null);
		} else if (difficultyLevel == NORMAL) {
			g.drawImage(wizardImg, 285, 255, 64, 64, null);
		} else {
			g.drawImage(wizardImg, 440, 255, 64, 64, null);
		}

	}

	private void drawText(Graphics g) {
		g.setColor(Color.black);
		g.setFont(new Font("MV Boli", Font.BOLD, 30));
		g.drawString("Difficulty", 245, 200);

	}

	private void initButtons() {

		bMenu = new Button("Menu", 10, 760, 100, 30);
		bEasy = new Button("Easy", 100, 230, 100, 30);
		bNormal = new Button("Normal", 257, 230, 100, 30);
		bHard = new Button("Hard", 410, 230, 100, 30);

	}

	private void drawLevel(Graphics g) {

		for (int y = 0; y < lvl.length; y++) {
			for (int x = 0; x < lvl[y].length; x++) {
				int id = lvl[y][x];
				g.drawImage(getSprite(id), x * 32, y * 32, null);
				if (isAnimation(id)) {
					g.drawImage(getSprite(id, animationIndex), x * 32, y * 32, null);
				} else {
					g.drawImage(getSprite(id), x * 32, y * 32, null);
				}

			}
		}

	}

	@Override
	public void mouseClicked(int x, int y) {

		if (bMenu.getBounds().contains(x, y)) {
			SetGameState(MENU);
		}

		if (sSound.getBounds().contains(x, y)) {
			sSound.setMousePos(x, y);
			sSound.setMouseClicked(true);
			game.getSound().setVolume(sSound.getFilling());

		}
		if (bEasy.getBounds().contains(x, y)) {
			difficultyLevel = EASY;
			bEasy.setLockColor(EASY);
			bNormal.setLockColor(-1);
			bHard.setLockColor(-1);

		} else if (bNormal.getBounds().contains(x, y)) {
			difficultyLevel = NORMAL;
			bEasy.setLockColor(-1);
			bNormal.setLockColor(NORMAL);
			bHard.setLockColor(-1);

		} else if (bHard.getBounds().contains(x, y)) {
			difficultyLevel = HARD;
			bEasy.setLockColor(-1);
			bNormal.setLockColor(-1);
			bHard.setLockColor(HARD);

		}

	}

	@Override
	public void mouseMoved(int x, int y) {

		bMenu.setMouseOver(false);
		bEasy.setMouseOver(false);

		bNormal.setMouseOver(false);
		bHard.setMouseOver(false);
		sSound.setMouseOver(false);

		if (bMenu.getBounds().contains(x, y)) {
			bMenu.setMouseOver(true);
		} else if (bEasy.getBounds().contains(x, y)) {
			bEasy.setMouseOver(true);
		} else if (bNormal.getBounds().contains(x, y)) {
			bNormal.setMouseOver(true);
		} else if (bHard.getBounds().contains(x, y)) {
			bHard.setMouseOver(true);
		} else if (sSound.getBounds().contains(x, y)) {
			sSound.setMouseOver(true);
		}
	}

	@Override
	public void mousePressed(int x, int y) {

		if (bMenu.getBounds().contains(x, y)) {
			bMenu.setMousePressed(true);
		} else if (bEasy.getBounds().contains(x, y)) {
			bEasy.setMousePressed(true);
		} else if (bNormal.getBounds().contains(x, y)) {
			bNormal.setMousePressed(true);
		} else if (bHard.getBounds().contains(x, y)) {
			bHard.setMousePressed(true);
		} else if (sSound.getBounds().contains(x, y)) {
			sSound.setMousePressed(true);
		}

	}

	@Override
	public void mouseReleased(int x, int y) {
		bMenu.resetBooleans();
		bEasy.resetBooleans();
		bNormal.resetBooleans();
		bHard.resetBooleans();
		sSound.resetBooleans();

	}

	@Override
	public void mouseDragged(int x, int y) {

		if (sSound.getBounds().contains(x, y)) {
			sSound.setMouseDragged(true);
			sSound.setMousePos(x, y);
			game.getSound().setVolume(sSound.getFilling());
		}

	}

	@Override
	public void leftClick() {

	}

	public int getDifficultyLevel() {

		return this.difficultyLevel;
	}

}
