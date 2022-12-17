package userInterface;

import static Main.GameStates.MENU;
import static Main.GameStates.SetGameState;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import helper.LoadSave;
import objects.Tile;
import scenes.Edit;

public class ToolBar extends Bar {

	private Edit edit;
	private Button bMenu, bSave;
	private Button bPathStart, bPathEnd;
	private BufferedImage pathStart, pathEnd;
	private Tile selectedTile;

	private HashMap<Button, ArrayList<Tile>> map = new HashMap<Button, ArrayList<Tile>>();
	
	private Button bGrass, bWater, bRoadS, bRoadC, bWaterC, bWaterB, bWaterI;
	private Button currentButton;
	private int currentIndex = 0;
	
	public ToolBar(int x, int y, int width, int height, Edit edit) {
		super(x, y, width, height);
		this.edit = edit;
		initPathImgs();
		initButtons();

	}
	
	private void initPathImgs() {
		pathStart = LoadSave.getSpriteAtlas().getSubimage(7 * 32, 2 * 32, 32, 32);
		pathEnd = LoadSave.getSpriteAtlas().getSubimage(8 * 32, 2 * 32, 32, 32);
	}

	private void initButtons() {
		bMenu = new Button("Menu", 2, 642, 100, 30);
		bSave = new Button("Save", 2, 674, 100, 30);
		
		int w = 50;
		int h = 50;
		int xStart = 105;
		int yStart = 642;
		int xOffset = (int) (w * 1.05f);
		int i = 0;
		
        bGrass = new Button("Grass", xStart, yStart, w, h, i++);
        bWater = new Button("Water", xStart + xOffset, yStart, w, h, i++);
        
        
        initMapButton(bRoadS, edit.getGame().getTileManager().getRoadsS(), xStart, yStart, xOffset, w, h, i++);
        initMapButton(bRoadC, edit.getGame().getTileManager().getRoadsC(), xStart, yStart, xOffset, w, h, i++);
        initMapButton(bWaterC, edit.getGame().getTileManager().getCorners(), xStart, yStart, xOffset, w, h, i++);
        initMapButton(bWaterB, edit.getGame().getTileManager().getBeaches(), xStart, yStart, xOffset, w, h, i++);
        initMapButton(bWaterI, edit.getGame().getTileManager().getIslands(), xStart, yStart, xOffset, w, h, i++);
        bPathStart = new Button("PathStart", xStart, yStart + xOffset, w, h, i++);
        bPathEnd = new Button("PathEnd", xStart+ xOffset , yStart + xOffset, w, h, i++);
	}
	
	private void initMapButton(Button b, ArrayList<Tile> list, int x, int y, int xOff, int w, int h, int id) {
		
		b = new Button("", x + xOff * id, y, w, h, id);
		map.put(b, list);
		 
	}
	
	public void draw(Graphics g) {
		g.setColor(new Color(220, 123, 15)); //g.setColor(new Color(220, 123, 15));
		g.fillRect(x, y, width, height);
		
		drawButtons(g);
	}
	
	private void drawButtons(Graphics g) {
		
		bMenu.draw(g);
		bSave.draw(g);
		
		drawPathButton(g, bPathStart, pathStart);
		drawPathButton(g, bPathEnd, pathEnd);

        drawBasicButtons(g, bGrass);
        drawBasicButtons(g, bWater);
		drawSelectedTile(g);
		drawMapButtons(g);
	}


	private void drawPathButton(Graphics g, Button b, BufferedImage img) {
           g.drawImage(img, b.x, b.y, b.width, b.height, null);
           drawButtonResponse(g, b);
		
	}

	private void drawBasicButtons(Graphics g, Button b) {
		
		g.drawImage(getBuffImg(b.getID()), b.x, b.y, b.width, b.height, null);
		drawButtonResponse(g, b);
	}

	private void drawMapButtons(Graphics g) {

		for (Map.Entry<Button, ArrayList<Tile>> entry : map.entrySet()) {
			
			Button b = entry.getKey();
			BufferedImage img = entry.getValue().get(0).getSprite();
			
			g.drawImage(img, b.x, b.y, b.width, b.height, null);
            drawButtonResponse(g, b);

		}

	}


	
	private void drawSelectedTile(Graphics g) {


		if(selectedTile != null) {
			
			g.drawImage(selectedTile.getSprite(), 550, 642, 50, 50, null);
			g.setColor(Color.BLACK);
			g.drawRect(550, 642, 50, 50);
		}
		
	}

	
	public BufferedImage getBuffImg(int id) {
		return edit.getGame().getTileManager().getSprite(id);
	}

	public void rotateSprite() {

		currentIndex++;
		
		if (currentIndex >= map.get(currentButton).size()) {
			currentIndex = 0;
		}
		selectedTile = map.get(currentButton).get(currentIndex);
		edit.setSelectedTile(selectedTile);
	}
        

	private void saveLevel() {

		edit.saveLevel();

	}
	
	public BufferedImage getStartPathImg() {
		return pathStart;
	}
	public BufferedImage getEndPathImg() {
		return pathEnd;
	}
	

	public void mouseClicked(int x, int y) {
 
        if(bMenu.getBounds().contains(x, y)) {
        	SetGameState(MENU);
        }
        else if(bSave.getBounds().contains(x, y)) {
        	saveLevel();
        }
    	else if (bGrass.getBounds().contains(x, y))
		{
    	selectedTile = edit.getGame().getTileManager().getTile(bGrass.getID());
    	edit.setSelectedTile(selectedTile);
    	return;
		}
		else if (bWater.getBounds().contains(x, y))
		{
		selectedTile = edit.getGame().getTileManager().getTile(bWater.getID());
		edit.setSelectedTile(selectedTile);
		return;
		}
        
		else if(bPathStart.getBounds().contains(x, y))
		{
			selectedTile = new Tile(pathStart, -1, -1);
			//selectedTile = edit.getGame().getTileManager().getTile(bPathStart.getID());
		edit.setSelectedTile(selectedTile);
		}
		else if(bPathEnd.getBounds().contains(x, y))
		{
			selectedTile = new Tile(pathEnd, -2, -2);
			//selectedTile = edit.getGame().getTileManager().getTile(bPathStart.getID());
		edit.setSelectedTile(selectedTile);
		}
        else {
			
			  for(Button b : map.keySet()) {
				  if(b.getBounds().contains(x, y))
				  {
				  selectedTile = map.get(b).get(0);
                  edit.setSelectedTile(selectedTile);
                  currentButton = b;
                  currentIndex = 0;
                  return;
				  }
			  }
        }
		
	}

	public void mouseMoved(int x, int y) {
		
		bMenu.setMouseOver(false);
		bSave.setMouseOver(false);
        bGrass.setMouseOver(false);
        bWater.setMouseOver(false);
        bPathEnd.setMouseOver(false);
        bPathStart.setMouseOver(false);
        
		  for(Button b : map.keySet()) {
			  b.setMouseOver(false);
		  }

		if (bMenu.getBounds().contains(x, y)) {
			bMenu.setMouseOver(true);
		}
	    else if(bSave.getBounds().contains(x, y)) {
        	bSave.setMouseOver(true);
	    }
		else if (bGrass.getBounds().contains(x, y))
		{
			bGrass.setMouseOver(true);
		}
		else if (bWater.getBounds().contains(x, y))
		{
			bWater.setMouseOver(true);
		}
		else if(bPathStart.getBounds().contains(x, y))
		{
			bPathStart.setMouseOver(true);
		}
		else if(bPathEnd.getBounds().contains(x, y))
		{
			bPathEnd.setMouseOver(true);
		}
		else {
			  for(Button b : map.keySet()) {
				 if(b.getBounds().contains(x, y)) {
					 b.setMouseOver(true);
					 return;
				 }
			  }

		}

	}


	public void mousePressed(int x, int y) {

		if (bMenu.getBounds().contains(x, y)) {
			
			bMenu.setMousePressed(true);
		} 
		else if (bSave.getBounds().contains(x, y)) 
		{
			bSave.setMousePressed(true);
		}
		else if (bGrass.getBounds().contains(x, y))
		{
			bGrass.setMousePressed(true);
		}
		else if (bWater.getBounds().contains(x, y))
		{
			bWater.setMousePressed(true);
		}
		else if(bPathStart.getBounds().contains(x, y))
		{
			bPathStart.setMousePressed(true);
		}
		else if(bPathEnd.getBounds().contains(x, y))
		{
			bPathEnd.setMousePressed(true);
		}
		

		else 
		{
			  for(Button b : map.keySet()) {
				 if(b.getBounds().contains(x, y))
				 {
				 b.setMousePressed(true);
				 return;
				 }
			  }
		}

	}

	public void mouseReleased(int x, int y) {
		
		resetButton();
		for(Button b: map.keySet())
			b.resetBooleans();
		
	}

	private void resetButton() {
		bMenu.resetBooleans();
		bSave.resetBooleans();
		bGrass.resetBooleans();
		bWater.resetBooleans();
		bPathEnd.resetBooleans();
		bPathStart.resetBooleans();
		
	}

	

}
