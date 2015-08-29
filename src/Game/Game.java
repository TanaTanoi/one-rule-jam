package Game;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayDeque;
import java.util.Queue;

import playerTools.Player;
import maps.*;

public class Game {

	public static final int TOTAL_MAPS = 3;

	int distance = 0;
	int speed = 1;
	private Queue<Map> maps = new ArrayDeque<Map>();
	private Map currentMap;
	private Map nextMap;
	private Player p;
	int canvasHeight = 500 ,canvasWidth= 500;
	public void playerJump(){
		p.jump();
	}

	public void playerPullGrapple(int x, int y){
		p.pullGrapple(x, y);
	}

	public String[] getCurrentRuleString(){
		return currentMap.getRule();
	}
	public Game(){
		p = new Player(this);
		currentMap = new BasicMap(canvasWidth,canvasHeight);
		nextMap = new BasicBlock(canvasWidth,canvasHeight);
		nextMap.translate(500, 0);
		offerNextMap();
		offerNextMap();
		maps.peek().translate(currentMap.getLength()+nextMap.getLength(),0);

	}

	public boolean intersectsCurrentMap(Rectangle rect, int y){
		//System.out.println("Point is " + x + " " + y);
		if(currentMap.intersects(new Point((int) rect.getMinX(),y))){
			return true;
		}else if(currentMap.intersects(new Point((int) rect.getMaxX(),y))){
			return true;
		}
		return false;
	}

	public void moveMaps(){
		currentMap.translate(-1, 0);
		nextMap.translate(-1, 0);
		maps.peek().translate(-1, 0);
		distance++;
		if(distance>=currentMap.getLength()){//if we are at the end of the map
			currentMap = nextMap;
			nextMap = maps.poll();
			maps.peek().translate(currentMap.getLength()+nextMap.getLength(), 0);
			distance = 0;
			offerNextMap();
		}
	}


	public void resizeGame(int canvasWidth, int canvasHeight){
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
	}


	public void drawMaps(Graphics g, int canvasWidth, int canvasHeight){
		speed = canvasWidth/500;
		moveMaps();
		currentMap.draw(g);
		nextMap.draw(g);
		maps.peek().draw(g);
		p.draw(g, canvasHeight, canvasWidth);
	}

	public void offerNextMap(){
		int i = (int)(Math.random()*TOTAL_MAPS);
		switch(i){
		case 0:
			maps.offer(new BasicMap(canvasWidth,canvasHeight));
			break;
		case 1:
			maps.offer(new BasicBlock(canvasWidth,canvasHeight));
			break;
		case 2:
			maps.offer(new BasicPit(canvasWidth,canvasHeight));
			break;
		}
	}
}
