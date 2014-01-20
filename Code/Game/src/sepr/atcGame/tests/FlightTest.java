package sepr.atcGame.tests;
import sepr.atcGame.Flight;
import sepr.atcGame.FlightStatus;
import sepr.atcGame.GameDifficulty;
import sepr.atcGame.Position;
import sepr.atcGame.Game;
import sepr.atcGame.testAircraft;
import static org.junit.Assert.*;
/*
 * MH: CANNOT INSTANTIATE ABSTRACT METHODS - NEED TO LOOK INTO THIS
 * SJ: USE 'testAircraft' THAT INHERITS CLASSES INSTEAD
 */
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test; import org.junit.Ignore;

public class FlightTest {
	public Flight testinstance;
	public Position testposition;
	public Game testgame = new Game(GameDifficulty.EASY);
	
	@Before
	public void setup() {
		//testgame = new Game(GameDifficulty.EASY);
		testposition = new Position(-1,-1,-1);
		testinstance = new testAircraft("Q12", (testgame.randomFlightPlan()));
	}
	
	@Test
	public void testFlight() {
		fail("Not yet implemented");
	}
	
	
	// Not implemented in actual game yet
	@Ignore
	@Test
	public void testGetCondition() {
		fail("Not yet implemented");
	}
	
	// Not implemented in actual game yet
	@Ignore
	@Test
	public void testSetConditions() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetIdentifier() {
		assertEquals("Q12", testinstance.getIdentifier());
	}

	@Test
	public void testGetStatus() {
		assertEquals(FlightStatus.WAITING, testinstance.getStatus());
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
