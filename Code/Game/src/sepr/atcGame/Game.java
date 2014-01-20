package sepr.atcGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import java.util.Random;

import static java.lang.Math.PI;

public class Game extends JFrame implements ActionListener{

	private Airport airport;
	private Airspace[] airspaces;
	private List<TransferWaypoint> transferWaypoints = new ArrayList<TransferWaypoint>();
	private static List<Queue<Waypoint>> flightPlans = new ArrayList<Queue<Waypoint>>();

	private static final int FPS_MAX = 60;
	private static final int FPS_DELAY = 1000/FPS_MAX;
	private javax.swing.Timer frameTimer = new javax.swing.Timer(FPS_DELAY, this);
	private FrameRateMonitor fps = new FrameRateMonitor(FPS_MAX *5);
	private long lastTime, gameTime;
	private boolean paused = true, gameOver = false;

	private static Random random = new Random();
	private long sinceLastFlight;
	private Queue<Flight> toAdd;	//{!}

	private ATC atc;
	private MouseInput input;
	private testOutput output = new testOutput();
	private JPanel statusPanel = new JPanel();
	private JLabel timerDisplay = new JLabel();
	private JLabel fpsDisplay = new JLabel();
	
	
	private static double nanoToGameTime(long time){
		return time/1000000000.0;}


	//constructor
	public Game(GameDifficulty difficulty) {
		ImageIcon timeImage,fpsImage;
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //{!}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("ATC Game | GAME");
		setResizable(true);	//may change if aspect ratio is locked
		timerDisplay.setText("Time : 0");
		timeImage = new ImageIcon("src/sepr/atcGame/Images/timer_bg.png");
		fpsImage = new ImageIcon("src/sepr/atcGame/Images/score_bg.png");
		//Sets background for status bar labels
		timerDisplay.setIcon(timeImage);
		timerDisplay.setVerticalTextPosition(JLabel.CENTER);
		timerDisplay.setHorizontalTextPosition(JLabel.CENTER);
		
		fpsDisplay.setIcon(fpsImage);
		fpsDisplay.setVerticalTextPosition(JLabel.CENTER);
		fpsDisplay.setHorizontalTextPosition(JLabel.CENTER);
	
		statusPanel.add(timerDisplay);
		statusPanel.add(fpsDisplay);
		statusPanel.setBackground(Color.WHITE);
		add(statusPanel,BorderLayout.PAGE_START);
		
		generateWorld();
		atc = new ATC("bob",airport);
		atc.addListener(output);
		add(atc,BorderLayout.EAST);
		add(airport);
		pack();
		input = new MouseInput(airport);
		getRootPane().getLayeredPane().add(input, JLayeredPane.MODAL_LAYER);
		setLocationRelativeTo(null);
		setMinimumSize(getSize());
		
		setVisible(true);
	}

	//methods
	private void generateWorld(){
		//Generate airports
		airport = new Heathrow();
		airspaces = new Airspace[]{
				new DummyAirspace("Athens"), 
				new DummyAirspace("Dubai"), 
				new DummyAirspace("Paris"),
				new DummyAirspace("Sydney"),
				new DummyAirspace("Zurich"),
				};
		double[] bearings = new double[]{
				1, 2, 3, 4, 5
		};

		//TRANSFERS
		//Generate all transfers
		for (int i=0; i<airspaces.length; i++){
			Airspace a = airspaces[i];
			transferWaypoints.add(new TransferWaypoint(a.getAirspaceName(), 
					airport, a, bearings[i]));
		}

		//Link airports: random subset of all transferWaypoints
		List<TransferWaypoint> transfers = new ArrayList<TransferWaypoint>();
		int links = 4,	//number to take
			num = transferWaypoints.size(), //number available
			count = 0;	//number taken so far
		for (TransferWaypoint t : transferWaypoints){
			if (random.nextDouble() < ((double)(links-count))/num){
				transfers.add(t);
				count++;
			}
			num--;
		}
		airport.setTransfers(transfers);

		//FLIGHTPLANS
		Waypoint[] intermediate = airport.getWaypoints();

		//new flight plan - Athens, Juliett, Charlie
		Queue<Waypoint> flightPlan = new LinkedList<Waypoint>();
		flightPlan.add(transferWaypoints.get(0)); flightPlan.add(intermediate[9]); flightPlan.add(intermediate[2]);
		flightPlans.add(flightPlan);

		//new flight plan - Athens, Foxtrot, Alpha, Bravo
		flightPlan = new LinkedList<Waypoint>();
		flightPlan.add(transferWaypoints.get(0)); flightPlan.add(intermediate[5]); flightPlan.add(intermediate[0]); flightPlan.add(intermediate[1]);
		flightPlans.add(flightPlan);

		//new flight plan - Dubai, Echo, Juliett, Foxtrot, Bravo
		flightPlan = new LinkedList<Waypoint>();
		flightPlan.add(transferWaypoints.get(1)); flightPlan.add(intermediate[4]); flightPlan.add(intermediate[9]); flightPlan.add(intermediate[5]); flightPlan.add(intermediate[1]);
		flightPlans.add(flightPlan);

		//new flight plan - Dubai, Echo, Charlie
		flightPlan = new LinkedList<Waypoint>();
		flightPlan.add(transferWaypoints.get(1)); flightPlan.add(intermediate[4]); flightPlan.add(intermediate[2]);
		flightPlans.add(flightPlan);

		//new flight plan - Paris, Delta, Indigo, Charlie
		flightPlan = new LinkedList<Waypoint>();
		flightPlan.add(transferWaypoints.get(2)); flightPlan.add(intermediate[3]); flightPlan.add(intermediate[8]); flightPlan.add(intermediate[2]);
		flightPlans.add(flightPlan);

		//new flight plan - Paris, Hotel, Echo, Juliett, Bravo
		flightPlan = new LinkedList<Waypoint>();
		flightPlan.add(transferWaypoints.get(2)); flightPlan.add(intermediate[7]); flightPlan.add(intermediate[4]); flightPlan.add(intermediate[9]); flightPlan.add(intermediate[1]);
		flightPlans.add(flightPlan);

		//new flight plan - Sydney, Delta, Golf, Bravo
		flightPlan = new LinkedList<Waypoint>();
		flightPlan.add(transferWaypoints.get(3)); flightPlan.add(intermediate[3]); flightPlan.add(intermediate[6]); flightPlan.add(intermediate[1]);
		flightPlans.add(flightPlan);

		//new flight plan - Sydney, Delta, Indigo, Charlie
		flightPlan = new LinkedList<Waypoint>();
		flightPlan.add(transferWaypoints.get(3)); flightPlan.add(intermediate[3]); flightPlan.add(intermediate[8]); flightPlan.add(intermediate[2]);
		flightPlans.add(flightPlan);

		//new flight plan -Zurich, Alpha, Foxtrot, Juliett, Charlie
		flightPlan = new LinkedList<Waypoint>();
		flightPlan.add(transferWaypoints.get(4)); flightPlan.add(intermediate[0]); flightPlan.add(intermediate[5]); flightPlan.add(intermediate[9]); flightPlan.add(intermediate[2]);
		flightPlans.add(flightPlan);

		//new flight plan -Zurich, Golf, Bravo
		flightPlan = new LinkedList<Waypoint>();
		flightPlan.add(transferWaypoints.get(4)); flightPlan.add(intermediate[6]); flightPlan.add(intermediate[1]);
		flightPlans.add(flightPlan);

		//remove any flightPlans that are incompatible with Transfer selection
		List<Waypoint> waypoints = new ArrayList<Waypoint>(transfers);
		for (Waypoint w : intermediate)
			waypoints.add(w);
		List<Queue<Waypoint>> invalid = new ArrayList<Queue<Waypoint>>();
		for (Queue<Waypoint> f : flightPlans){
			if (!waypoints.containsAll(f)){
				invalid.add(f);
			}
		}
		flightPlans.removeAll(invalid);

	}

	public Queue<Waypoint> randomFlightPlan(){		
		//take a flightPlan randomly from the pre-generated list
		Queue<Waypoint> flightPlan = new LinkedList<Waypoint>( 
				flightPlans.get(random.nextInt(flightPlans.size())));
		//System.out.println(flightPlan.toString());

		return flightPlan;
	}

	public void Play(){
		//{!} test logic
		toAdd = new LinkedList<Flight>();	
		for (int i=0; i<5; i++){
			toAdd.add(new testAircraft("test"+i, randomFlightPlan()));}
		sinceLastFlight = 0;

		gameTime = 0;
		Resume();
	}
	
	public void Pause(){
		if (!paused){
			frameTimer.stop();
			paused = true;
		}
	}
	
	public void Resume(){
		if (paused && !gameOver){
			paused = false;
			lastTime = System.nanoTime();
			frameTimer.start();
		}	
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
					f.init(f.cruiseV, 3000);	//enter < 10000 feet
					airport.receiveFlight(f, 
							(TransferWaypoint)f.getFlightPlan().poll());
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
		atc.update(elapsedGameTime);
		input.update(elapsedGameTime);
		
		//{!} update scheduler

		{	//update status panel
			gameTimeString = String.format("%d", gameTime/1000000000);
			timerDisplay.setText("Time: \t"+ gameTimeString);
			fpsDisplay.setText(String.valueOf(Math.round(fps.getFrameRate())) +"fps");
		}

	}

}
