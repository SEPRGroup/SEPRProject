package sepr.atcGame.tests; import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import sepr.atcGame.*;
import static java.lang.Math.PI;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class AirportTest {
	protected static final int MAX_FLIGHTS = 5;
	public Airport instance;
	private List<TransferWaypoint> transferWaypoints = new ArrayList<TransferWaypoint>();
	private static Random random = new Random();
	protected Flight[] aircraft = new Flight[MAX_FLIGHTS];
	Queue<Waypoint> flightPlan = new LinkedList<Waypoint>();				
	Waypoint[] intermediate;
	Flight f;
	
	int ATHENS=0, DUBAI=1, PARIS=2,SYDNEY=3,ZURICH=4,				
			ALPHA=0, BRAVO=1, CHARLIE=2, DELTA=3, ECHO=4, FOXTROT=5, GOLF=6, HOTEL=7, INDIGO=8, JULIETT=9;			
	int aircraftCount = 0; 
	List<TransferWaypoint> transfers = new ArrayList<TransferWaypoint>();
	@Before
	public void setup() {
		instance = new Heathrow();
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
		for (int i=0; i<airspaces.length; i++){
			Airspace a = airspaces[i];
			transferWaypoints.add(new TransferWaypoint(a.getAirspaceName(), 
					instance, a, bearings[i]));
		}
		intermediate = instance.getWaypoints();
	 f = new testAircraft("test 01", newFlightPlan(transferWaypoints, intermediate, new int[]{DUBAI, ECHO, JULIETT, FOXTROT, BRAVO, SYDNEY}	));
	}
	
	@Test
	public void testSetTransfers() {
		
		int links = 4,	//number to take
			num = transferWaypoints.size(), //number available
			count = 0;	//number taken so far
		for (TransferWaypoint t : transferWaypoints){
			if ( random.nextDouble() < (links-count)/(double)num ){
				transfers.add(t);
				count++;
			}
			num--;
		}
		instance.setTransfers(transfers);
		assertEquals(instance.getTransfers(),transfers);
		//fail("Not yet implemented");
	}
	private Queue<Waypoint> newFlightPlan(List<TransferWaypoint> transfers, Waypoint[] intermediate, int[] indexes){					
		Queue<Waypoint> flightPlan = new LinkedList<Waypoint>();				
		flightPlan.add(transfers.get(indexes[0]));				
		for (int i=1; i<=(indexes.length-2); i++)				
			flightPlan.add(intermediate[indexes[i]]);			
		flightPlan.add(transfers.get(indexes[indexes.length-1]));				
		return flightPlan;				
	}					
	@Test
	public void testNewFlight() {
		int i = 0;
		int before = aircraftCount;		
		
		while ((f != null) && (i < MAX_FLIGHTS)) {
			if (aircraft[i] == null) {
				aircraft[i] = f;
				aircraftCount++;
				assertEquals(f,aircraft[0]);
				f = null;
			} else i++;
		}
		if (MAX_FLIGHTS == i) { // was not added; would exceed MAX_FLIGHTS
			instance.eventLost(f);
		}
		//fail("Not yet implemented");
		
		assertEquals(aircraftCount,before + 1);
	}

	@Test
	public void testReceiveFlight() {
		TransferWaypoint t =  (TransferWaypoint) f.getFlightPlan().peek();
		f.transition(instance,t);

		int i = 0,before = aircraftCount;
		while ((f != null) && (i < MAX_FLIGHTS)) {
			if (aircraft[i] == null) {
				aircraft[i] = f;
				aircraftCount++;
				assertEquals(f,aircraft[0]);
				f = null;
			} else i++;
		}
		if (MAX_FLIGHTS == i) { // was not added; would exceed MAX_FLIGHTS
			instance.eventLost(f);
		}
		assertEquals(aircraftCount,before + 1);
	}

	@Test
	public void testNewObstacle() {
	//	fail("Not yet implemented");
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
		instance.generateWaypoints();
	
		assertEquals(instance.waypoints[0].toString(),new Waypoint("Alpha", new Position(2200,3000,3000)).toString());
		assertEquals(instance.waypoints[1].toString(),new Waypoint("Bravo", new Position(4000,6140,3000)).toString());
		assertEquals(instance.waypoints[2].toString(),new Waypoint("Charlie", new Position(12000,6140,3000)).toString());
		assertEquals(instance.waypoints[3].toString(),new Waypoint("Delta", new Position(7000,10400,3000)).toString());
		assertEquals(instance.waypoints[4].toString(),new Waypoint("Echo", new Position(13000,2000,3000)).toString());
		assertEquals(instance.waypoints[5].toString(),new Waypoint("Foxtrot", new Position(5380,2200,3000)).toString());
		assertEquals(instance.waypoints[6].toString(),new Waypoint("Golf", new Position(1840,8660,3000)).toString());
		assertEquals(instance.waypoints[7].toString(),new Waypoint("Hotel", new Position(14040,8480,3000)).toString());
		assertEquals(instance.waypoints[8].toString(),new Waypoint("Indigo", new Position(10200,8260,3000)).toString());
		assertEquals(instance.waypoints[9].toString(),new Waypoint("Juliett", new Position(10100,2600,3000)).toString());
		
	}
	
	@Ignore
	@Test
	public void testUpdateDouble() {
		fail("Not yet implemented");
	}

	//@Test
	//public void testPaintComponentGraphics() {
	//	fail("Not yet implemented");
	//}
	@After
	public void tearDown(){
		transferWaypoints = null;
		aircraft = null;
		flightPlan = null;
		intermediate = null;
		transfers = null;
		f = null;
	}

}
