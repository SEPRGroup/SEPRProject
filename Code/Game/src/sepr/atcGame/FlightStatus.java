package sepr.atcGame;

public enum FlightStatus {
	WAITING ("No orders"),
	TAXIING ("Taxiing"),
	CRUISING ("Cruising"),
	TAKEOFF ("Taking off"),
	LANDING ("Landing"),
	COMPLYING ("Following orders"),
	CRASHING ("Crashed");
	
	private final String labelText;
	FlightStatus(String text) {
		this.labelText = text;
	}
	
	public String labelText() { return labelText; }
		
}
