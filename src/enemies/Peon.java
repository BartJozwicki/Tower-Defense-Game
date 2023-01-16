package enemies;

import static helper.Constants.Enemies.*;

import managers.EnemyManager;

public class Peon extends Enemy {

	public Peon(float x, float y, int id, EnemyManager em) {

		super(x, y, id, PEON, DEAD_PEON, em);

	}

}
