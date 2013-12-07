package sepr.atcGame;

import java.util.ArrayList;

import javax.swing.JFrame;

public class Game extends JFrame{

	private Airport airport;
	private Output output;
	private ArrayList<TransferWaypoint> transferWaypoints = new ArrayList<TransferWaypoint>();
	
	
	//constructor
	public Game(GameDifficulty difficulty) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //{!}
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("ATC Game ¦ GAME");
		setResizable(false);	//may change if aspect ratio is locked
		setVisible(true);
		setLocationRelativeTo(null);
		
		generateWorld();
		add(airport);
		pack();
		setMinimumSize(getSize());
	}
	
	//methods
	private void generateWorld(){
		airport = new Heathrow();
		
		TransferWaypoint waypoint1 = new TransferWaypoint("Top",airport, null, 0);
		TransferWaypoint waypoint2 = new TransferWaypoint("Right",airport, null, Math.PI /2);
		TransferWaypoint waypoint3 = new TransferWaypoint("Bottom",airport, null, Math.PI);
		TransferWaypoint waypoint4 = new TransferWaypoint("Left",airport, null, Math.PI *3/2);
		TransferWaypoint waypoint5 = new TransferWaypoint("TR",airport, null, Math.PI/4);
		transferWaypoints.add(waypoint1);
		transferWaypoints.add(waypoint2);
		transferWaypoints.add(waypoint3);
		transferWaypoints.add(waypoint4);
		transferWaypoints.add(waypoint5);
		
		airport.setTransfers(transferWaypoints);
	}
	
	//getters/setters
	public void setOutput(Output output) {
		this.output = output;
	}

}
