package sepr.atcGame;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;


class Airspace implements GameTime{
	//constants
	private static final int MAXFLIGHTS = 5;
	private static final int MAXWAYPOINTS = 5;
	
	//variables
	private String name;
	private Flight[] aircraft = new Flight[MAXFLIGHTS];	//Fixed size; may be filled
	private Waypoint[] waypoints = new Waypoint[MAXWAYPOINTS];	//Fixed size, should be filled
	private List<TransferWaypoint> transfers = new ArrayList<TransferWaypoint>(5);	//Flexible size, initially set here
	
	//constructor
	public Airspace(String airspaceName, ArrayList<TransferWaypoint> transferWaypoints){
		name = airspaceName;
		transfers.addAll(transferWaypoints);
	}
	
	/*	Full event generating routines and data	*/
	
	//getters and setters
	public String getName() {
		return name;
	}

	public Flight[] getAircraft() {
		return aircraft;
	}

	public Waypoint[] getWaypoints() {
		return waypoints;
	}
	
	//{!}allows modification (mutable)
	public List<TransferWaypoint> getTransfers() {
		return transfers;
	}

	//methods
	public void newFlight(Flight f) {
		//method will go in here
	}

	public void receiveFlight(Flight f, TransferWaypoint t) {
		//method will go in here
	}

	public void newObstacle(Flight flight) {
		//method will go in here
	}

	public void draw(Rectangle boundaries) {	//also needs a canvas/image to draw on: tbc
		//method will go in here
	}

	public void update(double time) {	//expecting game time in seconds
		//method will go in here
	}

	public void eventCrash(Flight f1, Flight f2) {
		//method will go in here
	}

	public void eventLanded(Flight f) {
		//method will go in here
	}

	public void eventHandover(Flight f) {
		//method will go in here
	}

	public void eventLost(Flight f) {
		//method will go in here
	}
}