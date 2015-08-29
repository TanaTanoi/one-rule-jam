package UI;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import Game.Game;

public class GameFrame extends JFrame implements MouseListener, MouseMotionListener{
	private GameCanvas canvas;
	private Game game;
	private boolean paused;
	private boolean started;

	public GameFrame(Game game) {
		super("Grapplehook Game");
		addMouseListener(this);
		addMouseMotionListener(this);
		canvas = new GameCanvas(game);
		add(canvas);
		setBindings();
		pack();
		setResizable(false);
		setVisible(true);
		this.game = game;
	}

	private void setBindings() {
		//SPACE
		AbstractAction jump = new AbstractAction("jump") {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("jump needs implementing");
				game.playerJump();
			}
		};

		AbstractAction pause = new AbstractAction("pause") {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("pause needs implementing");
				canvas.pause();
				paused = !paused;
				setResizable(paused);
			}					
		};

		canvas.getInputMap().put(KeyStroke.getKeyStroke(' '), "jump");
		canvas.getActionMap().put("jump",jump);

		canvas.getInputMap().put(KeyStroke.getKeyStroke('P'), "pause");
		canvas.getActionMap().put("pause",pause);
		canvas.getInputMap().put(KeyStroke.getKeyStroke('p'), "pause");
		canvas.getActionMap().put("pause",pause);
		
		System.out.println("riter");
	}



	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("hook fired x: " + e.getX() + " y: " + e.getY());
		if (!started){
			if (canvas.isOnStart(new Point(e.getX(),e.getY()))){
				canvas.start();
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (!started){
			if (canvas.isOnStart(new Point(e.getX(),e.getY()))){
				canvas.startGlow();
			}else{
				canvas.stopGlow();
			}
		}
	}



	/*
	 * METHODS THAT WON'T BE IMPLEMENTED
	 */
	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {
		game.playerPullGrapple(e.getX(),e.getY());
	}

	@Override
	public void mousePressed(MouseEvent e) {}
	/*
	 * METHODS THAT WON'T BE IMPLEMENTED
	 */
}
