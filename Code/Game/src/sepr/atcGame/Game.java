package sepr.atcGame;

import java.util.ArrayList;
import javax.swing.JFrame;


import static java.lang.Math.PI;

public class Game extends JFrame{

	private Airport airport;
	private Output output;
	private ArrayList<TransferWaypoint> transferWaypoints = new ArrayList<TransferWaypoint>();
	
	private boolean paused = true;
	private boolean gameOver = false;
	
	
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
		
		airport.setTransfers(transferWaypoints);
		
		testAircraft aircraft = new testAircraft("test plane", null);
		airport.receiveFlight(aircraft, waypoint3);
		
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
			gameOver = (nanoToGameTime(gameTime) > 5.0);
		}
	}
	
	//getters/setters
	public void setOutput(Output output) {
		this.output = output;
	}

}
