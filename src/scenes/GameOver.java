package scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Main.Main;
import helper.LoadSave;
import userInterface.Button;
import static Main.GameStates.*;
public class GameOver extends GameScene implements SceneMethods{

	private BufferedImage skullImg;
	private Button bReplay, bMenu;
	
	public GameOver(Main game) {
		super(game);
		initButtons();
		
	}

	private void initButtons() {
		int w = 150;
		int h = w / 3;
		int x = 640 / 2 - w / 2;
		int y = 150;
		int yOffset = 100;
		
		skullImg = LoadSave.getHeartsAtlas().getSubimage(32 * 8, 32 * 2, 32, 32);
		
		bMenu = new Button("Menu", x, y, w, h);
		bReplay = new Button("Play Again", x, y + yOffset, w, h);
		
	}

	@Override
	public void render(Graphics g) {
	
		
     bMenu.draw(g);
     bReplay.draw(g);
     drawSkulls(g);
 	
     g.setColor(Color.red);
     g.setFont(new Font("Consolas", Font.BOLD, 30));
     g.drawString("Game Over", 243, 70);
		
	}

	private void drawSkulls(Graphics g) {
		
		g.drawImage(skullImg, 50, 100, 64, 64, null);
		g.drawImage(skullImg, 150, 60, 64, 64, null);
		g.drawImage(skullImg, 500, 100, 64, 64, null);
		
		g.drawImage(skullImg, 300, 500, 64, 64, null);
		g.drawImage(skullImg, 10, 300, 64, 64, null);
		g.drawImage(skullImg, 550, 300, 64, 64, null);
		g.drawImage(skullImg, 250, 600, 64, 64, null);
		g.drawImage(skullImg, 350, 650, 64, 64, null);
		g.drawImage(skullImg, 70, 600, 64, 64, null);
		g.drawImage(skullImg, 80, 390, 64, 64, null);
		
		g.drawImage(skullImg, 500, 550, 64, 64, null);
		g.drawImage(skullImg, 450, 450, 64, 64, null);
		
		g.drawString("If you find the game too difficult, you can always change the difficulty level in settings" , 200, 350);
	}

	public void update() {
		
	}
	
	@Override
	public void mouseClicked(int x, int y) {

		if(bMenu.getBounds().contains(x, y)) {
			game.getPlay().resetEverything();
			SetGameState(MENU);
		}
		else if(bReplay.getBounds().contains(x, y))
			replayGame();
	}

	private void replayGame() {
		game.getPlay().resetEverything();
		SetGameState(PLAYING);
		
	}

	@Override
	public void leftClick() {

		
	}

	@Override
	public void mouseMoved(int x, int y) {
		bMenu.setMouseOver(false);
		bReplay.setMouseOver(false);
		
		if(bMenu.getBounds().contains(x, y))
			bMenu.setMouseOver(true);
		else if(bReplay.getBounds().contains(x, y))
			bReplay.setMouseOver(true);
		
	}

	@Override
	public void mousePressed(int x, int y) {
		
		if(bMenu.getBounds().contains(x, y))
			bMenu.setMousePressed(true);
		else if(bReplay.getBounds().contains(x, y))
			bReplay.setMousePressed(true);
	}

	@Override
	public void mouseReleased(int x, int y) {
		
		bMenu.resetBooleans();
		bReplay.resetBooleans();
	}

	@Override
	public void mouseDragged(int x, int y) {
		
		
	}
}
