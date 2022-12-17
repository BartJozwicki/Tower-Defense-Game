package userInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Slider {
	
	public int x, y, width, height, id;
	private String name;
	private Rectangle boundsR;
	private boolean mouseOver, mousePressed, mouseDragged;
	
	public Slider(String name, int x, int y, int width, int height) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.name = name;
		this.id = -1;
		initBounds();
	}
	private void initBounds() {
		this.boundsR = new Rectangle(x, y, width, height);
	}
	
	public void draw(Graphics g) {
		
		drawBody(g);
		drawBorder(g);
		drawName(g);
	}
	
	private void drawName(Graphics g) {
		
		g.setColor(Color.black);
		g.setFont(new Font("MV Boli", Font.BOLD, 30));
		g.drawString(name, x + width / 2 - 50 , y - height);
		
	}
	private void drawBorder(Graphics g) {
		
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
		
	
	}
	

	
	private void drawBody(Graphics g) {
        
		if(mouseOver == true) {
		
		}
		else {
		g.setColor(new Color(102,51,0));
		}
		g.fillRect(x, y, width, height);

	}
	
	public int getID()
	{
		return this.id;
	}
	
	public Rectangle getBounds() {
		return boundsR;
	}
	
	public boolean isMouseOver() {
		return mouseOver;
	}
	
	public boolean isMousePressed() {
		return mousePressed;
	}
	
	public boolean isMouseDragged() {
		return mouseDragged;
	}
	
	public void setMousePressed(boolean mouseClicked) {
		this.mousePressed = mouseClicked;
	}
	
	public void setMouseOver(boolean mouseOver)
	{
			this.mouseOver = mouseOver;
	
	}
	
	public void resetBooleans() {
		this.mouseOver = false;
		this.mousePressed = false;
		this.mouseDragged = false;
	}
	

}
