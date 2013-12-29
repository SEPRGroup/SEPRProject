package sepr.atcGame.tests;

//Imports JUnit Annotations
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

//Imports JUnit Test Methods
import static org.junit.Assert.*;

//Import Position Class from atcGame Package -- MUST BE ADDED/UPDATED FOR EACH TEST CLASS
import sepr.atcGame.Position;


public class PositionTest {
	public Position instance;
	public double newvalue;
	
	
	//This method creates a test instance of class Position
	@Before //Junit will run this method before every @Test in this class
	public void setup() { //Convention names all @Before methods as setup()
		instance = new Position(4,8,12000);
		System.out.println("setup()| X = "+instance.getX()+",Y = "+instance.getY()+", Alt= "+instance.getAltitude());
		//Outputs aren't necessary, just useful to explain execution order at run-time.
	}
	
	//This method 'tears down' the test instance and variables.
	@After //Junit will run this method after every @Test in this class
	public void teardown() { //Convention names all @After methods as teardown()
		instance = null;
		newvalue = 0;
		System.out.println("teardown()| instance = "+ instance +", newvalue ="+ newvalue); 
		// This method isn't really necessary for this class - I've just included it to show it's possible.
	}
		
	//TESTS - Test Classes should test all getters/setters and methods (ideally constructors too, although I haven't done this here as its implied in setup())
	@Test
	public void testGetX() {
		System.out.println("testGetX()| X co-ord is: "+instance.getX());
		assertEquals(4.0, instance.getX(), 0);
		// assertequals(Expected value, Test value, delta) - Tests for equality.
		// delta is only required when comparing double integers, and specified the amount of difference permissable. i.e, Those caused by rounding errors.
	}

	@Test
	public void testSetX() {
		newvalue = 2.0;
		instance.setX(newvalue);
		assertEquals(newvalue, instance.getX(), 0);
		System.out.println("testSetX()| X co-ord changed to: "+instance.getX());
	}

	@Test
	public void testGetY() {
		System.out.println("testGetY()| Y co-ord is: "+instance.getY());
		assertEquals(8.0, instance.getY(), 0);
	}

	@Test
	public void testSetY() {
		newvalue = 3.0;
		instance.setY(newvalue);
		assertEquals(newvalue, instance.getY(), 0);
		System.out.println("testSetY()| Y co-ord changed to: "+instance.getY());
	}

	@Test
	public void testGetAltitude() {
		System.out.println("testGetAltitude()| Altitude is: "+instance.getAltitude());
		assertEquals(12000.0, instance.getAltitude(), 0);
	}

	@Test
	public void testSetAltitude() {
		newvalue = 12000.0;
		instance.setAltitude(newvalue);
		assertEquals(newvalue, instance.getAltitude(), 0);
		System.out.println("testSetAltitude()| Altitude changed to: "+instance.getAltitude());
	}

}
