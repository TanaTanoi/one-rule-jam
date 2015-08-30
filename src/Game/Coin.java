package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Coin {

	Rectangle box;
	private int sizeChange = 1;
	private int xSize = 5;
	
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
		g.setColor(Color.YELLOW);
		g.fillOval(box.x + (40-xSize)/2, box.y, xSize, 40);
		if (xSize < 5 || xSize > 40)sizeChange*=-1;
		xSize+=sizeChange;
	}
	public int getX(){
		return box.x;
	}
}
