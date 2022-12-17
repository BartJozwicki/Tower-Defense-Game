package managers;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import enemies.Bat;
import enemies.Enemy;
import enemies.Knight;
import enemies.Orc;
import enemies.Wolf;
import helper.LoadSave;
import objects.PathPoint;
import scenes.Play;
import static helper.Constants.Direction.*;
import static helper.Constants.Tiles.*;
import static helper.Constants.Enemies.*;
public class EnemyManager {

	private Play play;
    private BufferedImage[] enemyImgs;
	
	
	private PathPoint start, end;
	private int hpBarWidth = 20;
	private BufferedImage slowEffect;
	
	private ArrayList<Enemy> enemies = new ArrayList<>();
	

	
	public EnemyManager(Play play, PathPoint start, PathPoint end) {
		
	this.play = play;	
	this.start = start;
	this.end = end;
	enemyImgs = new BufferedImage[4];
	
	loadEffectImg();
	loadEnemyImgs();
	}
	
	private void loadEffectImg() {

     slowEffect = LoadSave.getSpriteAtlas().getSubimage(32*9, 32*2, 32, 32);
		
	}

	private void loadEnemyImgs() {
		
		BufferedImage atlas = LoadSave.getSpriteAtlas();
		for(int i = 0; i < 4; i++)
		{
		enemyImgs[i] = atlas.getSubimage(32 * i, 32, 32,32);
		}
	}

	public void update() {

		
		for (Enemy e : enemies) {
			// if the next tile is road check your next step
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
			e.move(GetSpeed(e.getEnemyType()), e.getLastDir());
		} else if (isAtTheEnd(e)) {
	       e.killEnemy();
	       play.removeOneLive();
		} else {
			setNewDirectionAndMove(e);
		}

	}

	private void setNewDirectionAndMove(Enemy e) {
		
		int dir = e.getLastDir();
		
		//move into the current tile 100%
		int xCord = (int)(e.getX() / 32);
		int yCord = (int)(e.getY() / 32);
		
		fixEnemyOffsetTile(e, dir, xCord, yCord);
		
		
		if(isAtTheEnd(e))
			return;
		
		if(LEFT == dir || RIGHT == dir) {
			int newY = (int)(e.getY() + getSpeedAndHeight(UP, e.getEnemyType()));
			
			if(getTileType((int) e.getX(), newY) == ROAD_TILE)
				e.move(GetSpeed(e.getEnemyType()), UP);
			else
				e.move(GetSpeed(e.getEnemyType()), DOWN);
		}
		else //(UP == dir || DOWN == dir) {
		{
			int newX = (int) (e.getX() + getSpeedAndWidth(RIGHT, e.getEnemyType()));
			
			if(getTileType(newX, (int)e.getY()) == ROAD_TILE)
				e.move(GetSpeed(e.getEnemyType()), RIGHT);
			else
				e.move(GetSpeed(e.getEnemyType()), LEFT);
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
	
		if(e.getX() == end.getXCord() * 32)
			if(e.getY() == end.getYCord() * 32)
				return true;
		
		return false;
	}

	private int getTileType(int x, int y) {
		return play.getTileType(x,y);
	}
	
	
	private float getSpeedAndHeight(int dir, int enemyType) {
		if(DOWN == dir)
		{
			return GetSpeed(enemyType) + 32;
		}
		else if(UP == dir)
		{
			return -GetSpeed(enemyType);
		}
		return 0;
	}
	private float getSpeedAndWidth(int dir, int enemyType) {
		if(LEFT == dir)
		{
			return -GetSpeed(enemyType);
		}
		else if(RIGHT == dir)
		{
			return GetSpeed(enemyType) + 32;
		}
		return 0;
	}

	public void addEnemy(int enemyType) {
		
		int x = start.getXCord() * 32;
		int y = start.getYCord() * 32;
		
		switch(enemyType) {
		
		case ORC -> enemies.add(new Orc(x, y, 0, this));
		case BAT -> enemies.add(new Bat(x, y, 0, this));
		case KNIGHT -> enemies.add(new Knight(x, y, 0, this));
		case WOLF -> enemies.add(new Wolf(x, y, 0, this));
		
		}
	}
	
	public void draw(Graphics g) {
		for (Enemy e : enemies) {
			if (e.isAlive()) {
				drawEnemy(e, g);
				drawHealthBar(e, g);
				drawEffects(e,g);
			}
		}

	}

	private void drawEffects(Enemy e, Graphics g) {
		
		if(e.isSlowed()) {
			g.drawImage(slowEffect,  (int)e.getX(), (int)e.getY(), null);
		}
		
		
	}

	private void drawHealthBar(Enemy e, Graphics g) {
	g.setColor(Color.RED);
	
	g.fillRect((int)e.getX() + 16 - (getBarWidth(e) / 2) , (int)e.getY() - 4 ,getBarWidth(e),2);
		
	}

	private int getBarWidth(Enemy e) {
		return (int)(hpBarWidth * e.getHealthBarFloat());
	}
	private void drawEnemy(Enemy e, Graphics g) {
	
		//g.drawImage(enemyImgs[e.getEnemyType()], (int)e.getX(), (int)e.getY(), null);
		
		g.drawImage(enemyImgs[e.getEnemyType()], (int)e.getX(), (int)e.getY()-10, 48, 48, null);
	}
	
	
	public ArrayList<Enemy> getEnemies(){
		return enemies;
	}

	public void spawnEnemy(int nextEnemy) {
		addEnemy(nextEnemy);
		
	}

	public int numberOfEnemiesAlive() {
		int size = 0;
		for(Enemy e : enemies)
			if(e.isAlive())
				size++;
		return size;
	}

	public void rewardPlayer(int enemyType) {
	
		play.rewardPlayer(enemyType);
		
	}
	public void reset() {
		enemies.clear();
	}

}
