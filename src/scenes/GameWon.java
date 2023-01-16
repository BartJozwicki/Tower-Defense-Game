package scenes;

import static Main.GameStates.MENU;
import static Main.GameStates.SetGameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Main.Main;
import helper.LoadSave;
import userInterface.Button;

public class GameWon extends GameScene implements SceneMethods {

	private Button bMenu, bQuit;
	private BufferedImage wizardImg;
	private BufferedImage coinImg;
	private BufferedImage crownImg;
	private BufferedImage cupImg;
	private BufferedImage deadBoss;
	
	private int[][] lvl;
	
	public GameWon(Main game) {
		
		super(game);
		lvl = LoadSave.GetLevelDataE("editView");
		loadImages();
		initButtons();
		
	}





	private void loadImages() {
		
		wizardImg = LoadSave.getSpriteAtlas().getSubimage(32 * 3, 32 * 3, 32, 32);
		coinImg = LoadSave.getSpriteAtlas().getSubimage(32 * 8, 32 * 3, 32, 32);
		crownImg = LoadSave.getSpriteAtlas().getSubimage(32 * 7, 32 * 3, 32, 32);
		cupImg = LoadSave.getSpriteAtlas().getSubimage(32 * 6, 32 * 3, 32, 32);
		deadBoss = LoadSave.getSpriteAtlas().getSubimage(32*5, 32 * 3, 32, 32);
		
	}





	public void update() {
	
	}

	
	@Override
	public void render(Graphics g) {
		drawLevel(g);
		bMenu.draw(g);
		bQuit.draw(g);
		drawDecorations(g);
		drawWizzardText(g);
		
	}
	
	private void drawWizzardText(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		//g.fillRect(360, 280, 120, 50);
		//g.fillOval(360, 280, 120, 50);
		
		g.setColor(Color.black);
		g.setFont(new Font("Consolas", Font.BOLD, 15));
		
		g.drawString("Thank you peasant!", 310, 160);
		g.drawString("Your courage and bravery defended our", 310, 180);
		g.drawString("beautiful village from this foul", 310, 200);
		g.drawString("creatures. There you go, pick ", 310, 220);
		g.drawString("something nice for your effort!", 310, 240);

	}





	private void drawDecorations(Graphics g) {
		
		g.drawImage(wizardImg, 280, 300, 96, 96, null);
		
		g.drawImage(crownImg, 260, 235, 96, 96, null);
		


		
		for(int i = 0; i < 14; i++) {
		g.drawImage(coinImg,  10+ i * 50, 400 + 100, 32, 32, null);
		}
		
		for(int i = 0; i < 14; i++) {
		g.drawImage(cupImg, i * 50 - 10, 600, 64, 64, null);
		}
		
		g.drawImage(deadBoss, 100, 200, 96, 96, null);
		
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

	
	private void initButtons() {

		bMenu = new Button("Menu", 10, 725, 100, 30);
		bQuit = new Button("Quit", 10, 760, 100, 30);
	}
	
	@Override
	public void mouseClicked(int x, int y) {

		if (bMenu.getBounds().contains(x, y)) {
			SetGameState(MENU);
		}
		if (bQuit.getBounds().contains(x, y)) {
			System.exit(y);
		} else {
			SetGameState(MENU);
		}
		
	}

	@Override
	public void leftClick() {

		
	}

	@Override
	public void mouseMoved(int x, int y) {
		
		bMenu.setMouseOver(false);
		bQuit.setMouseOver(false);
		
		if (bMenu.getBounds().contains(x, y)) {
			bMenu.setMouseOver(true);
			
			
		}
		if (bQuit.getBounds().contains(x, y)) {
			bQuit.setMouseOver(true);
		}
		
	}

	@Override
	public void mousePressed(int x, int y) {

		if (bMenu.getBounds().contains(x, y)) {
			bMenu.setMousePressed(true);
		}
		if(bQuit.getBounds().contains(x, y)) {
			bQuit.setMouseOver(true);
		}
		
	}

	@Override
	public void mouseReleased(int x, int y) {
		bMenu.resetBooleans();
		bQuit.resetBooleans();
		
	}

	@Override
	public void mouseDragged(int x, int y) {

		
	}

}
