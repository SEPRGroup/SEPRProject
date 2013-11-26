package sepr.atcGame.UI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuDisplay extends JFrame {
	private final int HEIGHT, WIDTH;
	private final Color bgColor = (new Color(176, 196, 222));
	private static final int NUMBEROFLABELS = 10;
	private MenuButton[] menuButtons = new MenuButton[NUMBEROFLABELS];
	private JPanel menuPanel = new JPanel();
	private JPanel imagePanel = new JPanel();
	private ImageIcon Air = new ImageIcon("src/sepr/atcGame/UI/Airport.png");
	private ImageIcon Air1 = new ImageIcon("src/sepr/atcGame/UI/Airport1.png");
	private ImageIcon Air2 = new ImageIcon("src/sepr/atcGame/UI/Airport2.png");
	private JLabel Airport = new JLabel("",Air,JLabel.CENTER);

	public MenuDisplay(int height, int width)
	{
		this.HEIGHT = height;
		this.WIDTH = width;
		this.setSize(HEIGHT, WIDTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("ATC Game ¦ MENU");
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		menuPanel.add(imagePanel);
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
		imagePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 20));
		menuPanel.setBackground(bgColor);
		imagePanel.setBackground(bgColor);
		
		Airport.setVisible(false);
		imagePanel.add(Airport);
		
		this.add(menuPanel);
		
		this.setVisible(true);
		
	}
	private void showMenus(int firstLabel, int lastLabel){
		
		for(int i = firstLabel; i <= lastLabel; i++){
			
			menuButtons[i].setVisible(true);
			
		}
		
	}

	private void closeMenus(int firstLabel, int lastLabel)
	{
		for(int i = firstLabel; i <=lastLabel; i++){
			menuButtons[i].setVisible(false);
		}
	}
	
	public void selectedOption(int menuOption)
	{
		switch (menuOption){
			case 0://main menu
				
				menuButtons[0].setVisible(false);
				showMenus(1, 3);
				break;
			case 1://Choose airport button
				closeMenus(1, 3);				
				menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
				Airport.setVisible(true);
				showMenus(7, 9);
				break;
			case 2://Choose difficulty button

				closeMenus(1, 3);
				showMenus(4, 6);
				menuButtons[9].setVisible(true);
				break;
			case 3://Exit button
				System.exit(0);
				break;
			case 4://Easy button
				
				break;
			case 5://Medium button
				break;
			case 6://Hard button
				break;
			case 7:
				break;
			case 8:				
				break;
			case 9://Back to Main menu
				closeMenus(4, 9);
				Airport.setVisible(false);
				menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 500, 20));
				showMenus(1, 3);
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
			if(menuOption == 7){
				Airport.setIcon(Air1);
			}
			if(menuOption == 8){
				Airport.setIcon(Air2);
			}
		}
		public void mouseExited(MouseEvent arg0){
			menuButtons[menuOption].setForeground(Color.white);
			if((menuOption == 7 || menuOption == 8)){
				Airport.setIcon(Air);
			}
		
		}
		public void mouseClicked(MouseEvent arg0){
			menuButtons[menuOption].setForeground(Color.white);
			selectedOption(menuOption);
			if(menuOption == 7){
				Air = Air1;
			}
			if(menuOption == 8){
				Air = Air2;
			}
		}
	}
}
