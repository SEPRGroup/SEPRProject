package sepr.atcGame;

import java.awt.Graphics;
import java.awt.Point;

import static java.lang.Math.PI;


public class TransferWaypoint extends Waypoint {
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
		super(name, new Position(-1,-1,-1));
		position2 = new Position(-1,-1,-1);
		this.airspace1 = airspace1;
		this.airspace2 = airspace2;
		this.bearing = bearing;
	}
	
	
	@Override
	public void draw(Graphics g, Point location, double scale) {	 
		//precalculate useful positioning values 
		int w=image.getWidth(null)/2, h=image.getHeight(null)/2;
		//draw image
		g.drawImage(image, location.x -w, location.y -h, null);
		
		{	//label attributes
			String idString = (getName());
			String dataString = String.format("\u21A5%1$4d", 
					Math.round(this.getPosition().altitude));
			
			Boolean AtoB;	//if the position is consistent with being AtoB for this airspace
			if ((0 < bearing) && (PI/2 > bearing)){
				AtoB = (location.x != 0); 
			}else if ((PI/2 <= bearing) && (PI >= bearing)){
				AtoB = (location.x != 0) && (location.y != 0); 
			}else if ((PI < bearing) && (3*PI/2 > bearing)){
				AtoB = location.y != 0; 
			}else {
				AtoB = (location.x == 0) || (location.y == 0); 
			}
			double dir;	//the direction to draw the text in
			if (AtoB) {
				dir = bearing<Math.PI ? bearing+Math.PI : bearing-Math.PI;	//draw in opposite direction to bearing
			} else {
				dir = bearing;
			}
			
			g.setFont(dataFont);
			int oX, oY, //x and y offsets of centre of text from location
				eX = 20, eY = dataFont.getSize();	//expected half-width, half-height of text
			oX = (int)(Math.round(Math.sin(dir)*(w+eX +2)));
			oY = (int)(Math.round(-Math.cos(dir)*(h+eY +2))) -3;
			g.drawString(idString, location.x +oX -eX, location.y +oY);
			g.drawString(dataString, location.x +oX -eX, location.y +oY +eY);
	
			
			/*	[for square icons]
			//test on which edge of the screen the waypoint lies 
			if (location.x == 0){//Waypoint is on left of screen so print to the right
				g.drawString(idString, location.x +w/2 -10 , location.y +h +7);	
				g.drawString(dataString, location.x +w/2 -10, location.y +h +18);
			}else if (location.y == 0){//Waypoint is at top of screen so print to the bottom
				g.drawString(idString, location.x -w/2 -15 , location.y +h +7);	
				g.drawString(dataString, location.x -w, location.y +h +18);
			}else if (location.x == airspace1.getBounds().getWidth()){//waypoint is on right side of screen so print to the left
				g.drawString(idString, location.x -w/2 - 52, location.y);	
				g.drawString(dataString, location.x -w/2 - 52, location.y +10 );
			}else if (location.y == airspace1.getBounds().getHeight()|| location.y > airspace1.getBounds().getHeight()-20){//waypoint is on the bottom side of screen so print to the top
				g.drawString(idString, location.x -w/2 - 15 , location.y -h -18);	
				g.drawString(dataString, location.x -w, location.y -h -7);
			}*/
		}
	}
	
	
	//getters and setters
	public Position getPosition(Airspace airspace) {
		if(airspace.equals(airspace1)){
			return this.getPosition();}
		else if(airspace.equals(airspace2)){
			return this.position2;}
		else {
			System.out.println("[ERROR TransferWaypoint.getPosition(" +airspace.getAirspaceName() +")] \t airspace is not is not registered with/t" +this.getName());	//{!}
			return null;			
		}
	}

	public void setPosition(Airspace airspace, Position position) {
		if(airspace.equals(airspace1)){
			setPosition(position);}
		else if(airspace.equals(airspace2)){
			setPosition2(position);}
		else System.out.println("[ERROR TransferWaypoint.setPosition(" +airspace.getAirspaceName() +")] \t airspace is not is not registered with/t" +this.getName());	//{!}
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
		else {
			System.out.println("[ERROR TransferWaypoint.getBearingFrom(" +airspace.getAirspaceName() +")] \t airspace is not is not registered with/t" +this.getName());	//{!}
			return -1;
		}
	}
	
	public double getBearingTo(Airspace airspace) {
		if(airspace.equals(airspace1)){
			return bearing<Math.PI ? bearing+Math.PI : bearing-Math.PI;}
		else if(airspace.equals(airspace2)){
			return bearing;}
		else {
			System.out.println("[ERROR TransferWaypoint.getBearingTo(" +airspace.getAirspaceName() +")] \t airspace is not is not registered with/t" +this.getName());	//{!}
			return -1;
		}
	}
}