package sepr.atcGame;

import java.awt.Graphics;
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
		g.drawImage(image,
				location.x -(image.getWidth(null)/2),
				location.y -(image.getHeight(null)/2),
				null);
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
		
}