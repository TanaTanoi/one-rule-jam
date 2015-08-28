package UI;

import java.awt.Dimension;

import javax.swing.JFrame;

import Game.Game;

public class GameFrame extends JFrame{
	private GameCanvas canvas;

	public GameFrame(Game game){
		super("Grapplehook Game");

		canvas = new GameCanvas(game);

		add(canvas);

		pack();
		setResizable(true);
		setVisible(true);
	}
}
