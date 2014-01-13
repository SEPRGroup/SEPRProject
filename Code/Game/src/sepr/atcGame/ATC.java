package sepr.atcGame;

import java.awt.Graphics;
import javax.swing.JPanel;


public class ATC extends JPanel implements GameTime{

	private String name;
	private Airspace airspace;
	

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
		
	}
	
	
	//overridden methods
	public void paintComponent(Graphics g) {
        super.paintComponent(g);       
        //method will go in here
        //draw using [g]: show current flights, flightplans
    }

}
