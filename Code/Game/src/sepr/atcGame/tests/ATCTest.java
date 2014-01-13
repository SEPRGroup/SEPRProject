package sepr.atcGame.tests; import sepr.atcGame.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test; import org.junit.Ignore; @Ignore

public class ATCTest {
	
	public ATC instance;
	public String newATCName;
	public Airspace newAirspace;

	@Test
	public void testGetName() {
		
		assertEquals(newATCName, instance.getName(), 0);
	}

	@Test
	public void testGetAirspace() {
		assertEquals(newAirspace, instance.getAirspace());
	}

	// I'm thinking this could be instead of the setup() function
	@Test
	public void testATC() {
		newATCName = "ATC";
		newAirspace = new Heathrow();
		instance = new ATC(newATCName, newAirspace);
		
		assertEquals(newATCName, instance.getName(), 0);
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
