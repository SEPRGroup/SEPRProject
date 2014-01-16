package sepr.atcGame.UI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import sepr.atcGame.UI.Main;


public class MenuButton extends JLabel {
	
	private Font font = new Font("Bauhaus 93",Font.PLAIN, 20);
	private Color color = new Color(0,0,255);
	private ImageIcon cloud = new ImageIcon("src/sepr/atcGame/Images/cloud.png");
	
	public MenuButton(String textName)
	{		
		this.setText(textName);	
		this.setBackground(color);
		this.setIcon(cloud);
		this.setIconTextGap(-75);
		this.setFont(font);	
		this.setForeground(color);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setVerticalAlignment(SwingConstants.CENTER);
	
	}
	
		
}

