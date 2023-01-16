package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import Main.GameStates;
import Main.Main;

import static Main.GameStates.*;

public class KeyboardListener implements KeyListener {

	private Main game;
	
	public KeyboardListener(Main game){
		this.game = game;
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	    if(EDIT == GameStates.gameState) {
	    	game.getEditor().keyPressed(e);
	    }
	    if(TUTORIAL == GameStates.gameState) {
	    	game.getTutorial().keyPressed(e);
	
	    }
	    
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		
	}

}
