package sepr.atcGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import sepr.atcGame.events.AirspaceListener;

public class Game extends JFrame implements ActionListener, AirspaceListener{

	private Airport airport;
	private Airspace[] airspaces;
	private List<TransferWaypoint> transferWaypoints = new ArrayList<TransferWaypoint>();
	private List<Queue<Waypoint>> flightPlans = new ArrayList<Queue<Waypoint>>();

	private static final int FPS_MAX = 60;
	private static final int FPS_DELAY = 1000/FPS_MAX;
	private javax.swing.Timer frameTimer = new javax.swing.Timer(FPS_DELAY, this);
	private FrameRateMonitor fps = new FrameRateMonitor(FPS_MAX *5);
	private long lastTime, gameTime, gameOverTime = -1;
	private boolean paused = true, gameOver = false;

	private static Random random = new Random();
	private long sinceLastFlight;

	private ATC atc;
	private MouseInput input;
	private testOutput output = new testOutput();
	private JPanel statusPanel = new JPanel();
	private JLabel timerDisplay = new JLabel();
	private JLabel fpsDisplay = new JLabel();
	
	
	private static double nanoToGameTime(long time){
		return time/1000000000.0;}
	private static long gameToNanoTime(double time){	
		return Math.round(time*1000000000);}



	//constructor
	public Game(GameDifficulty difficulty) {
		ImageIcon timeImage,fpsImage;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //{!} for while menu does not redisplay
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("ATC Game | GAME");
		setResizable(false);	//may change if aspect ratio is locked
		timerDisplay.setText("Time : 0");
		timeImage = new ImageIcon(getClass().getResource("/sepr/atcGame/Images/timer_bg.png"));
		fpsImage = new ImageIcon(getClass().getResource("/sepr/atcGame/Images/score_bg.png"));
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
		
		airport.addListener(this);
		
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

	private void generateWorld(){
		//Generate airspaces
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
		//Generate all possible transfers
		for (int i=0; i<airspaces.length; i++){
			Airspace a = airspaces[i];
			transferWaypoints.add(new TransferWaypoint(a.getAirspaceName(), 
					airport, a, bearings[i]));
		}
		
		//FLIGHTPLANS					
		Waypoint[] intermediate = airport.getWaypoints();					
		{	//Generate all possible flightPlans				
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
		
		if(airport.getAircraftCount() != Airspace.MAX_FLIGHTS){		
			//increase spawn rate while airport is near empty	
			long interval = gameToNanoTime(20)/ (Airspace.MAX_FLIGHTS -airport.getAircraftCount());	
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
