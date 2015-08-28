package maps;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Map {

	private static final int BOX_SIZE = 10;
	
	
	/**
	 * Draws the Map onto the graphics pane using the canvas width and height 
	 * as references to allow it to be resizeable and scalable. 
	 * @param g
	 * @param canvasWidth
	 * @param canvasHeight
	 */
	abstract void draw(Graphics2D g,int canvasWidth, int canvasHeight);
	
	/**
	 * Asks the map if the given polygon intercepts the bounding boxes
	 * of the map.  
	 * @param poly
	 * @return
	 */
	abstract boolean contains(Rectangle rect);
	//abstract boolean assessRule(Player p);
	
	/**
	 * Moves this map by the given delta x,y.
	 * To be used for shifting the map to the left.
	 * @param deltaX
	 * @param deltaY
	 */
	abstract void translate(int deltaX, int deltaY);
	
	int getDrawBoxSize(int canvasWidth, int canvasHeight){
		return canvasHeight/BOX_SIZE;
	}
}
