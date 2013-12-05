package sepr.atcGame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;


abstract class Airport extends Airspace{

	private Image background;
	private Dimension boundaries;	//size of airspace in metres

	
	//constructor
	protected Airport(String airspaceName,
			Dimension boundaries,
			ArrayList<TransferWaypoint> transferWaypoints) {
		super(airspaceName, transferWaypoints);
		this.boundaries = boundaries;
		
		generateWaypoints();
		setDoubleBuffered(true);
	}

	
	//getters/setters
	protected final void setBackground(Image background) {
		this.background = background;
	}


	//methods
	protected abstract void generateWaypoints();
	
	
	//overridden methods
	@Override
	public final void update(double time) {
		// TODO Auto-generated method stub
	}

	@Override
	public final void newFlight(Flight f) {
		// TODO Auto-generated method stub
	}

	@Override
	public final void receiveFlight(Flight f, TransferWaypoint t) {
		// TODO Auto-generated method stub
	}

	@Override
	public final void newObstacle(Flight flight) {
		// TODO Auto-generated method stub
	}
	
	public final void paintComponent(Graphics g) {
	       super.paintComponent(g);
	       
	       Rectangle bounds = getBounds();
	       double scale = bounds.getWidth() / boundaries.getWidth();
	       Point loc = new Point();
	       Position pos;
	       
	       //draw background
	       if(background != null){
	    	   g.drawImage(background, 0, 0, null);
	       }else{
		       g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height); 
	       } 
	       
	       //draw Waypoints
	       for(Waypoint w:getWaypoints()){
	    	   if(w != null){
	    		   pos = w.getPosition();
	    		   loc.x = Math.round( (float)(pos.x *scale) );
	    		   loc.y = Math.round( (float)(pos.y *scale) );
	    		   w.draw(g, loc, 1);
	    	   }
	       }
	       
	       //draw Flights
	       for(Flight f:getAircraft()){
	    	   if(f != null){
	    		   pos = f.getPosition();
	    		   loc.x = Math.round( (float)(pos.x *scale) );
	    		   loc.y = Math.round( (float)(pos.y *scale) );
	    		   f.draw(g, loc, 1);
	    	   }
	       }
	}  

}
