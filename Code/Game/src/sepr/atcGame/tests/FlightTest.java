package sepr.atcGame.tests;
import sepr.atcGame.Flight;
import sepr.atcGame.GameDifficulty;
import sepr.atcGame.Position;
import sepr.atcGame.Game;
import static org.junit.Assert.*;
/*
 * MH: CANNOT INSTANTIATE ABSTRACT METHODS - NEED TO LOOK INTO THIS
 * 
 */
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test; import org.junit.Ignore;

public class FlightTest {
	public Flight testinstance;
	public Position testposition;
	public Game testgame;
	
	@BeforeClass
	public void classsetup() {
		testgame = new Game(GameDifficulty.EASY);
		testposition = new Position(-1,-1,-1);
		testinstance = new Flight("Q12", (testgame.randomFlightPlan()));
	}
	
	@Test
	public void testFlight() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCondition() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetConditions() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetIdentifier() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStatus() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetPosition() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPosition() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFlightPlan() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBearing() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetBearing() {
		fail("Not yet implemented");
	}

	@Test
	public void testNextWaypoint() {
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
	public void testPaint() {
		fail("Not yet implemented");
	}

}
