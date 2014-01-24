package sepr.atcGame;

import java.util.Queue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import static sepr.atcGame.FlightStatus.*;
import static java.lang.Math.PI;
import static java.lang.Math.min;
import static java.lang.Math.max;
import static java.lang.Math.abs;
import static java.lang.Math.round;


public /*abstract*/ class Aircraft extends Flight {
	//MH: No longer Abstract Class - was necessary to instantiate for testing
	private BufferedImage image, rotatedImage;
	
	private static String 
		basePath = "/sepr/atcGame/Images/plane.png",
		highlightPath = "/sepr/atcGame/Images/Plane_Selected.png",
		crashedPath = "/sepr/atcGame/Images/plane_crashed.png";
	private static BufferedImage 
		base, highlight, crashed;	//images for the plane normally, when highlighted, crashing

	protected static Font dataFont = new Font(Font.MONOSPACED, Font.PLAIN, 12);

	//static characteristics
	public double
		minAlt, cruiseAlt, maxAlt,	//position:	m	
		minV, cruiseV, maxV, minClimb, maxClimb, maxTurn,	//velocity:	m/s , rad/s
		a, aClimb, aTurn;	//acceleration:	m/s/s , rad/s/s

	//dynamic characteristics
	public double
		v = 0,	//physical speed:	m/s
		vClimb = 0,	//current climb rate:	m/s
		vTurn = 0;	//current turn rate:	rad/s

	//target tracking variables
	public double 
		tAlt, tV, tBearing;
	

	//constructor
	public Aircraft(String id, Queue<Waypoint> flightPlan) {
		super(id, flightPlan);
		if (base == null || highlight == null){
			try {
				base = ImageIO.read(getClass().getResourceAsStream(basePath));
				highlight = ImageIO.read(getClass().getResourceAsStream(highlightPath));
				crashed = ImageIO.read(getClass().getResourceAsStream(crashedPath));
			} catch (IOException e){
				System.out.println("[ERROR Aircraft()]\tImage(s) loading failed");
			};
		}
		status = FlightStatus.WAITING;
		image = base;
	}


	//overridden methods
	@Override
	public final void update(double time) { 
		switch (status){
		case COMPLYING: {
			boolean changed = false;
			if (v != tV){
				changed = true;
				double dv = 0.7*(tV - v);	//arbitrary constant to limit motion;
				if (v < tV){
					dv = min(dv, a);}
				else{
					dv = max(dv, -a);
				}
				v += dv*time;
				if ( abs(tV-v) < 0.5){
					v = tV;	//end manoeuvres
					//System.out.println(getIdentifier() +"\thas completed accelerating");
				}
			}
			if (position.altitude != tAlt){
				changed = true;
				double dalt = 0.7*(tAlt - position.altitude);	//arbitrary constant to limit motion
				if (position.altitude < tAlt){
					vClimb += aClimb*time;
					vClimb = min(vClimb, maxClimb);	//cap climb rate
					vClimb = min(vClimb, dalt);	//cap to smooth climb
				}
				else{
					vClimb -= aClimb*time;
					vClimb = max(vClimb, minClimb);	//cap climb rate
					vClimb = max(vClimb, dalt);	//cap to smooth climb
				}
				if ( abs(tAlt-position.altitude) < 0.5){
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
					dturn *= 0.7;	//arbitrary constant to limit motion
					vTurn += aTurn*time;
					vTurn = min(vTurn, maxTurn);	//cap to limit rotation
					vTurn = min(vTurn, dturn);	//cap to smooth rotation
				}
				else{	//turn left
					if (dturn > 0){
						dturn = dturn -2*PI;}	//normalize, -PI to 0
					dturn *= 0.7;	//arbitrary constant to limit motion
					vTurn -= aTurn*time;
					vTurn = max(vTurn, -maxTurn);	//cap to limit rotation
					vTurn = max(vTurn, dturn);	//cap to smooth rotation
				}
				rotatedImage = null;
				if ( (abs(tBearing -bearing) < 0.01) || (abs(tBearing+2*PI -bearing) < 0.01)){
					vTurn = 0;
					bearing = tBearing;	//end manoeuvres
					//System.out.println(getIdentifier() +"\thas completed turning");
				}
			}
			if (!changed){
				status = CRUISING;
				//System.out.println(getIdentifier() +"\thas completed manoeuvres");
			}
			//execute cruising behaviour : no break
		}
		case CRUISING: {
			//update positions
			double vx, vy;	//velocity components
			bearing += vTurn*time; 
			vx = Math.sin(bearing) * v;
			vy = Math.cos(bearing) * v;
			position.x += vx*time;
			position.y -= vy*time;
			position.altitude += vClimb*time;
			break;
		}
		case WAITING: break;
		case CRASHING: {
			if (position.altitude > 0){
				double vx, vy;	//velocity components
				vClimb -= (9.8 *time);
				v *= 1 -(0.1 *time);
				bearing += vTurn*time; 
				vx = Math.sin(bearing) * v;
				vy = Math.cos(bearing) * v;
				position.x += vx*time;
				position.y -= vy*time;
				position.altitude += vClimb*time;
			}
			if (position.altitude < 0){
				position.altitude = 0;
				v = 0;
			}
			break;}
		case LANDING:
			break;
		case TAKEOFF: {
			double vx, vy;	//velocity components
			v += a*time;
			vx = Math.sin(bearing) * v;
			vy = Math.cos(bearing) * v;
			position.x += vx*time;
			position.y -= vy*time;
			if (v >= minV){
				tV = cruiseV;
				tAlt = cruiseAlt;
				status = COMPLYING;
			}
			break;
		}
		case TAXIING:
			break;

		}
	}

	public final void draw(Graphics g, Point location, double scale) {
		//calculate rotated image if invalidated
		if (rotatedImage == null){
			AffineTransformOp op = new AffineTransformOp(
					AffineTransform.getRotateInstance(bearing, image.getWidth()/2.0, image.getHeight()/2.0), 
					AffineTransformOp.TYPE_BILINEAR);
			rotatedImage = op.filter(image, null);
		}
		//precalculate useful positioning values 
		int w=rotatedImage.getWidth()/2, h=rotatedImage.getHeight()/2;

		//draw plane image
		g.drawImage(rotatedImage, location.x -w, location.y -h,	null);

		{	//label attributes
			String idString = ('\u2708' +getIdentifier());
			String dataString = String.format("\u21A5%1$4d \u2192%2$3d", 
					Math.round(position.altitude), round(v));
			//unused bearing code:	 \u21BB%3$03d	, round(Math.toDegrees(bearing))
			g.setFont(dataFont);
			g.drawString(idString, location.x -w/2 , location.y +h +dataFont.getSize() -3);	
			g.drawString(dataString, location.x -w, location.y +h +dataFont.getSize()*2 -3);
		}

	}

	@Override
	public final void transition(Airspace a, TransferWaypoint t){
		//set position, preserving old altitude
		bearing = t.getBearingTo(a);
		Position pos = new Position(t.getPosition(a));
		pos.altitude = position.altitude;
		position = pos;
		//place plane 2 second's worth of distance from edge of screen
		pos.x -= 2 * Math.sin(bearing) *v;
		pos.y += 2 * Math.cos(bearing) *v;

		//clear old instructions
		tAlt = position.altitude;
		tBearing = bearing;
		tV = v;	
		status = CRUISING;
	}

	public final void init(double speed, double altitude){
		if (WAITING == status){
			v = speed;
			position.altitude = altitude;
			//System.out.println(getIdentifier() +" init");
		}
		else System.out.println("Cannot call Aircraft.init() if not WAITING");
	}

	@Override
	public final void takeOff(Airspace a, TransferWaypoint t) {
		if (WAITING == status){
			position = new Position(t.getPosition(a));
			bearing = t.getBearingTo(a);
			status = TAKEOFF;
		}
		else System.out.println("Cannot call Aircraft.init() if not WAITING");//{!}error
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
		/*System.out.println("Aircraft.toAltitude()| minAlt="+minAlt+"maxAlt="+maxAlt); //MH: Used for test creation. */
		if (altitude < minAlt){
			System.out.println(getIdentifier() +" cannot go to this low");	//{!}
			tAlt = minAlt;
		} else if (altitude > maxAlt){
			System.out.println(getIdentifier() +" cannot go to this high");	//{!}
			tAlt = maxAlt;
		} else {
			tAlt = altitude;
		}
		if (CRUISING == status){
			status = COMPLYING;}
	}

	@Override
	public final void toSpeed(double speed) {
		if (speed < minV){
			System.out.println(getIdentifier() +" cannot go to this slow");	//{!}
			tV = minV;
		} else if (speed > maxV){
			System.out.println(getIdentifier() +" cannot go to this fast");	//{!}
			tV = maxV;
		} else {
			tV = speed;
		}
		if (CRUISING == status){
			status = COMPLYING;}
	}

	@Override
	public final void abort() {
		tAlt =cruiseAlt;
		vTurn = 0;
		tBearing = bearing;
		tV = cruiseV;
		status = COMPLYING;
	}

	@Override
	public final void crash() {
		status = CRASHING;
		image = crashed;
		rotatedImage = null;
	}

	@Override
	public final void highlight(Boolean aFlag){
		if (status != FlightStatus.CRASHING){
			if (aFlag){
				image = highlight;
			} else image = base;
			rotatedImage = null;
		}
	}
}