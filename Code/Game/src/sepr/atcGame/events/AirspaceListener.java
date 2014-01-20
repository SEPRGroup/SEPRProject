package sepr.atcGame.events;

import sepr.atcGame.Flight;

public interface AirspaceListener {
	
	public void eventCrash(Flight f1, Flight f2);
	public void eventLanded(Flight f);
	public void eventHandover(Flight f);
	public void eventLost(Flight f);
	public void eventHighlighted(Flight f);
}
