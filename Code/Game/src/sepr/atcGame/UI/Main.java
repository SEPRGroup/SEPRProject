package sepr.atcGame.UI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

import sepr.atcGame.Game;
import sepr.atcGame.GameDifficulty;



public class Main {

	public static void main(String[] args) {
		ImagePanel panel = new ImagePanel(new ImageIcon("src/sepr/atcGame/Images/sky.jpg").getImage());

		JFrame frame = new JFrame();
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("ATC Game | MENU");
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}


class ImagePanel extends JPanel {

	private Image img;

	private ImageIcon Air = new ImageIcon("src/sepr/atcGame/Images/Airport.png");
	private  ImageIcon Air1 = new ImageIcon("src/sepr/atcGame/Images/Airport1.png");
	private ImageIcon Air2 = new ImageIcon("src/sepr/atcGame/Images/Airport2.png");

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
		setLayout(new FlowLayout(FlowLayout.CENTER, 500, 80));

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
			showMenus(1, 2);
			break;
		case PLAY:				
			Game game = new Game(GameDifficulty.EASY);
			setVisible(false);
			game.Play();
			break;
		case CHOOSEAIRPORT:
			closeMenus(1, 2);				
			setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
			Airport.setVisible(true);
			showMenus(3,6);
			break;
		case EXIT:
			System.exit(0);
			break;	
		}	
	}

	private class mListener extends MouseAdapter
	{
		private final Color fgColor = (new Color(0,0,139));
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
			menuOption.menuButton.setForeground(new Color(0,0,255));
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


