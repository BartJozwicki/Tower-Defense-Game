package userInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Slider {
	
	public int x, y, width, height, id, mouseX, mouseY, sliderX, sliderY;
	private String name;
	private Rectangle boundsR;
	private boolean mouseOver, mousePressed, mouseDragged, mouseClicked;
	
	
	
	public Slider(String name, int x, int y, int width, int height) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.name = name;
		this.id = -1;
		mouseX = x+width;
		initBounds();
	}
	private void initBounds() {
		this.boundsR = new Rectangle(x, y, width, height);
	}
	
	public void draw(Graphics g) {
		
		drawBody(g);
		drawBorder(g);
		drawName(g);
		drawSlider(g);
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
	
	private void drawSlider(Graphics g) {
		
		g.drawRect(mouseX, this.y, 10, height);
		g.fillRect(mouseX, this.y, 10, height);
	}
	

	
	
	private void drawBody(Graphics g) {
        
		if(mouseOver == true) {
		   g.setColor(new Color(112,71,0));
		  
		}
		else {
		   g.setColor(new Color(102,51,0));
		 
		}
		
		g.fillRect(x, y, width, height);

		if(mouseClicked) {
		
			mouseClicked = false;
		
		}
	}
	
	public float getFilling() {
		//position X as % of the slider width
		float posX = (float)(mouseX-this.x) / (float)(width);
		
		return posX;
	}
	
	public Rectangle getBounds() {
		return boundsR;
	}
	
	public boolean isMouseClicked() {
		return mouseClicked;
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

	
	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}
	
	public void setMouseOver(boolean mouseOver){
			this.mouseOver = mouseOver;
	
	}
	public void setMouseDragged(boolean mouseDragged) {
		this.mouseDragged = mouseDragged;
	}
	
	public void setMouseClicked(boolean mouseClicked) {
		this.mouseClicked = mouseClicked;
	}
	
	
	public int getID(){
		return this.id;
	}
	public void resetBooleans() {
		this.mouseOver = false;
		this.mousePressed = false;
		this.mouseDragged = false;
		this.mouseClicked = false;

	}
	public void setMousePos(int x, int y) {
		this.mouseX = x;
		this.mouseY = y;	
	}
	
	
	

}
