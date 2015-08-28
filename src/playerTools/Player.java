package playerTools;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Player {

	private int vertSpeed;
	private int posX;
	private int posY;

	private Rectangle boundingBox;

	public Player(){

	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public Rectangle getBoundingBox(){
		return boundingBox;
	}

	public void draw(Graphics g){
		g.drawRect(x, y, width, height);
	}



}
