package sepr.atcGame.tests;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

import sepr.atcGame.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test; 

public class HeathrowTest {

	public Heathrow instance;
	public final int MAX_WAYPOINTS = 10;
	Waypoint[] newWaypoints;
	
	@Before
	public void setup(){
		
		instance = new Heathrow();
		
	}
	
	@Test
	// Generate the waypoints again and test them against the already generated waypoints in heathrow
	
	public void testGenerateWaypoints() {
		
		newWaypoints = new Waypoint[MAX_WAYPOINTS];
		
		newWaypoints[0] = new Waypoint("Alpha", new Position(2200,3000,3000));
	    newWaypoints[1] = new Waypoint("Bravo", new Position(4000,6140,3000));
		newWaypoints[2] = new Waypoint("Charlie", new Position(12000,6140,3000));
		newWaypoints[3] = new Waypoint("Delta", new Position(7000,10400,3000));
		newWaypoints[4] = new Waypoint("Echo", new Position(13000,2000,3000));
		newWaypoints[5] = new Waypoint("Foxtrot", new Position(5380,2200,3000));
		newWaypoints[6] = new Waypoint("Golf", new Position(1840,8660,3000));
		newWaypoints[7] = new Waypoint("Hotel", new Position(14040,8480,3000));
		newWaypoints[8] = new Waypoint("Indigo", new Position(10200,8260,3000));
		newWaypoints[9] = new Waypoint("Juliett", new Position(10100,2600,3000));
		
		assertEquals(newWaypoints[0].toString(), instance.getWaypoints()[0].toString());
		assertEquals(newWaypoints[1].toString(), instance.getWaypoints()[1].toString());
		assertEquals(newWaypoints[2].toString(), instance.getWaypoints()[2].toString());
		assertEquals(newWaypoints[3].toString(), instance.getWaypoints()[3].toString());
		assertEquals(newWaypoints[4].toString(), instance.getWaypoints()[4].toString());
		assertEquals(newWaypoints[5].toString(), instance.getWaypoints()[5].toString());
		assertEquals(newWaypoints[6].toString(), instance.getWaypoints()[6].toString());
		assertEquals(newWaypoints[7].toString(), instance.getWaypoints()[7].toString());
		assertEquals(newWaypoints[8].toString(), instance.getWaypoints()[8].toString());
		assertEquals(newWaypoints[9].toString(), instance.getWaypoints()[9].toString());
		
	}

	// Not sure how to check if the image came in??
	@Test@Ignore
	public void testHeathrow() {
		
		// Catch the IO image and assert if images are the same
		try {
			assertEquals(ImageIO.read(new File(getClass().getResource("/sepr/atcGame/Images/dummy1.png").toURI())), instance.getBackground());
		}catch (IOException e){
			System.out.println("Image cannnot be found");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@After
	public void tearDown(){
		instance = null;
		newWaypoints = null;
	}

}
