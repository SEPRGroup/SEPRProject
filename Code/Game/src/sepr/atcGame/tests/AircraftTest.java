package sepr.atcGame.tests; import java.util.List;
import java.util.Queue;

import sepr.atcGame.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class AircraftTest {
	public Aircraft testinstance, testconstruct;
	public Game testgame;
	public Queue<Waypoint> testflightplan;
	
	@Before
	public void setup() {
		testgame = new Game(GameDifficulty.EASY);
		testflightplan = testgame.randomFlightPlan();
		System.out.println(testflightplan);
		testinstance = new Aircraft("Q12", testflightplan);
		System.out.println(testinstance);
	}
	
	@After
	public void teardown() {
		testgame = null;
		testflightplan = null;
		testinstance = null;
		
	}

	@Test
	public void testSetBearing() {
		fail("Not yet implemented");
	}

	@Test
	public void testTakeOff() {
		fail("Not yet implemented");
	}

	@Test
	public void testLand() {
		fail("Not yet implemented");
	}

	@Test
	public void testTurnTo() {
		fail("Not yet implemented");
	}

	@Test
	public void testToAltitude() {
		fail("Not yet implemented");
	}

	@Test
	public void testToSpeed() {
		fail("Not yet implemented");
	}

	@Test
	public void testAbort() {
		fail("Not yet implemented");
	}

	@Test
	public void testCrash() {
		fail("Not yet implemented");
	}

	@Test
	public void testAircraft() {
		testconstruct = new Aircraft("Q12", testflightplan);
		assertEquals(testinstance.toString(),testconstruct.toString());
		
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testDraw() {
		fail("Not yet implemented");
	}

}
