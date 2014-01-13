package sepr.atcGame.tests; import sepr.atcGame.*;

import static org.junit.Assert.*;

import org.junit.Test; import org.junit.Ignore; @Ignore

public class WaypointTest {
	
	public Waypoint instance;
	public Position newPosition;
	
	
	public void setup() {
		newPosition = new Position(4, 8, 12000);
		instance = new Waypoint("Position 1", newPosition);
	}
	
	public void teardown() {
		instance = null;
		newPosition = null;
	}
	
	//Test instance for the waypoints 
	@Test
	public void testWaypoint() {
		// Not sure what I do here??
		fail("Not yet implemented");
	}
	
		
	@Test
	public void testDraw() {
		// Or here		
		fail("Not yet implemented");
	}
	
	
	@Test
	public void testGetName() {
		
		assertEquals("Position 1", instance.getName(), 0);
		
		
		fail("Not yet implemented");
	}

	@Test
	public void testGetPosition() {
		// Put both to string so can equal each other
		assertEquals(new Position(4, 8, 12000).toString(), instance.getPosition().toString(), 0);
		
	}

	@Test
	public void testSetPosition() {
		newPosition = new Position(10, 12, 1000);
		instance.setPosition(newPosition);
		assertEquals(newPosition.toString(), instance.getPosition().toString(), 0);
		
	}

}
