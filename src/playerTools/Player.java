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
	private Rectangle boundingBox;

	// Player images
	private Image[] images;

	// Physics helpers
	private double vertSpeed;

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

	public boolean isJumping(){
		// do something here
	}

	public void move(){
		if(isJumping()){
			Physics.moveJump(posX,posY,vertSpeed);
			vertSpeed = Physics.fallSpeed(vertSpeed);
		}
		else{
			Physics.moveRun(posX, posY);
		}
	}

	public void draw(Graphics g){
		g.drawRect(posX, posY, width, height);
	}

}
