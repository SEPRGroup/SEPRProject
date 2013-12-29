package sepr.atcGame;

import java.util.Queue;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import static sepr.atcGame.FlightStatus.*;


abstract class Aircraft extends Flight {

	private BufferedImage image, rotatedImage;
	
	//static characteristics
	protected double
		minAlt, cruiseAlt, maxAlt,	//position:	m	
		minV, cruiseV, maxV, minClimb, maxClimb, maxTurn,	//velocity:	m/s , rad/s
		a, aClimb, aTurn;	//acceleration:	m/s/s , rad/s/s

	//dynamic characteristics
	private double
		v = 0,	//physical speed:	m/s
		vClimb = 0,	//current climb rate:	m/s
		vTurn = 0;	//current turn rate:	rad/s

	//target tracking variables
	private double 
		tAlt, tV, tBearing;


	//constructor
	protected Aircraft(String id, Queue<Waypoint> flightPlan) {
		super(id, flightPlan);
		try {image = ImageIO.read(new File("src/sepr/atcGame/Images/plane.png"));}
		catch (IOException e){};

		status = FlightStatus.WAITING;
	}


	//overridden methods
	@Override
	public final void update(double time) { 
		switch (status){
		case COMPLYING:
			if (v != tV){
				if (v < tV){
					v += a*time;
					v = Math.min(v, maxV);
				}
				else{
					v -= a*time;
					v = Math.max(v, minV);
				}
			}
			if (position.altitude != tAlt){
				if (position.altitude < tAlt){
					vClimb += aClimb*time;
					vClimb = Math.min(vClimb, maxClimb);
					}
				else{
					vClimb -= aClimb*time;
					vClimb = Math.max(vClimb, minClimb);
				}
			}
			else vClimb = 0;
			if (bearing != tBearing){
				if (bearing < tBearing){	//{!} test if should turn left or right
					vTurn += aTurn*time;
					vTurn = Math.min(vTurn, maxTurn);
					}
				else{
					vTurn -= aTurn*time;
					vTurn = Math.max(vTurn, -maxTurn);
				}
				rotatedImage = null;
			}
			else vTurn = 0;
			//execute cruising behaviour
		case CRUISING:
			//update positions
			double vx, vy;	//velocity components
			bearing += vTurn*time; 
			vx = Math.sin(bearing) * v;
			vy = Math.cos(bearing) * v;
			position.x += vx*time;
			position.y -= vy*time;
			position.altitude += vClimb;
			break;
		case WAITING: break;
		case CRASHING:
			break;
		case LANDING:
			break;
		case TAKEOFF:
			break;
		case TAXIING:
			break;
					
		}
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
		status = FlightStatus.TAKEOFF;
	}

	@Override
	public final void land(TransferWaypoint t) {
		status = FlightStatus.LANDING;
	}

	@Override
	public final void turnTo(double bearing) {
		tBearing = bearing;
		if (CRUISING == status){
			status = COMPLYING;}
	}

	@Override
	public final void toAltitude(double altitude) {
		tAlt = altitude;
		if (CRUISING == status){
			status = COMPLYING;}
	}

	@Override
	public final void toSpeed(double speed) {
		tV = speed;
		if (CRUISING == status){
			status = COMPLYING;}
	}

	@Override
	public final void abort() {
		tAlt =cruiseAlt;
		tBearing = bearing;
		tV = cruiseV;
		status = COMPLYING;
	}

	@Override
	public final void crash() {
		status = FlightStatus.CRASHING;
	}

	@Override
	public final void setBearing(double bearing) {
		super.setBearing(bearing);
		//tBearing = bearing; {!} disabled while setBearing is used by Airspace
		if (CRUISING == status){
			status = COMPLYING;}
		rotatedImage = null;
	}

}