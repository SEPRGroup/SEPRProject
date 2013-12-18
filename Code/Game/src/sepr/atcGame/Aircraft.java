package sepr.atcGame;

import java.util.Queue;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
	

	abstract class Aircraft extends Flight {

		private Image image;

		public void draw(Graphics g, Point location, double scale) {
			g.drawImage(image,
					location.x -(image.getWidth(null)/2),
					location.y -(image.getHeight(null)/2),
					null);
		}
		
		
	//constructor
	protected Aircraft(String id, Queue<Waypoint> flightPlan) {
		super(id, flightPlan);
		try{
			image = ImageIO.read(new File("src/sepr/atcGame/Images/plane.jpg"));
		}catch (IOException e){};
	}
	
	public final void originalPosition(Position position){
		this.position = position;
	}
	
	//overridden methods
	@Override
	public final void update(double time) {
		position.x += time*250;	//move at a speed of 250 m/s {!} independent of bearing
	}

	@Override
	public final void takeOff(TransferWaypoint t) {
		// TODO
	}
	
	@Override
	public final void land(TransferWaypoint t) {
		// TODO
	}

	@Override
	public final void turnTo(double bearing) {
		// TODO
	}

	@Override
	public final void toAltitude(double altitude) {
		// TODO
	}

	@Override
	public final void toSpeed(double speed) {
		// TODO
	}

	@Override
	public final void abort() {
		// TODO
	}

	@Override
	public final void crash() {
		// TODO
	}

}