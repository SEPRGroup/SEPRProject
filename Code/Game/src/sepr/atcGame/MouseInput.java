package sepr.atcGame;

import java.awt.Graphics;
import java.awt.Point;

import sepr.atcGame.events.RadialMenuListener;

public final class MouseInput extends Input {
	private Airport airport;
	private Flight highlighted = null;
	
	private RadialMenu main, bearing, speed, altitude;
	
	public MouseInput(Airport airport) {
		super();
		this.airport = airport;
		
		setOpaque(false);
		setLayout(null);

		this.setBounds(airport.getBounds());
		
		initMain();
		initBearing();
		initSpeed();
		initAltitude();

		airport.addListener(this);
		this.setVisible(false);
	}
	
	
	private void initMain(){
		main = new RadialMenu();
		main.addButton("Bearing", null);
		main.addButton("Altitude", null);
		main.addButton("Speed", null);
		main.addButton("Abort", null);
		main.addListener(new RadialMenuListener(){
			public void eventButtonClicked(int button) {
				switch (button){
				case 0:	//bearing
					main.setVisible(false);
					tryLocation(bearing,
							airport.positionToLocation(highlighted.getPosition()));
					break;
				case 1:	//altitude
					main.setVisible(false);
					tryLocation(altitude,
							airport.positionToLocation(highlighted.getPosition()));
					break;
				case 2: //speed
					main.setVisible(false);
					tryLocation(speed,
							airport.positionToLocation(highlighted.getPosition()));
					break;
				case 3:	//abort
					setVisible(false);
					highlighted.abort();
					highlighted = null;
					break;
				};
			}
		});
		add(main);
	}
	
	private void initBearing(){
		bearing = new RadialMenu();
		bearing.setSpacing(0);
		bearing.setOffset(-2);
		for (int i=0; i<360; i+=4){
			bearing.addButton("", null);
		}
		bearing.addListener(new RadialMenuListener(){
			public void eventButtonClicked(int button) {
				setVisible(false);
				highlighted.turnTo(button *= 4);
				highlighted = null;
			}
		});
		add(bearing);
	}
	
	private void initSpeed(){
		final int[] speeds = {100, 150, 200, 250, 300, 350, 400};
		speed = new RadialMenu();
		for (int i=0; i<speeds.length; i++){
			speed.addButton(String.valueOf(speeds[i]), null);
		}
		speed.addListener(new RadialMenuListener(){
			public void eventButtonClicked(int button) {
				setVisible(false);
				highlighted.toSpeed(speeds[button]);
				highlighted = null;
			}
		});
		add(speed);
	}
	
	private void initAltitude(){
		final int[] altitudes = {100, 150, 200, 250, 300, 350, 400};
		altitude = new RadialMenu();
		for (int i=0; i<altitudes.length; i++){
			altitude.addButton(String.valueOf(altitudes[i]), null);
		}
		altitude.addListener(new RadialMenuListener(){
			public void eventButtonClicked(int button) {
				setVisible(false);
				highlighted.toAltitude(altitudes[button]);
				highlighted = null;
			}
		});
		add(altitude);
	}
	
	
	private void tryLocation(RadialMenu m, Point p){
		//places m as close to p as possible, within constraints of the airport
		//{!} in theory... incomplete
		m.setVisible(true);
		m.setLocation(p);
	}
	

	@Override
	public void eventCrash(Flight f1, Flight f2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eventLanded(Flight f) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eventHandover(Flight f) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eventLost(Flight f) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eventHighlighted(Flight f) {
		highlighted = f;
		//hide any existing menus
		main.setVisible(false);
		bearing.setVisible(false);
		speed.setVisible(false);
		altitude.setVisible(false);
		
		if (f != null){
			tryLocation(main,
					airport.positionToLocation(f.getPosition()));
			setVisible(true);
		} else setVisible(false);
	}

	@Override
	public void removeFlight(Flight f) {
		// TODO Auto-generated method stub	
	}
	
}
