package sepr.atcGame.UI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import sepr.atcGame.UI.Main;


public class MenuButton extends JLabel {
	
	private Font font = new Font("Bauhaus 93", Font.BOLD, 50);
	
	private int order;
	
	public MenuButton(String textName, int order)
	{
		this.order = order;
		this.setText(textName);		
		this.setFont(font);	
		this.setForeground(Color.white);
		this.setHorizontalAlignment(SwingConstants.LEFT);
		this.setVerticalAlignment(SwingConstants.CENTER);
		
	}
	
		
}

