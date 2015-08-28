package UI;

import java.awt.Dimension;

import javax.swing.JFrame;

public class GameFrame extends JFrame{
	private GameCanvas canvas;
	
	public GameFrame(){
		super("GayrappleHook Gayme");
		
		canvas = new GameCanvas();
		
		add(canvas);
		
		pack();
		setResizable(true);
		setVisible(true);
	}
}
