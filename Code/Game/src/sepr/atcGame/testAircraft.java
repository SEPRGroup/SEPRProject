package sepr.atcGame;
//No Junit Test Created for this - Unsure if this a test itself. mjh. 28/12/13
import java.util.Queue;

public final class testAircraft extends Aircraft {

	//constructor
	public testAircraft(String id, Queue<Waypoint> flightPlan) {
		super(id, flightPlan);
		
		minAlt = 500; cruiseAlt = 3000; maxAlt = 4000;	//position:	m	
		minV = 80; cruiseV = 250; maxV = 275;
		minClimb = -25; maxClimb = 10; maxTurn = 0.15;	//velocity:	m/s , rad/s
		a = 20; aClimb = 5; aTurn = 0.03;	//acceleration:	m/s/s , rad/s/s		
	}

	
	//overridden methods
	/*@Override
	public void draw(Graphics g, Point location, double scale) {
	
	}*/
	

}
