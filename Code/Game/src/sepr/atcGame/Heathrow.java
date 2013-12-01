package sepr.atcGame;

import java.util.ArrayList;

final class Heathrow extends Airport {
	
	//constructor
	public Heathrow(ArrayList<TransferWaypoint> transferWaypoints){
		super("Heathrow Airport", 
				new Image(), //load background image
				transferWaypoints);
		
	}
	
	
	//overridden methods
	@Override
	protected void generateWaypoints() {
		// TODO generate and place internal Waypoints, landing zones
	}

}
