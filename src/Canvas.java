

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

//TODO: Create entire map on BufferedImage, and clip it to view FOR EPIC SPEED


public class Canvas extends JPanel implements Moveable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Map map;
	int offsetX=0;
	int offsetY=0;
	int width;
	int height;
	int dWidth = 500;
	int dHeight = 500;
	int tileX=25;
	int tileY=25;
	int step=20;
	BufferedImage internalMap;
	BufferedImage base;
	Unit[] units;
	
	public Canvas (Map newMap){
		map = newMap;
		height = map.getHeight() * tileY;
		width = map.getWidth() * tileX;
		internalMap = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);
		updateInternal();
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics g2 = (Graphics2D) g; 
		g2.drawImage(internalMap, 0, 0, dWidth, dHeight, offsetX, offsetY, offsetX + dWidth, offsetY + dHeight, null);
	}
	
	public void updateInternal() {		
		Graphics2D g2 = internalMap.createGraphics();
		for (int x=0; x < map.getWidth();x++){
			for (int y=0; y < map.getHeight();y++) {
				g2.drawImage(map.sprite[map.getNode(x,y)], null, x*tileX, y*tileY);
			}
		}
		Unit unit;
		for (int i=0;i<map.units.size();i++){
			unit = map.getUnit(i);
			g2.drawImage(unit.getSprite(), null, (int)unit.getPosition().getX()*tileX, (int)unit.getPosition().getY()*tileY);
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
