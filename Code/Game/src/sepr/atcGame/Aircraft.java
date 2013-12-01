package sepr.atcGame;

import java.util.Queue;

abstract class Aircraft extends Flight {
	
	//{!} flight data
	
	
	//constructor
	protected Aircraft(String id, Queue<Waypoint> flightPlan) {
		super(id, flightPlan);
		// TODO Auto-generated constructor stub
	}

	
	//overridden methods
	@Override
	public final void update(double time) {
		// TODO realistic movement
	}

	@Override
	public final void takeOff(TransferWaypoint t) {
		// TODO
	}
	
	@Override
	public final void land(TransferWaypoint t) {
		// TODO
	}

	@Override
	public final void turnTo(double bearing) {
		// TODO
	}

	@Override
	public final void toAltitude(double altitude) {
		// TODO
	}

	@Override
	public final void toSpeed(double speed) {
		// TODO
	}

	@Override
	public final void abort() {
		// TODO
	}

	@Override
	public final void crash() {
		// TODO
	}

}
