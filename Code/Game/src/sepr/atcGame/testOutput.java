package sepr.atcGame;

import sepr.atcGame.events.ATCListener;

public class testOutput implements ATCListener {
	
	
	@Override
	public String eventViolation(Flight f1, Flight f2) {
		// TODO Auto-generated method stub		
		String s = f1.getIdentifier() + " is too close to " + f2.getIdentifier();
		System.out.println(s);
		return s; 	
	}
		
}
