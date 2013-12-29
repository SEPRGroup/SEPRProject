package sepr.atcGame;


public final class Position { //Had to change from final class type to public to test
	//variables
	public double x, y, altitude;
	
	//constructor
	public Position(double x, double y, double altitude){
		this.x = x;
		this.y = y;
		this.altitude = altitude;
	}
	
	public Position(Position p){
		this.x = p.x;
		this.y = p.y;
		this.altitude = p.altitude;
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
