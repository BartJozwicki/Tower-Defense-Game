package Main;
import java.awt.Graphics;

public class Render {

	private Main game;
	

	
	public Render(Main game) {
		this.game = game;	
	}
	

	public void render(Graphics g) {
		
		switch(GameStates.gameState) {
		
		case MENU -> game.getMenu().render(g);
		case PLAYING -> game.getPlay().render(g);
		case SETTINGS ->game.getSettings().render(g);
		case EDIT -> game.getEditor().render(g);
		case GAME_OVER -> game.getGameOver().render(g);
		
		default -> throw new IllegalArgumentException("Unexpected value: " + GameStates.gameState);
		
		}
	}
	

}
