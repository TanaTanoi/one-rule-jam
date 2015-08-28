package Scenery;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Cloud {
	double top,left;
	double speed;
	int[][] cloudSegments;

	public Cloud(){
		speed = Math.random()*0.3;
		top = Math.random()*30;
		left = 100;
		spawnSegments();
	}


	private void spawnSegments() {
		cloudSegments = new int[(int)(Math.random()*5)+3][3];
		for (int i = 0; i< cloudSegments.length; i++){
			cloudSegments[i][0] = (int)(Math.random()*5);//x distance from top left
			cloudSegments[i][1] = (int)(Math.random()*5);//y distance from top left
			cloudSegments[i][2] = (int)(Math.random()*3)+1; //diameter of cloud seg
		}
	}
	
	public void move(){
		left-=speed;
	}
	public Point getLoc(){
		return new Point((int)left,(int)top);
	}

	public BufferedImage draw(double x, double y){
		BufferedImage out = new BufferedImage((int)(x*20), (int)(y*20), BufferedImage.TYPE_INT_ARGB);
		Graphics drawScreen = out.getGraphics();
		drawScreen.setColor(Color.WHITE);
		for (int i = 0; i < cloudSegments.length; i++){
			int xPos = (int)(x*cloudSegments[i][0]);
			int yPos = (int)(y*cloudSegments[i][1]);
			int sizeX = (int)(x*cloudSegments[i][2]);
			int sizeY = (int)(x*cloudSegments[i][2]);
			drawScreen.fillOval(xPos, yPos, sizeX, sizeY);
		}
		return out;
	}
}
