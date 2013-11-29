package sepr.atcGame;

import javax.swing.JFrame;

public class Game extends JFrame{
	
	private Output output;
	
	//constructor
	public Game(int height, int width, GameDifficulty difficulty) {
		setSize(height, width);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("ATC Game ¦ GAME");
		setResizable(false);	//may change
	}
	
	
	//getters/setters
	public void setOutput(Output output) {
		this.output = output;
	}

}
