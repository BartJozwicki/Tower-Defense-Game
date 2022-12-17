package scenes;


import java.awt.Graphics;


import Main.Main;
import helper.LoadSave;
import userInterface.Button;
import userInterface.Slider;

public class Settings extends GameScene implements SceneMethods{

	Slider soundS;
	private Button bMenu;
	private int[][] lvl;
	
	public Settings(Main game) {
		super(game);
		initButtons();
		soundS = new Slider("Volume", 100, 100, 400, 20);
		lvl = LoadSave.GetLevelDataE("editView");
	}
	

	@Override
	public void render(Graphics g) {
	
	
		drawLevel(g);
		soundS.draw(g);
		bMenu.draw(g);
		
	}
	
	private void initButtons() {

		bMenu= new Button("Menu", 10, 760, 100, 30);


	}

	private void drawLevel(Graphics g) {

		for (int y = 0; y < lvl.length; y++) {
			for (int x = 0; x < lvl[y].length; x++) {
				int id = lvl[y][x];
				g.drawImage(getSprite(id), x * 32, y * 32, null);
				if (isAnimation(id)) {
					g.drawImage(getSprite(id, animationIndex), x * 32, y * 32, null);
				} else {
					g.drawImage(getSprite(id), x * 32, y * 32, null);
				}

			}
		}

	}

	
	@Override
	public void mouseClicked(int x, int y) {
		
		
	}

	@Override
	public void mouseMoved(int x, int y) {
		bMenu.setMouseOver(false);
		
		if(bMenu.getBounds().contains(x, y)) {
			bMenu.setMouseOver(true);
		}
	}

	@Override
	public void mousePressed(int x, int y) {
		
		
	}

	@Override
	public void mouseReleased(int x, int y) {
		
		
	}

	@Override
	public void mouseDragged(int x, int y) {
		
		
	}

	@Override
	public void leftClick() {
		
		
	}


}
