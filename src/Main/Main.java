package Main;

import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import helper.LoadSave;
import managers.TileManager;
import scenes.Edit;
import scenes.GameOver;
import scenes.GameWon;
import scenes.Menu;
import scenes.Play;
import scenes.Settings;
import scenes.Tutorial;

/* Class used to define UPS, FPS, implements game loop, controls scenes */
@SuppressWarnings("serial")
public class Main extends JFrame implements Runnable {

	private GamePanel gPanel;
	private Thread gameThread;

	private final double FPS = 60.0;
	private final double UPS = 120.0;

	// Classes
	private Render render;
	private Menu menu;
	private Tutorial tutorial;
	private Play play;
	private Settings settings;
	private Edit edit;
	private TileManager tileManager;
	private GameOver gameOver;
	private GameWon gameWon;
	private Sound sound;

	// Game Icon
	private static BufferedImage Img1;

	public Main(String gameName) {

		// Create new folder to store user made maps
		LoadSave.CreateFolder();

		// Create defualt map if no map is detected on the disc
		createDefaultLevel();

		initClasses();

		add(gPanel);
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// frame Icon
		Img1 = LoadSave.getSpriteAtlas().getSubimage(32 * 4, 32, 32, 32);

		// music
		sound.soundOn();
	}

	private void initClasses() {

		// Tile Manager
		tileManager = new TileManager();

		// Rendering
		render = new Render(this);

		// Graphical panel
		gPanel = new GamePanel(this);

		// Music/Sounds
		sound = new Sound();

		// 6 game states
		menu = new Menu(this);
		play = new Play(this);
		tutorial = new Tutorial(this);
		settings = new Settings(this);
		edit = new Edit(this);
		gameOver = new GameOver(this);
		gameWon = new GameWon(this);

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

	// Game loop for accurate timing
	@Override
	public void run() {

		double timePerUpdate = 1000000000.0 / UPS;
		double timePerFrame = 1000000000.0 / FPS;

		long lastFrame = System.nanoTime();
		long lastUpdate = System.nanoTime();
		long lastTimeCheck = System.currentTimeMillis();

		@SuppressWarnings("unused")
		int frames = 0;
		@SuppressWarnings("unused")
		int updates = 0;

		long now;

		while (true) {

			now = System.nanoTime();
			// Render visuals
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
				// System.out.println("FPS: " + frames + " UPS: " + updates);

				frames = 0;
				updates = 0;
				lastTimeCheck = System.currentTimeMillis();
			}
		}

	}

	// Takes care of movement, mechanics,
	// 120 times a second
	private void updateGame() {

		switch (GameStates.gameState) {
		case EDIT -> edit.update();
		case TUTORIAL -> tutorial.update();
		case MENU -> menu.update();
		case PLAYING -> play.update();
		case SETTINGS -> settings.update();
		case GAME_OVER -> gameOver.update();
		case GAME_WON -> gameWon.update();
		}

	}

	private void createDefaultLevel() {

		int[] arr = new int[400];

		for (int i = 0; i < arr.length; i++) {
			arr[i] = 0;
		}

		LoadSave.CreateLevel(arr);
	}

	public Tutorial getTutorial() {
		return tutorial;
	}

	public Sound getSound() {
		return sound;
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

	public GameWon getGameWon() {
		return gameWon;
	}

	public GamePanel getPanel() {
		return gPanel;
	}

}
