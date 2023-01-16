package enemies;

import managers.EnemyManager;
import static helper.Constants.Enemies.*;

public class FirstBoss extends Enemy{

	public FirstBoss(float x, float y, int id, EnemyManager enemyManager) {
		
		super(x, y, id, FIRST_BOSS, DEAD_BOSS, enemyManager);
		
	}

}
