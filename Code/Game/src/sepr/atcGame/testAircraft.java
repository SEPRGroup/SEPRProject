package sepr.atcGame;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Queue;

import javax.swing.JFrame;

final class testAircraft extends Aircraft {

	//constructor
	public testAircraft(String id, Queue<Waypoint> flightPlan) {
		super(id, flightPlan);	
	
	}

	
	//overridden methods
	@Override
	public void draw(Graphics g, Point location, double scale) {
		// TODO
		

	}
	

}
