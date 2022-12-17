package enemies;

import java.awt.Rectangle;
import managers.EnemyManager;

import static helper.Constants.Direction.*;

//All minions are derived from this class 
public abstract class Enemy {

	protected EnemyManager enemyManager;
	protected float x, y;
	protected Rectangle bounds;
	protected int health;
	protected int maxHealth;
	protected int id;
	protected int enemyType;
	protected int lastDir;
	protected boolean alive;
	protected int slowTick;
	protected int slowTime;
	
	
	public Enemy(float x, float y, int id, int enemyType, EnemyManager enemyManager) {
		
		this.x = x;
		this.y = y;
		this.id = id;
		this.enemyType = enemyType;
		
		
		//body of the minion
		bounds = new Rectangle((int) x, (int) y, 22, 22); 
		lastDir = -1;
		setStartHealth();
		slowTick = 121;
		slowTime = 120;
		alive = true;
		this.enemyManager = enemyManager;
	}
	
	private void setStartHealth() {
		health = helper.Constants.Enemies.GetStartHealth(enemyType);
		maxHealth = health;
	}
	public void move(float speed, int dir) {
		
		lastDir = dir;
		
		if(slowTick < slowTime) {
			slowTick++;
			speed *= 0.5f;
		}
		
		switch(dir) {
		
		case LEFT -> this.x -= speed;	
		case UP -> this.y -= speed;
		case RIGHT -> this.x += speed;	
		case DOWN -> this.y += speed;
				
		}
		updateHitbox();
	}
	
	private void updateHitbox() {
	  bounds.x = (int) x;
	  bounds.y = (int) y;
		
	}

	//Initial position of the 
	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	//Getters and Setter
	
	public float getHealthBarFloat() {
		return health / (float)maxHealth;
	}
	
	public void killEnemy() {
		
		alive = false;
		health = 0;
		
	}
	
	public void slow() {
		slowTick = 0;
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEnemyType() {
		return enemyType;
	}

	public void setEnemyType(int enemyType) {
		this.enemyType = enemyType;
	}
	
	public int getLastDir() {
		return lastDir;
	}

	public void takeDmg(int dmg) {
		
		this.health -= dmg;
		
		if(health <= 0) {
			alive = false;
			enemyManager.rewardPlayer(enemyType);
		}
	}
	
	
	public boolean isAlive() {
		return alive;
	}
	public boolean isSlowed() {
		return slowTick < slowTime;
	}


}
