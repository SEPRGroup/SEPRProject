package sepr.atcGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import sepr.atcGame.events.AirspaceListener;

public class Game extends JFrame implements ActionListener, AirspaceListener{
	private static final Random random = new Random();
	private static final String iconPath = "/sepr/atcGame/Images/plane.png";
	private static Image icon;
	private static final double BASE_GAME_SPEED = 1/1000000000.0;
	private static final int FPS_MAX = 60;
	private static final int FPS_DELAY = 1000/FPS_MAX;
	
	public static final int HEATHROW=1, SCHIPOL = 2;
	
	private Airport airport;
	private Airspace[] airspaces;
	private List<TransferWaypoint> transferWaypoints = new ArrayList<TransferWaypoint>();
	private List<Queue<Waypoint>> flightPlans = new ArrayList<Queue<Waypoint>>();

	private javax.swing.Timer frameTimer = new javax.swing.Timer(FPS_DELAY, this);
	private FrameRateMonitor fps = new FrameRateMonitor(FPS_MAX *5);
	private long lastTime, gameTime;
	private boolean paused = true, gameOver = false;

	private int flightsMax;
	private long sinceLastFlight, gameOverTime = -1;

	private ATC atc;
	private MouseInput input;
	private testOutput output = new testOutput();
	private JPanel statusPanel = new JPanel();
	private JLabel timerDisplay;
	private JLabel fpsDisplay = new JLabel();
	
	private static double nanoToGameTime(long time){
		return time*BASE_GAME_SPEED;}
	private static long gameToNanoTime(double time){	
		return Math.round(time/BASE_GAME_SPEED);}


	//constructor
	public Game(GameDifficulty difficulty, int airport_selection) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //{!} for while menu does not redisplay
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("When Planes Collide");
		try {icon = ImageIO.read(getClass().getResourceAsStream(iconPath));}
		catch (IOException e){System.out.println("[ERROR Game()]\tImage loading failed");};
		setIconImage(icon);
		setResizable(false);	//may change if aspect ratio is locked
		
		timerDisplay =  new JLabel("Time : 0");
		//Sets background for status bar labels
		timerDisplay.setIcon(new ImageIcon(getClass().getResource("/sepr/atcGame/Images/timer_bg.png")));
		timerDisplay.setVerticalTextPosition(JLabel.CENTER);
		timerDisplay.setHorizontalTextPosition(JLabel.CENTER);
		
		fpsDisplay.setIcon(new ImageIcon(getClass().getResource("/sepr/atcGame/Images/score_bg.png")));
		fpsDisplay.setVerticalTextPosition(JLabel.CENTER);
		fpsDisplay.setHorizontalTextPosition(JLabel.CENTER);
	
		statusPanel.add(timerDisplay);
		statusPanel.add(fpsDisplay);
		statusPanel.setBackground(Color.WHITE);
		add(statusPanel,BorderLayout.PAGE_START);
		
		//configure model
		generateWorld(airport_selection);
		atc = new ATC("bob",airport);
		atc.addListener(output);
		add(atc,BorderLayout.EAST);
		add(airport);
		pack();
		input = new MouseInput(airport);
		getRootPane().getLayeredPane().add(input, JLayeredPane.MODAL_LAYER);
				
		//configure game logic
		airport.addListener(this);
		switch (difficulty){
		case EASY:
			flightsMax = Airport.MAX_FLIGHTS-2;
			break;
		case MEDIUM:
			flightsMax = Airport.MAX_FLIGHTS-1;
			break;
		case HARD:
			flightsMax = Airport.MAX_FLIGHTS;
			break;			
		}
		
		setLocationRelativeTo(null);
		setMinimumSize(getSize());
		setVisible(true);
	}
	
	
	//methods

	private void newFlightPlan(List<TransferWaypoint> transfers, Waypoint[] intermediate, int[] indexes){					
		//creates a flightplan starting at a TransferWaypoint, 				
			//following some number of intermediate, and terminating at a transferWaypoint			
		Queue<Waypoint> flightPlan = new LinkedList<Waypoint>();				
		flightPlan.add(transfers.get(indexes[0]));				
		for (int i=1; i<=(indexes.length-2); i++)				
			flightPlan.add(intermediate[indexes[i]]);			
		flightPlan.add(transfers.get(indexes[indexes.length-1]));				
		flightPlans.add(flightPlan);				
	}					

	private void generateWorld(int airportSelection){
		//Generate airspaces
		double[] bearings = new double[0];
		switch (airportSelection){
		case HEATHROW:
			airport = new Heathrow();
			airspaces = new Airspace[]{
					new DummyAirspace("Athens"), 
					new DummyAirspace("Dubai"), 
					new DummyAirspace("Paris"),
					new DummyAirspace("Sydney"),
					new DummyAirspace("Zurich"),
			};
			bearings = new double[]{
					1, 2, 3, 4, 5
			};
			break;
		case SCHIPOL:
			//{!}
			break;
		default:
			System.out.println("[ERROR Game.generateWorld(" +String.valueOf(airportSelection) +")]\tInvalid airport");
		}
		
		//TRANSFERS
		//Generate all possible transfers
		for (int i=0; i<airspaces.length; i++){
			Airspace a = airspaces[i];
			transferWaypoints.add(new TransferWaypoint(a.getAirspaceName(), 
					airport, a, bearings[i]));
		}
		
		//FLIGHTPLANS	
		Waypoint[] intermediate = airport.getWaypoints();
		switch (airportSelection){
		case HEATHROW:
			int ATHENS=0, DUBAI=1, PARIS=2,SYDNEY=3,ZURICH=4,				
				ALPHA=0, BRAVO=1, CHARLIE=2, DELTA=3, ECHO=4, FOXTROT=5, GOLF=6, HOTEL=7, INDIGO=8, JULIETT=9;			

			newFlightPlan(transferWaypoints, intermediate, 				
					new int[]{ATHENS, JULIETT, HOTEL, DUBAI});		

			newFlightPlan(transferWaypoints, intermediate, 				
					new int[]{ATHENS, FOXTROT, ALPHA ,GOLF ,SYDNEY});		

			newFlightPlan(transferWaypoints, intermediate, 				
					new int[]{DUBAI, ECHO, JULIETT, FOXTROT, BRAVO, SYDNEY});		

			newFlightPlan(transferWaypoints, intermediate, 				
					new int[]{DUBAI, CHARLIE, ECHO, ATHENS});		

			newFlightPlan(transferWaypoints, intermediate, 				
					new int[]{PARIS, DELTA, BRAVO, JULIETT, ATHENS});		

			newFlightPlan(transferWaypoints, intermediate, 				
					new int[]{PARIS, INDIGO, CHARLIE, FOXTROT, ZURICH});		

			newFlightPlan(transferWaypoints, intermediate, 				
					new int[]{SYDNEY, DELTA, INDIGO, HOTEL, DUBAI});		

			newFlightPlan(transferWaypoints, intermediate, 				
					new int[]{SYDNEY, DELTA, INDIGO, CHARLIE, ATHENS});		

			newFlightPlan(transferWaypoints, intermediate, 				
					new int[]{ZURICH, FOXTROT, JULIETT, CHARLIE, HOTEL, DUBAI});		

			newFlightPlan(transferWaypoints, intermediate, 				
					new int[]{ZURICH, BRAVO, INDIGO, DUBAI});
			break;
		case SCHIPOL:
			//{!}
			break;
		}

		//Link airports: random subset of all transferWaypoints
		List<TransferWaypoint> transfers = new ArrayList<TransferWaypoint>();
		int links = 4,	//number to take
			num = transferWaypoints.size(), //number available
			count = 0;	//number taken so far
		for (TransferWaypoint t : transferWaypoints){
			if ( random.nextDouble() < (links-count)/(double)num ){
				transfers.add(t);
				count++;
			}
			num--;
		}
		airport.setTransfers(transfers);

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
		transferWaypoints = transfers;
	}

	public Queue<Waypoint> randomFlightPlan(){		
		//take a flightPlan randomly from the pre-generated list
		Queue<Waypoint> flightPlan = new LinkedList<Waypoint>( 
				flightPlans.get(random.nextInt(flightPlans.size())));
		//System.out.println(flightPlan.toString());
		return flightPlan;
	}
	
	private Flight randomFlight(){	
		//should determine which class of Flight to generate
		String idString = "test" +String.format("%1$04d", random.nextInt(10000));
		Aircraft f = new testAircraft(idString, randomFlightPlan());
		f.init(f.cruiseV, 3000);	//enter at <10000 feet
		return f;
	}	

	
	// for testing
	public List<Queue<Waypoint>> getFlightPlans() {
		System.out.println("Game.getFlightPlans should be called for test purposes only");
		return flightPlans;
	}

	public void Play(){
		sinceLastFlight = 0;
		gameTime = 0;
		
		Resume();
	}
	
	public int Pause(){
		if (!paused){
			frameTimer.stop();
			paused = true;
		}
		return frameTimer.getDelay();
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

		elapsedTime = System.nanoTime() -lastTime;
		lastTime += elapsedTime;	
		gameTime += elapsedTime;
		fps.newFrame(elapsedTime);
		elapsedGameTime = nanoToGameTime(elapsedTime);

		//Game logic
		sinceLastFlight += elapsedTime;
		
		if(airport.getAircraftCount() != flightsMax){		
			//increase spawn rate while airport is near empty	
			long interval = gameToNanoTime(100/flightsMax)/ (flightsMax -airport.getAircraftCount());	
			if(sinceLastFlight >= interval){	
				Flight f = randomFlight();
				airport.receiveFlight(f, (TransferWaypoint)f.getFlightPlan().poll());
				sinceLastFlight -= interval;
				
				/*airport.newFlight(f);
				f.takeOff(airport, (TransferWaypoint)f.getFlightPlan().poll());*/
			}	
		}		

		//update GameTime components
		//{!} update scheduler
		airport.update(elapsedGameTime);
		atc.update(elapsedGameTime);
		input.update(elapsedGameTime);
		

		{	//update status panel
			String gameTimeString = String.format("%d", Math.round(nanoToGameTime(gameTime)));
			timerDisplay.setText("Time: \t"+ gameTimeString);
			fpsDisplay.setText(String.valueOf(Math.round(fps.getFrameRate())) +"fps");
		}
		
		if (gameOver){		
			gameOverTime -= elapsedTime;	
			if (gameOverTime < 0)
				Pause();
		}		

	}

	
	// Testing purposes
	public Airport getAirport(){
		return airport;
	}
	
	@Override	
	public void eventCrash(Flight f1, Flight f2) {	
		gameOver = true;
		gameOverTime = gameToNanoTime(10); 
	}	
		
	@Override	
	public void eventLanded(Flight f) {	
		//score
	}	
		
	@Override	
	public void eventHandover(Flight f) {	
		//score
	}	
		
	@Override	
	public void eventLost(Flight f) {	
		//score
	}	
		
	@Override	
	public void eventHighlighted(Flight f) {	
		//nothing
	}	
	
	@Override
	public String toString(){ 
		String s = "<Game> | Flight plans: " + flightPlans +  
				", Frame rate monitor:" + fps + ", Timer: " + 
				timerDisplay.getText() + ", Difficulty: Not implemented" ;
		return s;
	}
	

}
