import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;


public class Canvas extends JPanel implements Moveable {
	/**
	 * Epic and great canvas
	 * Copyright 2008 Martin Sandsmark, Frederik M. J. Vestre
	 */
	
	private static final long serialVersionUID = 1L;
	
	Map map;
	int offsetX=0;
	int offsetY=0;
	int width;
	int height;
	int dWidth = 500;
	int dHeight = 500;
	int step=20;
	BufferedImage internalMap;
	BufferedImage baseMap;
	Unit[] units;
	
	public Canvas (Map newMap){
		map = newMap;
		height = map.getHeight();
		width = map.getWidth();
		baseMap = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D bg2 = baseMap.createGraphics();
		
		bg2.setColor(Color.green);
		bg2.fill(new Rectangle(0,0, width, height));
		bg2.setColor(Color.blue);
		bg2.draw(map.getWater());
		bg2.fill(map.getWater());
		
		internalMap = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);
		Graphics2D ig2 = internalMap.createGraphics();
		updateInternal();
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics g2 = (Graphics2D) g;
		dWidth = getSize().width;
		dHeight = getSize().height;
		g2.drawImage(internalMap, 0, 0, dWidth, dHeight, offsetX, offsetY, offsetX + dWidth, offsetY + dHeight, null);
	}
	
	public void updateInternal() {		
		Graphics2D ig2 = internalMap.createGraphics();
		ig2.drawImage(baseMap, null, 0, 0);
		Unit unit;
		Resource res;
		
		for (int i=0;i<map.getUnitNum();i++){
			unit = map.getUnit(i);
			ig2.drawImage(unit.getSprite(), null, (int)unit.getPosition().getX(), (int)unit.getPosition().getY());
		}
		
		for (int i=0;i<map.getResourceNum();i++){
			res = map.getResource(i);
			ig2.drawImage(res.getSprite(), null, res.getX(), res.getY());
		}
	}
	
	@Override
	public Dimension getMinimumSize(){
		return new Dimension(dWidth, dHeight);
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(dWidth, dHeight);
	}
	
	public int getWidth() {
		return dWidth;
	}
	
	public int getHeight() {
		return dHeight;
	}
	
	
	public void move(Direction dir){
		switch (dir) {
			case UP:
				if (offsetY > 0) {
					offsetY-=step;
				}
				break;
			case DOWN:
				if (dHeight + offsetY < height) {
					offsetY+=step;
				}
				break;
			case LEFT:
				if (offsetX > 0) {
					offsetX-=step;
				}
				break;
			case RIGHT:
				if (dWidth+offsetX < width) {
					offsetX+=step;
				}
				break;
		}
		
		if (dir != Direction.NONE){
			repaint();
		}
	}
}
