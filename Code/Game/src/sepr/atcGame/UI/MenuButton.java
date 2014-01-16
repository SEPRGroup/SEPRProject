package sepr.atcGame.UI;

import java.awt.*;

import javax.swing.*;



public class MenuButton extends JPanel {
	
	
	
	private Image img = new ImageIcon("src/sepr/atcGame/Images/menu_label.png").getImage();
	private FontMetrics fm;
	private Image scaleBackground;
	private Dimension size;
	private MenuText menuText;
	
	public MenuButton(String textName)
	{		
		setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		
		menuText = new MenuText(textName);
		
		this.fm = menuText.getFontMetrics(menuText.getFont());
		size = new Dimension(fm.getHeight(), fm.stringWidth(textName));	
		add(menuText);
		repaint();
	}
	
	public MenuText getMenuText(){
		return menuText; 
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
		Rectangle bounds = getBounds();
		
		
		if (scaleBackground != null){			
			g.drawImage(scaleBackground, 0, 0, null);}
		else if(img != null){
			scaleBackground = img.getScaledInstance(bounds.width, bounds.height, Image.SCALE_SMOOTH);
			g.drawImage(scaleBackground, 0, 0, null);}	
		else{
			g.setColor(Color.BLACK);
			g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height); 
		}
	}
	
	class MenuText extends JLabel {
		
		private Color color = new Color(0,0,255);
		private Font font = new Font("Bauhaus 93",Font.PLAIN, 35);
		
		
		public MenuText(String text){
			this.setForeground(color);
			this.setFont(font);
			this.setText(text);
			this.setVisible(true);
		}
		
	}
	
		
}

