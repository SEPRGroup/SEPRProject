package sepr.atcGame;

import static java.lang.Math.round;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Waypoint implements Drawable{
	private String name;
	private Position position;
	protected Image image = null;
	
	
	//constructor
	public Waypoint(String name, Position position){
		this.name = name;
		this.position = position;
		try{
			image = ImageIO.read(new File("src/sepr/atcGame/Images/Waypoint.png"));
		}catch (IOException e){};
	}

	
	//methods
	public void draw(Graphics g, Point location, double scale) {	 
		//precalculate useful positioning values 
		int w=image.getWidth(null), h=image.getHeight(null)/2;
		g.drawImage(image, location.x - w, location.y - h, null);
		
		{	//label attributes
			Font dataFont = new Font(Font.MONOSPACED, Font.PLAIN, 12);
			String idString = (getName());
			String dataString = String.format("\u21A5%1$4d", 
					Math.round(position.altitude));
			//unused bearing code:	 \u21BB%3$03d	, round(Math.toDegrees(bearing))
			g.setFont(dataFont);
			if(this instanceof TransferWaypoint){
				
			}else{
				g.drawString(idString, location.x -w/2 - 15 , location.y +h +7);	
				g.drawString(dataString, location.x -w, location.y +h +18);
			}
		
		}
	}
	
	
	//getters/setters
	public String getName() {
		return name;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
	
	@Override
	public String toString() {
		String s = this.name + " | " + position.toString(); 
		return s;
	}
		
}