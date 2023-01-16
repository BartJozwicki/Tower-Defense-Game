package objects;
import static helper.Constants.Towers.*;

public class Tower {
	private int x, y, id, towerType, cdTick, dmg;
	private float  range, cooldown;
	private int tier;
	
	public Tower(int x, int y, int id, int towerType) {
      this.x = x;
      this.y = y;
      this.id = id;
      this.towerType = towerType;
      tier = 1;
      dmg = defaultDmg();
      range = defaultRange();
      cooldown = defaultCooldown();
      
	}
	
	public void upgradeTower() {
		
		this.tier++;

		switch (towerType) {
		case AXE_THROWER:
			dmg += 2;
			range += 10;
			cooldown -= 5;
			break;
		case MAGIC:
			dmg += 2;
			range += 10;
			cooldown -= 5;
			break;
		case CANNON:
			dmg += 2;
			range += 10;
			cooldown -= 5;
			break;
		}
	}
	
	public void update() {
		cdTick++;
	}
	
	public boolean isCooldownOver() {
		
		return cdTick >= cooldown;
		
	}
	
	public void resetCooldown() {
		cdTick = 0;
	}
	
	private float defaultCooldown() {

		return helper.Constants.Towers.GetDefaultCooldown(towerType);
	}

	private float defaultRange() {
		return helper.Constants.Towers.GetDefaultRange(towerType);

	}

	private int defaultDmg() {

		return helper.Constants.Towers.GetDefaultDmg(towerType);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTowerType() {
		return towerType;
	}

	public void setTowerType(int towerType) {
		this.towerType = towerType;
	}
	

	public int getDmg() {
		return dmg;
	}

	public float getRange() {
		return range;
	}
	
	public float getCooldown() {
		return cooldown;
	}

	public int getTier(){
		return tier;
	}


}
