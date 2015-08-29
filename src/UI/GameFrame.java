package UI;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import Game.Game;

public class GameFrame extends JFrame implements MouseListener, MouseMotionListener{
	private GameCanvas canvas;
	private Game game;
	private boolean paused;
	private boolean started;

	// Sound clips
	Clip whooshClip;
	Clip jumpClip;

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
		setUpSound();
	}

	private void setBindings() {
		//SPACE
		AbstractAction jump = new AbstractAction("jump") {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("jump needs implementing");
				boolean success = 	game.playerJump();
				if(success){
					playJump();
				}
			
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

	public void setUpSound() {
		// Sets up whoosh sound
		try {
			File file = new File("Whoosh.wav");
			whooshClip = AudioSystem.getClip();
			whooshClip.open(AudioSystem.getAudioInputStream(file));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		// Sets up jump sound
		try {
			File file = new File("Jump.wav");
			jumpClip = AudioSystem.getClip();
			jumpClip.open(AudioSystem.getAudioInputStream(file));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

	public void playWhoosh(){
		whooshClip.setFramePosition(0);
		whooshClip.start();
	}
	
	public void playJump(){
		jumpClip.setFramePosition(0);
		jumpClip.start();
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
		boolean grapple = game.playerPullGrapple(e.getX(),e.getY());
		if(grapple){
			playWhoosh();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}
	/*
	 * METHODS THAT WON'T BE IMPLEMENTED
	 */
}
