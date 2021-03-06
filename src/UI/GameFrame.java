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
import javax.sound.sampled.FloatControl;
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
	Clip backGround;

	public GameFrame(Game game) {
		super("Grapplehook Game");
		addMouseListener(this);
		addMouseMotionListener(this);
		canvas = new GameCanvas(game);
		add(canvas);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		game.setCanvas(canvas);
		setBindings();
		pack();
		setResizable(false);
		setVisible(true);
		this.game = game;
		setUpSound();
		startMusic();
	}

	private void startMusic() {
		backGround.loop(Clip.LOOP_CONTINUOUSLY);
		//backGround.start();
	}

	private void setBindings() {
		//SPACE
		AbstractAction jump = new AbstractAction("jump") {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean success = 	game.playerJump();
				if(success){
					playJump();
				}

			}
		};

		AbstractAction pause = new AbstractAction("pause") {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.pause();
				paused = !paused;
				//setResizable(paused);
			}
		};



		canvas.getInputMap().put(KeyStroke.getKeyStroke(' '), "jump");
		canvas.getActionMap().put("jump",jump);

		canvas.getInputMap().put(KeyStroke.getKeyStroke('P'), "pause");
		canvas.getActionMap().put("pause",pause);
		canvas.getInputMap().put(KeyStroke.getKeyStroke('p'), "pause");
		canvas.getActionMap().put("pause",pause);

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == e.BUTTON3){
			boolean swingGrapple = game.playerSwingGrapple(e.getX(),e.getY());
			if(swingGrapple){
				playWhoosh();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//System.out.println("hook fired x: " + e.getX() + " y: " + e.getY());
		if (started){
			if(e.getButton() == e.BUTTON3){
				game.playerSetFalling();
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
		}else{
			canvas.setMousePos(new Point(e.getX(),e.getY()));
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

		// Sets up background music
		try {
			File file = new File("backGround.wav");
			backGround = AudioSystem.getClip();
			backGround.open(AudioSystem.getAudioInputStream(file));
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
		if (started){
			//			play();
			if(e.getButton() == e.BUTTON1){
				boolean grapple = game.playerPullGrapple(e.getX(),e.getY());
				if(grapple){
					playWhoosh();
				}
			}
		} else {
			if (canvas.isOnStart(new Point(e.getX(),e.getY()))){
				canvas.start();
				started = true;
			}
		}
	}


}
