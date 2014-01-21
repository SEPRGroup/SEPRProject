package sepr.atcGame.tests; import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sepr.atcGame.*;
import static java.lang.Math.PI;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class AirportTest {
	
	public Airport instance;
	private List<TransferWaypoint> transferWaypoints = new ArrayList<TransferWaypoint>();
	private static Random random = new Random();
	
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
	}
	
	@Test
	public void testSetTransfers() {
		List<TransferWaypoint> transfers = new ArrayList<TransferWaypoint>();
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
