package sepr.atcGame.tests; import sepr.atcGame.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test; import org.junit.Ignore; 

public class ATCTest {
	
	public ATC instance;
	public String newATCName;
	public Airspace newAirspace;
	
	@Before
	public void setup() {
		newATCName = "ATC";
		newAirspace = new Heathrow();
		instance = new ATC(newATCName, newAirspace);
	}
	
	// I'm thinking this could be instead of the setup() function
	@Test
	public void testATC() {
		
		
		
		assertEquals(newATCName, instance.getName());
		assertEquals(newAirspace, instance.getAirspace());
	}
	
	@Test
	public void testGetName() {
		
		assertEquals("ATC", instance.getName());
	}

	@Test
	public void testGetAirspace() {
		assertEquals(newAirspace, instance.getAirspace());
	}

	

	@After
	public void tearDown(){
		
		newAirspace = null;
		instance = null;
	}
	
	
	// ProccessCommand not fully implemented so will need changing once updated
	@Test
	public void testProcessCommand() {
		
		assertEquals(false, instance.processCommand("DummyCommand"));
	}

	@Test@Ignore
	public void testUpdateDouble() {
		fail("Not yet implemented");
	}

	@Test@Ignore
	public void testPaintComponentGraphics() {
		fail("Not yet implemented");
	}

}
