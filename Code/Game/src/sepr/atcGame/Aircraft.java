package sepr.atcGame;
import java.util.Queue;
import java.awt.*;

	
	abstract class Aircraft extends Flight {
	

		public static Image plane;
		plane = new Image ("plane.png");
		
				
				
//		//{!} flight data
		public int xPixel= 0;
		public int yPixel=0 ;
		public void draw(Graphics g){
			 plane.draw(xPixel, yPixel);
		}

	
		public void plane(){
			try{
				plane = Toolkit.getDefaultToolkit().getImage("plane.png");
			}
			catch(Exception e){}
			setSize(800,640);
			setVisible(true);
			moveImage();
		}
	
	private void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
		}
	
	
		private void setSize(int i, int j) {
			// TODO Auto-generated method stub			
		}
	
		public void paint(Graphics g){
			boolean b = g.drawImage(plane, xPixel, yPixel, this);
		}
		
	
		private void moveImage() {
			xPixel += 1;
			yPixel +=1;
			repaint();
			// TODO Auto-generated method stub
			
		}
		
	
		private void repaint() {
			// TODO Auto-generated method stub
			
		}
	

	//constructor
	protected Aircraft(String id, Queue<Waypoint> flightPlan) {
		super(id, flightPlan);
		// TODO Auto-generated constructor stub
	}

	
	//overridden methods
	@Override
	public final void update(double time) {
		// TODO realistic movement
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
