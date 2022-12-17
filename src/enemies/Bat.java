package enemies;
import static helper.Constants.Enemies.*;

import managers.EnemyManager;

public class Bat extends Enemy{

	public Bat(float x, float y, int id, EnemyManager em) {
		
		super(x, y, id, BAT, em);

	}

}
