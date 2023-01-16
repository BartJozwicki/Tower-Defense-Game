package enemies;

import static helper.Constants.Enemies.*;

import managers.EnemyManager;

public class BloodyWarrior extends Enemy {

	public BloodyWarrior(float x, float y, int id, EnemyManager em) {

		super(x, y, id, BLOODY_WARRIOR, DEAD_B_ORC, em);

	}

}
