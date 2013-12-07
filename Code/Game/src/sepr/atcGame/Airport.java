package sepr.atcGame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.PrintStream;
import java.util.List;


abstract class Airport extends Airspace{

	private Image background;
	private Dimension boundaries;	//size of airspace in metres

	
	//constructor
	protected Airport(String airspaceName,
			Dimension boundaries) {
		super(airspaceName);
		this.boundaries = boundaries;
		
		setDoubleBuffered(true);
		setPreferredSize(new Dimension(boundaries.width/10, boundaries.height/10));
		
		generateWaypoints();
		this.repaint();
	}

	
	//getters/setters
	@Override
	public final void setTransfers(List<TransferWaypoint> transfers){
		super.setTransfers(transfers);
		double w = boundaries.width/2;
		double h = boundaries.height/2;
		for(TransferWaypoint t:transfers){
			Position pos = t.getPosition(this);
			double bearing = t.getBearing(this);
			double a = Math.tan(bearing);
			double x = Math.min(Math.abs(h*a), w);
			double y = Math.min(Math.abs(w/a), h);
			pos.x = w +( bearing<Math.PI ? x : -x );	//RHS ? add : sub
			pos.y = h +( Math.abs(bearing-Math.PI)>(Math.PI/2) ? -y : y );	//TOP ? sub : add 
			pos.altitude = 1000; //{!}
			System.out.println("set transferWaypoint " +t.getName() +" : " +pos.x +"," +pos.y);	//{!}
		}
	}
	
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
	       
	       //draw TransferWaypoints
	       for(TransferWaypoint t:this.getTransfers()){
	    	   
	    	   if(t != null){
	    		   pos = t.getPosition();
	    		   loc.x = Math.round( (float)(pos.x *scale) );
	    		   loc.y = Math.round( (float)(pos.y *scale) );
	    		   t.draw(g, loc, 1);
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
