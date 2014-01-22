package sepr.atcGame;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public /*private*/ class Scheduler extends JPanel implements GameTime{ //MH: Changed to public class to implement testing.
	public /*private*/ List<ScheduleItem> schedule = new ArrayList<ScheduleItem>(5);
	public /*private*/ Airspace airspace;
	
	//constructor
	public Scheduler(Airspace airspace) {
		this.airspace = airspace;
	}


	//methods
	public boolean add(ScheduleItem scheduleItem){
		return schedule.add(scheduleItem);
	}
	
	public void update(double time) {
		//method will go in here
	}
	
	
	//overridden methods
	public void paintComponent(Graphics g) {
		super.paintComponent(g);       
	    //draw using [g]: flights waiting and times
	}

	
}
