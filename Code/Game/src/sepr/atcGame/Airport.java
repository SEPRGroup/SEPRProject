package sepr.atcGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static java.lang.Math.PI;

public abstract class Airport extends Airspace {

	private Image background;
	private Image scaleBackground;
	private Dimension boundaries; // size of airspace in metres
	private Flight highlighted;

	private ComponentListener resizeListener = new ComponentAdapter() {
		public void componentResized(ComponentEvent e) {
			scaleBackground = null;
			// {!} needs to lock ratio
		}
	};

	private MouseListener mouseListener = new MouseAdapter(){
		public void mouseClicked(MouseEvent e){
			//clear current highlighted plane
			if (highlighted != null){
				highlighted.highlight(false);}

			Position pos = new Position(e.getX(), e.getY(), -1);
			//convert to internal coordinates
			double scale = (double)boundaries.width / getWidth();
			pos.x *= scale;	pos.y *= scale;

			double minD = Double.MAX_VALUE; 
			Flight minF = null;
			for (Flight f : aircraft){
				if (f != null){
					Position p = f.getPosition();
					double d = Math.sqrt(Math.pow(pos.x -p.x, 2) +Math.pow(pos.y -p.y, 2));
					if (d < minD){
						minD = d;
						minF = f;
					}
				}
			}

			if (minF != null){	//if at least one Flight exists in the airspace
				if (minD < 800){	//if click was close enough to Flight
					highlighted = minF;
					minF.highlight(true);
					eventHighlighted(minF);
				} else {
					highlighted = null;
					eventHighlighted(null);
				}
			} else {
				eventHighlighted(null);
			}
		}
	};

	// constructor
	protected Airport(String airspaceName, Dimension boundaries) {
		super(airspaceName);
		this.boundaries = boundaries;

		this.setOpaque(true);
		setDoubleBuffered(true);
		addComponentListener(resizeListener);
		addMouseListener(mouseListener);

		generateWaypoints();
	}

	// getters/setters
	@Override
	public final void setTransfers(List<TransferWaypoint> transfers) {
		super.setTransfers(transfers);
		double w = boundaries.width / 2.0;
		double h = boundaries.height / 2.0;
		for (TransferWaypoint t : transfers) {
			Position pos = t.getPosition(this); // take mutable copy
			double bearing = t.getBearingFrom(this);
			double a = Math.tan(bearing);
			double x = Math.min(Math.abs(h * a), w);
			double y = Math.min(Math.abs(w / a), h);
			pos.x = w + (bearing < PI ? x : -x); // RHS ? add : sub
			pos.y = h + (Math.abs(bearing - PI) > (PI / 2) ? -y : y); // TOP ? sub : add
			pos.altitude = 3350;	//exit at >11000 feet
			//System.out.println("set transferWaypoint " +t.getName() +" : " +pos.x +"," +pos.y);
		}
	}

	protected final void setBackground(Image background) {
		this.background = background;
		this.scaleBackground = null;
		setPreferredSize(new Dimension(background.getWidth(null),
				background.getHeight(null)));
	}

	
	// methods
	public abstract void generateWaypoints();
	
	public final Point positionToLocation(Position p){
		//converts internal coordinate system to pixel coordinate system
		double scale = (double)getWidth() / boundaries.width;
		return new Point((int)Math.round(p.x*scale), (int)Math.round(p.y*scale));
	}
	
	
	// overridden methods
	@Override
	public final void update(double time) {
		for(Flight f:aircraft){
			if(f != null){
				f.update(time);	
				Waypoint w = f.getFlightPlan().peek();
				//calculate distances
				Position wPos, fPos;
				double distance;
				fPos = f.getPosition();
				if (w instanceof TransferWaypoint){
					wPos = ((TransferWaypoint)w).getPosition(this);
				} else wPos = w.getPosition();
				distance = Math.sqrt(Math.pow(fPos.x-wPos.x ,2) +Math.pow(fPos.y-wPos.y,2)+Math.pow(fPos.altitude-wPos.altitude,2));
				f.setWaypointDistance(distance);
				
				if (w instanceof TransferWaypoint){
					if ( (distance < 1600) && (fPos.y<0 || fPos.x<0 || fPos.x>boundaries.getWidth()|| fPos.y > boundaries.getHeight()) ){
						//removes the aircraft from the airspace if it has finished it's flightplan
						//ensures that the aircraft is outside the airspace before it is removed
						eventHandover(f);
					}
				} else{
					if (distance < 1600){
						f.nextWaypoint();
					}
				}
							
				int offset = 1000;
				//Checks to see if plane is outside the airspace, offset is used to allow spawning of planes slightly outside airspace
				if (fPos.y < -offset || fPos.x < -offset|| fPos.x > boundaries.width+offset|| fPos.y > boundaries.height+offset){
					eventLost(f);
				}
			}
		}
		
		//detect collisions
		for (int i=0; i<aircraft.length; i++){
			Flight f1 = aircraft[i];
			if (f1!= null){
				for (int ii = i+1; ii<aircraft.length; ii++){
					Flight f2 = aircraft[ii];
					if (f2!= null){
						Position f1Pos = f1.getPosition(), f2Pos = f2.getPosition();
						boolean vertical = Math.abs(f1Pos.altitude -f2Pos.altitude) < 50;
						if (vertical){
							boolean horizontal = Math.sqrt(Math.pow(f1Pos.x -f2Pos.x, 2) 
									+ Math.pow(f1Pos.y -f2Pos.y, 2)) < 100;
							if (horizontal){
								eventCrash(f1, f2);
							}
						}
					}
				}
			}
		}
		repaint();
	}

	@Override
	public final void newFlight(Flight f) {
		int i = 0;
		while ((f != null) && (i < MAX_FLIGHTS)) {
			if (aircraft[i] == null) {
				aircraft[i] = f;
				aircraftCount++;
				f = null;
			} else i++;
		}
		if (MAX_FLIGHTS == i) { // was not added; would exceed MAX_FLIGHTS
			eventLost(f);
		}
	}

	@Override
	public final void receiveFlight(Flight f, TransferWaypoint t) {
		f.transition(this, t);

		int i = 0;
		while ((f != null) && (i < MAX_FLIGHTS)) {
			if (aircraft[i] == null) {
				aircraft[i] = f;
				aircraftCount++;
				f = null;
			} else i++;
		}
		if (MAX_FLIGHTS == i) { // was not added; would exceed MAX_FLIGHTS
			eventLost(f);
		}
	}

	@Override
	public final void newObstacle(Flight flight) {
		// TODO Auto-generated method stub
	}

	@Override
	public final void paintComponent(Graphics g) {

		super.paintComponent(g);

		double scale = (double)getWidth() / boundaries.width;
		Point loc = new Point();
		Position pos;

		// draw background
		if (scaleBackground != null) {
			g.drawImage(scaleBackground, 0, 0, null);
		} else if (background != null) {
			scaleBackground = background.getScaledInstance(getWidth(),
					getHeight(), Image.SCALE_SMOOTH);
			g.drawImage(scaleBackground, 0, 0, null);
		} else {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		
		//highlight path
		if (highlighted != null){
			g.setColor(Color.RED);
			Queue<Waypoint> flightPlan = 
					new LinkedList<Waypoint>(highlighted.getFlightPlan());
			int num = flightPlan.size();
			pos = highlighted.getPosition();
			Point lastLoc = new Point(
					(int)Math.round(pos.x *scale), (int)Math.round(pos.y *scale));
			Waypoint w;
			for (int i=0; i<num; i++){
				w = flightPlan.poll();
				if (w instanceof TransferWaypoint)
					pos = ((TransferWaypoint)w).getPosition(this);
				else pos = w.getPosition();
				loc.x = (int) Math.round(pos.x *scale);
				loc.y = (int) Math.round(pos.y *scale);
				g.drawLine(lastLoc.x, lastLoc.y, loc.x, loc.y);
				lastLoc.x = loc.x; lastLoc.y = loc.y;
			}
		}

		// draw Waypoints
		for (Waypoint w : getWaypoints()) {
			if (w != null) {
				pos = w.getPosition();
				loc.x = (int) Math.round(pos.x * scale);
				loc.y = (int) Math.round(pos.y * scale);
				w.draw(g, loc, 1);
			}
		}

		// draw TransferWaypoints
		for (TransferWaypoint t : getTransfers()) {
			if (t != null) {
				pos = t.getPosition(this);
				loc.x = (int) Math.round(pos.x * scale);
				loc.y = (int) Math.round(pos.y * scale);
				t.draw(g, loc, 1);
			}
		}

		// draw Flights
		for (Flight f : getAircraft()) {
			if (f != null) {
				pos = f.getPosition();
				loc.x = (int) Math.round(pos.x * scale);
				loc.y = (int) Math.round(pos.y * scale);
				f.draw(g, loc, 1);
			}
		}
	}
	
	@Override
	public String toString(){
		String s = "Name of airport: " + this.getAirspaceName() + "Boudaries: " + boundaries + ", Highlighted: " + highlighted;
		return s;
	}
}
