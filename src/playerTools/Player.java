package playerTools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

public class Player {

	// Player positions and dimension
	private int posX; // should be constant as the background is moving, not the player
	private int posY;
	private int width;
	private int height;
	private int boxSize; // temp var
	private Rectangle boundingBox = new Rectangle(new Point(0,0));

	// Player images
	private Image[] runImages;
	private Image[] jumpImages;
	private Image[] grappleImages;

	// Physics helpers
	private final int initJumpSpeed = 4;
	private double vertSpeed;
	private double grappleAngle;
	private double pullSpeed;

	// Player actions
	private boolean isJumping;
	private boolean isPullGrapple;
	private boolean isSwingGrapple;

	public Player(){
		width = 40;
		height = 40;
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

	public void move(int canvasHeight){
		if(isJumping){
			posY = Physics.moveJump(posX,posY,vertSpeed);
			vertSpeed = Physics.fallSpeed(vertSpeed);
			System.out.println(posY);
			if(posY <= 0){
				System.out.println("OMG");
				posY = 0;
				isJumping = false;
			}

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

	public void draw(Graphics g, int canvasHeight, int canvasWidth){
		g.setColor(Color.pink);
		boxSize = canvasHeight/10;
		boundingBox = new Rectangle(boxSize/2, boxSize*9 - boxSize-1 - posY, boxSize, boxSize);
		g.fillRect(boundingBox.x,boundingBox.y,boundingBox.width,boundingBox.height);
		move(canvasHeight);
		//g.fillRect(x, y, canvasWidth, canvasHeight);
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
