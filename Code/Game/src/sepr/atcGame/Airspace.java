package sepr.atcGame;


class Airspace {
	//variables
	private String name;
	private Flight aircraft; 			//Flight is a user defined data type, an array [0..k]
	private Waypoint waypoints;			//Waypoint is a user defined data type, an array [0..k]
	private TransferWaypoint transfers;	//TransferWaypoint is a user defined data type
	
	//constructor
	public Airspace(String airspaceName, TransferWaypoint transferWaypoints){
		name = airspaceName;
		transfers = transferWaypoints;
	}
	
	//getters and setters
	public String getName() {
		return name;
	}

	public Flight getAircraft() {
		return aircraft;
	}

	public Waypoint getWaypoints() {
		return waypoints;
	}

	public TransferWaypoint getTransfers() {
		return transfers;
	}

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