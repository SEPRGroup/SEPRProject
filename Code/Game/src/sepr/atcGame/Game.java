package sepr.atcGame;

import java.util.ArrayList;

import javax.swing.JFrame;

public class Game extends JFrame{

	
	private Output output;
	private ArrayList<TransferWaypoint> transferWaypoints = new ArrayList<TransferWaypoint>();
	
	
	//constructor
	public Game(int height, int width, GameDifficulty difficulty) {
		setSize(height, width);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("ATC Game ¦ GAME");
		setResizable(false);	//may change
		setVisible(true);
		setLocationRelativeTo(null);
		add(new Heathrow(transferWaypoints));
	}
	
	
	//getters/setters
	public void setOutput(Output output) {
		this.output = output;
	}

}
