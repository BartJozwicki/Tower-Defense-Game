package helper;

//This class provides global variables and simple methods related to them.
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

		//Game difficulty
		public static final int EASY = 0;
		public static final int NORMAL = 1;
		public static final int HARD = 2;

		//Enemy types
		public static final int PEON = 0;
		public static final int WARRIOR = 1;
		public static final int BLOODY_WARRIOR = 2;
		public static final int BLOODY_SPEARMAN = 3;
		public static final int FIRST_BOSS = 4;

		//Carcasses
		public static final int DEAD_PEON = 10;
		public static final int DEAD_WARRIOR = 11;
		public static final int DEAD_B_ORC = 12;
		public static final int DEAD_BOSS = 13;

		//Reward for killing minion
		public static int GetReward(int enemyType) {

			int gold = 0;

			switch (enemyType) {
			case PEON -> gold = 2;
			case WARRIOR -> gold = 3;
			case BLOODY_WARRIOR -> gold = 5;
			case BLOODY_SPEARMAN -> gold = 7;
			case FIRST_BOSS -> gold = 100;
			}
			return gold;

		}

		//Speed of the minion depending on the game difficulty
		public static float GetSpeed(int enemyType, int difficulty) {

			float speed = 0;

			switch (difficulty) {

			case EASY:
				switch (enemyType) {
				case PEON -> speed = 0.37f;
				case WARRIOR -> speed = 0.38f;
				case BLOODY_WARRIOR -> speed = 0.1f;
				case BLOODY_SPEARMAN -> speed = 0.4f;
				case FIRST_BOSS -> speed = 0.4f;
				}
				break;

			case NORMAL:
				switch (enemyType) {
				case PEON -> speed = 0.4f;
				case WARRIOR -> speed = 0.4f;
				case BLOODY_WARRIOR -> speed = 0.15f;
				case BLOODY_SPEARMAN -> speed = 0.5f;
				case FIRST_BOSS -> speed = 0.5f;
				}
				break;

			case HARD:
				switch (enemyType) {
				case PEON -> speed = 0.5f;
				case WARRIOR -> speed = 0.5f;
				case BLOODY_WARRIOR -> speed = 0.2f;
				case BLOODY_SPEARMAN -> speed = 0.6f;
				case FIRST_BOSS -> speed = 0.5f;
				}
				break;
			}
			return speed;
		}

		// Minions start health depending on the game difficulty
		public static int GetStartHealth(int enemyType, int difficulty) {

			int hp = 0;
		
			switch (difficulty) {

			case EASY:
				switch (enemyType) {
				case PEON -> hp = 75;
				case WARRIOR -> hp = 150;
				case BLOODY_WARRIOR -> hp = 200;
				case BLOODY_SPEARMAN -> hp = 250;
				case FIRST_BOSS -> hp = 4000;
				}
				break;

			case NORMAL:
				switch (enemyType) {
				case PEON -> hp = 100;
				case WARRIOR -> hp = 200;
				case BLOODY_WARRIOR -> hp = 250;
				case BLOODY_SPEARMAN -> hp = 350;
				case FIRST_BOSS -> hp = 5500;
				}
				break;
			case HARD:
				switch (enemyType) {
				case PEON -> hp = 175;
				case WARRIOR -> hp = 225;
				case BLOODY_WARRIOR -> hp = 350;
				case BLOODY_SPEARMAN -> hp = 450;
				case FIRST_BOSS -> hp = 8500;
				}
				break;

			}
			return hp;
		}
	}

	//Tower related statistics
	public static class Towers {

		public static final int CANNON = 0;
		public static final int AXE_THROWER = 1;
		public static final int MAGIC = 2;

		public static int GetTowerCost(int towerType) {
			int cost = -1;
			switch (towerType) {
			case CANNON -> cost = 65;
			case AXE_THROWER -> cost = 30;
			case MAGIC -> cost = 45;
			}
			return cost;
		}

		public static String GetName(int towerType) {
			String type = "";
			switch (towerType) {
			case CANNON -> type = "Cannon";
			case AXE_THROWER -> type = "Axe Thrower";
			case MAGIC -> type = "Magic Tower";
			}
			return type;
		}
		
		public static String GetInfo(int towerType) {
			String type = "";
			switch (towerType) {
			case CANNON -> type = "Short range, high AOE dmg, good for clusters";
			case AXE_THROWER -> type = "Long range, medium dmg";
			case MAGIC -> type = "Medium range, medium dmg, slow effect";
			}
			return type;
		}

		public static int GetDefaultDmg(int towerType) {
			int type = -1;
			switch (towerType) {
			case CANNON -> type = 25;
			case AXE_THROWER -> type = 5;
			case MAGIC -> type = 6;
			}
			return type;
		}

		public static float GetDefaultCooldown(int towerType) {
			float type = -1.0f;
			switch (towerType) {
			case CANNON -> type = 120.0f;
			case AXE_THROWER -> type = 20.0f;
			case MAGIC -> type = 40.0f;
			}
			return type;
		}

		public static float GetDefaultRange(int towerType) {
			float type = -1.0f;
			switch (towerType) {
			case CANNON -> type = 80.0f;
			case AXE_THROWER -> type = 150.0f;
			case MAGIC -> type = 100.0f;
			}
			return type;
		}
	}

	//Tower's projectiles
	public static class Projectiles {

		public static final int ARROW = 0;
		public static final int LIGHTBALL = 1;
		public static final int BOMB = 2;

		public static float GetSpeed(int type) {
			
			float pSpeed = 0.0f;
			
			switch (type) {
			case BOMB -> pSpeed = 8.0f;
			case ARROW -> pSpeed = 4.0f;
			case LIGHTBALL -> pSpeed = 6.0f;

			}
			return pSpeed;
		}
	}
}
