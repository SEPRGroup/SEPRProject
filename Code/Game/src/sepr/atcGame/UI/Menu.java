package sepr.atcGame.UI;
import java.awt.*;

import java.awt.event.*;

import javax.swing.*;
public class Menu extends JFrame {
	

	public int HEIGHT,WIDTH;
	public boolean initialised = false;
	public static Color bgColor = Color.YELLOW;
	private static int labelCount = 9;
	private JLabel[] menuChoices = new JLabel[labelCount];
	
	public Menu(int width,int height)
	{
		this.HEIGHT = height;
		this.WIDTH = width;
		this.setSize(WIDTH,HEIGHT);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//initialises all the Labels
		for(int i = 0; i < labelCount; i++) {
			  menuChoices[i] = new JLabel();
			}
		
		this.setLayout(null);

		//Set window properties
		getContentPane().setBackground(bgColor);
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	
		//initial screen
		menuChoices[0].setText("Play");
		menuChoices[0].setFont(new Font("Franklin Gothic Medium", Font.BOLD, 80));
		
		//Secondary menu
		menuChoices[1].setText("Choose the crew");
		menuChoices[1].setFont(new Font("Franklin Gothic Medium", Font.BOLD, 30));
		menuChoices[2].setText("Choose the aircraft");
		menuChoices[2].setFont(new Font("Franklin Gothic Medium", Font.BOLD, 30));
		menuChoices[3].setText("Select Difficulty");
		menuChoices[3].setFont(new Font("Franklin Gothic Medium", Font.BOLD, 30));
		menuChoices[4].setText("Exit");
		menuChoices[4].setFont(new Font("Franklin Gothic Medium", Font.BOLD, 30));
		
		//Difficulty menu
		menuChoices[5].setText("Easy");
		menuChoices[5].setFont(new Font("Franklin Gothic Medium", Font.BOLD, 30));
		menuChoices[6].setText("Medium");
		menuChoices[6].setFont(new Font("Franklin Gothic Medium", Font.BOLD, 30));
		menuChoices[7].setText("Hard");
		menuChoices[7].setFont(new Font("Franklin Gothic Medium", Font.BOLD, 30));
		menuChoices[8].setText("Back");
		menuChoices[8].setFont(new Font("Franklin Gothic Medium", Font.BOLD, 30));
		//Graphics g = null;
		
		FontMetrics met = menuChoices[0].getFontMetrics(getFont());
		int text_height = met.getHeight();
		int text_width = met.stringWidth(menuChoices[0].getText());
		
		menuChoices[0].setBounds(this.WIDTH/2 - 125 ,this.HEIGHT/2 - 70, text_width + 250,text_height + 70);
		menuChoices[0].setHorizontalAlignment(SwingConstants.CENTER);
		this.add(menuChoices[0]);
		menuChoices[0].addMouseListener(new mListener(0));
		
		
		int currY = this.HEIGHT/2 - 100 ;
		
		//setting up secondary menu
		for(int i = 1; i < 5; i++) {
				//calculate text size
				met = menuChoices[i].getFontMetrics(menuChoices[i].getFont());
				text_height = met.getMaxAscent() + met.getMaxDescent();
				text_width = met.stringWidth(menuChoices[i].getText());
				currY += text_height;
				
				menuChoices[i].setBounds(this.WIDTH/2-text_width/2 ,currY, text_width + 10,text_height + 10);
				menuChoices[i].setHorizontalAlignment(SwingConstants.LEFT);
				menuChoices[i].setVerticalAlignment(SwingConstants.CENTER);
				menuChoices[i].addMouseListener(new mListener(i));
			}
		
		//setting up difficulty menu
		currY = this.HEIGHT/2 - 100;
		for(int i = 5; i < 9; i++) {
			//calculate text size
			met = menuChoices[i].getFontMetrics(menuChoices[i].getFont());
			text_height = met.getMaxAscent() + met.getMaxDescent();
			text_width = met.stringWidth(menuChoices[i].getText());
			currY += text_height;
			
			menuChoices[i].setBounds(this.WIDTH/2-text_width/2 ,currY, text_width + 10,text_height + 10);
			menuChoices[i].setHorizontalAlignment(SwingConstants.LEFT);
			menuChoices[i].setVerticalAlignment(SwingConstants.CENTER);
			menuChoices[i].addMouseListener(new mListener(i));
		}
			
	}
	
	private void showMainMenu(){
		this.add(menuChoices[1]);
		this.add(menuChoices[2]);
		this.add(menuChoices[3]);
		this.add(menuChoices[4]);
	}
	private void hideMainMenu(){
		this.remove(menuChoices[1]);
		this.remove(menuChoices[2]);
		this.remove(menuChoices[3]);
		this.remove(menuChoices[4]);
	}
	private void showDifficultyMenu(){
		this.add(menuChoices[5]);
		this.add(menuChoices[6]);
		this.add(menuChoices[7]);
		this.add(menuChoices[8]);
	}
	private void hideDifficultyMenu(){
		this.remove(menuChoices[5]);
		this.remove(menuChoices[6]);
		this.remove(menuChoices[7]);
		this.remove(menuChoices[8]);
	}
	public void selectedOption(int menuOption)
	{
		switch (menuOption){
			case 0://play button
				this.remove(menuChoices[0]);
				this.showMainMenu();
				repaint();
				break;
			case 1://Choose crew button
				break;
			case 2://Choose aircraft button
				break;
			case 3://Difficulty button
				hideMainMenu();
				showDifficultyMenu();
				repaint();
				break;
			case 4://Exit button
				System.exit(0);
				break;
			case 5://Easy button
				break;
			case 6://Medium button
				break;
			case 7://Hard button
				break;
			case 8://Back to Main menu
				hideDifficultyMenu();
				showMainMenu();
				repaint();
				break;
			case 9:
				break;
		}
	}
	
	private class mListener extends MouseAdapter
	{
		private int menuOption;
		public mListener(int i)
		{
			menuOption = i;
		}
		public void mouseEntered(MouseEvent arg0) {
			menuChoices[menuOption].setForeground(Color.CYAN);
		}
		public void mouseExited(MouseEvent arg0){
			menuChoices[menuOption].setForeground(Color.DARK_GRAY);
		}
		public void mouseClicked(MouseEvent arg0){
			menuChoices[menuOption].setForeground(Color.DARK_GRAY);
			selectedOption(menuOption);
		}
	}
	
}


