package sepr.atcGame;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;


public abstract class Airspace extends JPanel implements GameTime{
	//constants
	protected static final int MAX_FLIGHTS = 5;
	protected static final int MAX_WAYPOINTS = 5;
	
	//variables
	private String airspaceName;
	protected Flight[] aircraft = new Flight[MAX_FLIGHTS];	//Fixed size; may be filled
	protected Waypoint[] waypoints = new Waypoint[MAX_WAYPOINTS];	//Fixed size, may be filled
	protected List<TransferWaypoint> transfers;
	
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
	
	public void setTransfers(List<TransferWaypoint> transfers){
		this.transfers = new ArrayList<TransferWaypoint>(transfers);
	}

	
	//methods	
	public abstract void newFlight(Flight f);

	public abstract void receiveFlight(Flight f, TransferWaypoint t);

	public abstract void newObstacle(Flight flight);
	
	public final void findAndRemoveFlight(Flight f) {
		//This function cuts down on repeated code
		//It finds and removes a flight which has been terminated in one way or another
		//from the array of active flights 
		
	}

	public final void eventCrash(Flight f1, Flight f2) {
		//TODO remove crashed planes from array of active flights
		//TODO remove points or end game accordingly
	}

	public final void eventLanded(Flight f) {
		//TODO remove landed plane from array of active flights
		//TODO reward points accordingly
	}

	public final void eventHandover(Flight f) {
		//TODO remove landed plane from array of active flights
		//TODO reward points accordingly
	}

	public final void eventLost(Flight f) {
		//TODO remove landed plane from array of active flights
		//TODO remove points accordingly
	}
	
}