package sepr.atcGame;

public final class FlightConditions {
	public double windSpeed;
	public double windDirection;
	//extend as necessary, get/set all
	

	public double getWindSpeed() {
		return windSpeed;
	}
	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}

	public double getWindDirection() {
		return windDirection;
	}
	public void setWindDirection(double windDirection) {
		this.windDirection = windDirection;
	}

	//no constructor; class is likely to become too complex to pass by parameter
}
