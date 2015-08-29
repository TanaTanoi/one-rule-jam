package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import Game.Game;
import Scenery.Cloud;

public class GameCanvas extends JPanel{
	Timer tick;
	private ArrayList<Cloud> clouds;
	private boolean inTransition;
	private int ticks;


	Game game;
	public GameCanvas(Game game){
		super();
		this.game = game;
		clouds = new ArrayList<Cloud>();
		tick = new Timer(this);
		tick.start();

	}

	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		paintBackground(g);
		drawClouds(g);
		game.drawMaps(g, this.getWidth(),this.getHeight());
		if (inTransition) paintTransition(g);
	}

	private void paintTransition(Graphics g) {
		g.setColor(new Color(10,10,10,200));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(new Color(252,186,5));
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
		if (ticks > 3) g.drawString("THE", (getWidth()/5), (getHeight()/5)*2);
		if (ticks > 6) g.drawString("ONE", (getWidth()/5)*2, (getHeight()/5)*2);
		if (ticks > 9) g.drawString("RULE:", (getWidth()/5)*3, (getHeight()/5)*2);

		if (ticks > 11) g.drawString("KILL", (getWidth()/5), (getHeight()/5)*3);
		if (ticks > 12) g.drawString("ALL", (getWidth()/5)*2, (getHeight()/5)*3);
		if (ticks > 13) g.drawString("HUMANS.", (getWidth()/5)*3, (getHeight()/5)*3);

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
		if (inTransition) {
			if (ticks == 15) {
				tick.setTick(10);
				inTransition=false;
			}
			else ticks++;
		}
	}

	public void transition(){
		tick.setTick(200);
		ticks = 0;
		inTransition = true;
	}

	public void pause(){
		tick.pause();
	}
}
