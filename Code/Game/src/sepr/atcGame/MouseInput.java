package sepr.atcGame;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import sepr.atcGame.events.RadialMenuListener;

public final class MouseInput extends Input implements GameTime{
	private Airport airport;
	private Flight highlighted = null;
	
	private RadialMenu main, bearing, speed, altitude;
	
	public MouseInput(Airport a) {
		super();
		this.airport = a;
		
		setOpaque(false);
		setVisible(false);
		setLayout(null);
		setBounds(airport.getBounds());
		
		initMain();
		initBearing();
		initSpeed();
		initAltitude();

		airport.addListener(this);
		airport.addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent e) {
				setBounds(airport.getBounds());
			}
		});
	}
	
	
	private void initMain(){
		main = new RadialMenu();
		main.setFont(main.getFont().deriveFont(Font.PLAIN));
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
					highlighted.abort();
					break;
				};
			}
		});
		add(main);
	}
	
	private void initBearing(){
		final int interval = 4;	//even 1 will work, but you'll start to see input lag
		bearing = new RadialMenu();
		main.setFont(main.getFont().deriveFont(Font.PLAIN, 10));
		bearing.setSpacing(0);
		bearing.setOffset(-2);
		bearing.setStyle(RadialMenu.DONUT);
		bearing.setOutline(false);
		bearing.setButtonSize(25);
		for (int i=0; i<360; i+=interval){
			bearing.addButton("", null);
		}
		bearing.addListener(new RadialMenuListener(){
			public void eventButtonClicked(int button) {
				highlighted.turnTo(Math.toRadians(button *= interval));
				bearing.setVisible(false);
				tryLocation(main, 
						airport.positionToLocation(highlighted.getPosition()));
			}
		});
		add(bearing);
	}
	
	private void initSpeed(){
		final int min = 0, max = 300, interval = 10;
		speed = new RadialMenu();
		speed.setSpacing(1);
		speed.setOffset(0);
		speed.setStyle(RadialMenu.DONUT);
		bearing.setButtonSize(30);
		for (int i=min; i<=max; i+=interval){
			String s = (i%50 == 0) ? String.valueOf(i) : "";
			speed.addButton(s, null);
		}
		speed.addSpacer();
		
		speed.addListener(new RadialMenuListener(){
			public void eventButtonClicked(int button) {
				highlighted.toSpeed(min +button*interval);
				speed.setVisible(false);
				tryLocation(main,
						airport.positionToLocation(highlighted.getPosition()));
			}
		});
		add(speed);
	}
	
	private void initAltitude(){
		final int min = 100, max = 4000, interval = 100;
		altitude = new RadialMenu();
		altitude.setSpacing(1);
		altitude.setOffset(0);
		altitude.addSpacer();
		for (int i=min; i<=max; i+=interval){
			String s = (i%500 == 0) ? ' '+String.valueOf(i) : "";
			altitude.addButton(s, null);
		}
		
		altitude.addListener(new RadialMenuListener(){
			public void eventButtonClicked(int button) {
				highlighted.toAltitude(min +button*interval);
				altitude.setVisible(false);
				tryLocation(main,
						airport.positionToLocation(highlighted.getPosition()));
			}
		});
		add(altitude);
	}
	
	
	private void tryLocation(RadialMenu m, Point p){
		//places m as close to p as possible, within constraints of the airport
		int	r = m.getRadius(),
				minX = p.x -r, maxX = p.x +r,
				minY = p.y -r, maxY = p.y +r;
			
		if (minX < 0){
			p.x = r;
		} else if (maxX > getWidth()){
			p.x = getWidth() -r;
		}
		if (minY < 0){
			p.y = r;
		} else if (maxY > getHeight()){
			p.y = getHeight() -r;
		}

		m.setLocation(p);
		m.setVisible(true);
	}
	

	@Override
	public void eventCrash(Flight f1, Flight f2) {
		if (highlighted == f1 || highlighted == f2){
			highlighted = null;
			setVisible(false);
		}
	}

	@Override
	public void eventLanded(Flight f) {
		if (highlighted == f){
			highlighted = null;
			setVisible(false);
		}
	}

	@Override
	public void eventHandover(Flight f) {
		if (highlighted == f){
			highlighted = null;
			setVisible(false);
		}
	}

	@Override
	public void eventLost(Flight f) {
		if (highlighted == f){
			highlighted = null;
			setVisible(false);
		}
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
	public void update(double time) {
	//Checks which menu is highlighted if any then gets the menu to follow the plane
		if (highlighted != null){
			if(main.isVisible()){
				tryLocation(main,
						airport.positionToLocation(highlighted.getPosition()));
			}else if(bearing.isVisible()){
				tryLocation(bearing,
						airport.positionToLocation(highlighted.getPosition()));
			}else if(speed.isVisible()){
				tryLocation(speed,
						airport.positionToLocation(highlighted.getPosition()));
			}else if(altitude.isVisible()){
				tryLocation(altitude,
						airport.positionToLocation(highlighted.getPosition()));
			}
		}
		
		
	}
	
}
