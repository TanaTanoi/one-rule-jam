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
import java.awt.BasicStroke;
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
	private double transparency = 180;
	private double transChange = 0.6;
	private Point mousePos;
	private String[] curRule;
	private String ruleString;
	private int ruleLength;
	private int score;
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
		drawTracer((Graphics2D)g);
		game.drawMaps(g, this.getWidth(),this.getHeight());
		if (!game.isPlayerAlive()) paintDeathScreen((Graphics2D)g);
		else if (!started) paintStartScreen((Graphics2D)g);
		else if (paused) paintPauseScreen((Graphics2D)g);
		else if (inTransition) paintTransition(g);
		else printScore(g);
	}

	private void printScore(Graphics g) {
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		g.setColor(new Color(252,186,5,(int)transparency));
		g.drawString("Score: " + score, 10, 40);
	}

	private void drawTracer(Graphics2D g) {
		if (mousePos == null) return;
		g.setStroke(new BasicStroke(3));
		g.setPaint(new Color(255,0,0,200));

	}
	public void setMousePos(Point p){
		mousePos = p;
	}

	private void paintStartScreen(Graphics2D g) {
		Color c1 = new Color(255,255,255,(int)transparency);
		Color c2 =  new Color(252,186,5,(int)transparency);
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
			g.setPaint(new Color(252,186,5,(int)transparency));
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
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		if (ticks > 3) g.drawString("THE", (getWidth()/5), (getHeight()/5)*2);
		if (ticks > 6) g.drawString("ONE", (getWidth()/5)*2, (getHeight()/5)*2);
		if (ticks > 9) g.drawString("RULE:", (getWidth()/5)*3, (getHeight()/5)*2);
		int charsEntered = 0;
		for (int i = 0; i < curRule.length;i++){
			if (ticks > 11+i) {
				g.drawString(curRule[i], (getWidth()/2)-(22*ruleLength/2)+(25*charsEntered), (getHeight()/5)*3);
			}charsEntered+=curRule[i].length();
		}
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
	
	private void paintDeathScreen(Graphics2D g) {
		Color c1 = new Color(255,0,0,(int)transparency);
		Color c2 = new Color(10,10,10,(int)transparency);
		Point2D p = new Point2D.Float(getWidth()/2, getHeight()/2);
		float[] dist = {0.0f,0.3f,0.5f};
		Color[] colors = {c2,c1,c2};
		RadialGradientPaint pauseColor = new RadialGradientPaint(p, 900, dist, colors);
		g.setPaint(pauseColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setPaint(Color.BLACK);
		g.setFont(new Font("Comic Sans MS", Font.BOLD, getWidth()/10));
		g.drawString("YOU DIED", (getWidth()/2) - (getWidth()/20)*5, getHeight()/2);
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
			if (ticks == 18) {
				tick.setTick(10);
				inTransition=false;
			}
			else ticks++;
		}

		if (transparency < 240 && transparency > 100)	transparency+=transChange;
		else {
			transChange*=-1;
			transparency+=transChange;
		}
	}

	public void transition(String[] ruleString, String rule){
		System.out.println(rule + " "+ this.ruleString);
		if(rule.equals(this.ruleString) && this.ruleString != null)return;
		this.ruleString = rule;
		curRule = ruleString;
		ruleLength = 0;
		for(String s: curRule) ruleLength+=(curRule.length + 1); 
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
	public void setScore(int s){
		score = s;
	}
	
	
}
