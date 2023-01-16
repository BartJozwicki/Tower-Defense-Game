package enemies;

import java.awt.Rectangle;
import managers.EnemyManager;

import static helper.Constants.Direction.*;

/* All minions are derived from this class */
public abstract class Enemy {

	protected EnemyManager enemyManager;
	protected Rectangle bounds;
	protected float x, y;
	protected boolean alive;
	protected int health, maxHealth, id, enemyType;
	protected int lastDir;	
	protected int slowTick, slowTime;
	protected int deadTimer, deadTick;
	protected int caracassType;

	public Enemy(float x, float y, int id, int enemyType, int caracassType, EnemyManager enemyManager) {

		this.x = x;
		this.y = y;
		this.id = id;
		this.enemyType = enemyType;
		this.enemyManager = enemyManager;
		this.caracassType = caracassType;

		// Body of the minion
		bounds = new Rectangle((int) x, (int) y, 22, 22);
		lastDir = -1;

		//Special effect "slow" duration
		slowTick = 121;
		slowTime = 120;
		
		//Carcass timer 
		deadTimer = 360; // = 3sec
		deadTick = 1;	
		
		//Set health of the minion
		setStartHealth();
		
		//Let it live!
		alive = true;
	}

	private void setStartHealth() {
		health = helper.Constants.Enemies.GetStartHealth(enemyType, enemyManager.getDifficultyLevel());
		maxHealth = health;
	}
	
	public boolean displayBody(){
		
		if(deadTick < deadTimer) {
			deadTick++;
			return true;
		}
		return false;	
	}

	public void move(float speed, int dir) {

		lastDir = dir;

		if (slowTick < slowTime) {
			slowTick++;
			speed *= 0.5f;
		}

		switch (dir) {

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

	public void takeDmg(int dmg) {

		this.health -= dmg;

		if (health <= 0) {
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

	// Initial position of the minion
	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// Getters and Setters
	public float getHealthBarFloat() {
		return health / (float) maxHealth;
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
	
	public int getCaracassType() {
		return caracassType;
	}
	
	public void doNotDisplayCarcass() {
		deadTick = 361;
	}

}
