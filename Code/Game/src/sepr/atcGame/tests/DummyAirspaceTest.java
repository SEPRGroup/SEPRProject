package sepr.atcGame.tests; import sepr.atcGame.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test; import org.junit.Ignore;

public class DummyAirspaceTest {
	private DummyAirspace testinstance;
	private Aircraft testaircraft;
	private Game testgame;
	private double testtime;
	
	@Before
	public void setup() {
		testgame = new Game(GameDifficulty.EASY, 1);
		testaircraft = new Aircraft("A1", testgame.randomFlightPlan());
		testinstance = new DummyAirspace("testinstance");
	}
	
	@After
	public void teardown() {
		testgame = null;
		testaircraft = null;
		testinstance = null;
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void testNewFlight() {
		testinstance.newFlight(testaircraft);
	}

	@Ignore
	public void testReceiveFlight() {
		//MH: method prints a message to console
		//Not Testing, Annotation is @Ignore
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testNewObstacle() {
		testinstance.newObstacle(testaircraft);
	}

	@Test
	public void testDummyAirspace() {
		testinstance = null;
		testinstance = new DummyAirspace("testinstance");
		assertTrue(testinstance instanceof DummyAirspace);
		
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testUpdate() {
		testtime = 20;
		testinstance.update(testtime);
	}

}
