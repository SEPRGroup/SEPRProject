package sepr.atcGame;

import java.awt.Graphics;
import java.util.Queue;

abstract class Flight implements GameTime, Drawable {
	// variables
	private String identifier;
	protected FlightStatus status;  //FlightStatus is a user defined data type
	protected Position position;
	protected final Queue<Waypoint> flightPlan;
	private FlightConditions conditions;
	protected double bearing;	//radians
	
	
	// constructor
 	protected Flight(String id, Queue<Waypoint> flightPlan){
		identifier = id;
		this.flightPlan = flightPlan;
		position = new Position(-1, -1, -1);	//invalid position
	}
 	
	//getters and setters
 	public final FlightConditions getCondition() {
		return conditions;
	}
	public void setConditions(FlightConditions conditions) {
		this.conditions = conditions;
	}

	public final String getIdentifier() {
		return identifier;
	}
	
	public final FlightStatus getStatus() {
		return status;
	}

	public final Position getPosition() {
		return position;
	}

	//{!}allows modification: mutable
	public final Queue<Waypoint> getFlightPlan() {
		return flightPlan;
	}

	public final double getBearing() {
		return bearing;
	}


	// methods
	public final void nextWaypoint(){
		flightPlan.poll();
	}
	
	public abstract void init(double speed, double altitude);
	
	public abstract void transition(Airspace a, TransferWaypoint t);
	public abstract void takeOff(Airspace a, TransferWaypoint t);
	public abstract void land(TransferWaypoint t);
	public abstract void turnTo(double bearing);
	public abstract void toAltitude(double altitude);
	public abstract void toSpeed(double speed);
	public abstract void abort();
	public abstract void crash();


	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		
	}

}