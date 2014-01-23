package sepr.atcGame.tests; import sepr.atcGame.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test; import org.junit.Ignore;

public class ScheduleItemTest {
	private ScheduleItem testinstance;
	private Aircraft testaircraft;
	public Position testposition;
	private Game testgame;
	private double testtime;
	
	@Before
	public void setup() {
		testtime = 3;
		testgame = new Game(GameDifficulty.EASY, 1);
		testposition = new Position(-1,-1,-1);
		testaircraft = new Aircraft("A1", testgame.randomFlightPlan());		
		testinstance = new ScheduleItem(testaircraft, testtime);
	}
	
	@After
	public void teardown(){
		testtime = 0;
		testgame = null;
		testposition = null;
		testaircraft = null;		
		testinstance = null;
	}
	
	@Test
	public void testScheduleItem() {
		testinstance = null;
		testinstance = new ScheduleItem(testaircraft, testtime);
		assertTrue(testinstance.getFlight()==testaircraft && testinstance.getTime()==testtime);
	}

	@Test
	public void testGetFlight() {
		assertTrue(testinstance.getFlight()==testaircraft);
	}

	@Test
	public void testGetTime() {
		assertTrue(testinstance.getTime()==testtime);
	}

}
