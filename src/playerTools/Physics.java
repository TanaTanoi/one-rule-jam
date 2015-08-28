package playerTools;

import java.awt.Point;

import UI.Timer;

public class Physics {

	private static double gravity = -9.8;
	private static double speedX = 1;

	public static Point moveRun(int currentX, int currentY){
		return new Point((int)(currentX + speedX*Timer.tickRate), currentY); // d = vt
	}

	public static double fallSpeed(double vertSpeed) {
		return vertSpeed + gravity*Timer.tickRate; // vf = vi + at
	}

	public static Point moveJump(int currentX, int currentY, double vertSpeed) {
		int x = (int)(currentX + speedX*Timer.tickRate); // d = vt
		int y = (int)(vertSpeed*Timer.tickRate + 0.5*gravity*Timer.tickRate*Timer.tickRate); // d = vit + 1/2a(t*t)
		return new Point(x,y);
	}
}
