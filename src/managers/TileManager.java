package managers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import helper.ImageEditor;
import helper.LoadSave;
import objects.Tile;
import static helper.Constants.Tiles.*;



public class TileManager {
	
	public Tile GRASS, WATER, ROAD_LR, ROAD_TB, ROAD_B_TO_R, ROAD_L_TO_B, ROAD_L_TO_T, ROAD_T_TO_R, BL_WATER_CORNER, TL_WATER_CORNER, TR_WATER_CORNER, BR_WATER_CORNER, T_WATER, R_WATER, B_WATER,
	L_WATER, TL_ISLE, TR_ISLE, BR_ISLE, BL_ISLE;
	
	public BufferedImage atlas;
	public ArrayList<Tile> tiles = new ArrayList<>();
	
	public ArrayList<Tile> roadsS = new ArrayList<>();
	public ArrayList<Tile> roadsC = new ArrayList<>();
	public ArrayList<Tile> corners = new ArrayList<>();
	public ArrayList<Tile> beaches = new ArrayList<>();
	public ArrayList<Tile> islands = new ArrayList<>();
	
	public TileManager() {
	   loadAtlas();
	   createTiles();
	}

	private void createTiles() {
		 //X, Y
		int id = 0;
		tiles.add(GRASS = new Tile(getSprite(9, 0), id++, GRASS_TILE));
		tiles.add(WATER = new Tile(getAnimationSprites(0, 0), id++, WATER_TILE));
		
		roadsS.add(ROAD_LR = new Tile(getSprite(8, 0), id++, ROAD_TILE));
		roadsS.add(ROAD_TB = new Tile(ImageEditor.getRotImage(getSprite(8, 0), 90), id++, ROAD_TILE));
		
		roadsC.add(ROAD_B_TO_R = new Tile(getSprite(7, 0), id++, ROAD_TILE));
		roadsC.add(ROAD_L_TO_B = new Tile(ImageEditor.getRotImage(getSprite(7, 0), 90), id++, ROAD_TILE));
		roadsC.add(ROAD_L_TO_T = new Tile(ImageEditor.getRotImage(getSprite(7, 0), 180), id++, ROAD_TILE));
		roadsC.add(ROAD_T_TO_R = new Tile(ImageEditor.getRotImage(getSprite(7, 0), 270), id++, ROAD_TILE));
	                                                                        //1st img, 2nd img, rotation
		corners.add(BL_WATER_CORNER = new Tile(ImageEditor.getBuildRotImage(getAnimationSprites(0, 0), getSprite(5, 0), 0), id++, WATER_TILE));
		corners.add(TL_WATER_CORNER = new Tile(ImageEditor.getBuildRotImage(getAnimationSprites(0, 0), getSprite(5, 0), 90),id++, WATER_TILE));
		corners.add(TR_WATER_CORNER = new Tile(ImageEditor.getBuildRotImage(getAnimationSprites(0, 0), getSprite(5, 0), 180), id++, WATER_TILE));
		corners.add(BR_WATER_CORNER = new Tile(ImageEditor.getBuildRotImage(getAnimationSprites(0, 0), getSprite(5, 0), 270), id++, WATER_TILE));
		
		
		beaches.add(T_WATER = new Tile(ImageEditor.getBuildRotImage(getAnimationSprites(0, 0), getSprite(6, 0), 0), id++, WATER_TILE));
		beaches.add(R_WATER = new Tile(ImageEditor.getBuildRotImage(getAnimationSprites(0, 0), getSprite(6, 0), 90), id++, WATER_TILE));
		beaches.add(B_WATER = new Tile(ImageEditor.getBuildRotImage(getAnimationSprites(0, 0), getSprite(6, 0), 180), id++, WATER_TILE));
		beaches.add(L_WATER = new Tile(ImageEditor.getBuildRotImage(getAnimationSprites(0, 0), getSprite(6, 0), 270), id++, WATER_TILE));
		
		islands.add(TL_ISLE = new Tile(ImageEditor.getBuildRotImage(getAnimationSprites(0, 0), getSprite(4, 0), 0), id++, WATER_TILE));
		islands.add(TR_ISLE = new Tile(ImageEditor.getBuildRotImage(getAnimationSprites(0, 0), getSprite(4, 0), 90), id++, WATER_TILE));
		islands.add(BR_ISLE = new Tile(ImageEditor.getBuildRotImage(getAnimationSprites(0, 0), getSprite(4, 0), 180), id++, WATER_TILE));
		islands.add(BL_ISLE = new Tile(ImageEditor.getBuildRotImage(getAnimationSprites(0, 0), getSprite(4, 0), 270), id++, WATER_TILE));
		
		
		//watch-out if you insert them in a wrong order everything is messed...

		tiles.addAll(roadsS);
		tiles.addAll(roadsC);
		tiles.addAll(corners);
		tiles.addAll(beaches);
		tiles.addAll(islands);
		
		
	}


	private void loadAtlas() {
		
		atlas = LoadSave.getSpriteAtlas();
		
	}
	
	public Tile getTile(int id) {
		return tiles.get(id);
	}
	public BufferedImage getSprite(int id) {
		return tiles.get(id).getSprite();
	}
	
	public BufferedImage getAnimationSprite(int id, int animationIndex) {
		return tiles.get(id).getSprite(animationIndex);
	}
	
public BufferedImage[] getAnimationSprites(int xCord, int yCord) {
		
	//fixed size 4 atm can be adjusted
	BufferedImage[] arr = new BufferedImage[4];
	for(int i = 0; i < 4; i++)
	{
		arr[i] = getSprite(xCord + i, yCord);
	}
	return arr;
		
	}

	public BufferedImage getSprite(int xCord, int yCord) {
		
		return atlas.getSubimage(xCord*32, yCord*32, 32, 32);
	}

	public boolean isSpriteAnimation(int spriteID) {
		return tiles.get(spriteID).isAnimation();
	}
	
	public ArrayList<Tile> getRoadsS() {
		return roadsS;
	}

	public ArrayList<Tile> getRoadsC() {
		return roadsC;
	}

	public ArrayList<Tile> getCorners() {
		return corners;
	}

	public ArrayList<Tile> getBeaches() {
		return beaches;
	}

	public ArrayList<Tile> getIslands() {
		return islands;
	}



}
