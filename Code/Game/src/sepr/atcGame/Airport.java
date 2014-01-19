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
import java.util.List;

import static java.lang.Math.PI;

abstract class Airport extends Airspace {

	private Image background;
	private Image scaleBackground;
	private Dimension boundaries; // size of airspace in metres
	private Flight highlighted;

	private ComponentListener resizeListener = new ComponentAdapter() {
		public void componentResized(ComponentEvent arg0) {
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
		setDoubleBuffered(true); // {!} disable if not redrawing entire screen
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
			// System.out.println("set transferWaypoint " +t.getName() +" : " +pos.x +"," +pos.y); //{!}
		}
	}

	protected final void setBackground(Image background) {
		this.background = background;
		this.scaleBackground = null;
		setPreferredSize(new Dimension(background.getWidth(null),
				background.getHeight(null)));
	}

	
	// methods
	protected abstract void generateWaypoints();
	
	public final Point positionToLocation(Position p){
		//converts internal coordinate system to pixel coordinate system
		double scale = (double)getWidth() / boundaries.width;
		return new Point((int)Math.round(p.x*scale), (int)Math.round(p.y*scale));
	}
	
	
	// overridden methods
	@Override
	public final void update(double time) {
		Dimension bounds = this.boundaries;
		for(Flight f:getAircraft()){
			if(f != null){
				f.update(time);	
				Waypoint w  = f.flightPlan.peek();
				if (w != null){
					f.waypointDistance = Math.sqrt(Math.pow(f.getPosition().x-w.getPosition().x ,2) +Math.pow(f.getPosition().y-w.getPosition().y,2)+Math.pow(f.getPosition().altitude-w.getPosition().altitude,2));
					if(f.waypointDistance < 1600){
						f.nextWaypoint();
					}
					int offset = 1000;
					//Checks to see if plane is outside the airspace, offset is used to allow spawning of planes slightly outside airspace
					if(f.getPosition().y < 0- offset||f.getPosition().x <0 - offset|| f.getPosition().x > bounds.getWidth() + offset|| f.getPosition().y > bounds.getHeight() + offset){
						eventLost(f);
					}
				}else{
					if(f.getPosition().y < 0||f.getPosition().x <0|| f.getPosition().x > bounds.getWidth()|| f.getPosition().y > bounds.getHeight()){
						//removes the aircraft from the airspace if it has finished it's flightplan
						//ensures that the aircraft is outside the airspace before it is removed
						eventHandover(f);
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
				f = null;
			} else
				i++;
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
				f = null;
			} else
				i++;
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

}
