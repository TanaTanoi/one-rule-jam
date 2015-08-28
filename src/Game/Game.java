package Game;

import java.awt.Graphics;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;

import maps.BasicMap;
import maps.Map;

public class Game {

	int distance = 0;
	int speed = 1;
	Queue<Map> maps = new ArrayDeque<Map>();
	Map currentMap;
	Map nextMap;
	public Game(){

		maps.offer(new BasicMap());
		maps.offer(new BasicMap());
		maps.offer(new BasicMap());
		maps.offer(new BasicMap());
		maps.offer(new BasicMap());
		maps.offer(new BasicMap());
		maps.offer(new BasicMap());

		currentMap = new BasicMap();
		nextMap = new BasicMap();
		//nextMap.translate(currentMap.getLength(), 0);
		nextMap.translate(500, 0);
		maps.peek().translate(1000,0);//500+nextMap.getLength(), 0);

	}

	public void moveMaps(){
		currentMap.translate(-distance, 0);
		nextMap.translate(currentMap.getLength()-distance, 0);
		maps.peek().translate(nextMap.getLength()+currentMap.getLength()-distance, 0);
		distance+=1;
		if(distance>=currentMap.getLength()){//if we are at the end of the map
			currentMap = nextMap;
			nextMap = maps.poll();
			maps.peek().translate(currentMap.getLength()+nextMap.getLength(), 0);
			distance = 0;
			maps.offer(new BasicMap());
		}
	}

	public void drawMaps(Graphics g, int canvasWidth, int canvasHeight){
		moveMaps();
		currentMap.draw(g, canvasWidth, canvasHeight);
		nextMap.draw(g, canvasWidth, canvasHeight);
		maps.peek().draw(g, canvasWidth, canvasHeight);

	}
}
