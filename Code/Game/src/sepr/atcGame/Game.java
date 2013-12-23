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

	private static final int FPS_MAX = 60;
	private FrameRateMonitor fps = new FrameRateMonitor(FPS_MAX);

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
		//Generate airports
		airport = new Heathrow();

		//Generate all possible transfers
		transferWaypoints.add(new TransferWaypoint("Top",airport, null, 0));
		transferWaypoints.add(new TransferWaypoint("Right",airport, null, PI/2));
		transferWaypoints.add(new TransferWaypoint("Bottom", null, airport, 0));
		transferWaypoints.add(new TransferWaypoint("Left",airport, null, PI*3/2));
		transferWaypoints.add(new TransferWaypoint("TR1", airport, null, PI/4));

		//Link airports
		airport.setTransfers(transferWaypoints);	//should be a semi-random subset of

		//{!} TEST LOGIC
		testAircraft aircraft = new testAircraft("test plane", randomFlightPlan());
		airport.receiveFlight(aircraft, transferWaypoints.get(3));
	}

	private Queue<Waypoint> randomFlightPlan(){		
		Random random = new Random();
		Waypoint[] waypoints = airport.getWaypoints();

		//Generate intermediate path
		int[] indices = new int[3];
		indices[0] = random.nextInt(waypoints.length);
		for (int i=1; i <= indices.length-1; i++){
			boolean duplicate;
			do{
				indices[i] = random.nextInt(waypoints.length);
				duplicate = false;
				for (int ii=0; ii<i; ii++){
					duplicate = duplicate || (indices[i] == indices[ii]);}
			} while (duplicate);
		}		

		TransferWaypoint destination = transferWaypoints.get(
				random.nextInt(transferWaypoints.size()));

		//construct flightplan
		Queue<Waypoint> flightPlan = new LinkedList<Waypoint>();
		for (int index: indices){
			flightPlan.add(waypoints[index]);}
		flightPlan.add(destination);

		//{!} print flight plan
		for (int i=0; i < indices.length; i++){
			System.out.print(waypoints[indices[i]].getName() +", ");}
		System.out.println(destination.getName());

		return flightPlan;
	}

	public void Play(){
		paused = false;
		long gameTime, lastTime, elapsedTime;
		double elapsedGameTime;

		lastTime = System.nanoTime();
		gameTime = 0;	
		while (!gameOver){		
			do{
				try {Thread.sleep(1);} 
				catch (Exception e) {};
				elapsedTime = System.nanoTime() -lastTime;
			} while (elapsedTime < (1000000000/FPS_MAX));
			
			lastTime += elapsedTime;			
			fps.newFrame(elapsedTime);

			if (!paused){	//update game elements
				elapsedGameTime = nanoToGameTime(elapsedTime);
				gameTime += elapsedTime;

				airport.update(elapsedGameTime);
				airport.paintImmediately(airport.getBounds());
				//{!} update ATC
				//{!} update scheduler
			}

			//{!} end game after n seconds
			gameOver = (nanoToGameTime(gameTime) > 10.0);
		}
	}

	//getters/setters
	public void setOutput(Output output) {
		this.output = output;
	}

}
