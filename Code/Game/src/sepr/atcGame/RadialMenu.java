package sepr.atcGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;

import sepr.atcGame.events.RadialMenuListener;


public class RadialMenu extends JComponent implements MouseListener{
	private List<String> names = new ArrayList<String>();
	private List<Image> images = new ArrayList<Image>();
	private List<Area> buttons;
	private List<Image> scaleImages;

	protected int
	spacing = 4,	//degrees between  buttons
	offset = spacing/2;	//degrees that start of first button is offset from 12 o'clock
	private int
	style = FLOWER,
	holeRadius = 45,	//pixel radius of centre hole
	buttonSize = 40,	//pixel width of buttons
	radius;
	private Font font = new Font(Font.SERIF, Font.BOLD, 11);

	private List<RadialMenuListener> listeners = new ArrayList<RadialMenuListener>();

	public static final int DONUT = 0, FLOWER = 1;


	//constructor
	public RadialMenu() {
		setOpaque(false);
		setVisible(false);

		setBackground(new Color(200, 180, 20, 245));
		setForeground(new Color(50,20,10));
		setRadius();

		addMouseListener(this);
	}


	//getters/setters
	public int getHoleRadius() {
		return holeRadius;
	}
	public void setHoleRadius(int holeRadius) {
		this.holeRadius = holeRadius;
		setRadius();
	}

	public int getButtonSize() {
		return buttonSize;
	}
	public void setButtonSize(int buttonSize) {
		this.buttonSize = buttonSize;
		setRadius();
		scaleImages = null;
	}

	public int getSpacing() {
		return spacing;
	}
	public void setSpacing(int spacing) {
		this.spacing = spacing;
		buttons = null;
	}

	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
		buttons = null;
	}

	public int getRadius() {
		return radius;
	}
	private void setRadius() {
		radius = holeRadius +buttonSize;
		buttons = null;
		int diameter = 2*radius +1;
		Dimension size = new Dimension(diameter, diameter);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		repaint();
	}

	public int getStyle() {
		return style;
	}
	public void setStyle(int style) {
		this.style = style;
		buttons = null;
		repaint();
	}

	public Font getFont() {
		return font;
	}
	public void setFont(Font font) {
		this.font = font;
		repaint();
	}

	private void getButton(){
		if (buttons == null){
			buttons = new ArrayList<Area>();
			int num = names.size();
			int size = (360 -num*spacing) /num;	//angular size of buttons
			int position = offset;
			Area b;	//inner area to remove
			switch (style){
			case DONUT:{
				b = new Area(new Ellipse2D.Double(buttonSize, buttonSize, 2*holeRadius, 2*holeRadius));
				break;
			}
			case FLOWER:{
				Polygon p = new Polygon();
				double posR = Math.toRadians(offset -spacing/2.0);
				double sizeR = 2*Math.PI /num;
				for (int i=0; i<num; i++){
					p.addPoint(radius +(int)Math.round(holeRadius*Math.sin(posR)), 
							radius -(int)Math.round(holeRadius*Math.cos(posR)));
					posR += sizeR;
				}
				b = new Area(p);
				break;
			}
			default: b = new Area();
			}
			Area a;
			//account for coordinate reference system
			int spacing = -this.spacing;
			size = -size;
			position = 90 -position -size -spacing;	
			for (int i=0; i<num; i++){
				position += size +spacing;
				//generate partial segment; 
				a = new Area(new Arc2D.Double(0, 0, 2*radius, 2*radius, 
						position, size, Arc2D.PIE));
				a.subtract(b);
				buttons.add(a);
			}
		}
	}


	//methods
	public void addButton(String label, Image image){
		names.add(label);
		images.add(image);
		scaleImages = null;
		buttons = null;
		repaint();
	}

	public final void addListener(RadialMenuListener toAdd){
		listeners.add(toAdd);
	}

	private void eventButtonClicked(int button){
		for (RadialMenuListener l : listeners){
			l.eventButtonClicked(button);
		}
	}


	//overridden methods
	@Override
	public final void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		getButton();
		//calculate scaled icons if needed
		if (scaleImages == null){
			scaleImages = new ArrayList<Image>();
			for (Image i: images){
				if (i != null){
					int w = i.getWidth(null), h = i.getHeight(null);
					double scale = ((double)(buttonSize -font.getSize() -5)) / Math.max(w, h);
					scaleImages.add(i.getScaledInstance((int)Math.round(w*scale), 
							(int)Math.round(h*scale), Image.SCALE_SMOOTH));
				}else scaleImages.add(null);
			}
		}

		int num = names.size();
		int size = (360 -num*spacing) /num;	//angular size of buttons
		int pos = offset -size -spacing;	//angular position of start of current button
		double posCR;	//angular position of centre of button in radians
		int cx, cy;	//position of centre of button
		for (int i=0; i<num; i++){
			pos += size +spacing;
			posCR = Math.toRadians(pos +size/2);
			cx = radius +(int)Math.round( (holeRadius +buttonSize/2)*Math.sin(posCR) );
			cy = radius -(int)Math.round( (holeRadius +buttonSize/2)*Math.cos(posCR) );
			String name = names.get(i);
			Image image = scaleImages.get(i);
			Area button = buttons.get(i);
			//draw button background
			g2d.setColor(getBackground());
			g2d.fill(button);
			g2d.setColor(getBackground().darker());
			g2d.draw(button);
			//draw (image) and text
			g2d.setFont(font);
			g2d.setColor(getForeground());
			if (image != null){
				g2d.drawImage(image, cx -image.getWidth(null)/2, 
						cy -image.getHeight(null)/2 -font.getSize()/2, null);
				g2d.drawString(name, cx -image.getWidth(null)/2, 
						cy +image.getHeight(null)/2 +font.getSize()/2);
			}else {
				g2d.drawString(name, cx -buttonSize/2 +5, 
						cy +font.getSize()/2);
			}

		}
	}

	@Override
	public final void setLocation(int x, int y){	//set location of centre
		super.setLocation(x -radius/2, y -radius/2);
	}

	@Override
	public final void setLocation(Point p){	//set location of centre
		//{!} my favourite kind of comment...
		//I don't know why this is radius/2 instead of radius, but it works...
		p.x -= radius/2; p.y -= radius/2;
		super.setLocation(p);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//check which button was clicked
		for (int i=0; i<names.size(); i++){
			Area a = buttons.get(i);
			if (a.contains(e.getPoint())){
				System.out.println("button\t" +names.get(i) +"\tclicked");
				eventButtonClicked(i);	//signal that a button has been clicked
				break;	//areas should not intersect; no need to continue checking
			}
		}
	}




}
