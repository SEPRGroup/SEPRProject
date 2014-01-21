package sepr.atcGame.tests; import sepr.atcGame.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test; import org.junit.Ignore;

public class FrameRateMonitorTest {
	private static final int FPS_MAX = 60;
	private static final int FPS_DELAY = 1000/FPS_MAX;
	/*private javax.swing.Timer frameTimer = new javax.swing.Timer(FPS_DELAY, this);*/
	private FrameRateMonitor testinstance, testconstruct;
	private int testframenum;
	private long[] testzeroarray;
	private long testnano;
	
	@Before
	public void setup() {
		testinstance = new FrameRateMonitor(FPS_MAX *5);
	}
	
	@After
	public void teardown(){
		testinstance = null;
	}
	
	@Test
	public void testFrameRateMonitor() {
		testconstruct = new FrameRateMonitor(FPS_MAX *5);
		assertTrue(testconstruct.frameTimes instanceof long[]);
	}

	@Test
	public void testGetFrameRate() {
		/* MH: FAULTY TEST
		 * Another faulty test throwing NullPointerException.
		 * Not sure how to fix.
		 */ 
		testconstruct.totalTime = 10000;
		testconstruct.getFrameRate();
		assertTrue((testconstruct.frameTimes.length*1000000000.0 /testconstruct.totalTime)==testconstruct.getFrameRate());
	}

	@Test
	public void testGetFrames() {
		testframenum = 5;
		testinstance.frames = testframenum;
		assertTrue(testinstance.getFrames() == testframenum);
	}

	@Test
	public void testNewFrame() {
		/* THIS TEST DETECTS A FAILURE 
		 * MH: Can't tell what if anything is wrong with test, see console output.
		 */
		
		testinstance.frames = 0;
		testinstance.pos = 0;
		testinstance.totalTime= 0;
		testnano = 10;
		testinstance.newFrame(testnano);
		System.out.println("<testNewFrame() | ((testinstance.frameTimes[testinstance.pos]) = "+testinstance.frameTimes[testinstance.pos]+") should equal (testnano = "+testnano+")>");
		System.out.println("<testNewFrame() | ((testinstance.totalTime - testinstance.frameTimes[testinstance.pos] + testnano) = "+(testinstance.totalTime - testinstance.frameTimes[testinstance.pos] + testnano)+") should equal (testinstance.totalTime = "+testinstance.totalTime+")>");
		assertTrue((testinstance.frameTimes[testinstance.pos] == testnano) && ((testinstance.totalTime - testinstance.frameTimes[testinstance.pos] + testnano) == testinstance.totalTime));
	}

	@Test
	public void testReset() {
		testinstance.frames = 1;
		testinstance.pos = 2;
		testinstance.totalTime= 3;
		testinstance.reset();
		assertTrue((testinstance.frames == 0) && (testinstance.pos == 0) && (testinstance.totalTime == 0));
		/* MH: Null Pointer Exception creating fault.
		   Essentially trying to create testzeroarray same length as frameTimes all with zeros, and testing for equality
		for (int i=0; i < testinstance.frameTimes.length; i++){
			testzeroarray[i] = 0;
		}
		assertArrayEquals(testzeroarray, testinstance.frameTimes); */
	}

}
