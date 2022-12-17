package managers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import enemies.Enemy;
import helper.LoadSave;
import objects.Tower;
import scenes.Play;

public class TowerManager {
	
	private Play play;
	private BufferedImage[] towerImgs;
	private ArrayList<Tower> towers = new ArrayList<>();
    private int towerAmount;
	
	public TowerManager(Play play) {
		this.play = play;
		towerAmount = 0;
		loadTowerImgs();

	}


	private void loadTowerImgs() {
		
		BufferedImage atlas = LoadSave.getSpriteAtlas();
		towerImgs = new BufferedImage[3];
		
		for(int i = 0; i < 3; i++) {
		towerImgs[i] = atlas.getSubimage((4 * 32 + i * 32), 32, 32, 32);	
		}
		
	}
	public void addTower(Tower selectedTower, int xPos, int yPos) {
		
	towers.add(new Tower(xPos, yPos, towerAmount++, selectedTower.getTowerType()));
		
	}
	

	public void update() {
		
		for(Tower t : towers) {
			t.update();
		    attackEnemiesInRange(t);
		 
		}
	}

	public void removeTower(Tower selectedTower) {
		
		
		for(int i = 0; i < towers.size(); i++) {
			if(towers.get(i).getId() == selectedTower.getId())
				towers.remove(i);
		}
	}
	
	public void upgradeTower(Tower selectedTower) {

		
       for(int i = 0; i < towers.size(); i++) {
    	 
    	   if(towers.get(i).getId() == selectedTower.getId()) {
    		   towers.get(i).upgradeTower();
    	
    	   }
    	 
       }
	}



	
	private void attackEnemiesInRange(Tower t) {
	
			for(Enemy e : play.getEnemyManager().getEnemies()) {
	
				if(e.isAlive() && enemyInRange(t, e) && t.isCooldownOver()){
		
							play.attack(t, e);
							t.resetCooldown();
				}
				else {
					
				}
			}
	}


	


	private boolean enemyInRange(Tower t, Enemy e) {
	    
		int range = helper.Utils.GetHypoDistance(t.getX(), t.getY(), e.getX(), e.getY());
		
		return range < t.getRange();

	}


	public void draw(Graphics g) {
		for(Tower t : towers) {
			g.drawImage(towerImgs[t.getTowerType()], t.getX(), t.getY(), null);
		}

	}
	
	public Tower getTowerAt(int x, int y) {
		
		for(Tower t : towers)
			if(t.getX() == x)
				if(t.getY() == y)
					return t;
		
		return null;
		
	}
	public BufferedImage[] getTowerImages() {
		return towerImgs;
	}

public void reset() {
	towers.clear();
	towerAmount = 0;
}
	

	

}
