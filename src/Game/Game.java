package Game;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayDeque;
import java.util.Queue;

import UI.GameCanvas;
import playerTools.Player;
import maps.*;

public class Game {

	public static final int TOTAL_MAPS = 5;

	int distance = 0;
	private  int speed = 5;
	private Queue<Map> maps = new ArrayDeque<Map>();
	private Map currentMap;
	private Map nextMap;
	private Player p;
	private Coin coin;
	private GameCanvas canvas;
	private boolean isAlive = true;
	int canvasHeight = 500 ,canvasWidth= 500;
	int ticks = 0;

	private static int score = 0;
	private static final int COIN_WORTH = 10;

	public Game(){
		p = new Player(this);
		currentMap = new BasicMap(canvasWidth,canvasHeight);
		nextMap = new DontTouchGround(canvasWidth,canvasHeight);
		nextMap.translate(currentMap.getLength(), 0);
		maps.offer(new BasicMap(canvasWidth,canvasHeight));
		offerNextMap();
		offerNextMap();
		maps.peek().translate(currentMap.getLength()+nextMap.getLength(),0);
		newCoin();
	}

	public boolean playerJump(){
		return p.jump();
	}

	public boolean isPlayerAlive(){
		return isAlive;
	}

	public Point getPlayerPos(){
		return new Point(p.getPosX(),p.getPosY());
	}

	public boolean playerPullGrapple(int x, int y){
		return p.pullGrapple(x, y);
	}

	public String[] getCurrentRuleString(){
		return currentMap.getRule();
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
	public void newCoin(){
		int y = 65 + (int)(Math.random()*185);
		coin = new Coin((currentMap.getLength() + ((int)(Math.random()*nextMap.getLength())))/(int)(Math.random()*3+2),y);
	}
	public void moveMaps(){
		currentMap.translate(-speed, 0);
		nextMap.translate(-speed, 0);
		maps.peek().translate(-speed, 0);
		distance+=speed;
		if(distance>=currentMap.getLength()){//if we are at the end of the map
			canvas.transition(nextMap.getRule(), nextMap.getClass().getName());
			ticks = 100;
			currentMap = nextMap;
			nextMap = maps.poll();
			maps.peek().translate(currentMap.getLength()+nextMap.getLength(), 0);
			distance = 0;
			offerNextMap();
		}
		coin.translate(-speed);
		if(coin.intercepts(p.getBoundingBox())){
			newCoin();
			score+=COIN_WORTH;
			System.out.println("SCORE: " + score);
			canvas.setScore(score);
		}
		if(coin.getX()<-50){
			newCoin();
		}


	}


	public void resizeGame(int canvasWidth, int canvasHeight){
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
	}


	public void drawMaps(Graphics g, int canvasWidth, int canvasHeight){
		speed = canvasWidth/190;
		moveMaps();
		currentMap.draw(g);
		nextMap.draw(g);
		maps.peek().draw(g);
		p.draw(g, canvasHeight, canvasWidth);
		if(!currentMap.assessRule(p) && ticks < 1){
			isAlive = false;
		}
		coin.draw(g);
		if(currentMap.intersects(p.getFrontPoint())){
			isAlive = false;
		}
		if(ticks > 0)ticks--;
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
		case 3:
			maps.offer(new DontTouchGround(canvasWidth,canvasHeight));
			break;
		case 4:
			maps.offer(new DontJump(canvasWidth,canvasHeight));
		}
	}

	public boolean playerSwingGrapple(int x, int y) {
		return p.swingGrapple(x, y);
	}

	public void playerSetFalling() {
		p.setFalling();
	}
	public void setCanvas(GameCanvas g){
		canvas = g;
	}
}
