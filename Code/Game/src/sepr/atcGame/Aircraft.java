package sepr.atcGame;

import java.util.Queue;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


abstract class Aircraft extends Flight {

	private BufferedImage image, rotatedImage;
	private double speed = 250;	//physical speed; {!}


	//constructor
	protected Aircraft(String id, Queue<Waypoint> flightPlan) {
		super(id, flightPlan);
		try {image = ImageIO.read(new File("src/sepr/atcGame/Images/plane.png"));}
		catch (IOException e){};
	}


	//overridden methods
	@Override
	public final void update(double time) { 
		double vx, vy, vz;	//velocity components
		vx = Math.sin(getBearing()) * speed;
		vy = Math.cos(getBearing()) * speed;

		position.x += vx*time;
		position.y -= vy*time;
		setBearing(getBearing() +0.15*time);	//{!} test code to continuously turn right
	}

	public final void draw(Graphics g, Point location, double scale) {
		if (rotatedImage == null){
			AffineTransformOp op = new AffineTransformOp(
					AffineTransform.getRotateInstance(getBearing(), image.getWidth()/2, image.getHeight()/2), 
					AffineTransformOp.TYPE_BILINEAR);
			rotatedImage = op.filter(image, null);
		}

		g.drawImage(rotatedImage,
				location.x -(rotatedImage.getWidth()/2),
				location.y -(rotatedImage.getHeight()/2),
				null);
	}

	@Override
	public final void takeOff(TransferWaypoint t) {
		// TODO
	}

	@Override
	public final void land(TransferWaypoint t) {
		// TODO
	}

	@Override
	public final void turnTo(double bearing) {
		// TODO
	}

	@Override
	public final void toAltitude(double altitude) {
		// TODO
	}

	@Override
	public final void toSpeed(double speed) {
		// TODO
	}

	@Override
	public final void abort() {
		// TODO
	}

	@Override
	public final void crash() {
		// TODO
	}

	@Override
	public final void setBearing(double bearing) {
		super.setBearing(bearing);
		rotatedImage = null;
	}

}