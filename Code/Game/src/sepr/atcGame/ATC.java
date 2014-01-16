package sepr.atcGame;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import sepr.atcGame.events.ATCListener;


public class ATC extends JPanel implements GameTime{

	private String name;
	private Airspace airspace;
	
	private static final double CHECK_PERIOD = 1;
	private double sinceLastCheck = 0;
	
	List<ATCListener> listeners = new ArrayList<ATCListener>();
	

	//getters/setters
	public String getName() {
		return name;
	}

	public Airspace getAirspace() {
		return airspace;
	}
	
	
	public ATC(String name, Airspace airspace) {
		this.name = name;
		this.airspace = airspace;
	}
	
	
	//methods
	public boolean processCommand(String command){ //return if command is valid
		//method will go in here
		
		return false; //{!}
	}
	
	public void update(double time){
		sinceLastCheck += time;
		if (sinceLastCheck >= CHECK_PERIOD){
			checkViolations();
			sinceLastCheck -= CHECK_PERIOD;
		}
	}
	
	private void checkViolations(){
		Flight[] aircraft = airspace.getAircraft();
		for (int i	= 0; i<aircraft.length; i++){
			Flight f1 = aircraft[i];
			if (f1!= null){
				for (int ii = i+1; ii<aircraft.length; ii++){
					Flight f2 = aircraft[ii];
					if (f2!= null){
						Position f1Pos = f1.getPosition(), f2Pos = f2.getPosition();
						boolean vertical = Math.abs(f1Pos.altitude - f2Pos.altitude) < 300;
						if (vertical){
							boolean horizontal = Math.pow(Math.pow(f1Pos.x - f2Pos.x, 2) 
									+ Math.pow(f1Pos.y - f2Pos.y, 2),0.5) < 4830;
							if (horizontal){
								eventViolation(f1, f2);
							}
						}
					}
				}
			}
		}
	}
	
	
	private void eventViolation(Flight f1,Flight f2){
		for (ATCListener l : listeners)
            l.eventViolation(f1, f2);
	}
	
	public void addListener(ATCListener toAdd) {
        listeners.add(toAdd);
    }
	
	
	//overridden methods
	public void paintComponent(Graphics g) {
        super.paintComponent(g);       
        //method will go in here
        //draw using [g]: show current flights, flightplans
    }

}
