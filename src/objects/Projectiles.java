package objects;

import java.awt.geom.Point2D;

public class Projectiles {

	private Point2D.Float pos;
	private int id, projectileType, dmg;
	private float xSpeed, ySpeed, rotation;
	private boolean active;

	public Projectiles(float x, float y, float xSpeed, float ySpeed, int dmg, float rotation, int id,
			int projectileType) {

		pos = new Point2D.Float(x, y);
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.id = id;
		this.projectileType = projectileType;
		this.dmg = dmg;
		this.rotation = rotation;
		active = true;

	}

	public void reuse(int x, int y, float xSpeed, float ySpeed, int dmg, float rotate, int k, int type) {

		pos = new Point2D.Float(x, y);
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.dmg = dmg;
		this.rotation = rotate;
		active = true;
	}

	public void move() {
		pos.x += xSpeed;
		pos.y += ySpeed;
	}

	public Point2D.Float getPos() {
		return pos;
	}

	public void setPos(Point2D.Float pos) {
		this.pos = pos;
	}

	public int getId() {
		return id;
	}

	public int getProjectileType() {
		return projectileType;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getDmg() {
		return dmg;
	}

	public float getRotation() {
		return rotation;
	}

}
