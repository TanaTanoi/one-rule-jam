package UI;

public class Timer extends Thread{

	GameCanvas canvas;
	public static final Long tickRate = (long) 10;
	private long tick = (long) 10;
	private boolean paused;

	public Timer(GameCanvas c){
		canvas = c;
	}
	public void run() {
		while(true){
			try {
				Thread.sleep(tick);
				if (!paused)canvas.tick();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void setTick(int tick){
		this.tick = (long)tick;
	}

	public void pause(){
		paused = !paused;
	}
}