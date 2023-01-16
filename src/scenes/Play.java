package scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static Main.GameStates.*;
import Main.Main;
import enemies.Enemy;
import helper.LoadSave;
import managers.EnemyManager;
import managers.ProjectileManager;
import managers.TowerManager;
import managers.WaveManager;
import objects.PathPoint;
import objects.Tower;
import userInterface.ActionBar;
import static helper.Constants.Tiles.*;

/* Class activated when the game is played */
public class Play extends GameScene implements SceneMethods {

	private int[][] lvl;
	private int mouseX, mouseY;
	private ActionBar actionBar;
	private EnemyManager enemyManager;
	private TowerManager towerManager;
	private ProjectileManager projectileManager;
	private WaveManager waveManager;
	private Tower selectedTower;
	private int goldTick;
	private boolean gamePaused;
	private PathPoint start, end;
	private int mouseYacc;
	private BufferedImage[] trees;

	public Play(Main game) {

		super(game);

		trees = new BufferedImage[2];
		loadDefaultLevel();
		// loadTreeImages();
		
		towerManager = new TowerManager(this);
		enemyManager = new EnemyManager(this, start, end);
		actionBar = new ActionBar(0, 640, 640, 160, this);
		projectileManager = new ProjectileManager(this);
		waveManager = new WaveManager(this);

		gamePaused = false;

	}

	// Not good enough, can be used if the tree models are replaced
	private void loadTreeImages() {

		trees[0] = LoadSave.getTrees().getSubimage(0, 0, 32, 32);
		trees[1] = LoadSave.getTrees().getSubimage(0, 32, 32, 32);

	}

	private void loadDefaultLevel() {
		lvl = LoadSave.GetLevelData();
		ArrayList<PathPoint> points = LoadSave.GetLevelPathPoints();
		start = points.get(0);
		end = points.get(1);
	}

	@Override
	public void render(Graphics g) {
		drawLevel(g);
		drawTrees(g);
		actionBar.draw(g);
		towerManager.draw(g);
		drawSelectedTower(g);
		enemyManager.draw(g);
		drawHighlight(g);
		projectileManager.draw(g);
	}

	// Currently not used but may come useful in the future
	private void drawTrees(Graphics g) {

		for (int i = 0; i < 7; i = i + 2) {
			g.drawImage(trees[0], i * 32, 500, 32, 32, null);
			g.drawImage(trees[1], i * 32 + 32, 532, 32, 32, null);
			g.drawImage(trees[1], i * 32 + 32, 500 - 32, 32, 32, null);

		}
		for (int i = 0; i < 10; i++) {
			g.drawImage(trees[1], i * 32, 564, 32, 32, null);
		}
		for (int i = 0; i < 12; i++) {
			g.drawImage(trees[1], i * 32, 596, 32, 32, null);
		}

	}

	private void drawHighlight(Graphics g) {

		// Need to use accurate Y if we want to get rid of the highlight while using
		// action bar.
		if (mouseYacc <= 630) {
			g.setColor(Color.MAGENTA);
			g.drawRect(mouseX, mouseY, 32, 32);
		}
	}

	private void drawSelectedTower(Graphics g) {

		if (selectedTower != null)
			g.drawImage(towerManager.getTowerImages()[selectedTower.getTowerType()], mouseX, mouseY, null);

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

	public void update() {

		if (!gamePaused) {

			updateTick();
			waveManager.update();

			// Gold increase
			goldTick++;
			if (goldTick % (60 * 3) == 0)
				actionBar.addGold(1);

			// If there are more waves start timer else the game is won
			if (isAllEnemyDead()) {
				if (isThereMoreWaves()) {
					waveManager.startWaveTimer();
					if (isWaveTimerOver()) {
						waveManager.increaseWaveIndex();
						enemyManager.getEnemies().clear();
						waveManager.resetEnemyIndex();
					}
				} else {
					SetGameState(GAME_WON);
				}
			}

			if (isTimeForNewWave()) {
				spawnEnemy();
			}

			enemyManager.update();
			towerManager.update();
			projectileManager.update();
		}

	}

	private boolean isWaveTimerOver() {

		return waveManager.isWaveTimerOver();
	}

	private boolean isThereMoreWaves() {
		return waveManager.isThereMoreWaves();

	}

	private boolean isAllEnemyDead() {

		if (waveManager.isEnemyLeft()) {
			return false;
		}

		for (Enemy e : enemyManager.getEnemies())
			if (e.isAlive())
				return false;

		return true;
	}

	private void spawnEnemy() {

		enemyManager.spawnEnemy(waveManager.getNextEnemy());
	}

	private boolean isTimeForNewWave() {

		if (waveManager.isTimeForNewEnemy() && waveManager.isEnemyLeft() && waveManager.isInitDelayOver()) {
			return true;
		}
		return false;
	}

	public void upgradeTower(Tower selectedTower) {

		towerManager.upgradeTower(selectedTower);

	}

	public void removeTower(Tower selectedTower) {

		towerManager.removeTower(selectedTower);

	}

	public void setSelectedTower(Tower selectedTower) {
		this.selectedTower = selectedTower;
	}

	// Saves and pass level from edit -> play
	public void setLevel(int[][] lvl) {
		this.lvl = lvl;
	}

	public void setGamePaused(boolean gamePaused) {
		this.gamePaused = gamePaused;
	}

	@Override
	public void mouseClicked(int x, int y) {

		if (y >= 640) {
			actionBar.mouseClicked(x, y);
		} else {
			if (selectedTower != null) {
				if (isTileGrass(mouseX, mouseY)) {
					if (getTowerAt(mouseX, mouseY) == null) {
						towerManager.addTower(selectedTower, mouseX, mouseY);
						removeGold(selectedTower.getTowerType());
						selectedTower = null;
					}
				}
			} else {
				Tower t = getTowerAt(mouseX, mouseY);
				actionBar.displayTower(t);
			}
		}

	}

	private void removeGold(int towerType) {

		actionBar.payForTower(towerType);

	}

	private Tower getTowerAt(int x, int y) {

		return towerManager.getTowerAt(x, y);

	}

	private boolean isTileGrass(int x, int y) {

		int id = lvl[y / 32][x / 32];
		int tileType = game.getTileManager().getTile(id).getTileType();

		return tileType == GRASS_TILE;
	}

	public void attack(Tower t, Enemy e) {

		projectileManager.newProjectile(t, e);

	}

	public int getTileType(int x, int y) {

		int xCord = x / 32;
		int yCord = y / 32;

		if (xCord < 0 || xCord > 19)
			return 0;

		if (yCord < 0 || yCord > 19)
			return 0;

		int id = lvl[y / 32][x / 32];
		return game.getTileManager().getTile(id).getTileType();
	}

	public void rewardPlayer(int enemyType) {
		actionBar.addGold(helper.Constants.Enemies.GetReward(enemyType));
	}

	@Override
	public void mouseMoved(int x, int y) {

		if (y >= 640) {
			actionBar.mouseMoved(x, y);
		} else {

			// Snap to grid
			mouseX = (x / 32) * 32;
			mouseY = (y / 32) * 32;
			mouseYacc = y;
		}

	}

	@Override
	public void mousePressed(int x, int y) {

		if (y >= 640) {
			actionBar.mousePressed(x, y);
		}
	}

	@Override
	public void mouseReleased(int x, int y) {

		actionBar.mouseReleased(x, y);
	}

	@Override
	public void mouseDragged(int x, int y) {

	}

	@Override
	public void leftClick() {
		selectedTower = null;
	}

	public TowerManager getTowerManager() {
		return towerManager;
	}

	public EnemyManager getEnemyManager() {
		return enemyManager;
	}

	public WaveManager getWaveManager() {
		return waveManager;
	}

	public boolean isGamePaused() {
		return gamePaused;
	}

	public void removeOneLive() {
		actionBar.removeOneLife();
	}

	public int getDifficultyLevel() {
		return game.getSettings().getDifficultyLevel();
	}

	public void resetEverything() {

		actionBar.resetEverything();

		enemyManager.reset();
		towerManager.reset();
		waveManager.reset();
		projectileManager.reset();

		mouseX = 0;
		mouseY = 0;
		selectedTower = null;
		goldTick = 0;
		gamePaused = false;

	}

}
