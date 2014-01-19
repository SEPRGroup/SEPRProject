package sepr.atcGame.tests; import java.util.LinkedList;
import java.util.Queue;

import sepr.atcGame.*;
import static java.lang.Math.PI;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test; import org.junit.Ignore;

public class testOutputTest {
	private Queue<Waypoint> flightPlan1;
	
	private Airspace a1;
	private Airspace a2;
	private Waypoint[] intermediate;
	private Flight f1;
	private Flight f2;
	private testOutput instance;
	
	@Before
	public void setup(){
		
		a1 = new DummyAirspace("Dubai");
		a2 = new DummyAirspace("Athens");
		
		Heathrow airport = new Heathrow();
		intermediate = airport.getWaypoints();
		//new flight plan - Athens, Juliett, Charlie
		flightPlan1 = new LinkedList<Waypoint>();
		flightPlan1.add(new TransferWaypoint("AirspaceTest", a1, a2, (2*PI))); flightPlan1.add(intermediate[9]); flightPlan1.add(intermediate[2]);
		
		
		
		f1 = new testAircraft("DummyName", flightPlan1);
		f2 = new testAircraft("DummyName2", flightPlan1);
		instance = new testOutput();
	}
	
	@Test
	public void testEventViolation(){
		a1 = new DummyAirspace("Dubai");
		a2 = new DummyAirspace("Athens");
		
		Heathrow airport = new Heathrow();
		intermediate = airport.getWaypoints();
		//new flight plan - Athens, Juliett, Charlie
		flightPlan1 = new LinkedList<Waypoint>();
		flightPlan1.add(new TransferWaypoint("AirspaceTest", a1, a2, (2*PI))); flightPlan1.add(intermediate[9]); flightPlan1.add(intermediate[2]);
		
		assertEquals((instance.eventViolation(f1, f2)),(instance.eventViolation(f1, f2))) ;
		
		
		
	}
}
