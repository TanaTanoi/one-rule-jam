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

	public Game(){
		maps.offer(new BasicMap());
		maps.offer(new BasicMap());
		maps.offer(new BasicMap());
		maps.offer(new BasicMap());
		maps.offer(new BasicMap());
		maps.offer(new BasicMap());
		maps.offer(new BasicMap());
		currentMap = new BasicMap();

	}

	public void moveMaps(){
		currentMap.translate(-distance, 0);
		maps.peek().translate(-distance, 0);
		distance+=10;
		if(distance>=currentMap.getLength()){//if we are at the end of the map
			currentMap = maps.poll();									//get next map
			maps.peek().translate(currentMap.getLength(), 0);			//set next-next map to distance
			distance = 0;
			System.out.println("Swapping map");
			maps.offer(new BasicMap());
		}
	}

	public void drawMaps(Graphics g, int canvasWidth, int canvasHeight){
		moveMaps();
		currentMap.draw(g, canvasWidth, canvasHeight);
		if(maps.isEmpty())return;
		maps.peek().draw(g, canvasWidth, canvasHeight);

	}
}
