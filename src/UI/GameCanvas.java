package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
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
	private boolean paused;
	private boolean started;
	private boolean glow;
	private int ticks;
	private int transparency = 0;


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
		else if (paused) paintPauseScreen((Graphics2D)g);
		else if (!started) paintStartScreen((Graphics2D)g);
	}

	private void paintStartScreen(Graphics2D g) {
		Color c1 = new Color(255,255,255,200);
		Color c2 =  new Color(252,186,5,150);
		Point2D p = new Point2D.Float(getWidth()/2, getHeight()/2);
		float[] dist = {0.0f,0.3f,0.5f};
		Color[] colors = {c2,c1,c2};
		RadialGradientPaint pauseColor = new RadialGradientPaint(p, 900, dist, colors);
		g.setPaint(pauseColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		//draw cloud
		g.setPaint(Color.WHITE);
		g.fillOval(getWidth()/10, getHeight()/10, (getWidth()/10)*3, (getHeight()/10)*3);
		g.fillOval((getWidth()/10)*2, (getHeight()/10)*2, (getWidth()/10)*4, (getHeight()/10)*3);
		g.fillOval((getWidth()/10)*4, (getHeight()/10)*2, (getWidth()/10)*4, (getHeight()/10)*4);
		g.fillOval((getWidth()/10)*6, (getHeight()/10)*3, (getWidth()/10)*3, (getHeight()/10)*3);
		g.fillOval((getWidth()/10)*4, (getHeight()/10)*5, (getWidth()/10)*4, (getHeight()/10)*4);
		g.fillOval((getWidth()/10), (getHeight()/10)*3, (getWidth()/10)*4, (getHeight()/10)*5);
		//draw text
		g.setPaint(Color.BLACK);
		g.setFont(new Font("Comic Sans MS", Font.BOLD, getWidth()/10));
		g.drawString("START GAME", (getWidth()/2) - (getWidth()/10)*3, getHeight()/2);
		if (glow){
			g.setPaint(new Color(252,186,5,transparency));
			g.drawString("START GAME", (getWidth()/2) - (getWidth()/10)*3, getHeight()/2);
		}
	}

	private void paintPauseScreen(Graphics2D g) {
		Color c1 = new Color(252,186,5,150);
		Color c2 = new Color(10,10,10,150);
		Point2D p = new Point2D.Float(getWidth()/2, getHeight()/2);
		float[] dist = {0.0f,0.3f,0.5f};
		Color[] colors = {c2,c1,c2};
		RadialGradientPaint pauseColor = new RadialGradientPaint(p, 900, dist, colors);
		g.setPaint(pauseColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setPaint(Color.BLACK);
		g.setFont(new Font("Comic Sans MS", Font.BOLD, getWidth()/10));
		g.drawString("PAUSED", (getWidth()/2) - (getWidth()/10)*2, getHeight()/2);
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
		else if(!started){
			if (transparency < 255)	transparency+=1;
			else transparency = 80;
		}
	}

	public void transition(){
		tick.setTick(200);
		ticks = 0;
		inTransition = true;
	}

	public void pause(){
		tick.pause();
		paused = !paused;
		repaint();
	}
	
	public void start(){
		started = true;
	}

	public boolean isOnStart(Point p) {
		int x = (getWidth()/2) - (getWidth()/10)*3;
		int y = getHeight()/2;
		int sizeY = getWidth()/10;
		int maxX = (getWidth()/10)*9;
		if (p.x > x && p.x < maxX){
			if (p.y > y && p.y < y + sizeY){
				return true;
			}
		}
		return false;
	}

	public void startGlow() {
		glow = true;		
	}
	public void stopGlow() {
		glow = false;
	}
}
