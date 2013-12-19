package sepr.atcGame;

import java.util.Queue;
import java.util.Random;

public class FlightPlan {
	private Queue<Waypoint> flightPlan;
	private int MAX_WAYPOINTS = 3;
	private TransferWaypoint initialWaypoint;
	private Random randomGenerator = new Random();
	
	
	public FlightPlan(TransferWaypoint initialWaypoint)
	{
		this.initialWaypoint = initialWaypoint;		
	}
	
	private boolean add(Waypoint waypoint)
	{
		flightPlan.add(waypoint);
		return true;
	}

	public Queue<Waypoint> getFlightPlan(){		
		return flightPlan;
	}
	
	
	// generate random waypoints into a flight plan
	public void generateFlightPlan(Waypoint[] waypoints){
				
		int[] store = new int[MAX_WAYPOINTS];
		store[0] = randomGenerator.nextInt(waypoints.length);
		int y;
		for (y = 1; y <= MAX_WAYPOINTS -1; y++){
				
			
			do {
				store[y] = randomGenerator.nextInt(waypoints.length);
			}while(store[y-1] == store[y]);
		}
		
		
		// add waypoints to flightplan
		for (int index: store){
			System.out.println(index);
		}
		
		
	}
	
	public TransferWaypoint getInitialWaypoint()
	{
		return initialWaypoint;
	}

}
