package playerTools;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Player {

	// Player positions and dimension
	private int posX;
	private int posY;
	private int width;
	private int height;

	// Player images
	private Image[] images;

	// Physics helpers
	private int vertSpeed;
	private boolean isJumping;

	private Rectangle boundingBox;

	public Player(){

	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public Rectangle getBoundingBox(){
		return boundingBox;
	}

	public void move(){

	}

	public void draw(Graphics g){
		g.drawRect(posX, posY, width, height);
	}



}
