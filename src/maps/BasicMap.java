package maps;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import playerTools.Player;

/**
 * A flat map made of two polygons at the top and bottom
 * @author Tana
 *
 */
public class BasicMap extends Map {

	Polygon bottom, top;
	Color color;
	//array 1,2 top x,y 3,4 bottom,x,y
	private final int[][] polyPoints= {{0,0,10,10},{0,1,1,0},{0,0,10,10},{10,9,9,10}};

	public BasicMap(){
		length = 10;
		color = new Color((int) (Math.random()*100000));
		top = new Polygon(polyPoints[0],polyPoints[1],4);
		bottom = new Polygon(polyPoints[2],polyPoints[3],4);
	}

	@Override
	public void draw(Graphics g, int canvasWidth, int canvasHeight) {
		g.setColor(color);
		g.fillPolygon(top);
		g.fillPolygon(bottom);
		calculatePolygons(canvasWidth,canvasHeight);
	}

	private void calculatePolygons(int canvasWidth, int canvasHeight){
		int boxSize = super.getDrawBoxSize(canvasWidth, canvasHeight);
		int[][] localPolyPoints = new int[4][4];
		for(int j = 0; j < 4; j++){
			for(int i = 0;i<4;i++){
				localPolyPoints[j][i] = polyPoints[j][i]*boxSize;
				if(i==3&&j==0){
					length = localPolyPoints[j][i];
				}
				System.out.println(localPolyPoints[j][i]);
			}
		}
		top = new Polygon(localPolyPoints[0],localPolyPoints[1],4);
		bottom = new Polygon(localPolyPoints[2],localPolyPoints[3],4);
	}
	@Override
	public boolean intersects(Rectangle rect) {
		return bottom.intersects(rect)||top.intersects(rect);
	}

	@Override
	public boolean intersects(Point p) {
		return bottom.contains(p)||top.contains(p);
	}

	@Override
	public void translate(int deltaX, int deltaY) {
		bottom.translate(deltaX, deltaY);
		top.translate(deltaX, deltaY);
	}

	@Override
	public boolean assessRule(Player p) {
		//No Rule
		return true;
	}



}