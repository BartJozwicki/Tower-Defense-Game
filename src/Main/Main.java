package Main;



import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import helper.LoadSave;
import managers.TileManager;
import scenes.Edit;
import scenes.GameOver;
import scenes.Menu;
import scenes.Play;
import scenes.Settings;

@SuppressWarnings("serial")
public class Main extends JFrame implements Runnable{


	private GamePanel gPanel;
	private Thread gameThread;
	
	private final double FPS = 60.0;
	private final double UPS = 120.0;
	

    
    //Classes
    private Render render;
    private Menu menu;
    private Play play;
    private Settings settings;
    private Edit edit;
	private TileManager tileManager;
	private GameOver gameOver;
	private Sound sound;
	
	//Game Icon
	private static BufferedImage Img1;
	
    
	public Main(String gameName){
    
	
	LoadSave.CreateFolder();
	
    createDefaultLevel();
    initClasses();
    
    
	add(gPanel);
	pack();
	setLocationRelativeTo(null);
	setResizable(false);
	setVisible(true);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	
	//frame Icon
    Img1 = LoadSave.getSpriteAtlas().getSubimage(32*4, 32, 32, 32);
    //music 
	sound.soundOn();
	}
	
	private void initClasses() {
		//Tile Manager
		 tileManager = new TileManager();
		 
		//Rendering
		render = new Render(this);
		
		//Graphical panel
		gPanel = new GamePanel(this);

	
		//3 game states
		menu = new Menu(this);
		play = new Play(this);
		settings = new Settings(this);
		edit = new Edit(this);
		gameOver = new GameOver(this);
		sound = new Sound();
	

	}


	
	private void start() {
		gameThread = new Thread(this);	
		gameThread.start();
	}
	

	public static void main(String args[]) {

	  Main frame = new Main("Tower defense");
	  frame.setIconImage(Img1);
      frame.gPanel.initInputs();
	  frame.start();
     
	  
	}
	
	@Override
	public void run() {

		double timePerUpdate = 1000000000.0 / UPS;
		double timePerFrame = 1000000000.0 / FPS;

		long lastFrame = System.nanoTime();
		long lastUpdate = System.nanoTime();
		long lastTimeCheck = System.currentTimeMillis();

		int frames = 0;
		int updates = 0;

		long now;

		while (true) {

			now = System.nanoTime();
			// Render visual
			if (System.nanoTime() - lastFrame >= timePerFrame) {
				lastFrame = now;
				repaint();
				frames++;

			}

			// Updates IO
			if (System.nanoTime() - lastUpdate >= timePerUpdate) {
				lastUpdate = now;
				updates++;
				updateGame();

			}

			// Check FPS and UPS
			if (System.currentTimeMillis() - lastTimeCheck >= 1000) {
				System.out.println("FPS: " + frames + " UPS: " + updates);

				frames = 0;
				updates = 0;
				lastTimeCheck = System.currentTimeMillis();
			}
		}

	}

	private void updateGame() {
	
		switch(GameStates.gameState) {
		case EDIT:
			edit.update();
			break;
		case MENU:
			break;
		case PLAYING:
			play.update();
			break;
		case SETTINGS:
			break;
		default:
			break;
		
		}
		
	}

	private void createDefaultLevel() {
		
		int[] arr = new int[400];

		for (int i = 0; i < arr.length; i++) {
			arr[i] = 0;
		}
		LoadSave.CreateLevel(arr);
	}

	public Edit getEditor() {
		return edit;
	}
	public Render getRender() {
		return render;
	}

	public Menu getMenu() {
		return menu;
	}

	public Play getPlay() {
		return play;
	}

	public Settings getSettings() {
		return settings;
	}
	
	public TileManager getTileManager() {
		return tileManager;
	}

	public GameOver getGameOver() {
		
		return gameOver;
	}

	
	public GamePanel getPanel() {
		return gPanel;
	}

	
}
