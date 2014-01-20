package sepr.atcGame;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

import sepr.atcGame.events.ATCListener;
import sepr.atcGame.events.AirspaceListener;


public abstract class Airspace extends JPanel implements GameTime{
	//constants
	protected static final int MAX_FLIGHTS = 5;
	protected static final int MAX_WAYPOINTS = 10;
	
	//variables
	private String airspaceName;
	protected Flight[] aircraft = new Flight[MAX_FLIGHTS];	//Fixed size; may be filled
	protected Waypoint[] waypoints = new Waypoint[MAX_WAYPOINTS];	//Fixed size, may be filled
	protected List<TransferWaypoint> transfers;
	
	private List<AirspaceListener> listeners = new ArrayList<AirspaceListener>();
	
	
	//constructor
	protected Airspace(String airspaceName){
		this.airspaceName = airspaceName;
	}
	
	
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
	
	public void setTransfers(List<TransferWaypoint> transfers){
		this.transfers = new ArrayList<TransferWaypoint>(transfers);
	}

	
	//methods	
	public abstract void newFlight(Flight f);
	public abstract void receiveFlight(Flight f, TransferWaypoint t);
	public abstract void newObstacle(Flight flight);
	
	public final void addListener(AirspaceListener toAdd){
		listeners.add(toAdd);
	}
	
	protected final void eventCrash(Flight f1, Flight f2) {
		removeFlight(f1);
		removeFlight(f2);
		for (AirspaceListener l : listeners)
			l.eventCrash(f1, f2);
	}

	protected final void eventLanded(Flight f) {
		removeFlight(f);
		for (AirspaceListener l : listeners)
			l.eventLanded(f);
	}

	protected final void eventHandover(Flight f) {
		removeFlight(f);
		for (AirspaceListener l : listeners)
			l.eventHandover(f);	
	}

	protected final void eventLost(Flight f) {
		removeFlight(f);
		for (AirspaceListener l : listeners)
			l.eventLost(f);
	}
	
	protected final void eventHighlighted(Flight f) {
		for (AirspaceListener l : listeners)
			if(l!= null){
				l.eventHighlighted(f);
			}
			
	}
	
	private final void removeFlight(Flight f) {
		//finds and removes a flight from the array of active flights 
		for(int i=0; i<MAX_FLIGHTS; i++){
			if(aircraft[i] == f){
				aircraft[i] = null;
				break;
			}
		}		
	}
	
}