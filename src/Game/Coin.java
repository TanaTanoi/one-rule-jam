package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Coin {

	Rectangle box;


	public Coin(int x, int y){
		box = new Rectangle(x,y,40,40);
	}


	public boolean intercepts(Rectangle player){
		return box.intersects(player);
	}

	public void translate(int deltaX){
		box.translate(deltaX, 0);
	}

	public void draw(Graphics g){
		g.setColor(Color.yellow);
		g.fillOval(box.x, box.y, 40, 40);
		System.out.println("DRAWING AT " + box.x + " " +box.y);
	}
	public int getX(){
		return box.x;
	}
}
