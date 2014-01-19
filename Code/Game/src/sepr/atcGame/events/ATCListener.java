package sepr.atcGame.events;
import sepr.atcGame.Flight;

public interface ATCListener {
	public String eventViolation(Flight f1, Flight f2);
}
