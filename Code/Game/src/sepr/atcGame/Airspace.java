package sepr.atcGame;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;


abstract class Airspace extends JPanel implements GameTime{
	//constants
	private static final int MAX_FLIGHTS = 5;
	private static final int MAX_WAYPOINTS = 5;
	
	//variables
	private String airspaceName;
	private Flight[] aircraft = new Flight[MAX_FLIGHTS];	//Fixed size; may be filled
	private Waypoint[] waypoints = new Waypoint[MAX_WAYPOINTS];	//Fixed size, may be filled
	private List<TransferWaypoint> transfers;
	
	//constructor
	protected Airspace(String airspaceName, ArrayList<TransferWaypoint> transferWaypoints){
		this.airspaceName = airspaceName;
		
		TransferWaypoint waypoint1 = new TransferWaypoint(null, this, null, 360, "Top",new Position(400,0,50));
		TransferWaypoint waypoint2 = new TransferWaypoint(null, this, null, 360, "Left",new Position(0,300,50));
		TransferWaypoint waypoint3 = new TransferWaypoint(null, this, null, 360, "Right",new Position(770,300,50));
		TransferWaypoint waypoint4 = new TransferWaypoint(null, this, null, 360, "Bottom",new Position(400,545,50));
		
		transferWaypoints.add(waypoint1);
		transferWaypoints.add(waypoint2);
		transferWaypoints.add(waypoint3);
		transferWaypoints.add(waypoint4);
		transfers = new ArrayList<TransferWaypoint>(transferWaypoints);
		
		
	}
	
	/*	Full event generating routines and data	*/
	
	
	//getters and setters
	public String getAirspaceName(){
		return airspaceName;
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
	public abstract void newFlight(Flight f);

	public abstract void receiveFlight(Flight f, TransferWaypoint t);

	public abstract void newObstacle(Flight flight);

	public final void eventCrash(Flight f1, Flight f2) {
		//method will go in here
	}

	public final void eventLanded(Flight f) {
		//method will go in here
	}

	public final void eventHandover(Flight f) {
		//method will go in here
	}

	public final void eventLost(Flight f) {
		//method will go in here
	}
	
}