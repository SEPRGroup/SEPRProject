package sepr.atcGame.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import sepr.atcGame.Game;
import sepr.atcGame.GameDifficulty;

public class Menu extends JFrame{

	public Menu() {
		super("ATC Game | MENU");
		
		ImagePanel panel = new ImagePanel(new ImageIcon(getClass().getResource("/sepr/atcGame/Images/sky.jpg")).getImage());
		getContentPane().add(panel);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(810, 610);
		setResizable(false);
		setLocationRelativeTo(null);
	}
	
	class ImagePanel extends JPanel {

		private Image img;

		private ImageIcon Air = new ImageIcon(getClass().getResource("/sepr/atcGame/Images/Airport.png"));
		private  ImageIcon Air1 = new ImageIcon(getClass().getResource("/sepr/atcGame/Images/Airport1.png"));
		private ImageIcon Air2 = new ImageIcon(getClass().getResource("/sepr/atcGame/Images/Airport2.png"));

		private JLabel Airport = new JLabel("",Air,JLabel.CENTER);

		public ImagePanel(String img) {
			this(new ImageIcon(img).getImage());
		}


		public ImagePanel(Image img) {

			this.img = img;
			Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
			setPreferredSize(size);
			setMinimumSize(size);
			setMaximumSize(size);
			setLayout(new FlowLayout(FlowLayout.CENTER, 500, 50));

			Airport.setVisible(false);
			add(Airport);

			for (MenuOptions o : MenuOptions.values()){
				o.menuButton.addMouseListener(new mListener(o));
				o.menuButton.setVisible(false);			
				add(o.menuButton);
			}
			MenuOptions.START.menuButton.setVisible(true);
		}


		public void paintComponent(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}

		private void showMenus(int firstLabel, int lastLabel){
			for(int i = firstLabel; i <= lastLabel; i++){
				MenuOptions.values()[i].menuButton.setVisible(true);
			}

		}
		
		private Menu getOuterClass() {
			return Menu.this;
		}

		private void closeMenus(int firstLabel, int lastLabel)
		{
			for(int i = firstLabel; i <= lastLabel; i++){
				MenuOptions.values()[i].menuButton.setVisible(false); 
			}
		}


		public void selectedOption(MenuOptions menuOption)
		{	Game game;
			GameDifficulty difficulty = GameDifficulty.MEDIUM;
			switch (menuOption){
				case START:
					MenuOptions.START.menuButton.setVisible(false);
					showMenus(1, 4);
					break;
				case PLAY:				
					game = new Game(difficulty, Game.HEATHROW);
					getOuterClass().setVisible(false);
					game.Play();
					break;
				case CHOOSEAIRPORT:
					closeMenus(1, 5);				
					setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
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
					difficulty = GameDifficulty.EASY;
					closeMenus(5, 7);
					showMenus(1, 4);
					break;
				case MEDIUM:
					difficulty = GameDifficulty.MEDIUM;
					closeMenus(5, 7);
					showMenus(1, 4);
					break;
				case HARD:
					difficulty = GameDifficulty.HARD;
					closeMenus(5, 7);
					showMenus(1, 4);
					break;
				case AIRPORT1:
					game = new Game(GameDifficulty.EASY, Game.HEATHROW);
					getOuterClass().setVisible(false);
					game.Play();
					break;
				case AIRPORT2:		
					JOptionPane.showMessageDialog(null,"Unfortunately this airport is unavailable!");
					break;
				case BACK:
					closeMenus(5, 10);
					Airport.setVisible(false);
					setLayout(new FlowLayout(FlowLayout.CENTER, 500, 50));
					showMenus(1, 4);
					break;
			}
		}

		private class mListener extends MouseAdapter
		{
			private MenuOptions menuOption;

			public mListener(MenuOptions menuOption)
			{
				this.menuOption = menuOption;
			}			

			public void mouseEntered(MouseEvent e) {
				menuOption.menuButton.getMenuText().setForeground(Color.BLACK);
				if(menuOption.equals(MenuOptions.AIRPORT1)){
					Airport.setIcon(Air1);
				}
				if(menuOption.equals(MenuOptions.AIRPORT2)){
					Airport.setIcon(Air2);
				}
			}

			public void mouseExited(MouseEvent e){
				menuOption.menuButton.getMenuText().setForeground(Color.WHITE);
				if(menuOption.equals(MenuOptions.AIRPORT2) || menuOption.equals(MenuOptions.AIRPORT1)){
					Airport.setIcon(Air);
				}
			}

			public void mouseClicked(MouseEvent e){
				menuOption.menuButton.getMenuText().setForeground(Color.BLACK);
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

}
