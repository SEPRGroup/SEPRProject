package sepr.atcGame;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;


class TransferWaypoint extends Waypoint {
	//Variables
	private Position position2;
	private Airspace airspace1, airspace2; 
	private double bearing;	//radians, 0 - 2*Pi; from airspace1->airspace2
	
	//constructor
	/** Creates an waypoint that provides the linking between airspaces.
	 * 
	 * @param name :	name of waypoint
	 * @param airspace1
	 * @param airspace2
	 * @param bearing	: bearing from airspace1 to airspace2
	 */
	public TransferWaypoint(String name, Airspace airspace1, Airspace airspace2,
			double bearing){
		super(name, new Position(-1,-1,-1));	//{!}	
		position2 = new Position(-1,-1,-1);		//{!}
		this.airspace1 = airspace1;
		this.airspace2 = airspace2;
		this.bearing = bearing;
	}
	
	public void draw(Graphics g, Point location, double scale) {	 
		//precalculate useful positioning values 
		int w=image.getWidth(null), h=image.getHeight(null)/2;
		g.drawImage(image, location.x - w, location.y - h, null);
		
		{	//label attributes
			Font dataFont = new Font(Font.MONOSPACED, Font.PLAIN, 12);
			String idString = (getName());
			String dataString = String.format("\u21A5%1$4d", 
					Math.round(this.getPosition().altitude));
			//unused bearing code:	 \u21BB%3$03d	, round(Math.toDegrees(bearing))
			g.setFont(dataFont);
			//test on which edge of the screen the waypoint lies, nested to prevent labels being drawn twice in cases where the waypoint lies on corners or near corners
			if(location.x == 0){//Waypoint is on left of screen so print to the right
				g.drawString(idString, location.x +w/2 - 10 , location.y +h +7);	
				g.drawString(dataString, location.x +w/2 -10, location.y +h +18);
			}else{
			
				if(location.y == 0){//Waypoint is at top of screen so print to the bottom
					g.drawString(idString, location.x -w/2 - 15 , location.y +h +7);	
					g.drawString(dataString, location.x -w, location.y +h +18);
				}else{
			
					if(location.x == airspace1.getBounds().getWidth()){//waypoint is on right side of screen so print to the left
						g.drawString(idString, location.x -w/2 - 52, location.y);	
						g.drawString(dataString, location.x -w/2 - 52, location.y +10 );
					}else{
						if(location.y == airspace1.getBounds().getHeight()|| location.y > airspace1.getBounds().getHeight()-20){//waypoint is on the bottom side of screen so print to the top
							g.drawString(idString, location.x -w/2 - 15 , location.y -h -18);	
							g.drawString(dataString, location.x -w, location.y -h -7);
						}
					}
				}
			}
				
				//g.drawString(idString, location.x -w/2 - 15 , location.y +h +7);	
				//g.drawString(dataString, location.x -w, location.y +h +18);
			
		
		}
	}
	//getters and setters
	public Position getPosition(Airspace airspace) {
		if(airspace.equals(airspace1)){
			return this.getPosition();}
		else if(airspace.equals(airspace2)){
			return this.position2;}
		else return null;			
	}

	public void setPosition(Airspace airspace, Position position) {
		if(airspace.equals(airspace1)){
			setPosition(position);}
		else if(airspace.equals(airspace2)){
			setPosition2(position);}
		//else {!}
	}
		
	public Position getPosition2() {
		return position2;
	}
	
	public void setPosition2(Position position) {
		this.position2 = position;
	}

	public Airspace getAirspace1() {
		return airspace1;
	}

	public Airspace getAirspace2() {
		return airspace2;
	}

	public double getBearingFrom(Airspace airspace) {
		if(airspace.equals(airspace1)){
			return bearing;}
		else if(airspace.equals(airspace2)){
			return bearing<Math.PI ? bearing+Math.PI : bearing-Math.PI;}
		else return 0; //{!}
	}
	
	public double getBearingTo(Airspace airspace) {
		if(airspace.equals(airspace1)){
			return bearing<Math.PI ? bearing+Math.PI : bearing-Math.PI;}
		else if(airspace.equals(airspace2)){
			return bearing;}
		else return 0; //{!}
	}
}