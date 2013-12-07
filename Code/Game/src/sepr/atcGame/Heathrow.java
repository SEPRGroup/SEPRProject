package sepr.atcGame;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;


public final class Heathrow extends Airport {
	
	//constructor
	public Heathrow(List<TransferWaypoint> transferWaypoints){
		
		super("Heathrow Airport", 
				new Dimension(8000, 6000),
				transferWaypoints);
		try{
			setBackground(ImageIO.read(new File("src/sepr/atcGame/Images/dummy1.png")));
		} catch (IOException e){};
		
	}
	
	
	//overridden methods
	@Override
	protected void generateWaypoints() {
		// TODO generate and place internal Waypoints, landing zones
	}

}
