package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import Main.GameStates;
import Main.Main;

public class MyMouseListener implements MouseListener, MouseMotionListener {

	
	private Main game;
	
	public MyMouseListener(Main game) {
		this.game = game;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.getButton() == MouseEvent.BUTTON1) {
			switch (GameStates.gameState) {

			case MENU -> game.getMenu().mouseClicked(e.getX(), e.getY());
			case TUTORIAL -> game.getTutorial().mouseClicked(e.getX(), e.getY());
			case PLAYING -> game.getPlay().mouseClicked(e.getX(), e.getY());
			case SETTINGS -> game.getSettings().mouseClicked(e.getX(), e.getY());
			case EDIT -> game.getEditor().mouseClicked(e.getX(), e.getY());
			case GAME_OVER -> game.getGameOver().mouseClicked(e.getX(), e.getY());
			case GAME_WON -> game.getGameWon().mouseClicked(e.getX(), e.getY());
			}
		}
		if (e.getButton() == MouseEvent.BUTTON3) {
			switch (GameStates.gameState) {

			case MENU -> game.getMenu().leftClick();
			case TUTORIAL -> game.getMenu().leftClick();
			case PLAYING -> game.getPlay().leftClick();
			case SETTINGS -> game.getSettings().leftClick();
			case EDIT -> game.getEditor().leftClick();
			case GAME_OVER -> game.getGameOver().leftClick();
			case GAME_WON -> game.getGameWon().leftClick();
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

		if (e.getButton() == MouseEvent.BUTTON1) {
			switch (GameStates.gameState) {

			case MENU -> game.getMenu().mousePressed(e.getX(), e.getY());
			case TUTORIAL -> game.getTutorial().mousePressed(e.getX(), e.getY());
			case PLAYING -> game.getPlay().mousePressed(e.getX(), e.getY());
			case SETTINGS -> game.getSettings().mousePressed(e.getX(), e.getY());
			case EDIT -> game.getEditor().mousePressed(e.getX(), e.getY());
			case GAME_OVER -> game.getGameOver().mousePressed(e.getX(), e.getY());
			case GAME_WON -> game.getGameWon().mouseReleased(e.getX(), e.getY());
			}
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			switch (GameStates.gameState) {

			case MENU -> game.getMenu().mouseReleased(e.getX(), e.getY());
			case TUTORIAL -> game.getTutorial().mouseReleased(e.getX(), e.getY());
			case PLAYING -> game.getPlay().mouseReleased(e.getX(), e.getY());
			case SETTINGS -> game.getSettings().mouseReleased(e.getX(), e.getY());
			case EDIT -> game.getEditor().mouseReleased(e.getX(), e.getY());
			case GAME_OVER -> game.getGameOver().mouseReleased(e.getX(), e.getY());
			case GAME_WON -> game.getGameWon().mouseReleased(e.getX(), e.getY());
			}
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
	
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
		
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void mouseDragged(MouseEvent e) {
		
		switch (GameStates.gameState) {

		case MENU -> game.getMenu().mouseDragged(e.getX(), e.getY());	
		case TUTORIAL -> game.getTutorial().mouseDragged(e.getX(), e.getY());
		case PLAYING -> game.getPlay().mouseDragged(e.getX(), e.getY());
		case SETTINGS -> game.getSettings().mouseDragged(e.getX(), e.getY());
		case EDIT -> game.getEditor().mouseDragged(e.getX(), e.getY());
		case GAME_WON -> game.getGameWon().mouseDragged(e.getX(), e.getY());

		
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		switch (GameStates.gameState) {

		case MENU -> game.getMenu().mouseMoved(e.getX(), e.getY());
		case TUTORIAL -> game.getTutorial().mouseDragged(e.getX(), e.getY());
		case PLAYING -> game.getPlay().mouseMoved(e.getX(), e.getY());
		case SETTINGS ->game.getSettings().mouseMoved(e.getX(), e.getY());
		case EDIT -> game.getEditor().mouseMoved(e.getX(), e.getY());
		case GAME_OVER -> game.getGameOver().mouseMoved(e.getX(), e.getY());
		case GAME_WON -> game.getGameWon().mouseMoved(e.getX(), e.getY());
		}
	}
}
