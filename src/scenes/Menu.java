package scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Main.Main;
import helper.LoadSave;
import userInterface.Button;

import static Main.GameStates.*;

public class Menu extends GameScene implements SceneMethods {

	private BufferedImage[] charactersImgs;
	private Button bPlay, bEdit, bSettings, bQuit;
	private int[][] lvl;

	public Menu(Main game) {
		super(game);
		initButton();

		lvl = LoadSave.GetLevelDataE("editView");
		charactersImgs = new BufferedImage[5];
		loadCharactersImgs();

	}

	private void initButton() {

		int w = 150;
		int h = w / 3;
		int x = 640 / 2 - w / 2;
		int y = 150;
		int yOffset = 100;

		bPlay = new Button("Play", x, y, w, h);
		bEdit = new Button("Edit", x, y + yOffset, w, h);
		bSettings = new Button("Settings", x, y + yOffset * 2, w, h);
		bQuit = new Button("Quit", x, y + yOffset * 3, w, h);

	}

	private void loadCharactersImgs() {

		BufferedImage atlas = LoadSave.getSpriteAtlas();
		for (int i = 0; i < 4; i++) {
			charactersImgs[i] = atlas.getSubimage(32 * i, 32, 32, 32);
		}
		charactersImgs[4] = atlas.getSubimage(32 * 3, 3 * 32, 32, 32);
	}

	@Override
	public void render(Graphics g) {

		drawLevel(g);
		drawButtons(g);
		drawCharacters(g);
		drawTitle(g);

	}

	private void drawTitle(Graphics g) {

		g.setColor(Color.black);
		g.setFont(new Font("MV Boli", Font.BOLD, 60));
		g.drawString("Boj TD", 223, 660);

	}

	private void drawCharacters(Graphics g) {

		g.drawImage(charactersImgs[0], 100, 640, 64, 64, null);
		g.drawImage(charactersImgs[0], 50, 50, 64, 64, null);
		g.drawImage(charactersImgs[0], 70, 450, 64, 64, null);
		g.drawImage(charactersImgs[0], 580, 540, 64, 64, null);
		g.drawImage(charactersImgs[0], 90, 300, 64, 64, null);
		g.drawImage(charactersImgs[0], 450, 30, 64, 64, null);

		g.drawImage(charactersImgs[1], 180, 150, 64, 64, null);
		g.drawImage(charactersImgs[2], 300, 500, 64, 64, null);
		g.drawImage(charactersImgs[3], 400, 300, 64, 64, null);
		// g.drawImage(charactersImgs[4], 500, 640, 64, 64, null);
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

	private void drawButtons(Graphics g) {

		bPlay.draw(g);
		bEdit.draw(g);
		bSettings.draw(g);
		bQuit.draw(g);

	}

	@Override
	public void mouseClicked(int x, int y) {

		if (bPlay.getBounds().contains(x, y)) {
			SetGameState(PLAYING);
		}

		if (bSettings.getBounds().contains(x, y)) {
			SetGameState(SETTINGS);
		}

		if (bEdit.getBounds().contains(x, y)) {
			SetGameState(EDIT);
		}

		if (bQuit.getBounds().contains(x, y)) {
			System.exit(y);
		}

	}

	@Override
	public void mouseMoved(int x, int y) {

		bPlay.setMouseOver(false);
		bSettings.setMouseOver(false);
		bEdit.setMouseOver(false);
		bQuit.setMouseOver(false);

		if (bPlay.getBounds().contains(x, y)) {
			bPlay.setMouseOver(true);
		}

		if (bEdit.getBounds().contains(x, y)) {
			bEdit.setMouseOver(true);
		}

		if (bSettings.getBounds().contains(x, y)) {
			bSettings.setMouseOver(true);
		}

		if (bQuit.getBounds().contains(x, y)) {
			bQuit.setMouseOver(true);
		}
	}

	@Override
	public void mousePressed(int x, int y) {

		if (bPlay.getBounds().contains(x, y)) {
			bPlay.setMousePressed(true);
		}
		if (bSettings.getBounds().contains(x, y)) {
			bSettings.setMousePressed(true);
		}
		if (bQuit.getBounds().contains(x, y)) {
			bQuit.setMousePressed(true);
		}
		if (bEdit.getBounds().contains(x, y)) {
			bEdit.setMousePressed(true);
		}

	}

	@Override
	public void mouseReleased(int x, int y) {

		resetButton();

	}

	private void resetButton() {
		bPlay.resetBooleans();
		bSettings.resetBooleans();
		bQuit.resetBooleans();
		bEdit.resetBooleans();

	}

	@Override
	public void mouseDragged(int x, int y) {

	}

	@Override
	public void leftClick() {

	}
}
