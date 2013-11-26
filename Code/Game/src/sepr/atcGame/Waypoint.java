package sepr.atcGame;


class Waypoint {
	private String name;
	private Vector position;		//Vector is a user defined variable, possibly a 3d array?
	
	//constructor
	public Waypoint(String waypointName, Vector waypointPosition){
		name = waypointName;
		position = waypointPosition;
	}

	
	//getters
	public String getName() {
		return name;
	}

	public Vector getPosition() {
		return position;
	}
}