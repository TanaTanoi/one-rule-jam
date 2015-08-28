package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import Scenery.Cloud;

public class GameCanvas extends JPanel{
	Timer tick;
	ArrayList<Cloud> clouds;
	public GameCanvas(){
		super();
		clouds = new ArrayList<Cloud>();
		tick = new Timer(this);
		tick.start();
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		paintBackground(g);
		drawClouds(g);
		
	}

	private void drawClouds(Graphics g) {
		Set<Cloud> delete = new HashSet<Cloud>();
		double xScale = getWidth()/500.0;
		double yScale = getHeight()/500.0;
		if (!clouds.isEmpty()){
			for(Cloud c: clouds){
				Point p = c.getLoc();
				g.drawImage(c.draw(xScale, yScale), (int)(p.x*xScale), (int)(p.y*yScale), this);
				c.move();
				if(p.x < -200) delete.add(c);
			}
			for(Cloud c:delete){
				clouds.remove(c);
			}
		}
	}

	private void paintBackground(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;	
		Color sky1 = new Color(0,110,255);
		Color sky2 = new Color(57,200,247);
		GradientPaint backColor = new GradientPaint(0,0, sky1, 0,getHeight(), sky2);
		g2d.setPaint(backColor);
		g2d.fillRect(0, 0, getWidth(), getHeight());		
	}

	@Override
	public Dimension getPreferredSize(){
		return new Dimension(500,500);
	}
	
	public void tick(){
		if (clouds.size() < 10 && Math.random() < 0.1){
			clouds.add(new Cloud());	
		}
		repaint();
	}
}
