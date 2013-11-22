package sepr.atcGame;
class Flight {
	// variables
	public String identifier;
	public FlightStatus status;  //FlightStatus is a user defined data type
	public Vector position;		 //Vector is a user defined data type
	public Queue flightPlan;	 //Queue **might** be a user defined data type
	public Conditions condition; //Conditions is a user defined data type

	//methods
	public void takeOff(TransferWaypoint t) { 	//TransferWaypoint is a user defined data type
		//method will go in here
	}

	public void land(TransferWaypoint t) { 		
		//method will go in here
	}

	public void turnTo(Bearing b) {				//Bearing is a user defined data type
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