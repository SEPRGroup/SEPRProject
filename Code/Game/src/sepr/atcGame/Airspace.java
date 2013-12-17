package sepr.atcGame;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;


abstract class Airspace extends JPanel implements GameTime{
	//constants
	private static final int MAX_FLIGHTS = 5;
	protected static final int MAX_WAYPOINTS = 5;
	
	//variables
	private String airspaceName;
	private Flight[] aircraft = new Flight[MAX_FLIGHTS];	//Fixed size; may be filled
	protected Waypoint[] waypoints = new Waypoint[MAX_WAYPOINTS];	//Fixed size, may be filled
	private List<TransferWaypoint> transfers;
	private List<testAircraft> planes;
	
	//constructor
	protected Airspace(String airspaceName){
		this.airspaceName = airspaceName;	
		// Math.sin(Math.toRadians(i*360/MAX_WAYPOINTS))
		
	}
	
	/*	Full event generating routines and data	*/
	
	
	//getters and setters
	public String getAirspaceName(){
		return airspaceName;
	}

//	public Flight[] getAircraft() {
//		return aircraft;
//	}
	
	public List<testAircraft> getAircraft() {
		return planes;
	}

	public Waypoint[] getWaypoints() {
		return waypoints;
	}
	
	//{!}allows modification (mutable)
	public List<TransferWaypoint> getTransfers() {
		return transfers;
	}
	
	public void setTransfers(List<TransferWaypoint> transfers){
		this.transfers = new ArrayList<TransferWaypoint>(transfers);
	}
	
	public void setFlights(List<testAircraft> flights){
		this.planes = new ArrayList<testAircraft>(flights);
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