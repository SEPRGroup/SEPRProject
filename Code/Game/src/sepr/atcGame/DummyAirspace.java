package sepr.atcGame;

public final class DummyAirspace extends Airspace {

	public DummyAirspace(String airspaceName) {
		super(airspaceName);
	}
	
	
	@Override
	public final void update(double time) {
		throw new UnsupportedOperationException("DummyAirspace does not support update()");
	}

	@Override
	public final void newFlight(Flight f) {
		throw new UnsupportedOperationException("DummyAirspace does not support newFlight()");
	}

	@Override
	public final void receiveFlight(Flight f, TransferWaypoint t) {
		System.out.println(f.getIdentifier() +"\thas left the game.");
		//nothing: drop reference to f
	}

	@Override
	public final void newObstacle(Flight flight) {
		throw new UnsupportedOperationException("DummyAirspace does not support newObstacle()");
	}

}
