package sepr.atcGame.tests; import sepr.atcGame.*;
import static java.lang.Math.PI;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class AirportTest {
	
	public Airport instance;
	
	@Before
	public void setup() {
		instance = new Heathrow();
	}
	
	@Test
	public void testSetTransfers() {
		fail("Not yet implemented");
	}

	@Test
	public void testNewFlight() {
		
		fail("Not yet implemented");
	}

	@Test
	public void testReceiveFlight() {
		fail("Not yet implemented");
	}

	@Test
	public void testNewObstacle() {
		fail("Not yet implemented");
	}

	@Test
	public void testAirport() {
		Airport testInstance = new Heathrow();
		assertEquals(testInstance.toString(), instance.toString());
	}

	//@Test
	//public void testSetBackgroundImage() {
	//	fail("Not yet implemented");
	//

	@Test
	public void testGenerateWaypoints() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateDouble() {
		fail("Not yet implemented");
	}

	//@Test
	//public void testPaintComponentGraphics() {
	//	fail("Not yet implemented");
	//}

}
