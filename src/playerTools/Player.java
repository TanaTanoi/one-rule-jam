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
	private Image[] runImages;
	private Image[] jumpImages;
	private Image[] grappleImages;

	// Physics helpers
	private double vertSpeed;
	private boolean isJumping;
	private boolean isPullGrapple;
	private boolean isSwingGrapple;

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
		if(isJumping){
			Physics.moveJump(posX,posY,vertSpeed);
			vertSpeed = Physics.fallSpeed(vertSpeed);
		}
		else if(isPullGrapple){

		}
		else if(isSwingGrapple){

		}
		else{
			Physics.moveRun(posX, posY);
		}

		// Need a check here that is intersection point is too low (i.e. below the ground)
		// that will place the player at the lowest possible point (i.e. ground level)
	}

	public void draw(Graphics g){
		g.drawRect(posX, posY, width, height);
		if(isJumping){

		}
		else if(isPullGrapple){

		}
		else if(isSwingGrapple){

		}
		else{

		}
	}

}
