package sepr.atcGame;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Queue;

abstract class Flight implements GameTime, Drawable {
	// variables
	private String identifier;
	private FlightStatus status;  //FlightStatus is a user defined data type
	private Position position;
	private Queue<Waypoint> flightPlan;
	private FlightConditions condition;
	private double bearing;	//radians
	
	// constructor
 	public Flight(String id, Queue<Waypoint> flightPlan){
		identifier = id;
		this.flightPlan = flightPlan;
		
		assert(flightPlan.peek() instanceof TransferWaypoint);
	}
	
	//getters and setters
 	public FlightConditions getCondition() {
		return condition;
	}
	public void setCondition(FlightConditions condition) {
		this.condition = condition;
	}

	public String getIdentifier() {
		return identifier;
	}
	
	public FlightStatus getStatus() {
		return status;
	}

	public Position getPosition() {
		return position;
	}

	//{!}allows modification: mutable
	public Queue<Waypoint> getFlightPlan() {
		return flightPlan;
	}

	public double getBearing() {
		return bearing;
	}
	public void setBearing(double bearing) {
		this.bearing = bearing;
	}
	
	
	// methods
	public void takeOff(TransferWaypoint t) { 	//TransferWaypoint is a user defined data type
		//method will go in here
	}

	public void land(TransferWaypoint t) { 		
		//method will go in here
	}

	public void turnTo(double bearing) {	//bearing in radians
		//method will go in here
	}

	public void toAltitude(double altitude) {
		//method will go in here
	}

	public void nextWaypoint() {
		//method will go in here
	}

	public void toSpeed(double speed) {
		//method will go in here
	}

	public void abort() {
		//method will go in here
	}

	public void crash() {
		//method will go in here
	}
	
	public void draw(Graphics g, Point location, double scale) {
		//method will go in here		
	}



	public void update(double time) {	//expecting game time in seconds
		//method will go in here
	}
}