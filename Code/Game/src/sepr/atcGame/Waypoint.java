package sepr.atcGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;


public class Waypoint implements Drawable{
	private String name;
	private Position position;
	protected Image image = null;
	
	protected static Font dataFont = new Font(Font.MONOSPACED, Font.PLAIN, 12);
	
	
	//constructor
	public Waypoint(String name, Position position){
		this.name = name;
		this.position = position;
		try{
			image = ImageIO.read(getClass().getResourceAsStream("/sepr/atcGame/Images/Waypoint.png"));
		}catch (IOException e){System.out.println(e.toString());};
	}

	
	//methods
	public void draw(Graphics g, Point location, double scale) {	 
		//precalculate useful positioning values 
		int w=image.getWidth(null)/2, h=image.getHeight(null)/2;
		//draw image
		g.drawImage(image, location.x -w, location.y -h, null);
		
		{	//label attributes
			String idString = (getName());
			String dataString = String.format("\u21A5%1$4d", 
					Math.round(position.altitude));
			g.setFont(dataFont);
			g.setColor(Color.BLACK);
			g.drawString(idString, location.x -w/2, location.y +h +dataFont.getSize() -3);	
			g.drawString(dataString, location.x -w/2, location.y +h +dataFont.getSize()*2 -3);
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