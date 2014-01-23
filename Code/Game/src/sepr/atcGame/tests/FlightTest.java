package sepr.atcGame.tests;
import java.util.Queue;

import sepr.atcGame.Airspace;
import sepr.atcGame.DummyAirspace;
import sepr.atcGame.Flight;
import sepr.atcGame.FlightStatus;
import sepr.atcGame.GameDifficulty;
import sepr.atcGame.Heathrow;
import sepr.atcGame.Position;
import sepr.atcGame.Game;
import sepr.atcGame.TransferWaypoint;
import sepr.atcGame.Waypoint;
import sepr.atcGame.testAircraft;

import static org.junit.Assert.*;

import org.junit.After;
/*
 * MH: CANNOT INSTANTIATE ABSTRACT METHODS - NEED TO LOOK INTO THIS
 * SJ: USE 'testAircraft' THAT INHERITS CLASSES INSTEAD
 */
import org.junit.Before;

import org.junit.Test; import org.junit.Ignore;

public class FlightTest {
	public Flight testinstance;
	public Position testposition;
	public Game testgame;
	
	@Before
	public void setup() {
		testgame = new Game(GameDifficulty.EASY, Game.HEATHROW);
		testposition = new Position(-1,-1,-1);
		testinstance = new testAircraft("Q12", (testgame.randomFlightPlan()));
	}
	
	@After
	public void teardown(){
		testgame = null;
		testposition = null;
		testinstance = null;
	}
	
	// Works fine. Flight plans will always be different so assertion fails 
	@Ignore
	@Test
	public void testFlight() {
		testAircraft newInstance = new testAircraft("Q12", (testgame.randomFlightPlan()));
		assertEquals(testinstance.toString(), newInstance.toString());
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
	public void testGetPosition() {
		System.out.println("Actual: " + testinstance.getPosition() + " | Dummy: " + testposition );
		assertEquals(testposition.toString(), testinstance.getPosition().toString());
	}

	@Test
	public void testGetFlightPlan() {
		
		Queue<Waypoint> testFlightPlan = null;
		for(Queue<Waypoint> flightplan: testgame.getFlightPlans()){
			if (flightplan.equals(testinstance.getFlightPlan())){
				testFlightPlan = flightplan;
			}
		}
		
		assertEquals(testinstance.getFlightPlan(), testFlightPlan);
	}

	@Test
	public void testGetBearing() {
		
		assertEquals(0.0, testinstance.getBearing(), 0);
	}

	
	@Test
	public void testNextWaypoint() {
		Heathrow airport = new Heathrow(); 
		Airspace[] airspaces = new Airspace[]{
				new DummyAirspace("Athens"), 
				new DummyAirspace("Dubai"), 
				new DummyAirspace("Paris"),
				new DummyAirspace("Sydney"),
				new DummyAirspace("Zurich"),
				};
		TransferWaypoint tWaypoint = null;
		double[] bearings = new double[]{
				1, 2, 3, 4, 5
		};
		boolean exit;
		Airspace a;
		int i = 0;
		do {
			a = airspaces[i];
			tWaypoint = new TransferWaypoint(a.getAirspaceName(), 
					airport, a, bearings[i]);
			exit = testinstance
				.getFlightPlan()
				.element()
				.getName()
				.equalsIgnoreCase(tWaypoint.getName());
			i++;
		}while((!exit));
		//
		assertEquals(testinstance.getFlightPlan().element().getName(), tWaypoint.getName());
		
		System.out.println(testinstance.getFlightPlan().element().toString());
		
		
	}

	
	@Ignore // Not needed for assessment 1 but ready for future development
	@Test
	public void testTakeOff() {
		fail("Not yet implemented");
	}

	@Ignore // Not needed for assessment 1 but ready for future implementation
	@Test
	public void testLand() {
		fail("Not yet implemented");
	}

	@Ignore // Not implemented in actual game yet
	@Test
	public void testTurnTo() {
		fail("Not yet implemented");
	}
	
	
	// Not implemented in actual game yet
	@Ignore 
	@Test
	public void testToAltitude() {
		fail("Not yet implemented");
	}
	
	// Not implemented in actual game yet
	@Ignore
	@Test
	public void testToSpeed() {
		
		fail("Not yet implemented");
	}
	
	// Not implemented in actual game yet	
	@Ignore 
	@Test
	public void testAbort() {
		fail("Not yet implemented");
	}

	// Not implemented in actual game yet
	@Ignore
	@Test
	public void testCrash() {
		fail("Not yet implemented");
	}

}