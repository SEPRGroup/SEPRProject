package sepr.atcGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

import static java.lang.Math.PI;

abstract class Airport extends Airspace{

	private Image background;
	private Image scaleBackground;
	private Dimension boundaries;	//size of airspace in metres


	//constructor
	protected Airport(String airspaceName,
			Dimension boundaries) {
		super(airspaceName);
		this.boundaries = boundaries;

		setDoubleBuffered(true);
		generateWaypoints();
	}


	//getters/setters
	@Override
	public final void setTransfers(List<TransferWaypoint> transfers){
		super.setTransfers(transfers);
		double w = boundaries.width/2.0;
		double h = boundaries.height/2.0;
		for(TransferWaypoint t:transfers){
			Position pos = t.getPosition(this);
			double bearing = t.getBearingFrom(this);
			double a = Math.tan(bearing);
			double x = Math.min(Math.abs(h*a), w);
			double y = Math.min(Math.abs(w/a), h);
			pos.x = w +( bearing<PI ? x : -x );	//RHS ? add : sub
			pos.y = h +( Math.abs(bearing-PI)>(PI/2) ? -y : y );	//TOP ? sub : add
			
			//System.out.println("set transferWaypoint " +t.getName() +" : " +pos.x +"," +pos.y);	//{!}
		}
	}

	protected final void setBackground(Image background) {
		this.background = background;
		this.scaleBackground = null;
		setPreferredSize(new Dimension(background.getWidth(null), background.getHeight(null)));
	}

	public final int getWidth(){
		return boundaries.width;
	}

	public final int getHeight(){
		return boundaries.height;
	}
	//methods
	protected abstract void generateWaypoints();


	//overridden methods
	@Override
	public final void update(double time) {
		for(Flight f:getAircraft()){
			if(f != null){
				f.update(time);
			}
		}
		repaint();
	}

	@Override
	public final void newFlight(Flight f) {
		// TODO Auto-generated method stub
	}

	@Override
	public final void receiveFlight(Flight f, TransferWaypoint t) {
		f.setPosition(new Position(t.getPosition(this)));
		f.setBearing(t.getBearingTo(this));

		int i = 0;
		while ((f != null) && (i < MAX_FLIGHTS)){
			if (aircraft[i] == null){
				aircraft[i] = f;
				f = null;}
			else i++;
		}
	}

	@Override
	public final void newObstacle(Flight flight) {
		// TODO Auto-generated method stub
	}

	@Override
	public final void paintComponent(Graphics g) {
		super.paintComponent(g);

		Rectangle bounds = getBounds();
		double scale = bounds.getWidth() / boundaries.getWidth();
		Point loc = new Point();
		Position pos;

		//draw background
		if (scaleBackground != null){
			if(bounds.width != scaleBackground.getWidth(null)){
				scaleBackground = background.getScaledInstance(bounds.width, bounds.height, Image.SCALE_SMOOTH);}			
			g.drawImage(scaleBackground, 0, 0, null);}
		else if(background != null){
			scaleBackground = background.getScaledInstance(bounds.width, bounds.height, Image.SCALE_SMOOTH);
			g.drawImage(scaleBackground, 0, 0, null);}	
		else{
			g.setColor(Color.BLACK);
			g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height); 
		}

		//draw Waypoints
		for(Waypoint w:getWaypoints()){
			if(w != null){
				pos = w.getPosition();
				loc.x = (int)Math.round(pos.x *scale);
				loc.y = (int)Math.round(pos.y *scale);
				w.draw(g, loc, 1);   		   
			}
		}

		//draw TransferWaypoints
		for(TransferWaypoint t:getTransfers()){	   
			if(t != null){
				pos = t.getPosition(this);
				loc.x = (int)Math.round(pos.x *scale);
				loc.y = (int)Math.round(pos.y *scale);
				t.draw(g, loc, 1);
			}
		}

		//draw Flights
		for(Flight f:getAircraft()){
			if(f != null){
				pos = f.getPosition();
				loc.x = (int)Math.round(pos.x *scale);
				loc.y = (int)Math.round(pos.y *scale);
				f.draw(g, loc, 1);
			}
		}
	}

}
