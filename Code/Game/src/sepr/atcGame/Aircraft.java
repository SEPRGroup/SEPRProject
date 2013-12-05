package sepr.atcGame;

import java.util.Queue;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;
	
	abstract class Aircraft extends Flight {
		
		public class LoadImage extends Component {
	       
			private static final long serialVersionUID = 1L;//?????????????
			BufferedImage img;
		 
		    public void paint(Graphics g) {
		        g.drawImage(img, 0, 0, null);
		    }
		 
		    public LoadImage() {
		       try {
		           img = ImageIO.read(new File("plane.png"));
		       } catch (IOException e) {
		       }
		 
		    }
		 
	
		 public class MoveImage extends JFrame implements ActionListener {
			 
			 public int x;
			 public int y;
			@Override
			 // will be called every 50 milisecond
			public void actionPerformed(ActionEvent e) {
				// update x,y coordinates
		        x += 10;
		        y += 10;
		        // ask GUI to repaint itself
		        repaint();
		    }
				
			}
		}
//		       Timer timer = new Timer(50, this);
//		       timer.start();
//		    }
//		    
		    public void paint(Graphics g) {
		       super.paint(g);   // to redraw the background
		       int image = 0;
			Image x = null;
			int y = 0;
			g.drawImage(x, y, image, null);
		    }

		    
		private void repaint() {
		// TODO Auto-generated method stub
				}
		
		public void display () {
			 
	        JFrame f = new JFrame("Load Plane Image ");
	             
	        f.addWindowListener(new WindowAdapter(){
	                public void windowClosing(WindowEvent e) {
	                    System.exit(0);
	                }
	            });
	        f.add(new LoadImage());
	        f.pack();
	        f.setVisible(true);
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