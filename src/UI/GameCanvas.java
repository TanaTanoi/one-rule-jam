package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GameCanvas extends JPanel{
	Timer tick;
	public GameCanvas(){
		super();
		tick = new Timer(this);
		tick.start();
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		paintBackground(g);
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
		
	}
}
