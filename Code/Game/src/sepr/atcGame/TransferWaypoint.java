package sepr.atcGame;


public class TransferWaypoint extends Waypoint {
	//Variables
	private Position position2;
	private Airspace airspace1, airspace2; 
	private double bearing;	//radians
	
	//constructor
	public TransferWaypoint(Position tranferPosition, Airspace airspace1Name, Airspace airspaceName2,
			double transferBearing, String waypointName, Position waypointPosition){
		super(waypointName, waypointPosition);
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