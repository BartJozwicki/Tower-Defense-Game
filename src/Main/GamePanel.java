package Main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import inputs.KeyboardListener;
import inputs.MyMouseListener;

//Contents inside the frame
@SuppressWarnings("serial")
public class GamePanel extends JPanel{


    private MyMouseListener myMouseListener;
    private KeyboardListener keyListener;
    private Dimension size;
    private Main game;
    
	GamePanel(Main game){
		
		this.game = game;
		setPanelSize();
        
	}
	
	public void initInputs() {

		myMouseListener = new MyMouseListener(game);
		keyListener = new KeyboardListener(game);

		addMouseListener(myMouseListener);
		addMouseMotionListener(myMouseListener);
		addKeyListener(keyListener);

		requestFocusInWindow();

	}
	
	private void setPanelSize() {
	
		size = new Dimension(640, 800);
		
		//setDoubleBuffered(true);
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
	}

	
	
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		game.getRender().render(g);
     	
	}




	

	
}
