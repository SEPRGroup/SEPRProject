package sepr.atcGame.UI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sepr.atcGame.Game;
import sepr.atcGame.GameDifficulty;

public class MenuDisplay extends JFrame {
	private final int HEIGHT, WIDTH;
	private final Color bgColor = (new Color(176, 196, 222));
	
	private static final int NUMBEROFLABELS = 10;
	private JPanel menuPanel = new JPanel();
	private JPanel imagePanel = new JPanel();
	private ImageIcon Air = new ImageIcon("src/sepr/atcGame/Images/Airport.png");
	private ImageIcon Air1 = new ImageIcon("src/sepr/atcGame/Images/Airport1.png");
	private ImageIcon Air2 = new ImageIcon("src/sepr/atcGame/Images/Airport2.png");
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
		

		for (MenuOptions o : MenuOptions.values()){
					
			o.menuButton.addMouseListener(new mListener(o));
			o.menuButton.setVisible(false);			
			menuPanel.add(o.menuButton);
			
		}
				
		MenuOptions.START.menuButton.setVisible(true);
		
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
			
			MenuOptions.values()[i].menuButton.setVisible(true);
			
		}
		
	}

	private void closeMenus(int firstLabel, int lastLabel)
	{
		for(int i = firstLabel; i <= lastLabel; i++){
			
			MenuOptions.values()[i].menuButton.setVisible(false);
			
		}
	}
	
	public void selectedOption(MenuOptions menuOption)
	{
		switch (menuOption){
			case START:
				MenuOptions.START.menuButton.setVisible(false);
				showMenus(1, 4);
				break;
			case PLAY:				
				new Game(800, 600, GameDifficulty.EASY);
				setVisible(false);
				
				
				break;
			case CHOOSEAIRPORT:
				closeMenus(1, 4);				
				menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
				Airport.setVisible(true);
				showMenus(8, 10);
				break;
			case SELECTDIFFICULTY:
				closeMenus(1, 4);
				showMenus(5, 7);
				MenuOptions.BACK.menuButton.setVisible(true);
				break;
			case EXIT:
				System.exit(0);
				
				break;
			case EASY:				
				break;
			case MEDIUM:
				break;
			case HARD:
				break;
			case AIRPORT1:
				break;
			case AIRPORT2:				
				break;
			case BACK:
				closeMenus(5, 10);
				Airport.setVisible(false);
				menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 500, 20));
				showMenus(1, 4);
				break;
		}
	}
	
	private class mListener extends MouseAdapter
	{
		private final Color fgColor = (new Color(65,105,225));
		private MenuOptions menuOption;
		
		
		public mListener(MenuOptions menuOption)
		{
			this.menuOption = menuOption;
		
		}
		public void mouseEntered(MouseEvent arg0) {
			menuOption.menuButton.setForeground(fgColor);
			if(menuOption.equals(MenuOptions.AIRPORT1)){
				Airport.setIcon(Air1);
			}
			if(menuOption.equals(MenuOptions.AIRPORT2)){
				Airport.setIcon(Air2);
			}
		}
		public void mouseExited(MouseEvent arg0){
			menuOption.menuButton.setForeground(Color.white);
			if(menuOption.equals(MenuOptions.AIRPORT2) || menuOption.equals(MenuOptions.AIRPORT1)){
				Airport.setIcon(Air);
			}
		
		}
		public void mouseClicked(MouseEvent arg0){
			menuOption.menuButton.setForeground(fgColor);
			selectedOption(menuOption);
			if(menuOption.equals(MenuOptions.AIRPORT1)){
				Air = Air1;
			}
			if(menuOption.equals(MenuOptions.AIRPORT2)){
				Air = Air2;
			}
		}
	}
}
