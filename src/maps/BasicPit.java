package maps;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

import playerTools.Player;

/**
 * A flat map made of two polygons at the top and bottom
 * @author Tana
 *
 */
public class BasicPit extends Map {

	Polygon bottomA, bottomB, top;
	Color color;
	//array 1,2 top x,y 3,4 bottom,x,y
	private final int[][] polyPoints= {{0,0,10,10},{0,1,1,0},{0,0,4,4},{10,9,9,10},{6,6,10,10},{10,8,8,10}};
	public BasicPit(int canvasWidth, int canvasHeight){
		super(canvasWidth,canvasHeight);

		String[] ruleString = {"NO","RULE"};
		this.ruleString = ruleString;

		int rand = (int)(Math.random()*5)+1;
		int[][] pitPoints = {{0,0,rand,rand},{10,9,9,10},{rand+3,rand+3,10,10},{10,8,8,10}};
		for(int i = 0;i<4;i++){
			polyPoints[i+2]=pitPoints[i];
		}
		length = 10;
		color = new Color((int) (Math.random()*100000));
		top = new Polygon(polyPoints[0],polyPoints[1],4);
		bottomA = new Polygon(polyPoints[2],polyPoints[3],4);
		bottomB = new Polygon(polyPoints[4],polyPoints[5],4);
		calculatePolygons(canvasWidth, canvasHeight);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.fillPolygon(top);
		g.setColor(color);
		g.fillPolygon(bottomA);
		g.fillPolygon(bottomB);
	}

	private void calculatePolygons(int canvasWidth, int canvasHeight){
		int boxSize = super.getDrawBoxSize(canvasWidth, canvasHeight);
		int[][] localPolyPoints = new int[6][4];
		for(int j = 0; j < 6; j++){
			for(int i = 0;i<4;i++){
				localPolyPoints[j][i] = polyPoints[j][i]*boxSize;
				if(i==3&&j==0){
					length = localPolyPoints[j][i];
				}
			}
		}
		top = new Polygon(localPolyPoints[0],localPolyPoints[1],4);
		bottomA = new Polygon(localPolyPoints[2],localPolyPoints[3],4);
		bottomB = new Polygon(localPolyPoints[4],localPolyPoints[5],4);
	}
	@Override
	public boolean intersects(Rectangle rect) {

		Point bl = new Point((int)rect.getMinX(),(int)rect.getMaxY());
		Point tl = new Point((int)rect.getMinX(),(int)rect.getMinY());
		Point br = new Point((int)rect.getMaxX(),(int)rect.getMaxY());
		Point tr = new Point((int)rect.getMaxX(),(int)rect.getMinY());
		return intersects(bl)||intersects(br)||intersects(tl)||intersects(tr);
	}

	@Override
	public boolean intersects(Point p) {
		return bottomA.contains(p)||top.contains(p)||bottomB.contains(p);
	}

	@Override
	public void translate(int deltaX, int deltaY) {
		bottomA.translate(deltaX, deltaY);
		top.translate(deltaX, deltaY);
		bottomB.translate(deltaX,deltaY);
	}

	@Override
	public boolean assessRule(Player p) {
		//No Rule
		return true;
	}


	@Override
	public void resize(int canvasWidth, int canvasHeight) {
		calculatePolygons(canvasWidth, canvasHeight);

	}



}
