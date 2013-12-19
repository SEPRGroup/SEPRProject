package sepr.atcGame;

import java.awt.Graphics;
import java.util.Queue;

abstract class Flight implements GameTime, Drawable {
	// variables
	private String identifier;
	private FlightStatus status;  //FlightStatus is a user defined data type
	protected Position position;
	private Queue<Waypoint> flightPlan;
	private FlightConditions conditions;
	private double bearing;	//radians
	
	
	// constructor
 	protected Flight(String id, Queue<Waypoint> flightPlan){
		identifier = id;
		this.flightPlan = flightPlan;
		this.position = new Position(-1, -1, -1);
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
	
	public final void setPosition(Position position){
		this.position = position;
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
	
	public void setBearing(double bearing) {
		this.bearing = bearing;		
	}
	
	
	// methods
	public final void nextWaypoint(){
		flightPlan.poll();
	}
	
	public abstract void takeOff(TransferWaypoint t);
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