package sepr.atcGame.tests; import sepr.atcGame.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test; import org.junit.Ignore;

public class TransferWaypointTest {
	
	public TransferWaypoint instance; 	
	public Airspace testAirspace;
	public Airspace testDummyAirspace;
	public double bearing;
	
	@Before
	public void setup(){
		bearing = 1;
		testDummyAirspace = new DummyAirspace("Athens");
		testAirspace = new Heathrow();
		instance = new TransferWaypoint(testAirspace.getAirspaceName(), testAirspace, testDummyAirspace, bearing);
	}
	
	@Test
	public void testTransferWaypoint() {
		double newBearing = 1;
		Airspace newTestDA = new DummyAirspace("Athens");
		Airspace newAirspace =  new Heathrow();
		// Check initiation is the same
		// Should work with the same data
		TransferWaypoint tw = new TransferWaypoint(newAirspace.getAirspaceName(), newAirspace, newTestDA, newBearing);
		assertEquals(tw.toString(), instance.toString());
	}

	@Test
	public void testGetPosition() {
		Position newPosition = new Position(-1, -1, -1);
		// Initially the position is the value above
		assertEquals(newPosition.toString(), instance.getPosition().toString());

	}

	@Test
	public void testSetPosition() {
		Position newPosition = new Position(5, 10, 20);
		
		// Use the same initialised airspace
		instance.setPosition(testAirspace, newPosition);
		assertEquals(newPosition.toString(), instance.getPosition().toString());
	}

	// No implementation of a second airport no need to test
	// Ready to be tested once implementation is done
	@Ignore
	@Test
	public void testGetPosition2() {
		fail("Not yet implemented");
	}
	
	// Same as above - not needed yet
	@Ignore
	@Test
	public void testSetPosition2() {
		fail("Not yet implemented");
	}

		
	@Test
	public void testGetAirspace1() {
		Airspace newAirspace = new Heathrow();
		assertEquals(newAirspace.toString(), instance.getAirspace1().toString());
	}

	// Not necessary but worth testing as
	// the class is initiated with a second airspace
	// value
	@Test
	public void testGetAirspace2() {
		Airspace newAirspace = new Heathrow();
		assertEquals(newAirspace.toString(), instance.getAirspace1().toString());
	}

	@Test
	public void testGetBearingFrom() {
		double bearing = 1.0;
		assertEquals(bearing, instance.getBearingFrom(testAirspace), 0);
		
	}

	@Test
	public void testGetBearingTo() {
		double bearing = 1;
		bearing = bearing<Math.PI ? bearing+Math.PI : bearing-Math.PI;
		assertEquals(bearing, instance.getBearingTo(testAirspace), 0); 
	}

}
