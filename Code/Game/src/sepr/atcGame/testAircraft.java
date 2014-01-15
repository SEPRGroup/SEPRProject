package sepr.atcGame;
//No Junit Test Created for this - Unsure if this a test itself. mjh. 28/12/13
import java.util.Queue;

final class testAircraft extends Aircraft {

	//constructor
	public testAircraft(String id, Queue<Waypoint> flightPlan) {
		super(id, flightPlan);
		
		minAlt = 100; cruiseAlt = 200; maxAlt = 300;	//position:	m	
		minV = 150; cruiseV = 250; maxV = 350;
		minClimb = -15; maxClimb = 10; maxTurn = 0.15;	//velocity:	m/s , rad/s
		a = 20; aClimb = 5; aTurn = 0.03;	//acceleration:	m/s/s , rad/s/s
		
	}

	
	//overridden methods
	/*@Override
	public void draw(Graphics g, Point location, double scale) {
	
	}*/
	

}
