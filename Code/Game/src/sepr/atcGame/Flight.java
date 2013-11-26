package sepr.atcGame;
abstract class Flight {
	// variables
	private String identifier;
	private FlightStatus status;  //FlightStatus is a user defined data type
	private Vector position;		 //Vector is a user defined data type
	private Queue flightPlan;	 //Queue **might** be a user defined data type
	private Conditions condition; //Conditions is a user defined data type
	private double bearing;
	
	// constructor
 	public Flight(String id, Queue plan){
		identifier = id;
		flightPlan = plan;
	}
	
	//getters and setters
	
 	public Conditions getCondition() {
		return condition;
	}

	public void setCondition(Conditions condition) {
		this.condition = condition;
	}

	public String getIdentifier() {
		return identifier;
	}

	public FlightStatus getStatus() {
		return status;
	}

	public Vector getPosition() {
		return position;
	}

	public Queue getFlightPlan() {
		return flightPlan;
	}

	public double getBearing() {
		return bearing;
	}

	public void setBearing(double bearing) {
		this.bearing = bearing;
	}
	
	// methods
	
	public void takeOff(TransferWaypoint t) { 	//TransferWaypoint is a user defined data type
		//method will go in here
	}

	public void land(TransferWaypoint t) { 		
		//method will go in here
	}

	public void turnTo(double b) {				//Bearing is a user defined data type
		//method will go in here
	}

	public void toAltitude(double altitude) {
		//method will go in here
	}

	public void nextWaypoint() {
		//method will go in here
	}

	public void toSpeed(double speed) {
		//method will go in here
	}

	public void abort() {
		//method will go in here
	}

	public void crash() {
		//method will go in here
	}

	public void draw(Vector location, int size) {
		//method will go in here
	}

	public void update(Time t) { 				//Time **might** be a user defined data type
		//method will go in here
	}
}