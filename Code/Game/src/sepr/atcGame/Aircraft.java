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

		private BufferedImage image,rotatedimage;
		private double xIncrease,yIncrease,speed = 250;
		private AffineTransform tx = new AffineTransform();
		
		public void draw(Graphics g, Point location, double scale) {
			
		
			g.drawImage(rotatedimage,
					location.x -(image.getWidth(null)/2),
					location.y -(image.getHeight(null)/2),
					null);
		}
		
		
	//constructor
	protected Aircraft(String id, Queue<Waypoint> flightPlan) {
		super(id, flightPlan);
		try{
			image = ImageIO.read(new File("src/sepr/atcGame/Images/plane.png"));
			rotatedimage = image;
		}catch (IOException e){};
	}
	
	public final void originalPosition(Position position){
		this.position = position;
	}
	
	//overridden methods
	@Override
	public final void update(double time) { 
		xIncrease = Math.sin(getBearing()) * speed ;
		yIncrease = -(Math.cos(getBearing()) * speed) ;
		
		position.x += xIncrease*time;
		position.y += yIncrease*time;
		//position.x += time*250;	//move at a speed of 250 m/s {!} independent of bearing
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
	public void setBearings(double bearing) {//Use setBearings instead of setBearing to allow for rotation of the image
		setBearing(bearing);
		tx.rotate(bearing-(Math.PI/2),image.getWidth(null)/2,image.getHeight(null)/2);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		rotatedimage = op.filter(image, null);
	}

}