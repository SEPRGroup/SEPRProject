package sepr.atcGame;


public class TransferWaypoint extends Waypoint {
	//Variables
	private Position position2;
	private Airspace airspace1, airspace2; 
	private double bearing;	//radians
	
	//constructor
	/** Creates an waypoint that provides the linking between airspaces.
	 * 
	 * @param tranferPosition 
	 * @param airspace1Name
	 * @param airspace2Name
	 * @param transferBearing
	 * @param waypointName Name for the waypoint
	 * @param waypointPosition Position which the waypoint is to be created at in the airspace
	 */
	public TransferWaypoint(Position tranferPosition, Airspace airspace1Name, Airspace airspace2Name,
			double transferBearing, String waypointName, Position waypointPosition){
		
		super(waypointName, waypointPosition);
		
		airspace1 = airspace1Name;
		airspace2 = airspace2Name;
		bearing = transferBearing;
		
	}
	
	//getters and setters

	public Position getPosition2() {
		return position2;
	}

	public void setPosition2(Position tPosition) {
		this.position2 = tPosition;
	}

	public Airspace getAirspace1() {
		return airspace1;
	}

	public Airspace getAirspace2() {
		return airspace2;
	}

	public double getBearing() {
		return bearing;
	}
}