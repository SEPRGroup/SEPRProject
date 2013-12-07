package sepr.atcGame;


class TransferWaypoint extends Waypoint {
	//Variables
	private Position position2;
	private Airspace airspace1, airspace2; 
	private double bearing;	//radians, 0 - 2*Pi
	
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
	public TransferWaypoint(String name, Airspace airspace1, Airspace airspace2,
			double bearing){
		super(name, new Position(-1,-1,-1));	
		this.airspace1 = airspace1;
		this.airspace2 = airspace2;
		this.bearing = bearing;
		position2 = new Position(-1,-1,-1);	
	}
	
	
	//getters and setters
	public Position getPosition(Airspace airspace) {
		if(airspace.equals(airspace1)){
			return this.getPosition();}
		else if(airspace.equals(airspace2)){
			return this.position2;}
		else return null;			
	}

	public void setPosition(Airspace airspace, Position position) {
		if(airspace.equals(airspace1)){
			setPosition(position);}
		else if(airspace.equals(airspace2)){
			setPosition2(position);}
		//else {!}
	}
		
	public Position getPosition2() {
		return position2;
	}
	
	public void setPosition2(Position position) {
		this.position2 = position;
	}

	public Airspace getAirspace1() {
		return airspace1;
	}

	public Airspace getAirspace2() {
		return airspace2;
	}

	public double getBearing(Airspace airspace) {
		if(airspace.equals(airspace1)){
			return bearing;}
		else if(airspace.equals(airspace2)){
			return bearing<Math.PI ? bearing+Math.PI : bearing-Math.PI;}
		else return 0; //{!}
	}
}