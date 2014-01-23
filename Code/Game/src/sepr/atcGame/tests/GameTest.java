package sepr.atcGame.tests; 
import java.util.Queue;

import sepr.atcGame.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test; import org.junit.Ignore;

public class GameTest {

	private Game instance;
	
	@Before
	public void Setup(){
		instance = new Game(GameDifficulty.EASY, 1);
	}
	
	public void teardown(){
		instance = null;
	}
	
	@Ignore
	public void testGame() {
		Game testGame = new Game(GameDifficulty.EASY, 1);
		//no longer necessarily true since 
			//random transfers => flightPlans are no longer the same
		assertEquals(testGame.toString(), instance.toString());
	}
	
	
	// As long as there are 5 planes this is fine. Due to randomFlightPlan cannot
	// compare directly 
	@Ignore
	@Test
	public void testPlay() {
		fail("Not yet implemented");
	}
	
	
	// Not sure how to test this
	@Ignore
	@Test
	public void testActionPerformed() {
		fail("Not yet implemented");
	}
	
	// This is tested in setup of game and other methods
	// Doesn't need testing
	@Ignore
	@Test
	public void testGenerateWorld() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testPause(){
		// Time takes to load up
		int interval = 16;
		instance.Play();
		
		assertEquals(interval, instance.Pause());
	}

	@Test
	public void testRandomFlightPlan(){
		
		boolean isSuccess = false;
		Queue<Waypoint> randomFlightPlan = instance.randomFlightPlan();
		
		// Check that there exists the random flight plan
		for(Queue<Waypoint> flightplan: instance.getFlightPlans()){
			
			if (flightplan.toString().equals(randomFlightPlan.toString())){
				isSuccess = true;
				
			}
		}
		
		assertEquals(true, isSuccess);
	}
}
