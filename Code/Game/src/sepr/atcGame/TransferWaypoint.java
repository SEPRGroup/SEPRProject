package sepr.atcGame;


class TransferWaypoint extends Waypoint {
	//Variables
	private Vector tPosition;		//Vector is a user defined data type
	private Airspace airspace1, airspace2; 
	private double bearing;			//Bearing is a user defined data type
	
	//constructor
	public TransferWaypoint(Vector tranferPosition, Airspace airspace1Name, Airspace airspaceName2,
			double transferBearing, String waypointName, Vector waypointPosition){
		super(waypointName, waypointPosition);
	}
	
	//getters and setters

	public Vector gettPosition() {
		return tPosition;
	}

	public void settPosition(Vector tPosition) {
		this.tPosition = tPosition;
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