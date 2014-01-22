package sepr.atcGame.UI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuButton extends JPanel {

	private Image scaleBackground;
	private MenuText menuText;
	private File font_file = new File("/sepr/atcGame/Images/backlash.ttf");
	private static Image img;
	 

	public MenuButton(String textName)
	{	
		setLayout(new FlowLayout(FlowLayout.CENTER, 40, 30));
		setOpaque(false);

		menuText = new MenuText(textName);

		add(menuText);
	}

	public MenuText getMenuText(){
		return menuText; 
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (scaleBackground == null){
			if (img == null){
				try {img = ImageIO.read(getClass().getResource("/sepr/atcGame/Images/menu_label.png"));}
				catch (IOException e){System.out.println(e.toString());}
			}
			scaleBackground = img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
		}

		g.drawImage(scaleBackground, 0, 0, null);
	}

	public class MenuText extends JLabel {

		private Font font;

		public MenuText(String text){
			super(text);
			try{font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/sepr/atcGame/Images/backlash.ttf"));}
			catch (IOException | FontFormatException e){};
			if(font != null){
				Font resizedFont = font.deriveFont(30f);
				this.setFont(resizedFont);
			} else{
				this.setFont(font);
			}
			this.setForeground(Color.WHITE);
			
			
		}

	}

}

