package Game;

import java.awt.Graphics;
import java.awt.Point;
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
		maps.peek().translate(1000,0);

	}

	public boolean intersectsCurrentMap(int x, int y){
		//System.out.println("Point is " + x + " " + y);
		return currentMap.intersects(new Point(x,y));
	}

	public void moveMaps(){
		currentMap.translate(-distance, 0);
		nextMap.translate(currentMap.getLength()-distance, 0);
		maps.peek().translate(nextMap.getLength()+currentMap.getLength()-distance, 0);
		distance+=speed;
		if(distance>=currentMap.getLength()){//if we are at the end of the map
			currentMap = nextMap;
			nextMap = maps.poll();
			maps.peek().translate(currentMap.getLength()+nextMap.getLength(), 0);
			distance = 0;
			offerNextMap();
		}
		/*if(currentMap.intersects(p.getBoundingBox())){
			p.setLanded(currentMap.intersectY(p.getBoundingBox()));
		}else{
			p.setFalling();
		}*/
	}
	public void resizeGame(int canvasWidth, int canvasHeight){

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
