package sepr.atcGame;

import java.util.ArrayList;
import java.util.Queue;

import javax.swing.JFrame;


import static java.lang.Math.PI;

public class Game extends JFrame{

	private Airport airport;
	private Output output;
	private ArrayList<TransferWaypoint> transferWaypoints = new ArrayList<TransferWaypoint>();
	private ArrayList<testAircraft> planes = new ArrayList<testAircraft>();
	
	//constructor
	public Game(GameDifficulty difficulty) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //{!}
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("ATC Game ¦ GAME");
		setResizable(false);	//may change if aspect ratio is locked		
		setLocationRelativeTo(null);
		
		
		
		
		generateWorld();
		add(airport);
		pack();
		setMinimumSize(getSize());
		setVisible(true);
	}
	
	//methods
	private void generateWorld(){
		airport = new Heathrow();

		TransferWaypoint waypoint1 = new TransferWaypoint("Top",airport, null, 0);
		TransferWaypoint waypoint2 = new TransferWaypoint("Right",airport, null, PI /2);
		TransferWaypoint waypoint3 = new TransferWaypoint("Bottom", null, airport, 0);
		TransferWaypoint waypoint4 = new TransferWaypoint("Left",airport, null, PI*3/2);
		//TransferWaypoint waypoint5 = new TransferWaypoint("TR1", airport, null, PI/4);
		transferWaypoints.add(waypoint1);
		transferWaypoints.add(waypoint2);
		transferWaypoints.add(waypoint3);
		transferWaypoints.add(waypoint4);
		//transferWaypoints.add(waypoint5);
		
		airport.setTransfers(transferWaypoints);
		
		Queue<Waypoint> flightplan = null;
		testAircraft taircraft = new testAircraft("test plane", flightplan);
		planes.add(taircraft);
		
		airport.setFlights(planes);
		
	}
	
	//getters/setters
	public void setOutput(Output output) {
		this.output = output;
	}

}
