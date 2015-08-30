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

	Polygon bottomA, bottomB, bottomC, bottomD, bottomE, bottomF, top;
	Color color;
	//array 1,2 top x,y 3,4 bottom,x,y

	private final int[][] polyPoints= {{0,0,100,100},{0,1,1,0},{0,0,4,4},{10,9,9,10},{6,6,10,10},{10,8,8,10},
			{13,13,30,30},{10,8,8,10}, {32,32,59,59},{10,9,9,10}, {55,55,78,78},{10,9,9,10},  {82,82,115,115},{10,9,9,10}};

	public BasicPit(int canvasWidth, int canvasHeight){
		super(canvasWidth,canvasHeight);

		String[] ruleString = {"NO","RULE"};
		this.ruleString = ruleString;

		int rand = (int)(Math.random()*5)+1;

		int[][] pitPoints = {{0,0,rand,rand},{10,9,9,10},{rand+3,rand+3,10,10},{10,8,8,10},
			{rand+10, rand+10, 25, 25},{10,8,8,10},{rand+35, rand+35, 45, 45},{10,9,9,10},
			{rand+58, rand+58, 70, 70},{10,9,9,10},{rand+78, rand+78, 95, 95},{10,9,9,10}};
		for(int i = 0;i<4;i++){
			polyPoints[i+2]=pitPoints[i];
		}

		length = 10;
		color = new Color((int) (Math.random()*100000));
		top = new Polygon(polyPoints[0],polyPoints[1],4);
		bottomA = new Polygon(polyPoints[2],polyPoints[3],4);
		bottomB = new Polygon(polyPoints[4],polyPoints[5],4);
		bottomC = new Polygon(polyPoints[6],polyPoints[7],4);
		bottomD = new Polygon(polyPoints[8],polyPoints[9],4);
		bottomE = new Polygon(polyPoints[10],polyPoints[11],4);
		bottomF = new Polygon(polyPoints[12],polyPoints[13],4);
		calculatePolygons(canvasWidth, canvasHeight);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.fillPolygon(top);
		g.setColor(color);
		g.setColor(Color.RED);
		g.fillPolygon(bottomA);
		g.setColor(color.YELLOW);
		g.fillPolygon(bottomB);
		g.fillPolygon(bottomC);
		g.fillPolygon(bottomD);
		g.fillPolygon(bottomE);
		g.fillPolygon(bottomF);
	}

	private void calculatePolygons(int canvasWidth, int canvasHeight){
		int boxSize = super.getDrawBoxSize(canvasWidth, canvasHeight);
		int[][] localPolyPoints = new int[14][8];
		for(int j = 0; j < 14; j++){
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
		bottomC = new Polygon(localPolyPoints[6],localPolyPoints[7],4);
		bottomD = new Polygon(localPolyPoints[8],localPolyPoints[9],4);
		bottomE = new Polygon(localPolyPoints[10],localPolyPoints[11],4);
		bottomF = new Polygon(localPolyPoints[12],localPolyPoints[13],4);

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
		return bottomA.contains(p)||top.contains(p)||bottomB.contains(p)
			|| bottomC.contains(p) ||bottomD.contains(p)||bottomE.contains(p) || bottomF.contains(p);
	}

	@Override
	public void translate(int deltaX, int deltaY) {
		bottomA.translate(deltaX, deltaY);
		top.translate(deltaX, deltaY);
		bottomB.translate(deltaX,deltaY);
		bottomC.translate(deltaX, deltaY);
		bottomD.translate(deltaX, deltaY);
		bottomE.translate(deltaX,deltaY);
		bottomF.translate(deltaX,deltaY);
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
