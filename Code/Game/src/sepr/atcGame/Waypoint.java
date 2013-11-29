package sepr.atcGame;

import java.awt.Graphics;
import java.awt.Point;


class Waypoint implements Drawable{
	private String name;
	private Position position;
	
	//constructor
	public Waypoint(String name, Position position){
		this.name = name;
		this.position = position;
	}

	
	//methods
	public void draw(Graphics g, Point location, double scale) {
		//method will go in here
	}
	
	
	//getters
	public String getName() {
		return name;
	}

	public Position getPosition() {
		return position;
	}

	
}