package enemies;
import static helper.Constants.Enemies.*;

import managers.EnemyManager;
public class Bloody_Spearman extends Enemy{

	public Bloody_Spearman(float x, float y, int id, EnemyManager em) {
		
		super(x, y, id, BLOODY_SPEARMAN, DEAD_B_ORC, em);
		
	}

}
