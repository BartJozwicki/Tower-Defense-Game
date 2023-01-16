package enemies;

import static helper.Constants.Enemies.*;

import managers.EnemyManager;

public class Warrior extends Enemy {

	public Warrior(float x, float y, int id, EnemyManager em) {

		super(x, y, id, WARRIOR, DEAD_WARRIOR, em);

	}

}
