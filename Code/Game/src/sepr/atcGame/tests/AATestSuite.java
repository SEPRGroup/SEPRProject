package sepr.atcGame.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

////////////// THIS TEST SUITE PROVIDES ABILITY TO TEST ALL CLASSES AT ONCE 
/* As you create new test classes in sepr.atcGame.tests, you MUST add the name to this list.
 * 
 * ////// TO RUN THIS TEST SUITE:
 * Right-click on AATestSuite, choose 'Run as' and then click 1 Junit Test.
 * You should see outputs in both the console and the JUnit 4 Test Window, which should open automatically.
 * Keyboard Shortcut: Alt+Shift+X, T.
 * Alternatively running AATestRunner.java will provide (significantly less) test result details in the console only. 
 *  
 * ////// IMPORTANT - TEST LOG:
 * Once you have synced your commit on Git, please fill out the Test Log here: http://bit.ly/19wc1D0
 * Filling this out is essential to proving that we proactively tested our work as comprehensively as possible.
 * 
 * ////// These classes do not require test classes:
 * Drawable.java (Interface), Flightconditions.java (Not implemented, No constructor), FlightStatus.java (enum),
 * GameDifficulty.java (enum), GameTime.java (nothing to test), InputTest.java (uses super class),
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ AircraftTest.class, AirportTest.class, AirspaceTest.class,
		ATCTest.class, DummyAirspaceTest.class, FlightTest.class,
		FrameRateMonitorTest.class, GameTest.class, 
		HeathrowTest.class, MouseInputTest.class, PositionTest.class, 
		ScheduleItemTest.class, SchedulerTest.class, TransferWaypointTest.class,
		WaypointTest.class })
public class AATestSuite {

}


