package playerTools;

import java.awt.Point;

import UI.Timer;

public class Physics {

	private static double gravity = -2.5;
	private static double speedX = 1;

	public static Point moveRun(int currentX, int currentY){
		return new Point((int)(currentX + speedX*Timer.tickRate/1000), currentY); // d = vt
	}

	public static double fallSpeed(double vertSpeed) {
		return vertSpeed + gravity*Timer.tickRate/1000; // vf = vi + at
	}

	public static int moveJump(int currentX, int currentY, double vertSpeed) {
		return (int)((vertSpeed*Timer.tickRate/1000 + 0.5*gravity*Timer.tickRate/1000*Timer.tickRate/1000)*100  + currentY); // d = vit + 1/2a(t*t)
	}

	public static double calculateGrappleAngle(double currentX, double currentY,
			int grappleX, int grappleY) {

		// calculates lengths of each side of triangle
		double adj = grappleY - currentY;
		double opp = grappleX - currentX;
		double hypot = Math.sqrt(adj*adj + opp*opp);

		return Math.cos(adj/hypot);
	}

	public static int movePullGrapple(int currentX, int currentY, double pullSpeed,
			double grappleAngle) {

		// calculates lengths of each side of triangle
		double hypot = pullSpeed*Timer.tickRate/1000;
		double distX = speedX*Timer.tickRate/1000;
		double distY = Math.sqrt(hypot*hypot - distX*distX);
		System.out.println(hypot + " " + distX + " " + distY);

		return (int)(currentY+distY*100);
	}

	public static int calculateConnectPoint(double centreX, double centreY,
			int x, int y) {
		int grad = (centreY - )
		return 0;
	}
}