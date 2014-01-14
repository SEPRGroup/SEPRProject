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
import static java.lang.Math.PI;
import static java.lang.Math.min;
import static java.lang.Math.max;
import static java.lang.Math.abs;


abstract class Aircraft extends Flight {

	private BufferedImage image, rotatedImage,altitudeImage;

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
			boolean changed = false;
			if (v != tV){
				changed = true;
				double dv = 0.5*(tV - v);	//arbitrary constant to limit motion;
				if (v < tV){
					dv = min(dv, a);}
				else{
					dv = max(-dv, -a);
				}
				v += dv*time;
				if ( abs(tV-v) < 0.1){
					v = tV;	//end manoeuvres
					//System.out.println(getIdentifier() +"\thas completed accelerating");
				}
			}
			if (position.altitude != tAlt){
				changed = true;
				double dalt = 0.5*(tAlt - position.altitude);	//arbitrary constant to limit motion
				if (position.altitude < tAlt){
					vClimb += aClimb*time;
					vClimb = min(vClimb, maxClimb);	//cap climb rate
					vClimb = min(vClimb, dalt);	//cap to smooth climb
				}
				else{
					vClimb -= aClimb*time;
					vClimb = max(vClimb, minClimb);	//cap climb rate
					vClimb = max(vClimb, -dalt);	//cap to smooth climb
				}
				if ( abs(tAlt-position.altitude) < 0.1){
					vClimb = 0;
					position.altitude = tAlt;	//end manoeuvres
					//System.out.println(getIdentifier() +"\thas completed climbing");
				}
			}
			if (bearing != tBearing){
				changed = true;
				double dturn = tBearing -bearing;
				if ( ((dturn>=0) && (dturn<=PI)) || (dturn<=-PI) ){	//turn right
					if (dturn < 0){
						dturn += 2*PI;}	//normalize, 0 to PI
					dturn *= 0.5;	//arbitrary constant to limit motion
					vTurn += aTurn*time;
					vTurn = min(vTurn, maxTurn);	//cap to limit rotation
					vTurn = min(vTurn, dturn);	//cap to smooth rotation
				}
				else{	//turn left
					if (dturn > 0){
						dturn = dturn -2*PI;}	//normalize, -PI to 0
					dturn *= 0.5;	//arbitrary constant to limit motion
					vTurn -= aTurn*time;
					vTurn = max(vTurn, -maxTurn);	//cap to limit rotation
					vTurn = max(vTurn, dturn);	//cap to smooth rotation
				}
				rotatedImage = null;
				altitudeImage = null;
				if ( (abs(tBearing -bearing) < 0.01) || (abs(tBearing+2*PI -bearing) < 0.05)){
					vTurn = 0;
					bearing = tBearing;	//end manoeuvres
					//System.out.println(getIdentifier() +"\thas completed turning");
				}
			}
			if (!changed){
				status = CRUISING;
				System.out.println(getIdentifier() +"\thas completed manoeuvres");
			}
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
			altitudeImage = new BufferedImage(rotatedImage.getWidth(), 20, BufferedImage.TYPE_INT_ARGB);
			Graphics g2 = altitudeImage.getGraphics();
			g2.setColor(Color.black); 
			g2.drawString(String.valueOf(position.altitude), 10, 10);
			g2.drawImage(altitudeImage,0,0,null);
		}
		
		g.drawImage(rotatedImage,
				location.x -(rotatedImage.getWidth()/2),
				location.y -(rotatedImage.getHeight()/2),
				null);
		
		if (bearing >1.5 * Math.PI){//draw data labels(eg. altitude) at top of image
			//System.out.println(bearing+ "\n");
			g.drawImage(altitudeImage,
					location.x -(altitudeImage.getWidth()/2),
					location.y -(rotatedImage.getHeight()/2)-(altitudeImage.getHeight()/2),
					null);
		}else if(bearing < Math.PI/2){
			g.drawImage(altitudeImage,
					location.x -(altitudeImage.getWidth()/2),
					location.y -(rotatedImage.getHeight()/2)-(altitudeImage.getHeight()/2),
					null);
			
		}else{//draw data labels(eg. altitude) at bottom of image
			g.drawImage(altitudeImage,
					location.x -(altitudeImage.getWidth()/2),
					location.y +(rotatedImage.getHeight()/2)+(altitudeImage.getHeight()/2),
					null);
		}
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
		//{!} min/max checks
		tAlt = altitude;
		if (CRUISING == status){
			status = COMPLYING;}
	}

	@Override
	public final void toSpeed(double speed) {
		//{!} min/max checks
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
		altitudeImage = null;
	}

}