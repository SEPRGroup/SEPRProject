package sepr.atcGame.UI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import sepr.atcGame.UI.Main;


public class MenuButton extends JLabel {
	
	private Font font = new Font("Bauhaus 93",Font.PLAIN, 50);
	private LineBorder border = new LineBorder(new Color(173,255,47), 2, true);
	
	
	public MenuButton(String textName)
	{		
		this.setText(textName);	
		this.setBackground(Color.green);
		this.setFont(font);	
		this.setForeground(Color.white);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setVerticalAlignment(SwingConstants.CENTER);
		this.setBorder(border);
	}
	
		
}

