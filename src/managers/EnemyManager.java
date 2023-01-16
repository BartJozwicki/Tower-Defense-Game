package managers;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import enemies.Warrior;
import enemies.Enemy;
import enemies.FirstBoss;
import enemies.BloodyWarrior;
import enemies.Peon;
import enemies.Bloody_Spearman;
import helper.LoadSave;
import objects.PathPoint;
import scenes.Play;
import static helper.Constants.Direction.*;
import static helper.Constants.Tiles.*;
import static helper.Constants.Enemies.*;

public class EnemyManager {

	private Play play;

	private PathPoint start, end;

	private BufferedImage[] enemyImgs;
	private BufferedImage[] bodiesImgs;
	private BufferedImage slowEffect;

	private int hpBarWidth;

	private ArrayList<Enemy> enemies = new ArrayList<>();

	public EnemyManager(Play play, PathPoint start, PathPoint end) {

		this.play = play;
		this.start = start;
		this.end = end;

		hpBarWidth = 20;

		enemyImgs = new BufferedImage[5];
		bodiesImgs = new BufferedImage[15];

		loadEffectImg();
		loadEnemyImgs();
		loadCarcasses();
	}

	private void loadEffectImg() {
		// special effects that stay 'on' the enemy
		slowEffect = LoadSave.getSpriteAtlas().getSubimage(32 * 9, 32 * 2, 32, 32);

	}

	private void loadCarcasses() {

		bodiesImgs[DEAD_PEON] = LoadSave.getSpriteAtlas().getSubimage(0, 32 * 3, 32, 32);
		bodiesImgs[DEAD_WARRIOR] = LoadSave.getSpriteAtlas().getSubimage(32, 32 * 3, 32, 32);
		bodiesImgs[DEAD_B_ORC] = LoadSave.getSpriteAtlas().getSubimage(64, 32 * 3, 32, 32);
		bodiesImgs[DEAD_BOSS] = LoadSave.getSpriteAtlas().getSubimage(32 * 5, 32 * 3, 32, 32);

	}

	private void loadEnemyImgs() {

		// Map enemy image to the numbered name
		BufferedImage atlas = LoadSave.getSpriteAtlas();
		for (int i = 0; i < 4; i++) {
			enemyImgs[i] = atlas.getSubimage(32 * i, 32, 32, 32);
		}
		// Boss
		enemyImgs[4] = atlas.getSubimage(32 * 4, 32 * 3, 32, 32);
	}

	public void update() {

		// If alive keep moving!
		for (Enemy e : enemies) {
			if (e.isAlive()) {
				updateEnemyMove(e);
			}
		}
	}

	private void updateEnemyMove(Enemy e) {

		if (e.getLastDir() == -1) {
			setNewDirectionAndMove(e);
		}

		int newX = (int) (e.getX() + getSpeedAndWidth(e.getLastDir(), e.getEnemyType()));
		int newY = (int) (e.getY() + getSpeedAndHeight(e.getLastDir(), e.getEnemyType()));

		if (ROAD_TILE == getTileType(newX, newY)) {
			e.move(GetSpeed(e.getEnemyType(), play.getDifficultyLevel()), e.getLastDir());
		} else if (isAtTheEnd(e)) {
			e.doNotDisplayCarcass();
			e.killEnemy();
			// Boss damages 20 lives
			if(e.getEnemyType() == FIRST_BOSS)
				for(int i = 0; i < 21; i++) {
					play.removeOneLive();
				}
			else
		      play.removeOneLive();
		} else {
			setNewDirectionAndMove(e);
		}

	}

	private void setNewDirectionAndMove(Enemy e) {

		int dir = e.getLastDir();

		// Int division to snap into the tile
		int xCord = (int) (e.getX() / 32);
		int yCord = (int) (e.getY() / 32);

		fixEnemyOffsetTile(e, dir, xCord, yCord);

		if (isAtTheEnd(e))
			return;

		if (LEFT == dir || RIGHT == dir) {
			int newY = (int) (e.getY() + getSpeedAndHeight(UP, e.getEnemyType()));

			if (getTileType((int) e.getX(), newY) == ROAD_TILE)
				e.move(GetSpeed(e.getEnemyType(), play.getDifficultyLevel()), UP);
			else
				e.move(GetSpeed(e.getEnemyType(), play.getDifficultyLevel()), DOWN);
		} else {
			int newX = (int) (e.getX() + getSpeedAndWidth(RIGHT, e.getEnemyType()));

			if (getTileType(newX, (int) e.getY()) == ROAD_TILE)
				e.move(GetSpeed(e.getEnemyType(), play.getDifficultyLevel()), RIGHT);
			else
				e.move(GetSpeed(e.getEnemyType(), play.getDifficultyLevel()), LEFT);
		}

	}

	private void fixEnemyOffsetTile(Enemy e, int dir, int xCord, int yCord) {

		switch (dir) {
		case RIGHT:
			if (xCord < 19)
				xCord++;
			break;
		case DOWN:
			if (yCord < 19)
				yCord++;
			break;
		}

		e.setPos(xCord * 32, yCord * 32);
	}

	private boolean isAtTheEnd(Enemy e) {

		if (e.getX() == end.getXCord() * 32)
			if (e.getY() == end.getYCord() * 32)
				return true;

		return false;
	}

	private int getTileType(int x, int y) {
		return play.getTileType(x, y);
	}

	private float getSpeedAndHeight(int dir, int enemyType) {
		if (DOWN == dir) {
			return GetSpeed(enemyType, play.getDifficultyLevel()) + 32;
		} else if (UP == dir) {
			return -GetSpeed(enemyType, play.getDifficultyLevel());
		}
		return 0;
	}

	private float getSpeedAndWidth(int dir, int enemyType) {
		if (LEFT == dir) {
			return -GetSpeed(enemyType, play.getDifficultyLevel());
		} else if (RIGHT == dir) {
			return GetSpeed(enemyType, play.getDifficultyLevel()) + 32;
		}
		return 0;
	}

	public void addEnemy(int enemyType) {

		int x = start.getXCord() * 32;
		int y = start.getYCord() * 32;

		switch (enemyType) {

		case PEON -> enemies.add(new Peon(x, y, 0, this));
		case WARRIOR -> enemies.add(new Warrior(x, y, 0, this));
		case BLOODY_WARRIOR -> enemies.add(new BloodyWarrior(x, y, 0, this));
		case BLOODY_SPEARMAN -> enemies.add(new Bloody_Spearman(x, y, 0, this));
		case FIRST_BOSS -> enemies.add(new FirstBoss(x, y, 0, this));

		}
	}

	public void draw(Graphics g) {
		for (Enemy e : enemies) {
			if (e.isAlive()) {
				drawEnemy(e, g);
				drawHealthBar(e, g);
				drawEffects(e, g);
			} else {

				if (e.displayBody()) {
					drawDeadBody(e, g);
				}
			}
		}

	}

	private void drawDeadBody(Enemy e, Graphics g) {

		g.drawImage(bodiesImgs[e.getCaracassType()], (int) e.getX() - 8, (int) e.getY() - 14, 48, 48, null);

	}

	private void drawEffects(Enemy e, Graphics g) {

		if (e.isSlowed()) {
			g.drawImage(slowEffect, (int) e.getX(), (int) e.getY(), null);
		}

	}

	private void drawHealthBar(Enemy e, Graphics g) {

		g.setColor(Color.RED);

		if (e.getEnemyType() == FIRST_BOSS) {
			g.fillRect((int) e.getX() - (getBarWidth(e) / 2), (int) e.getY() - 70, (int) (2.5 * getBarWidth(e)), 4);
		} else {
			g.fillRect((int) e.getX() + 16 - (getBarWidth(e) / 2), (int) e.getY() - 8, getBarWidth(e), 2);
		}

	}

	private void drawEnemy(Enemy e, Graphics g) {

		if (e.getEnemyType() == FIRST_BOSS) {
			g.drawImage(enemyImgs[e.getEnemyType()], (int) e.getX() - 25, (int) e.getY() - 68, 96, 96, null);
		} else {
			g.drawImage(enemyImgs[e.getEnemyType()], (int) e.getX() - 8, (int) e.getY() - 14, 48, 48, null);
		}
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public void spawnEnemy(int nextEnemy) {
		addEnemy(nextEnemy);

	}

	public int numberOfEnemiesAlive() {
		int size = 0;
		for (Enemy e : enemies)
			if (e.isAlive())
				size++;
		return size;
	}

	public void rewardPlayer(int enemyType) {

		play.rewardPlayer(enemyType);

	}

	private int getBarWidth(Enemy e) {
		return (int) (hpBarWidth * e.getHealthBarFloat());
	}

	public int getDifficultyLevel() {
		return play.getDifficultyLevel();
	}

	public void reset() {
		enemies.clear();
	}

}
