package managers;

import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import enemies.Enemy;
import helper.LoadSave;

import objects.Projectiles;
import objects.Tower;
import scenes.Play;
import static helper.Constants.Towers.*;
import static helper.Constants.Projectiles.*;

public class ProjectileManager {

	private Play play;
	private ArrayList<Projectiles> projectiles = new ArrayList<>();
	private ArrayList<Explosion> explosions = new ArrayList<>();
	private BufferedImage[] projectile_imgs, explosion_imgs;
	private int projectile_id;

	public ProjectileManager(Play play) {

		this.play = play;
		projectile_id = 0;
		importImgs();

	}

	private void importImgs() {

		BufferedImage atlas = LoadSave.getSpriteAtlas();

		projectile_imgs = new BufferedImage[3];

		for (int i = 0; i < 3; i++) {
			projectile_imgs[i] = atlas.getSubimage((7 + i) * 32, 32, 32, 32);
		}
		importExplosion(atlas);
	}

	private void importExplosion(BufferedImage atlas) {

		explosion_imgs = new BufferedImage[7];
		for (int i = 0; i < 7; i++)
			explosion_imgs[i] = atlas.getSubimage(i * 32, 2 * 32, 32, 32);

	}

	public void newProjectile(Tower t, Enemy e) {

		int type = getProjectileType(t);

		int xDist = (int) Math.abs(t.getX() - e.getX());
		int yDist = (int) Math.abs(t.getY() - e.getY());

		int totalDist = xDist + yDist;

		float xPer = (float) xDist / totalDist;

		float xSpeed = xPer * helper.Constants.Projectiles.GetSpeed(type);
		float ySpeed = helper.Constants.Projectiles.GetSpeed(type) - xSpeed;

		if (t.getX() > e.getX())
			xSpeed = -xSpeed;
		if (t.getY() > e.getY())
			ySpeed = -ySpeed;

		float rotate = 0;

		if (type == ARROW) {
			float arcValue = (float) Math.atan(yDist / (float) xDist);
			rotate = (float) Math.toDegrees(arcValue);

			
			if (t.getX() < e.getX() && t.getY() > e.getY()) {
				rotate += (90 - rotate);
			} else if(t.getX() > e.getX() && t.getY() < e.getY()) {
				rotate += 270;
			} else if(t.getX() < e.getX() && t.getY() < e.getY()) {
				rotate += 180;
			}  else if(t.getX() < e.getX() && t.getY() == e.getY()) {
				rotate += 180;
			}	
		}

		for (Projectiles p : projectiles)
			if (!p.isActive())
				if (p.getProjectileType() == type) {
					p.reuse(t.getX() + 16, t.getY() + 16, xSpeed, ySpeed, t.getDmg(), rotate, projectile_id++, type);
					return;
				}
		projectiles.add(new Projectiles(t.getX() + 16, t.getY() + 16, xSpeed, ySpeed, t.getDmg(), rotate,
				projectile_id++, type));

	}

	private boolean isProjectileHittingEnemy(Projectiles p) {
		for (Enemy e : play.getEnemyManager().getEnemies()) {
			if (e.isAlive()) {
				if (e.getBounds().contains(p.getPos())) {
					e.takeDmg(p.getDmg());
					if (p.getProjectileType() == LIGHTBALL)
						e.slow();
					return true;
				}
			}
		}
		return false;
	}

	private boolean isProjOutsideBounds(Projectiles p) {
		if (p.getPos().x > 0 && p.getPos().x < 640 && p.getPos().y > 0 && p.getPos().y <= 800)
			return false;
		else
			return true;
	}

	private int getProjectileType(Tower t) {

		int p = t.getTowerType();
		
		switch (t.getTowerType()) {
		case AXE_THROWER -> p = ARROW;
		case CANNON -> p = BOMB;
		case MAGIC -> p = LIGHTBALL;
		}
		return p;
	}

	public void update() {
		for (Projectiles p : projectiles) {
			if (p.isActive()) {
				p.move();
				if (isProjectileHittingEnemy(p)) {
					p.setActive(false);
					if (p.getProjectileType() == BOMB) {
						explosions.add(new Explosion(p.getPos()));
						explodeOnEnemies(p);
					}
				} else if (isProjOutsideBounds(p)) {
					p.setActive(false);

				}
			}
		}
		for (Explosion e : explosions) {
			if (e.getIndex() < 7)
				e.update();
		}
	}

	private void explodeOnEnemies(Projectiles p) {

		for (Enemy e : play.getEnemyManager().getEnemies()) {
			if (e.isAlive()) {
				float radius = 300.0f;

				float xDist = Math.abs(p.getPos().x - e.getX());
				float yDist = Math.abs(p.getPos().y - e.getY());

				float realDist = (float) Math.hypot(xDist, yDist);

				if (realDist <= radius)
					e.takeDmg(p.getDmg());
			}

		}

	}

	public void draw(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		for (Projectiles p : projectiles)
			if (p.isActive()) {
				if (p.getProjectileType() == ARROW) {
					// Point around which rotation is done
					g2d.translate(p.getPos().x, p.getPos().y);
					g2d.rotate(Math.toRadians(p.getRotation()));
					g2d.drawImage(projectile_imgs[p.getProjectileType()], -16, -16, null);
					g2d.rotate(-Math.toRadians(p.getRotation()));
					g2d.translate(-p.getPos().x, -p.getPos().y);
				} else {
					g2d.drawImage(projectile_imgs[p.getProjectileType()], (int) p.getPos().x - 16, (int) p.getPos().y - 16, null);
				}

			}

		drawExplosions(g2d);

	}

	private void drawExplosions(Graphics2D g2d) {

		for (Explosion e : explosions) {
			if (e.getIndex() < 7) {
				g2d.drawImage(explosion_imgs[e.getIndex()], (int) e.getPos().x - 16, (int) e.getPos().y - 16, 74, 74, null);
			
			}
		}
	}

	public class Explosion {

		private Point2D.Float pos;
		private int explosionTick, explosionIndex;

		public Explosion(Point2D.Float pos) {
			this.pos = pos;
		}

		public void update() {

			explosionTick++;
			explosionIndex++;
			
			if (explosionTick >= 80) {
				explosionTick = 0;
				explosionIndex = 0;

			}
		}

		public int getIndex() {
			return explosionIndex;
		}

		public Point2D.Float getPos() {
			return pos;
		}
	}

	public void reset() {
		projectiles.clear();
		explosions.clear();
		projectile_id = 0;

	}

}
