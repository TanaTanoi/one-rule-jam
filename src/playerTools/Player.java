package playerTools;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

public class Player {

	// Player positions and dimension
	private int posX; // should be constant as the background is moving, not the player
	private int posY;
	private int width;
	private int height;
	private Rectangle boundingBox;

	// Player images
	private Image[] runImages;
	private Image[] jumpImages;
	private Image[] grappleImages;

	// Physics helpers
	private final int initJumpSpeed = 3;
	private double vertSpeed;
	private double grappleAngle;
	private double pullSpeed;

	// Player actions
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

	public boolean inAction(){
		return isJumping || isPullGrapple || isSwingGrapple;
	}

	public boolean isGrappling(){
		return isPullGrapple || isSwingGrapple;
	}

	// Need to think about if they still are 'running' while the grapple hook is attaching

	/**
	 * Responsible for setting up grapple pulling motion
	 */
	public void pullGrapple(int x, int y){
		if(!isGrappling()){
			double centreX = boundingBox.getCenterX();
			double centreY = boundingBox.getCenterY();

			if(x > centreX && y > centreY){ // should be only able to go forwards and upwards
				grappleAngle = Physics.calculateGrappleAngle(centreX,centreY,x, y);
				isPullGrapple = true;
			}
		}
	}

	/**
	 * Responsible for setting up jumping motion
	 */
	public void jump(){
		if(!inAction()){
			isJumping = true;
			vertSpeed = initJumpSpeed;
		}
	}

	/**
	 * Responsible for setting up grapple swinging motion
	 */
	public void swingGrapple(int x, int y){
		if(!isGrappling()){
			double centreX = boundingBox.getCenterX();
			double centreY = boundingBox.getCenterY();

			if(x > centreX && y > centreY){ // should be only able to go forwards and upwards
				grappleAngle = Physics.calculateGrappleAngle(centreX,centreY,x, y);
				isPullGrapple = true;
			}
		}
	}

	public void move(){
		if(isJumping){
			posY = Physics.moveJump(posX,posY,vertSpeed);
			vertSpeed = Physics.fallSpeed(vertSpeed);
		}
		else if(isPullGrapple){
			// need a check in here to see whether to release the grapple hook
			// at that point maybe it needs to be changed to isJumping so it falls,
			// vertSpeed should be zero and not grappling anymore
			posY = Physics.movePullGrapple(posX,posY,pullSpeed,grappleAngle);
		}
		else if(isSwingGrapple){
			// grapple angle needs to change
		}
		else{
			//Physics.moveRun(posX, posY);
			// does nothing as the xposition is constant
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
