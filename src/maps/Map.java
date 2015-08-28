package maps;

import java.awt.Graphics;
import java.awt.Point;
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
	abstract public void draw(Graphics g,int canvasWidth, int canvasHeight);

	/**
	 * Asks the map if the given polygon intercepts the collision boxes
	 * of the map.
	 * @param poly
	 * @return
	 */
	abstract public boolean intersects(Rectangle rect);

	/**
	 * Asks the map if the given point intercepts the
	 * collision boxes of the map
	 * @param p
	 * @return
	 */
	abstract public boolean intersects(Point p);
	/**
	 * Asks the map if the player is currently achieving the selected rule.
	 *
	 * @param p
	 * @return - true if they are obeying
	 */
	//abstract boolean assessRule(Player p);

	/**
	 * Moves this map by the given delta x,y.
	 * To be used for shifting the map to the left.
	 * @param deltaX
	 * @param deltaY
	 */
	abstract public void translate(int deltaX, int deltaY);

	int getDrawBoxSize(int canvasWidth, int canvasHeight){
		return canvasHeight/BOX_SIZE;
	}
}
