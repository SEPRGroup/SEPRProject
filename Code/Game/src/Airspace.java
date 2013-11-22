
class Airspace {
	//variables
	public String name;
	public Flight aircraft; 			//Flight is a user defined data type, an array [0..k]
	public Waypoint waypoints;			//Waypoint is a user defined data type, an array [0..k]
	public TransferWaypoint tranfers;	//TransferWaypoint is a user defined data type

	//methods
	public void newFlight(Flight f) {
		//method will go in here
	}

	public void recieveFlight(Flight f, TransferWaypoint t) {
		//method will go in here
	}

	public void newObstacle(Flight flight) {	//I believe flight here is referring to the class
		//method will go in here
	}

	public void draw(Rect boundaries) {			//unsure what Rect is - Charlie can clarify?
		//method will go in here
	}

	public void update(Time time) {				//Time isn't a Java recognised data type - but there may be Java classes etc for it?
		//method will go in here
	}

	public void eventCrash(Flight f1, f2) {
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