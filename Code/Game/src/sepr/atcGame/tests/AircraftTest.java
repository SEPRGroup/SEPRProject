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
	public Heathrow testheathrow;
	public TransferWaypoint testtransferwaypoint;
	public DummyAirspace testdummyairspace1, testdummyairspace2;
	public double minAlt, maxAlt, testaltitude, testspeed, minV, maxV, tAlt, cruiseAlt, time;
	
	@Before
	public void setup() {
		testgame = new Game(GameDifficulty.EASY);
		testflightplan = testgame.randomFlightPlan();
		System.out.println(testflightplan);
		testinstance = new Aircraft("Q12", testflightplan);
		testheathrow = new Heathrow();
		testdummyairspace1 = new DummyAirspace("Airspace1");
		testdummyairspace2 = new DummyAirspace("Airspace2");
		testtransferwaypoint = new TransferWaypoint("testtransferwaypoint", testdummyairspace1,testdummyairspace2, 3);
		System.out.println(testinstance);
	}
	
	@After
	public void teardown() {
		testgame = null;
		testflightplan = null;
		testinstance = null;
		testheathrow = null;
		testdummyairspace1 = null;
		testdummyairspace2 = null;
		testtransferwaypoint = null;
		
	}
	
	@Test
	public void testInit(){
		//MH: Assumes Aircraft.getStatus() is "WAITING"
		//MH: Doesn't consider error condition. (else)
		testspeed = 200;
		testaltitude = 3000;
		testinstance.init(testspeed, testaltitude);
		assertTrue((testinstance.v == testspeed) && (testinstance.getPosition().altitude == testaltitude));
	}
	
	@Test
	public void testTransition(){
		//MH: FAULTY TEST
		//MH: Causes Errors, can't find way to fix
		//MH: Doesn't consider error condition. (else)
		testinstance.transition(testheathrow, testtransferwaypoint);
		assertTrue(testinstance.getStatus().toString() == "CRUISING");
	}

	@Test
	public void testTakeOff() {
		//MH: FAULTY TEST
		//MH: Causes errors, can't find way to fix
		testinstance.takeOff(testheathrow, testtransferwaypoint);
		System.out.println(testinstance.getStatus().toString());
		assertEquals("TAKEOFF", (testinstance.getStatus()).toString());
	}

	@Test
	public void testLand() {
		testinstance.land(testtransferwaypoint);
		assertEquals("LANDING", (testinstance.getStatus().toString()));
	}

	@Test
	public void testTurnTo() {
		testinstance.turnTo(3);
		assertEquals("WAITING", testinstance.getStatus().toString());
	}

	@Test
	public void testToAltitude() {
		testinstance.minAlt = -1;
		testinstance.maxAlt = 4000;
		testaltitude = 3000;
		testinstance.toAltitude(testaltitude);
		System.out.println("testToAltitude()| "+testinstance);
		assertTrue(testinstance.tAlt == testaltitude);
	}

	@Test
	public void testToSpeed() {
		testinstance.minV = 0;
		testinstance.maxV = 4000;
		testspeed = 2000;
		testinstance.toSpeed(testspeed);
		System.out.println("testToSpeed()| "+testinstance);
		assertTrue(testinstance.tV == testspeed);
	}

	@Test
	public void testAbort() {
		testinstance.tAlt = 100;
		testinstance.cruiseAlt = 200;
		testinstance.abort();
		assertTrue(testinstance.tAlt == testinstance.cruiseAlt);
	}

	@Test
	public void testCrash() {
		testinstance.crash();
		assertEquals("CRASHING", testinstance.getStatus().toString());
	}

	@Test
	public void testAircraft() {
		testconstruct = new Aircraft("Q12", testflightplan);
		assertEquals(testinstance.toString(),testconstruct.toString());
		
	}

	@Ignore
	public void testUpdate() {
		//MH: Really complicated, will come back to it.
		//MH: Annotation set to @Ignore
	}

	@Ignore
	public void testDraw() {
		//MH: Not tested, JUnit doesn't handle GUI stuff.
		//MH: Annotation set to @Ignore
		fail("Not yet implemented");
	}
	
	@Ignore
	public void testHighlight() {
		//MH: Not tested, JUnit doesn't handle GUI stuff.
		//MH: Annotation set to @Ignore
		fail("Not yet implemented");
	}

}
