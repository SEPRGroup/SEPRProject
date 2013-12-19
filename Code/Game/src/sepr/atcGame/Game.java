package sepr.atcGame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JFrame;

import java.util.Random;

import static java.lang.Math.PI;

public class Game extends JFrame{

	private Airport airport;
	private Output output;
	private ArrayList<TransferWaypoint> transferWaypoints = new ArrayList<TransferWaypoint>();
	
	private boolean paused = true;
	private boolean gameOver = false;
	private Random randomGenerator = new Random();
	
	private static double nanoToGameTime(long time){
		return time/1000000000.0;}
	
	
	//constructor
	public Game(GameDifficulty difficulty) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //{!}
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("ATC Game ¦ GAME");
		setResizable(false);	//may change if aspect ratio is locked		
		

		generateWorld();
		add(airport);
		pack();
		setLocationRelativeTo(null);
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
		TransferWaypoint waypoint5 = new TransferWaypoint("TR1", airport, null, PI/4);
		
		transferWaypoints.add(waypoint1);
		transferWaypoints.add(waypoint2);
		transferWaypoints.add(waypoint3);
		transferWaypoints.add(waypoint4);
		transferWaypoints.add(waypoint5);
		
		int selectTransferWaypoint = randomGenerator.nextInt(transferWaypoints.size());		
		TransferWaypoint initialWaypoint = transferWaypoints.get(selectTransferWaypoint);
		
		//where the plane exits the airspace
		selectTransferWaypoint = randomGenerator.nextInt(transferWaypoints.size());		
		TransferWaypoint finalWaypoint = transferWaypoints.get(selectTransferWaypoint);
		
		
		Queue<Waypoint> flightPlan = new LinkedList<Waypoint>();
		
		Waypoint[] waypoints = airport.getWaypoints();
		
		int[] store = new int[3];
		store[0] = randomGenerator.nextInt(waypoints.length);
		
		System.out.println("random number = " + store[0]);
		int i;
		for (i = 1; i <= store.length -1; i++){
							
			do {
				store[i] = randomGenerator.nextInt(waypoints.length);	
			}while(store[i-1] == store[i]);

		}		
		
		// add waypoints to flightplan
		for (int index: store){
			flightPlan.add(waypoints[index]);
		}			
		
		flightPlan.add(finalWaypoint);
		
		airport.setTransfers(transferWaypoints);
					
		testAircraft aircraft = new testAircraft("test plane", flightPlan);
		airport.receiveFlight(aircraft, initialWaypoint);
		
	}
		
	public void Play(){
		paused = false;
		long gameTime, lastTime, elapsedTime;
		double elapsedGameTime;
		
		lastTime = System.nanoTime();
		gameTime = 0;	
		while (!gameOver){
			try {Thread.sleep(1);} 
			catch (Exception e) {};
			
			elapsedTime = System.nanoTime() -lastTime;
			lastTime += elapsedTime;
				
			if (!paused){
				elapsedGameTime = nanoToGameTime(elapsedTime);
				gameTime += elapsedTime;
				
				airport.update(elapsedGameTime);
				airport.paintImmediately(airport.getBounds());
				//{!} update ATC
				//{!} update scheduler
				
				System.out.println(elapsedGameTime);
			}
			
			//{!} end game after 5 seconds
			gameOver = (nanoToGameTime(gameTime) > 3.0);
		}
	}
	
	//getters/setters
	public void setOutput(Output output) {
		this.output = output;
	}

}
