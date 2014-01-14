package sepr.atcGame.tests;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import sepr.atcGame.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test; 

public class HeathrowTest {

	public Heathrow instance;
	public final int MAX_WAYPOINTS = 5;
	Waypoint[] newWaypoints;
	
	@Before
	public void setup(){
		
		instance = new Heathrow();
		
	}
	
	@Test
	// Generate the waypoints again and test them against the already generated waypoints in heathrow
	// Use assertArrayEquals
	public void testGenerateWaypoints() {
		
		newWaypoints = new Waypoint[MAX_WAYPOINTS];
		
		
		for(int i = 0 ; i < MAX_WAYPOINTS; i++){
			newWaypoints[i] = new Waypoint("waypoint" +i,
					new Position((i)*instance.getWidth()/(MAX_WAYPOINTS)+400, (Math.sin(Math.toRadians(i*360/MAX_WAYPOINTS))+1)*(instance.getHeight()/3)+200, 5000) );
			System.out.println("Dummy Waypoint: " + newWaypoints[i]);
			
		}
		for (Waypoint w: instance.getWaypoints()){
			System.out.println("Actual Waypoint:" + w);
		}
		
		// Not sure why this isn't working as they are exactly the same
		assertArrayEquals(newWaypoints, instance.getWaypoints());
	}

	// Not sure how to check if the image came in??
	@Test@Ignore
	public void testHeathrow() {
		
		// Catch the IO image and assert if images are the same
		try {
			assertEquals(ImageIO.read(new File("src/sepr/atcGame/Images/dummy1.png")), instance.getBackground());
		}catch (IOException e){
			System.out.println("Image cannnot be found");
		}

	}
	
	@After
	public void tearDown(){
		instance = null;
		newWaypoints = null;
	}

}
