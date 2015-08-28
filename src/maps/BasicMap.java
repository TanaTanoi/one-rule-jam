package maps;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;

/**
 * A flat map made of two polygons at the top and bottom
 * @author Tana
 *
 */
public class BasicMap extends Map {
	
	Polygon bottom, top;
	//array 1,2 top x,y 3,4 bottom,x,y
	private static final int[][] polyPoints= {{0,0,10,10},{0,1,1,0},{0,0,10,10},{10,9,9,10}};
	
	public BasicMap(){
		
		top = new Polygon(polyPoints[0],polyPoints[1],4);
		bottom = new Polygon(polyPoints[4],polyPoints[3],4);
	}
	
	@Override
	void draw(Graphics2D g, int canvasWidth, int canvasHeight) {
		int boxSize = super.getDrawBoxSize(canvasWidth, canvasHeight);
		int[][] localPolyPoints = polyPoints;
		for(int[] array:localPolyPoints){
			for(int i = 0;i<array.length;i++){
				array[i] = array[i]*boxSize;
			}
		}
		top = new Polygon(localPolyPoints[0],localPolyPoints[1],4);
		bottom = new Polygon(localPolyPoints[4],localPolyPoints[3],4);
		g.setColor(Color.red);
		g.drawPolygon(top);
		g.drawPolygon(bottom);
		
	}

	@Override
	boolean contains(Rectangle rect) {
		return bottom.intersects(rect)||top.intersects(rect);
	}

	@Override
	void translate(int deltaX, int deltaY) {
		bottom.translate(deltaX, deltaY);
		top.translate(deltaX, deltaY);

	}

}
