package sepr.atcGame.tests;

import sepr.atcGame.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test; 

public class testOutputTest {
	private Flight f1;
	private Flight f2;
	private testOutput instance;
	private Game testGame;
	
	@Before
	public void setup(){
		testGame = new Game(GameDifficulty.EASY, 1);
						
		f1 = new testAircraft("DummyName", testGame.randomFlightPlan());
		f2 = new testAircraft("DummyName2", testGame.randomFlightPlan());
		instance = new testOutput();
	}
	
	@After
	public void teardown(){
		testGame = null;
		
		f1 = null;
		f2 = null;
		instance = null;
	}
	
	@Test
	public void testEventViolation(){
		
		assertEquals((instance.eventViolation(f1, f2)),(instance.eventViolation(f1, f2))) ;
		
		
		
	}
}
