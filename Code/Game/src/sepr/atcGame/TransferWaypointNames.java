package sepr.atcGame;

import static java.lang.Math.PI;
public enum TransferWaypointNames {
	TOP ("Top", 0),
	RIGHT ("Right", PI/2),
	BOTTOM ("Bottom", 0),
	LEFT ("Left", PI*3/2),
	TR1 ("TR1", PI/4);
	
	
	private final String name;
	private final double bearing;
	TransferWaypointNames(String name, double bearing)
	{
		this.name = name;
		this.bearing = bearing;
	}
	
	public String getName(){
		return name;
	}
	
	public double getBearing(){
		return bearing;
	}

}
