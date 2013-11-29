package sepr.atcGame;

import java.awt.Graphics;
import javax.swing.JPanel;

class GameOutput extends JPanel implements Output {

	public GameOutput() {
		
	}

	@Override
	public void showMessage(String Message) {
		//method will go in here
	}
	
	
	//overridden methods
	public void paintComponent(Graphics g) {
		super.paintComponent(g);       
		//draw using [g]: flights waiting and times
	}

}
