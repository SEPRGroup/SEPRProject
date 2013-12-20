package sepr.atcGame;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


final class Heathrow extends Airport {
	
	//constructor
	public Heathrow(){
		super("Heathrow Airport", new Dimension(8000, 6000));
		
		try {setBackground(ImageIO.read(new File("src/sepr/atcGame/Images/dummy1.png")));}
		catch (IOException e){};	
	}
	
	
	//overridden methods
	@Override
	protected void generateWaypoints() {
		// TODO generate and place internal Waypoints, landing zones
		for(int i = 0 ; i < MAX_WAYPOINTS; i++){
			waypoints[i] = new Waypoint("waypoint" +i,
					new Position((i)*getWidth()/(MAX_WAYPOINTS)+400, (Math.sin(Math.toRadians(i*360/MAX_WAYPOINTS))+1)*(getHeight()/3)+200, 5000) );
			
		}
	}

}
