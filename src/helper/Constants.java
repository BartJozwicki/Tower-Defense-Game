package helper;

public class Constants {

	
	public static class Direction {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}

	public static class Tiles {
		public static final int WATER_TILE = 0;
		public static final int GRASS_TILE = 1;
		public static final int ROAD_TILE = 2;
	}

	public static class Enemies {

		public static final int ORC = 0;
		public static final int BAT = 1;
		public static final int KNIGHT = 2;
		public static final int WOLF = 3;
		
		
		public static int GetReward(int enemyType) {
			int gold = 0;

			switch (enemyType) {
			case ORC -> gold = 5;
			case BAT -> gold = 5;
			case KNIGHT -> gold = 25;
			case WOLF -> gold = 10;
			}
			return gold;
		}

		public static float GetSpeed(int enemyType) {

			float speed = 0;

			switch (enemyType) {
			case ORC -> speed =0.4f;
			case BAT -> speed = 0.4f;
			case KNIGHT -> speed = 0.1f;
			case WOLF -> speed = 0.5f;
			}
			return speed;
		}

		public static int GetStartHealth(int enemyType) {

			int hp = 0;
			switch (enemyType) {
			case ORC -> hp = 100;
			case BAT -> hp = 50;
			case KNIGHT -> hp = 250;
			case WOLF -> hp = 75;
			}
			return hp;
		}
	}

	public static class Towers {
		public static final int CANNON = 0;
		public static final int ARCHER = 1;
		public static final int WIZARD = 2;
		
		public static int GetTowerCost(int towerType) {
			int cost = -1;
			switch (towerType) {
			case CANNON -> cost = 65;
			case ARCHER -> cost = 30;
			case WIZARD -> cost = 45;
			}
			return cost;
		}

		public static String GetName(int towerType) {
			String type = "";
			switch (towerType) {
			case CANNON -> type = "Cannon";
			case ARCHER -> type = "Archer";
			case WIZARD -> type = "Wizard";
			}
			return type;
		}

		public static int GetDefaultDmg(int towerType) {
			int type = -1;
			switch (towerType) {
			case CANNON -> type = 10;
			case ARCHER -> type = 5;
			case WIZARD -> type = 8;
			}
			return type;
		}

		public static float GetDefaultCooldown(int towerType) {
			float type = -1.0f;
			switch (towerType) {
			case CANNON -> type = 120.0f;
			case ARCHER -> type = 20.0f;
			case WIZARD -> type = 40.0f;
			}
			return type;
		}

		public static float GetDefaultRange(int towerType) {
			float type = -1.0f;
			switch (towerType) {
			case CANNON -> type = 80.0f;
			case ARCHER -> type = 150.0f;
			case WIZARD -> type = 100.0f;
			}
			return type;
		}
	}

	public static class Projectiles {
		public static final int ARROW = 0;
		public static final int LIGHTBALL = 1;
		public static final int BOMB = 2;

		public static float GetSpeed(int type) {
			float pSpeed = 0.0f;
			switch(type) {
			case BOMB -> pSpeed = 8.0f ;
			case ARROW -> pSpeed = 4.0f;
			case LIGHTBALL -> pSpeed = 6.0f;
			
			}
        return pSpeed;
		}
	}
}
