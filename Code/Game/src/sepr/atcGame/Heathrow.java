package sepr.atcGame;

import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public final class Heathrow extends Airport {
	
	private static String 
		backgroundPath = "src/sepr/atcGame/Images/dummy1.jpg";
	private static Image background;
	
	//constructor
	public Heathrow(){
		super("Heathrow Airport", new Dimension(16000, 12000));
		
		if (background == null){
			try {
				background = ImageIO.read(new File(backgroundPath));
			}
			catch (IOException e){
				System.out.println("Heathrow:\tImage loading failed");
			};
		}
		setBackground(background);
	}
	
	
	//overridden methods
	@Override
	public void generateWaypoints() {
		waypoints[0] = new Waypoint("Alpha", new Position(2200,3000,3000));
		waypoints[1] = new Waypoint("Bravo", new Position(4000,6140,3000));
		waypoints[2] = new Waypoint("Charlie", new Position(12000,6140,3000));
		waypoints[3] = new Waypoint("Delta", new Position(7000,10400,3000));
		waypoints[4] = new Waypoint("Echo", new Position(13000,2000,3000));
		waypoints[5] = new Waypoint("Foxtrot", new Position(5380,2200,3000));
		waypoints[6] = new Waypoint("Golf", new Position(1840,8660,3000));
		waypoints[7] = new Waypoint("Hotel", new Position(14040,8480,3000));
		waypoints[8] = new Waypoint("Indigo", new Position(10200,8260,3000));
		waypoints[9] = new Waypoint("Juliett", new Position(10100,2600,3000));

		//transfers.add(new TransferWaypoint("Runway1", null, this, Math.PI*2));
		//will not show up if added here; overwritten by setTransfers
	}

}
