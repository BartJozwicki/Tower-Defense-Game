package scenes;

import java.awt.image.BufferedImage;

import Main.Main;

public class GameScene {

	protected Main game;
	protected int animationIndex;
	protected int WATER_ANIMATION_SPEED = 25;
	protected int tick;
	
	
	public GameScene(Main game) {
	
		this.game = game;

	}
	
	public Main getGame() {
		return game;
	}
	
	protected void updateTick() {
		
		tick++;
		
		if(tick >= WATER_ANIMATION_SPEED) {
			tick = 0;
			animationIndex++;
			if(animationIndex >= 4)
			{
				animationIndex = 0;
			}
		}
		
	}
	
	protected boolean isAnimation(int spriteID) {
		return game.getTileManager().isSpriteAnimation(spriteID);
	}
	
	protected BufferedImage getSprite(int spriteID) {
		return game.getTileManager().getSprite(spriteID);
	}
	
	protected BufferedImage getSprite(int spriteID, int animationIndex) {
		return game.getTileManager().getAnimationSprite(spriteID, animationIndex);
	}
	
}
