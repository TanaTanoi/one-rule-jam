package Game;

import java.awt.Graphics;
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

	public void playerJump(){
		p.jump();
	}
	public String[] getCurrentRuleString(){
		return currentMap.getRule();
	}
	public Game(){
		p = new Player();
		currentMap = new BasicMap();
		nextMap = new BasicBlock();
		nextMap.translate(500, 0);
		offerNextMap();
		offerNextMap();
		maps.peek().translate(1000,0);

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
		if(currentMap.intersects(p.getBoundingBox())){
			System.out.println("Collide");
			p.setLanded(currentMap.intersectY(p.getBoundingBox()));
		}else{
			p.setFalling();
		}
	}

	public void drawMaps(Graphics g, int canvasWidth, int canvasHeight){
		speed = canvasWidth/500;
		moveMaps();
		currentMap.draw(g, canvasWidth, canvasHeight);
		nextMap.draw(g, canvasWidth, canvasHeight);
		maps.peek().draw(g, canvasWidth, canvasHeight);
		p.draw(g, canvasHeight, canvasWidth);
	}

	public void offerNextMap(){
		int i = (int)(Math.random()*TOTAL_MAPS);
		switch(i){
		case 0:
			maps.offer(new BasicMap());
			break;
		case 1:
			maps.offer(new BasicBlock());
			break;
		case 2:
			maps.offer(new BasicPit());
			break;
		}
	}
}
