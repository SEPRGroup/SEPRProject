package sepr.atcGame;

import sepr.atcGame.events.ATCListener;

public class testOutput implements ATCListener {

	@Override
	public void eventViolation(Flight f1, Flight f2) {
		// TODO Auto-generated method stub
		System.out.println(f1.getIdentifier() + " is too close to " + f2.getIdentifier());
	}

}
