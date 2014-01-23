package sepr.atcGame.tests; import sepr.atcGame.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test; import org.junit.Ignore;

public class SchedulerTest {
	private DummyAirspace testdummyairspace;
	private Scheduler testinstance;
	private Aircraft testaircraft;
	private ScheduleItem testscheduleitem;
	public Position testposition;
	public Game testgame;

	@Before
	public void setup() {
		testdummyairspace = new DummyAirspace("testdummyairspace");
		testgame = new Game(GameDifficulty.EASY, 1);
		testposition = new Position(-1,-1,-1);
		testinstance = new Scheduler(testdummyairspace);
		testaircraft = new Aircraft("A1", testgame.randomFlightPlan());
		testscheduleitem = new ScheduleItem(testaircraft, 1);
	}
	
	@After
	public void teardown() {
		testdummyairspace = null;
		testgame = null;
		testposition = null;
		testinstance = null;
		testaircraft = null;
		testscheduleitem = null;
	}
	@Test
	public void testScheduler() {
		testinstance = new Scheduler(testdummyairspace);
		assertTrue(testinstance.airspace == testdummyairspace);
	}

	@Test
	public void testAdd() {
		
		testinstance.add(testscheduleitem);
		assertTrue(testinstance.schedule.get(0) == testscheduleitem);
	}

	@Ignore //Method not yet created
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Ignore //Overridden Method
	public void testPaintComponent() {
		fail("Not yet implemented");
	}

}
