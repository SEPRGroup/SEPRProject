package sepr.atcGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sepr.atcGame.events.ATCListener;
import sepr.atcGame.events.AirspaceListener;


public class ATC extends JPanel implements GameTime{

	private String name;
	private Airspace airspace;

	private static final double CHECK_PERIOD = 5;
	private double sinceLastCheck = 0;

	private List<ATCListener> listeners = new ArrayList<ATCListener>();
	private Flight highlighted;
	private JLabel[] labels = new JLabel[5];
	private Icon background = new ImageIcon("src/sepr/atcGame/Images/side_bg.png");
	private Icon selectedBg = new ImageIcon("src/sepr/atcGame/Images/side_bg_selected.png");
	private Icon baseBg  = background;


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
		setPreferredSize(new Dimension(215,300));
		setBackground(Color.WHITE);
		airspace.addListener(new AirspaceListener(){
			public void eventHighlighted(Flight f){
				//System.out.println("selected");
				highlighted = f;
				for (int i	= 0; i<labels.length; i++){
					if(f != null){
						if(labels[i]!= null){
							if(labels[i].getText().contains(f.getIdentifier())){
								labels[i].setIcon(selectedBg);
							}else{
								labels[i].setIcon(baseBg);
							}
						}	
					}else{
						if(labels[i] != null){
							labels[i].setIcon(baseBg);
						}
					}
				}
			}

			@Override
			public void eventCrash(Flight f1, Flight f2) {
				if(f1 == highlighted || f2 == highlighted){
					highlighted= null;
				}	
			}

			@Override
			public void eventLanded(Flight f) {
				if(f == highlighted){
					highlighted= null;
				}

			}

			@Override
			public void eventHandover(Flight f) {
				if(f == highlighted){
					highlighted= null;
				}
					Flight[] aircraft = getAirspace().getAircraft();
					for (int i=0; i<aircraft.length; i++){
						if (f!= null){
							if (labels[i] != null){
								if(labels[i].getText().contains(f.getIdentifier())){
									remove(labels[i]);
									labels[i] = null;
								}
							}
						}
					}
				
			}

			@Override
			public void eventLost(Flight f) {
				if(f == highlighted){
					highlighted= null;
				}
				Flight[] aircraft = getAirspace().getAircraft();
				for (int i	= 0; i<aircraft.length; i++){
					Flight f1 = aircraft[i];
					if (f1 == null){
						if(labels[i] != null){
							if(labels[i].getText().contains(f.getIdentifier())){
								remove(labels[i]);
								labels[i] = null;
							}
						}
					}
				}
			}
		});
	}


	//methods
	public boolean processCommand(String command){ //return if command is valid
		System.out.println("Unimplemented method: ATC.processCommand()");	//{!}
		return false;
	}

	public void update(double time){
		String output = null;
		sinceLastCheck += time;
		if (sinceLastCheck >= CHECK_PERIOD){
			checkViolations();
			sinceLastCheck -= CHECK_PERIOD;
		}
		Flight[] aircraft = airspace.getAircraft();
		for (int i=0; i<aircraft.length; i++){
			Flight f1 = aircraft[i];
			if (f1 != null){
				if(labels[i]!= null){
					if(labels[i].getText().contains(f1.getIdentifier())){
						output = "<html>"+f1.getIdentifier() 
								+"<br> Flight Status: " + f1.getStatus().toString()
								+"<br> Next Waypoint : " 
								+f1.getFlightPlan().peek().getName() 
								+"<br> Waypoint distance : " +String.valueOf(Math.round(f1.getWaypointDistance())) + "</html>";
						labels[i].setText(output);
					}else{
						labels[i] = new JLabel(baseBg);
						output = "<html>"+f1.getIdentifier() 
								+"<br> Flight Status: " +f1.getStatus().toString()
								+"<br> Next Waypoint: " +f1.getFlightPlan().peek().getName() 
								+"<br> Waypoint distance : " +String.valueOf(Math.round(f1.getWaypointDistance())) +"</html>";
						labels[i].setVerticalTextPosition(JLabel.CENTER);
						labels[i].setHorizontalTextPosition(JLabel.CENTER);
						labels[i].setText(output);
						add(labels[i]);
					}
				}else{
					labels[i] = new JLabel(baseBg);
					output = "<html>"+f1.getIdentifier() 
							+"<br> Flight Status: " +f1.getStatus().toString()
							+"<br> Next Waypoint: " +f1.getFlightPlan().peek().getName() 
							+"<br> Waypoint distance:" +String.valueOf(Math.round(f1.getWaypointDistance())) +"</html>";
					labels[i].setVerticalTextPosition(JLabel.CENTER);
					labels[i].setHorizontalTextPosition(JLabel.CENTER);
					labels[i].setText(output);
					add(labels[i]);
				}
			}
		}
		repaint();
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
							boolean horizontal = Math.sqrt( Math.pow(f1Pos.x -f2Pos.x, 2) 
									+Math.pow(f1Pos.y -f2Pos.y, 2) ) < 4830;
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


	public final void addListener(ATCListener toAdd) {
		listeners.add(toAdd);
	}


	//overridden methods
	public void paintComponent(Graphics g) {
		super.paintComponent(g);      
	}
}


