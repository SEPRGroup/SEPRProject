package sepr.atcGame.UI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuDisplay extends JFrame {
	private final int HEIGHT, WIDTH;
	private final Color bgColor = Color.YELLOW;
	private static final int NUMBEROFLABELS = 9;
	private MenuButton[] menuButtons = new MenuButton[NUMBEROFLABELS];
	private JPanel menuPanel = new JPanel();
	
	
	public MenuDisplay(int height, int width)
	{
		this.HEIGHT = height;
		this.WIDTH = width;
		this.setSize(HEIGHT, WIDTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("ATC Game ¦ MENU");
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		int i = 0;

		for (MenuOptions o : MenuOptions.values()){
			
			MenuButton mb = new MenuButton(o.labelText(), i);
			
			mb.addMouseListener(new mListener(i));
			mb.setVisible(false);
			menuButtons[i] = mb;
			menuPanel.add(mb);
			i++;
		}
		menuButtons[0].setVisible(true);
		menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 500, 20));
		
		menuPanel.setBackground(bgColor);
		this.add(menuPanel);
		
		this.setVisible(true);
		
	}

	
	public void selectedOption(int menuOption)
	{
		switch (menuOption){
			case 0://main menu
				menuButtons[0].setVisible(false);
				
				
				menuButtons[1].setVisible(true);
				menuButtons[2].setVisible(true);
				menuButtons[3].setVisible(true);
				
				break;
			case 1://Choose airport button
				;
				break;
			case 2://Choose difficulty button
				menuButtons[0].setVisible(false);
				menuButtons[1].setVisible(false);
				menuButtons[2].setVisible(false);
				menuButtons[3].setVisible(false);
				
				menuButtons[5].setVisible(true);
				menuButtons[6].setVisible(true);
				menuButtons[7].setVisible(true);	
				break;
			case 3://Difficulty button
				System.exit(0);
				break;
			case 4://Exit button
				
				break;
			case 5://Easy button
				break;
			case 6://Medium button
				break;
			case 7://Hard button
				menuButtons[5].setVisible(false);
				menuButtons[6].setVisible(false);
				menuButtons[7].setVisible(false);
				
				
				menuButtons[0].setVisible(true);
				menuButtons[1].setVisible(true);
				menuButtons[2].setVisible(true);
				menuButtons[3].setVisible(true);
				break;
			case 8://Back to Main menu
				
				break;
			case 10:
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
			menuButtons[menuOption].setForeground(Color.white);
		}
		public void mouseExited(MouseEvent arg0){
			menuButtons[menuOption].setForeground(Color.black);
		}
		public void mouseClicked(MouseEvent arg0){
			menuButtons[menuOption].setForeground(Color.white);
			selectedOption(menuOption);
			
		}
	}
}
