package sepr.atcGame;


final class Position {
	//variables
	private double x, y, altitude;
	
	//constructor
	public Position(double x, double y, double altitude){
		this.x = x;
		this.y = y;
		this.altitude = altitude;
	}
	
	//methods
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}	
	
	public double getAltitude() {
		return altitude;	}
	
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
}
