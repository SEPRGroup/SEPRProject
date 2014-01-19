package sepr.atcGame.UI;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MenuButton extends JPanel {

	private Image scaleBackground;
	private MenuText menuText;
	private File font_file = new File("src/sepr/atcGame/Images/backlash.ttf");
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
				try {img = ImageIO.read(new File("src/sepr/atcGame/Images/menu_label.png"));}
				catch (IOException e){};
			}
			scaleBackground = img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
		}

		g.drawImage(scaleBackground, 0, 0, null);
	}

	public class MenuText extends JLabel {

		private Font font;

		public MenuText(String text){
			super(text);
			try{font = Font.createFont(Font.TRUETYPE_FONT, font_file);}
			catch (IOException | FontFormatException e){};
			Font resizedFont = font.deriveFont(30f);
			
			this.setForeground(Color.WHITE);
			
			this.setFont(resizedFont);
		}

	}

}

