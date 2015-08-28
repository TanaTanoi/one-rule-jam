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

	public static int moveJump(int currentX, int currentY, double vertSpeed) {
		return (int)(vertSpeed*Timer.tickRate + 0.5*gravity*Timer.tickRate*Timer.tickRate); // d = vit + 1/2a(t*t)
	}

	public static double calculateGrappleAngle(double currentX, double currentY,
			int grappleX, int grappleY) {

		// calculates lengths of each side of triangel
		double adj = grappleY - currentY;
		double opp = grappleX - currentX;
		double hypot = Math.sqrt(adj*adj + opp*opp);

		return Math.cos(adj/hypot);
	}
}