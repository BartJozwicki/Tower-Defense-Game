package userInterface;

import static Main.GameStates.*;
import static Main.GameStates.SetGameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import helper.LoadSave;
import helper.Constants.Towers;
import objects.Tower;
import scenes.Play;

public class ActionBar extends Bar {

	private Button bMenu, bPause;
	private Play play;

	private Button[] towerButtons;
	private Button sellTower, upgradeTower;
	private Tower selectedTower; // Draw tower
	private Tower displayTower; // Display tower
	private BufferedImage[] hearts; //lives left

	private DecimalFormat formatter;
	
	
    private int lives;
	private int gold;
	private boolean showTowerCost;
	private boolean needMoreGold;
	private int towerCostType;

	public ActionBar(int x, int y, int width, int height, Play play) {
		super(x, y, width, height);
		this.play = play;
		initButtons();
		gold = 100;
		lives = 28;
		formatter = new DecimalFormat("0.0");
		hearts = new BufferedImage[30];
		needMoreGold = false;
		loadHearts();
	}



	private void initButtons() {
		bMenu = new Button("Menu", 2, 642, 100, 30);
		bPause = new Button("Pause", 2, 682, 100, 30);
		towerButtons = new Button[3];
		int w = 50;
		int h = 50;
		int xStart = 105;
		int yStart = 642;
		int xOffset = (int) (w * 1.05f);

		for (int i = 0; i < towerButtons.length; i++) {
			towerButtons[i] = new Button("", xStart + xOffset * i, yStart, w, h, i);
		}

		sellTower = new Button("Sell", 420, 702, 80, 25);
		upgradeTower = new Button("Upgrade", 545, 702, 80, 25);
	}

	public void removeOneLife() {
		lives--;
		if(lives <= 0)
			SetGameState(GAME_OVER);
	}
	
	public void resetEverything() {
		lives = 25;
		towerCostType = 0;
		showTowerCost = false;
		gold = 100;
		selectedTower = null;
		displayTower = null;
		
	}
	public void draw(Graphics g) {

		g.setColor(new Color(0, 123, 15));
		g.fillRect(x, y, width, height);

		drawButtons(g);

		drawWaveInfo(g);

		drawGoldAmount(g);

		if (showTowerCost) {
			drawTowerCost(g);
		}
		// DisplayedTower
		drawDisplayedTower(g);
		
		if(play.isGamePaused()) {
			g.setColor(Color.black);
			g.setFont(new Font("Consolas", Font.BOLD, 20));
			g.drawString("Game paused", 110, 790);
		}
		
		drawLivesLeft(g);
		
		
	}





	private void drawButtons(Graphics g) {

		bMenu.draw(g);
		bPause.draw(g);
		for (Button b : towerButtons) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(b.x, b.y, b.width, b.height);
			g.drawImage(play.getTowerManager().getTowerImages()[b.getID()], b.x, b.y, b.width, b.height, null);

			drawButtonResponse(g, b);
		}

	}

	private void drawTowerCost(Graphics g) {

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(280, 650, 120, 50);

		g.setColor(Color.black);
		g.drawRect(280, 650, 120, 50);

		g.drawString("" + getTowerCostName(), 285, 670);
		g.drawString("Cost: " + getTowerCostCost() + "g", 285, 695);

		// if not enough money
		if (isCurrentCostMoreThanCurrentGold() || needMoreGold) {
			g.setColor(Color.red);
			g.drawString("Need more gold!", 245, 725);
			
		}

	}

	// Tower info action-bar
	private void drawDisplayedTower(Graphics g) {

		if (displayTower != null) {

			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(410, 645, 220, 85);
			g.setColor(Color.BLACK);
			g.drawRect(410, 645, 220, 85);
			g.drawRect(420, 650, 50, 50);
			g.drawImage(play.getTowerManager().getTowerImages()[displayTower.getTowerType()], 420, 650, 50, 50, null);

			g.setFont(new Font("Consolas", Font.BOLD, 15));
			g.drawString("" + Towers.GetName(displayTower.getTowerType()), 490, 660);
			g.drawString("ID: " + displayTower.getId(), 490, 680);
			g.drawString("Tier: " + displayTower.getTier(), 560, 660);

			drawSelectedTowerRange(g);
			drawSelectedTowerBorder(g);

			// sell
			sellTower.draw(g);
			drawButtonResponse(g, sellTower);

			// upgrade
			if (displayTower.getTier() < 3) {
				upgradeTower.draw(g);
				drawButtonResponse(g, upgradeTower);
			}
			if (sellTower.isMouseOver()) {
				g.setColor(Color.red);
				g.drawString("Sell for: " + getSellAmount(selectedTower) + "g", 490, 700);
			} else if (upgradeTower.isMouseOver()) {
				g.setColor(Color.blue);
				g.drawString("Upgrade for: " + getUpgradeCost(selectedTower) + "g", 490, 700);
			}

		}

	}
	
	private void drawLivesLeft(Graphics g) {
		g.setFont(new Font("Consolas", Font.BOLD, 20));
		g.setColor(Color.black);
		g.drawImage(hearts[28-lives], 179, 725, 38, 38, null);
		g.drawString("Lives: " + lives, 110, 750);
		
	}

	private int getSellAmount(Tower selectedTower) {

		int upgradeCost = (selectedTower.getTier() - 1) * getUpgradeCost(selectedTower);
		upgradeCost /= 2;
		
		return helper.Constants.Towers.GetTowerCost(selectedTower.getTowerType()) / 2 + upgradeCost;
	}

	private void drawWaveInfo(Graphics g) {

		g.setFont(new Font("Consolas", Font.BOLD, 20));
		drawWaveTimerInfo(g);
		drawEnemiesLeftInfo(g);
		drawWavesLeftInfo(g);

	}

	private void upgradeTowerClicked() {

	
		if(getUpgradeCost(displayTower) <= gold) {
		   gold -= getUpgradeCost(displayTower);
		   play.upgradeTower(displayTower);
		}else {
			needMoreGold = true;
		}
			

	}

	private int getUpgradeCost(Tower displayedTower) {
		return (int) (helper.Constants.Towers.GetTowerCost(displayedTower.getTowerType()) * 0.3f);
	}

	private boolean isCurrentCostMoreThanCurrentGold() {

		return gold < getTowerCostCost();
	}
	
	private void loadHearts() {
		int imgCnt = 0;
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 10; j++) {
			hearts[imgCnt] = LoadSave.getHeartsAtlas().getSubimage(j*32, i*32, 32, 32);
			imgCnt++;
			}
	}

	private int getTowerCostCost() {

		return helper.Constants.Towers.GetTowerCost(towerCostType);
	}

	private String getTowerCostName() {

		return helper.Constants.Towers.GetName(towerCostType);
	}

	private void drawGoldAmount(Graphics g) {
		g.setFont(new Font("Consolas", Font.BOLD, 20));
		g.setColor(Color.black);
		g.drawString("Gold : " + gold + "g", 110, 725);
	}

	private void drawEnemiesLeftInfo(Graphics g) {
		int remaining = play.getEnemyManager().numberOfEnemiesAlive();
		g.drawString("Enemies Left: " + remaining, 425, 770);

	}

	private void drawWavesLeftInfo(Graphics g) {

		int current = play.getWaveManager().getWaveIndex();
		int size = play.getWaveManager().getWaves().size();

		g.drawString("Wave " + (current + 1) + " / " + size, 425, 790);
	}

	private void drawWaveTimerInfo(Graphics g) {
		if (play.getWaveManager().isWaveTimerStarted()) {

			float timeLeft = play.getWaveManager().getTimeLeft();
			String formatedText = formatter.format(timeLeft);
			g.setColor(Color.black);
			g.drawString("Time left: " + formatedText, 425, 750);

		}
	}

	private void sellTowerClicked() {

		play.removeTower(displayTower);
		int upgradeCost = (selectedTower.getTier() - 1) * getUpgradeCost(selectedTower);
		upgradeCost /= 2;
		gold += helper.Constants.Towers.GetTowerCost(selectedTower.getTowerType()) / 2 + upgradeCost;
		displayTower = null;

	}

	private void drawSelectedTowerRange(Graphics g) {

		g.drawOval(displayTower.getX() + 16 - (int) displayTower.getRange(),
				displayTower.getY() + 16 - (int) displayTower.getRange(), (int) displayTower.getRange() * 2,
				(int) displayTower.getRange() * 2);

	}

	private void drawSelectedTowerBorder(Graphics g) {

		g.setColor(Color.MAGENTA);
		g.drawRect(displayTower.getX(), displayTower.getY(), 32, 32);

	}

	public void displayTower(Tower t) {

		displayTower = t;

	}

	private void setGameState() {

		play.setGamePaused(!play.isGamePaused());
		if(play.isGamePaused())
			bPause.setText("Start");
		else
			bPause.setText("Pause");
		
	}
	
	private boolean isGoldEnoughtForTower(int towerType) {

		return gold >= helper.Constants.Towers.GetTowerCost(towerType);
	}

	public void mouseClicked(int x, int y) {

		if (bMenu.getBounds().contains(x, y)) {
			SetGameState(MENU);
		} else if (bPause.getBounds().contains(x, y)) {
			setGameState();
		} else {

			if (selectedTower != null) {

				if (sellTower.getBounds().contains(x, y)) {
					sellTowerClicked();
					return;
				} else if (upgradeTower.getBounds().contains(x, y) && displayTower.getTier() < 3) {
					upgradeTowerClicked();
					return;
				}

			}
		}

		for (Button b : towerButtons) {
			if (b.getBounds().contains(x, y)) {
				if (!isGoldEnoughtForTower(b.getID()))
					return;

				selectedTower = new Tower(0, 0, -1, b.getID());
				play.setSelectedTower(selectedTower);
				return;
			}
		}
	}



	public void mouseMoved(int x, int y) {

		bMenu.setMouseOver(false);
		bPause.setMouseOver(false);
		needMoreGold = false;

		showTowerCost = false;
		sellTower.setMouseOver(false);
		upgradeTower.setMouseOver(false);

		for (Button b : towerButtons)
			b.setMouseOver(false);

		if (bMenu.getBounds().contains(x, y)) {
			bMenu.setMouseOver(true);
		} else if (bPause.getBounds().contains(x, y)) {
			bPause.setMouseOver(true);
		} else {
			if (displayTower != null) {
				if (sellTower.getBounds().contains(x, y)) {
					sellTower.setMouseOver(true);
					return;
				} else if (upgradeTower.getBounds().contains(x, y) && displayTower.getTier() < 3) {
					upgradeTower.setMouseOver(true);
					return;

				}

			}
		}
		for (Button b : towerButtons) {
			if (b.getBounds().contains(x, y)) {
				b.setMouseOver(true);
				showTowerCost = true;
				towerCostType = b.getID();
				return;
			}
		}
	}

	public void mousePressed(int x, int y) {

		if (bMenu.getBounds().contains(x, y)) {
			bMenu.setMousePressed(true);
		} else if (bPause.getBounds().contains(x, y)) {
			bPause.setMousePressed(true);
		} else {

			if (displayTower != null) {

				if (sellTower.getBounds().contains(x, y)) {
					sellTower.setMousePressed(true);
					return;
				} else if (upgradeTower.getBounds().contains(x, y) && displayTower.getTier() < 3) {
					upgradeTower.setMousePressed(true);
					return;

				}

			}

			for (Button b : towerButtons) {
				if (b.getBounds().contains(x, y)) {
					b.setMousePressed(true);
					return;
				}
			}
		}
	}

	public void mouseReleased(int x, int y) {

		resetButton();
		for (Button b : towerButtons) {
			{
				b.resetBooleans();
			}
			sellTower.resetBooleans();
			upgradeTower.resetBooleans();
		}
	}

	private void resetButton() {
		bMenu.resetBooleans();
		bPause.resetBooleans();
	}

	public void payForTower(int towerType) {
		this.gold -= helper.Constants.Towers.GetTowerCost(towerType);

	}

	public void addGold(int getReward) {

		this.gold += getReward;

	}
	public int getLives() {
		return lives;
	}
}
