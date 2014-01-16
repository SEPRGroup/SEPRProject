package sepr.atcGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.util.Random;

import static java.lang.Math.PI;

public class Game extends JFrame implements ActionListener{

	private Airport airport;
	private Airspace[] airspaces;
	private Output output;
	private List<TransferWaypoint> transferWaypoints = new ArrayList<TransferWaypoint>();

	private static final int FPS_MAX = 60;
	private static final int FPS_DELAY = 1000/FPS_MAX;
	private javax.swing.Timer frameTimer = new javax.swing.Timer(FPS_DELAY, this);
	private FrameRateMonitor fps = new FrameRateMonitor(FPS_MAX *5);
	private long lastTime, gameTime;
	private boolean paused = true, gameOver = false;

	private static Random random = new Random();
	private long sinceLastFlight;
	private Queue<Flight> toAdd;	//{!}

	private ATC activeFlights = new ATC("bob",airport);
	private JPanel timerPanel = new JPanel();
	private JLabel timerDisplay = new JLabel();
	


	private static double nanoToGameTime(long time){
		return time/1000000000.0;}


	//constructor
	public Game(GameDifficulty difficulty) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //{!}
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("ATC Game | GAME");
		setResizable(true);	//may change if aspect ratio is locked
		timerDisplay.setText("Time : 0");

		timerPanel.add(timerDisplay);
		timerPanel.setBackground(Color.WHITE);
		add(timerPanel,BorderLayout.PAGE_START);
		
		generateWorld();
		add(activeFlights,BorderLayout.EAST);
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
		Waypoint[] waypoints = airport.getWaypoints();
		List<TransferWaypoint> transfers = airport.getTransfers();
		
		//Generate starting waypoint
		TransferWaypoint source = transfers.get(random.nextInt(transfers.size()));

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
		flightPlan.add(source);
		for (int index: indices){
			flightPlan.add(waypoints[index]);}
		flightPlan.add(destination);

		//{!} print flight plan
		System.out.print(source.getName() +", ");
		for (int i=0; i < indices.length; i++){
			System.out.print(waypoints[indices[i]].getName() +", ");}
		System.out.println(destination.getName());

		return flightPlan;
	}

	public void Play(){
		toAdd = new LinkedList<Flight>();	
		for (int i=0; i<5; i++){
			toAdd.add(new testAircraft("test"+i, randomFlightPlan()));}
		sinceLastFlight = 0;

		paused = false;
		gameTime = 0;
		lastTime = System.nanoTime();
		frameTimer.start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		long elapsedTime;
		double elapsedGameTime;
		String gameTimeString;

		elapsedTime = System.nanoTime() -lastTime;
		lastTime += elapsedTime;	
		gameTime += elapsedTime;
		fps.newFrame(elapsedTime);
		elapsedGameTime = nanoToGameTime(elapsedTime);

		//Game logic

		sinceLastFlight += elapsedTime;
		if (toAdd.size() > 0){	//{!} test logic
			if (sinceLastFlight > 1500000000){
				Aircraft f = (Aircraft)toAdd.poll();
				{
					f.init(f.cruiseV, 200);
					airport.receiveFlight(f, 
							(TransferWaypoint)f.getFlightPlan().poll());
					f.turnTo(0);
					f.toAltitude(300);
				}
				/*{
					airport.newFlight(f);
					f.takeOff(airport, (TransferWaypoint)f.getFlightPlan().poll());
				}*/
				System.out.println("add flight:\t" +f.getIdentifier());
				sinceLastFlight -= 1500000000;
			}
		}

		airport.update(elapsedGameTime);
		//{!} update ATC
		//{!} update scheduler

		{	//update score panel
			gameTimeString = String.format("%d", gameTime/1000000000);
			timerDisplay.setText("Time: \t"+ gameTimeString);
		}

		//{!} end game after n seconds
		gameOver = (nanoToGameTime(gameTime) > 60.0);
		if (gameOver){
			frameTimer.stop();
			System.out.println("[END]");
		}
	}


	//getters/setters
	public void setOutput(Output output) {
		this.output = output;
	}

}
