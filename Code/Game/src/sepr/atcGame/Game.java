package sepr.atcGame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JFrame;

import java.util.Random;

import static java.lang.Math.PI;

public class Game extends JFrame{

	private Airport airport;
	private Airspace[] airspaces;
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
		airspaces = new Airspace[]
				{new DummyAirspace("Athens"), 
				new DummyAirspace("Dubai"), 
				new DummyAirspace("Paris"),
				new DummyAirspace("Sydney"),
				new DummyAirspace("Zurich")
				};

		//Generate all possible transfers	{!} random bearings
		for (int i=0; i<airspaces.length; i++){
			Airspace a = airspaces[i];
			transferWaypoints.add(new TransferWaypoint(a.getAirspaceName(), 
					airport, a, 
					(2*PI*i)/airspaces.length));
		}

		//Link airports
		airport.setTransfers(transferWaypoints);	//should be a semi-random subset of
	}

	private Queue<Waypoint> randomFlightPlan(){		
		Random random = new Random();
		Waypoint[] waypoints = airport.getWaypoints();

		//Generate intermediate path
		int[] indices = new int[3];
		indices[0] = random.nextInt(waypoints.length);
		for (int i=1; i <= indices.length-1; i++){
			boolean duplicate;
			do{	//repeat until a waypoint is generated that is not a duplicate
				indices[i] = random.nextInt(waypoints.length);
				duplicate = false;
				for (int ii=0; ii<i; ii++){	//compare to existing waypoints
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
		long lastTime, gameTime, elapsedTime;
		long sinceLastFlight;
		double elapsedGameTime;
		Random random = new Random();

		//{!} Test setup logic only
		Queue<Flight> toAdd = new LinkedList<Flight>();	
		for (int i=0; i<5; i++){
			toAdd.add(new testAircraft("test"+i, randomFlightPlan()));}

		//game loop
		lastTime = System.nanoTime();
		gameTime = 0;
		sinceLastFlight = 0;
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
				sinceLastFlight += elapsedTime;

				if (toAdd.size() > 0){
					if (sinceLastFlight > 1500000000){
						Flight f = toAdd.poll();
						airport.receiveFlight(f , 
								transferWaypoints.get( random.nextInt(transferWaypoints.size()) ));
						System.out.println("add flight:\t" +f.getIdentifier());
						sinceLastFlight -= 1500000000;
					}
				}

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
