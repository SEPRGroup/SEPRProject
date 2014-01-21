package sepr.atcGame.tests; import sepr.atcGame.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test; import org.junit.Ignore;

public class GameTest {

	private Game instance;
	
	@Before
	public void Setup(){
		instance = new Game(GameDifficulty.EASY);
	}
	
	@Test
	public void testGame() {
		Game testGame = new Game(GameDifficulty.EASY);
		
		assertEquals(testGame.toString(), instance.toString());
	}

	@Test
	public void testPlay() {
		fail("Not yet implemented");
	}

	@Test
	public void testActionPerformed() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetOutput() {
		fail("Not yet implemented");
	}

}
