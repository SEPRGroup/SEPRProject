package sepr.atcGame.UI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import sepr.atcGame.UI.Main;


public class MenuButton extends JLabel {
	
	private Font font = new Font("Bauhaus 93",Font.PLAIN, 50);
	
	
	
	public MenuButton(String textName)
	{		
		this.setText(textName);	
		this.setBackground(Color.white);
		this.setFont(font);	
		this.setForeground(Color.white);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setVerticalAlignment(SwingConstants.CENTER);
	
	}
	
		
}

