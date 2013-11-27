package sepr.atcGame;


class Waypoint {
	private String name;
	private Position position;
	
	//constructor
	public Waypoint(String name, Position position){
		this.name = name;
		this.position = position;
	}

	
	//getters
	public String getName() {
		return name;
	}

	public Position getPosition() {
		return position;
	}
}