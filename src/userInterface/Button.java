package userInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Button {

	public int x, y, width, height, id;
	private String text;
	private Rectangle bounds; // to check if mouse is within bounds
	private boolean mouseOver, mousePressed;
	
	//reg Buttons
	public Button(String text, int x, int y, int width, int height) {
		
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
        this.id = -1;
        
		initBounds();
	}
	
	public void setText(String text) {
		this.text = text;
	}
	//tile Buttons
	public Button(String text, int x, int y, int width, int height, int id) {
	
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;

		initBounds();
	}
	private void initBounds() {
		this.bounds = new Rectangle(x, y, width, height);
	}
	
	public void draw(Graphics g) {
		
		//Body
		drawBody(g);
		
		
		//Border
		drawBorder(g);
		
		
		//Text
		drawText(g);
		
		
	}

	private void drawBorder(Graphics g) {
		
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
		
		if(mousePressed) {
			g.drawRect(x + 1, y + 1, width - 2, height - 2);
		    g.drawRect(x + 2, y + 2, width - 4, height - 4);
		}
	}
	
	public void setMousePressed(boolean mouseClicked) {
		this.mousePressed = mouseClicked;
	}

	private void drawBody(Graphics g) {
        
		if(mouseOver == true) {
			g.setColor(Color.GRAY);
		}
		else {
		g.setColor(Color.WHITE);
		}
		g.fillRect(x, y, width, height);

	}
	
	public void setMouseOver(boolean mouseOver)
	{
			this.mouseOver = mouseOver;
	
	}

	private void drawText(Graphics g) {
		
		g.setFont(new Font("Consolas", Font.ROMAN_BASELINE, 15));
		int w = g.getFontMetrics().stringWidth(text);
		int h = g.getFontMetrics().getHeight();
	
		g.drawString(text, x - w / 2 + width / 2, y + height / 2 + h / 2 );
		
		
	}

	public Rectangle getBounds() {
		return bounds;
	}
	
	public void resetBooleans() {
		this.mouseOver = false;
		this.mousePressed = false;
	}
	
	public boolean isMouseOver() {
		return mouseOver;
	}
	
	public boolean isMousePressed() {
		return mousePressed;
	}
	public int getID()
	{
		return this.id;
	}
	
}
