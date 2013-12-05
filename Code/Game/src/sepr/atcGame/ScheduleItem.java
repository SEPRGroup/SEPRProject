package sepr.atcGame;

final class ScheduleItem {
	
	public Flight flight;
	public double time;
	
	public ScheduleItem(Flight flight, double time) {
		this.flight = flight;
		this.time = time;
	}

	
	public Flight getFlight() {
		return flight;
	}

	public double getTime() {
		return time;
	}

}
