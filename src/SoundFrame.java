import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;

import sun.audio.*;

public class SoundFrame extends JFrame implements MouseListener{

	Clip whooshClip;

	public SoundFrame(){
		setSize(200,200);
		setVisible(true);
		addMouseListener(this);
		setUpSound();
	}

	public static void whooshSound(){
		AudioPlayer player = AudioPlayer.player;
		AudioStream stream;
		AudioData data;

		try {
			stream = new AudioStream(new FileInputStream("Whoosh.wav"));
			data = stream.getData();
		} catch (FileNotFoundException e) {	e.printStackTrace();}
		catch (IOException e) { e.printStackTrace();	}

	}
	public void setUpSound() {
		  try {
		   File file = new File("Whoosh.wav");
		   whooshClip = AudioSystem.getClip();
		   whooshClip.open(AudioSystem.getAudioInputStream(file));
		  } catch (Exception e) {
		   System.err.println(e.getMessage());
		  }

		 }

	public void play(){
		whooshClip.setFramePosition(0);
		whooshClip.start();
	}



	@Override
	public void mouseClicked(MouseEvent e) {
		//whooshSound();
		play();

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public static void main(String args[]){
		new SoundFrame();
	}

}