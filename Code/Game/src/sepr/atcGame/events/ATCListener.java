package sepr.atcGame.events;
import sepr.atcGame.Flight;

public interface ATCListener {
	public void eventViolation(Flight f1, Flight f2);
}
