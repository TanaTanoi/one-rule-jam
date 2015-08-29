package playerTools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

import Game.Game;

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
	private final int initJumpSpeed = 7;
	private double vertSpeed;
	private double grappleAngle;
	private double pullSpeed;
	private int grappleX;
	private int swingLength;

	// Player actions
	private boolean isJumping;
	private boolean isPullGrapple;
	private boolean isSwingGrapple;

	private Game game;


	public Player(Game game){
		this.game = game;
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

	public void setFalling(){
		isPullGrapple = false;
		isSwingGrapple = false;
		isJumping = true;
	}

	public void setLanded(int posY){
		isJumping = false;
		vertSpeed = 0;
		this.posY = 450-posY;
	}

	// Need to think about if they still are 'running' while the grapple hook is attaching

	/**
	 * Responsible for setting up grapple pulling motion
	 */
	public boolean pullGrapple(int x, int y){
		if(!isGrappling()){
			double centreX = boundingBox.getCenterX();
			double centreY = boundingBox.getCenterY();


			if(x > centreX){ // should be only able to go forwards and upwards
				pullSpeed = 2;
				grappleAngle = Physics.calculateGrappleAngle(centreX,centreY,x, y);
				grappleX = Physics.calculateConnectPoint(centreX, centreY, x, y, boxSize);
				isPullGrapple = true;
				isJumping = false;
				return true;
			}
		}
		return false;
	}

	/**
	 * Responsible for setting up jumping motion
	 */
	public boolean jump(){
		if(!inAction()){
			isJumping = true;
			vertSpeed = initJumpSpeed;
			return true;
		}
		return false;
	}

	/**
	 * Responsible for setting up grapple swinging motion
	 * @return
	 */
	public boolean swingGrapple(int x, int y){
		if(!isGrappling()){
			double centreX = boundingBox.getCenterX();
			double centreY = boundingBox.getCenterY();

			if(x > centreX && isJumping){ // should be only able to go forwards and upwards
				//grappleAngle = Physics.calculateGrappleAngle(centreX,centreY,x, y);
				grappleX = Physics.calculateConnectPoint(centreX, centreY, x, y, x);
				swingLength = Physics.calculateSwingRopeLength(centreX,centreY,grappleX,boxSize);
				isSwingGrapple = true;
				isJumping = false;
				System.out.println("Actaully grappling");
				return true;
			}
		}
		return false;
	}

	public void move(int canvasHeight, int canvasWidth){
		if(isJumping){
			int newPosY = Physics.moveJump(posX,posY,vertSpeed);
			double newVertSpeed = Physics.fallSpeed(vertSpeed);

			if (!game.intersectsCurrentMap(boundingBox,boxSize*9-10-newPosY)){
				posY = newPosY;
				vertSpeed = newVertSpeed;
			}else{//has collided
				isJumping = false;
			}
			/*if(posY <= 0){
				//System.out.println("OMG");
				//posY = 0;
				isJumping = false;
			}*/

		}
		else if(isPullGrapple){
			// need a check in here to see whether to release the grapple hook
			// at that point maybe it needs to be changed to isJumping so it falls,
			// vertSpeed should be zero and not grappling anymore
			posY = Physics.movePullGrapple(posX,posY,pullSpeed,grappleAngle);
			if(posY > 6*boxSize || grappleX <= boxSize/2){
				vertSpeed = 0;
				isPullGrapple = false;
				isJumping = true;
			}
		}
		else if(isSwingGrapple){
			posY = Physics.moveSwingGrapple(posY, grappleX, canvasWidth, canvasHeight, swingLength, boxSize);
			System.out.println("Swing move " + posY);

			if(grappleX <= 0 || posY >= boxSize*6){
				vertSpeed = 0;
				isSwingGrapple = false;
				isJumping = true;
			}
		}
		else{

			double newVertSpeed = Physics.fallSpeed(0);
			int newPosY = Physics.moveJump(posX,posY,newVertSpeed);

			if (!game.intersectsCurrentMap(boundingBox,boxSize*9-10-newPosY)){
				posY = newPosY;
				vertSpeed = newVertSpeed;
			}else{//has collided
				vertSpeed/=2;
			}
		}

		// Need a check here that is intersection point is too low (i.e. below the ground)
		// that will place the player at the lowest possible point (i.e. ground level)
	}

	public void draw(Graphics g, int canvasHeight, int canvasWidth){
		g.setColor(Color.pink);
		boxSize = canvasHeight/10;
		boundingBox = new Rectangle(boxSize/2, boxSize*8-10 - posY, boxSize, boxSize);
		g.fillRect(boundingBox.x,boundingBox.y,boundingBox.width,boundingBox.height);
		move(canvasHeight, canvasWidth);
		//g.fillRect(x, y, canvasWidth, canvasHeight);
		if(isJumping){

		}
		if(isPullGrapple){
			g.setColor(Color.YELLOW);
			g.drawLine((int)boundingBox.getCenterX(), (int)boundingBox.getCenterY(), grappleX, boxSize);
			grappleX--;
		}
		else if(isSwingGrapple){
			g.setColor(Color.YELLOW);
			g.drawLine((int)boundingBox.getCenterX(), (int)boundingBox.getCenterY(), grappleX, boxSize);
			grappleX--;
		}
		else{

		}
	}

	public Point nextPoint(){
		int newPosY = Physics.moveJump(posX,posY,vertSpeed);
		return new Point(posX,newPosY);
	}

}
