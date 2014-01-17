package sepr.atcGame;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public final class Heathrow extends Airport {
	
	//constructor
	public Heathrow(){
		super("Heathrow Airport", new Dimension(8000, 6000));
		
		try {setBackground(ImageIO.read(new File("src/sepr/atcGame/Images/dummy1.jpg")));}
		catch (IOException e){};	
	}
	
	
	//overridden methods
	@Override
	protected void generateWaypoints() {
		// TODO generate and place internal Waypoints, landing zones
		waypoints[0] = new Waypoint("Alpha", new Position(1100,1500,500));
		waypoints[1] = new Waypoint("Bravo", new Position(2000,3070,200));
		waypoints[2] = new Waypoint("Charlie", new Position(6000,3070,200));
		waypoints[3] = new Waypoint("Delta", new Position(3500,5200,1000));
		waypoints[4] = new Waypoint("Echo", new Position(6500,1000,600));
		waypoints[5] = new Waypoint("Foxtrot", new Position(2690,1100,700));
		waypoints[6] = new Waypoint("Golf", new Position(920,4330,900));
		waypoints[7] = new Waypoint("Hotel", new Position(7020,4240,600));
		waypoints[8] = new Waypoint("Indigo", new Position(5100,4130,400));
		waypoints[9] = new Waypoint("Juliett", new Position(5050,1300,700));
		
		//transfers.add(new TransferWaypoint("Runway1", null, this, Math.PI*2));
	}

}
