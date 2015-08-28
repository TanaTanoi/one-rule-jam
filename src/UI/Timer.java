package UI;

public class Timer extends Thread{
	
	GameCanvas canvas;
	Long tickRate = (long) 50;
	
	public Timer(GameCanvas c){
		canvas = c;
	}
	public void run() {
		while(true){
			try {
				Thread.sleep(tickRate);
				canvas.tick();
			} catch (InterruptedException e) {
				System.out.println("shits fucked");
				e.printStackTrace();
			}
			
		}
	}
}