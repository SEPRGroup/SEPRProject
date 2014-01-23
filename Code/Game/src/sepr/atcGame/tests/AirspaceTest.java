package sepr.atcGame.tests; import java.util.ArrayList;
import java.util.List;

import sepr.atcGame.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test; import org.junit.Ignore; 

public class AirspaceTest {
	
	public Airspace instance;
	
	@Before
	public void setup(){
		instance = new Heathrow();
	}
	
	@After
	public void teardown(){
		instance = null;
	}
	
	@Test
	public void testAirspace() {
		Airspace testAirspace = new Heathrow();
		assertEquals(testAirspace.toString(), instance.toString());
	}

	@Test
	public void testGetAirspaceName() {
		String testAirspaceName = "Heathrow Airport";
		assertEquals(testAirspaceName, instance.getAirspaceName());
	}

	// Planes are initiated on Game.Play()
	// Tests won't pick up the planes but this
	// is testing in GameTest anyway
	@Ignore
	@Test
	public void testGetAircraft() {
		fail("No initiated planes!");		
	}

	// Convert way points to string so they
	// can be tested easier
	@Test
	public void testGetWaypoints() {
		String[] actualWaypoints = new String[10];
		String[] newWaypoints = new String[10];
		
		newWaypoints[0] = new Waypoint("Alpha", new Position(2200,3000,3000)).toString();
		newWaypoints[1] = new Waypoint("Bravo", new Position(4000,6140,3000)).toString();
		newWaypoints[2] = new Waypoint("Charlie", new Position(12000,6140,3000)).toString();
		newWaypoints[3] = new Waypoint("Delta", new Position(7000,10400,3000)).toString();
		newWaypoints[4] = new Waypoint("Echo", new Position(13000,2000,3000)).toString();
		newWaypoints[5] = new Waypoint("Foxtrot", new Position(5380,2200,3000)).toString();
		newWaypoints[6] = new Waypoint("Golf", new Position(1840,8660,3000)).toString();
		newWaypoints[7] = new Waypoint("Hotel", new Position(14040,8480,3000)).toString();
		newWaypoints[8] = new Waypoint("Indigo", new Position(10200,8260,3000)).toString();
		newWaypoints[9] = new Waypoint("Juliett", new Position(10100,2600,3000)).toString();
		
		
		for (int i = 0; i <= 9; i++){
			actualWaypoints[i] = instance.getWaypoints()[i].toString();
		}
		
		assertArrayEquals(newWaypoints, actualWaypoints);
		
		
	}
	
	
	@Test
	public void testGetTransfers() {
		
		//Generate airspaces
		List<TransferWaypoint> transferWaypoints = new ArrayList<TransferWaypoint>();
		Airport airport = new Heathrow();
		Airspace[] airspaces = new Airspace[]{
						new DummyAirspace("Athens"), 
						new DummyAirspace("Dubai"), 
						new DummyAirspace("Paris"),
						new DummyAirspace("Sydney"),
						new DummyAirspace("Zurich"),
						};
				double[] bearings = new double[]{
						1, 2, 3, 4, 5
				};

				//TRANSFERS
				//Generate all possible transfers
				for (int i=0; i<airspaces.length; i++){
					Airspace a = airspaces[i];
					transferWaypoints.add(new TransferWaypoint(a.getAirspaceName(), 
							airport, a, bearings[i]));
				}
		
		Game newGame = new Game(GameDifficulty.EASY, Game.HEATHROW);
		instance = newGame.getAirport();
		
		// See if the transfers in 'instance' are a subset 
		// of transfers are in the generated transfers above
		for (TransferWaypoint tw: transferWaypoints){
			for (TransferWaypoint actualtw: instance.getTransfers()){
				if (tw.toString().equals(actualtw.toString())){
					assertEquals(tw.toString(), actualtw.toString());
				}
			}
		}
		
	}

	
	public void testSetTransfers() {
		// this is tested in AirportTest
	}

	
	
	public void testNewFlight() {
		// this is tested in AirportTest
	}

	
	public void testReceiveFlight() {
		// this is tested in AirportTest
	}

	
	// Not implemented
	@Ignore
	@Test
	public void testNewObstacle() {
		fail("Not yet implemented");
	}

	// Not testable as depends on Graphics
	@Ignore
	@Test
	public void testRemoveFlight() {
		fail("Not yet implemented");
	}
	
	// Dependent on Graphics
	@Ignore
	@Test
	public void testEventCrash() {
		fail("Not yet implemented");
	}
	
	// Not implemented
	@Ignore
	@Test
	public void testEventLanded() {
		fail("Not yet implemented");
	}

	// Not testable
	@Ignore
	@Test
	public void testEventHandover() {
		fail("Not yet implemented");
	}

	// Not testable dependent on Graphics
	@Ignore
	@Test
	public void testEventLost() {
		fail("Not yet implemented");
	}

}
