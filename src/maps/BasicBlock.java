package maps;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
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
public class BasicBlock extends Map {

	Polygon bottom, top;
	List<Polygon> blocks;
	Color color;
	//array 1,2 top x,y 3,4 bottom,x,y
	private final int[][] polyPoints= {{0,0,100,100},{0,1,1,0},{0,0,100,100},{10,9,9,10}};
	//private final int[][] blockPoints;

	public BasicBlock(int canvasWidth, int canvasHeight){
		super(canvasWidth,canvasHeight);
		String[] ruleString = {"NO","RULE"};
		this.ruleString = ruleString;
		//int bHeight = randomNumber(4, 8);
		//int[][] blockPoints = {{4,4,7,7},{9,bHeight,bHeight,9}};
		//this.blockPoints = blockPoints;
		length = 10;
		color = new Color((int) (Math.random()*100000));
		top = new Polygon(polyPoints[0],polyPoints[1],4);
		bottom = new Polygon(polyPoints[2],polyPoints[3],4);

		blocks = new ArrayList<Polygon>();
		int count = 5;
		while(count < 100){
			int blockLength = randomNumber(count, count + 10);
			if(blockLength < 100){
				int[] x = {count, count, blockLength, blockLength };
				int[] y = {9,randomNumber(4,8),randomNumber(4,8),9};
				blocks.add(new Polygon(x,y,4));
			}
			count = blockLength + 5;
		}


		//block = new Polygon(blockPoints[0],blockPoints[1],4);
		//calculatePolygons(canvasWidth, canvasHeight);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.fillPolygon(top);
		g.setColor(color);

		g.fillPolygon(bottom);
		for(Polygon p : blocks){
			g.fillPolygon(p);
		}
	}
//
//	private void calculatePolygons(int canvasWidth, int canvasHeight){//TODO rewrite so that when it does resize, they stay same place
//		int boxSize = super.getDrawBoxSize(canvasWidth, canvasHeight);
//		int[][] localPolyPoints = new int[4][4];
//		int[][] blockPoints = new int[2][4];
//		for(int j = 0; j < 4; j++){
//			for(int i = 0;i<4;i++){
//				localPolyPoints[j][i] = polyPoints[j][i]*boxSize;
//				if(i==3&&j==0){
//					length = localPolyPoints[j][i];
//				}
//				blockPoints[j%2][i] = this.blockPoints[j%2][i]*boxSize;
//			}
//		}
//		top = new Polygon(localPolyPoints[0],localPolyPoints[1],4);
//		bottom = new Polygon(localPolyPoints[2],localPolyPoints[3],4);
//		block = new Polygon(blockPoints[0],blockPoints[1],4);
//	}

	@Override
	public boolean intersects(Rectangle rect) {

		//return bottom.intersects(rect)||top.intersects(rect)||block.intersects(rect);
		//return bottom.contains(rect.getMaxX())||bottom.contains(rect.getMinY())||bottom.contains(rect.getMaxY())||bottom.contains(rect.getMinX())||
		Point bl = new Point((int)rect.getMinX(),(int)rect.getMaxY());
		Point tl = new Point((int)rect.getMinX(),(int)rect.getMinY());
		Point br = new Point((int)rect.getMaxX(),(int)rect.getMaxY());
		Point tr = new Point((int)rect.getMaxX(),(int)rect.getMinY());
		return intersects(bl)||intersects(br)||intersects(tl)||intersects(tr);
	}

	@Override
	public boolean intersects(Point p) {
		return bottom.contains(p)||blocks.contains(p);

	}

	@Override
	public void translate(int deltaX, int deltaY) {
		bottom.translate(deltaX, deltaY);
		top.translate(deltaX, deltaY);

		for(Polygon block: blocks){
			block.translate(deltaX,deltaY);
		}
	}

	@Override
	public boolean assessRule(Player p) {
		//No Rule
		return true;
	}

	@Override
	public void resize(int canvasWidth, int canvasHeight) {
		//calculatePolygons(canvasWidth, canvasHeight);
	}

}
