package sepr.atcGame;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

abstract class Airport extends Airspace{

	private Image background;

	
	//constructor
	protected Airport(String airspaceName, Image background, ArrayList<TransferWaypoint> transferWaypoints) {
		super(airspaceName, transferWaypoints);
		generateWaypoints();
		this.background = background;
	}


	//methods
	protected abstract void generateWaypoints();
	
	
	//overridden methods
	@Override
	public final void update(double time) {
		// TODO Auto-generated method stub
	}

	@Override
	public final void newFlight(Flight f) {
		// TODO Auto-generated method stub
	}

	@Override
	public final void receiveFlight(Flight f, TransferWaypoint t) {
		// TODO Auto-generated method stub
	}

	@Override
	public final void newObstacle(Flight flight) {
		// TODO Auto-generated method stub
	}
	
	public final void paintComponent(Graphics g) {
	       super.paintComponent(g);       
	       //draw using [g]: background, waypoints, flights
	}  

}
