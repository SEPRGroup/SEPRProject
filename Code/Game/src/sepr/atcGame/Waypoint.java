package sepr.atcGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


class Waypoint implements Drawable{
	private String name;
	private Position position;
	private Image image = null;
	
	
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
		//method will go in here
		 
		g.drawImage(image,location.x,location.y,null);
		
	}
	
	
	//getters
	public String getName() {
		return name;
	}

	public Position getPosition() {
		return position;
	}

	
}