package sepr.atcGame.tests; import sepr.atcGame.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test; import org.junit.Ignore;  

public class WaypointTest {
	
	public Waypoint instance;
	public Position newPosition;
	
	@Before
	public void setup() {
		newPosition = new Position(4, 8, 12000);
		instance = new Waypoint("Position 1", newPosition);
	}
	@After
	public void teardown() {
		instance = null;
		newPosition = null;
	}
	
	//Test instance for the waypoints 
	@Test
	public void testWaypoint() {
		// MH: Test for the Constructor needs to go here, not entirely sure how we can test this yet.
		// MH: Maybe by checking that attributes are private?
		
		// Test that the Waypoint has been initiated correctly (same as getPosition test)
		assertEquals(newPosition, instance.getPosition());

	}
	
		
	@Ignore
	public void testDraw() {
		// MH: This method is inherited from Draw class (I think?) so will tested in DrawableTest()
		// MH: Test method annotation set to @Ignore so Junit will not throw failure/even acknowledge.
		fail("Not yet implemented");
	}
	
	
	@Test
	public void testGetName() {
		
		assertEquals("Position 1", instance.getName()); 
	}

	@Test
	public void testGetPosition() {
		
		assertEquals(newPosition, instance.getPosition());
		
	}

	@Test
	public void testSetPosition() {
		instance.setPosition(newPosition);
		assertEquals(newPosition, instance.getPosition());
		
	}

}
