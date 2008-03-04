

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

//TODO: Create entire map on BufferedImage, and clip it to view FOR EPIC SPEED


public class Canvas extends JPanel {
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
	BufferedImage internalMap;
	
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
			for (int y=0; y < map.getHeight();y++){
				g2.drawImage(map.sprite[map.getNode(x,y)], null, x*tileX, y*tileY);
			}
		}
	}
	
	@Override
	public Dimension getMinimumSize(){
		return new Dimension(dWidth, dHeight);
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(dWidth, dHeight);
	}
	
	void screenRight() {
		if (dWidth+offsetX < width) {
			offsetX++;
		}
	}
	
	void screenLeft() {
		if (offsetX > 0) {
			offsetX--;
		}
	}
	
	void screenDown() {
		if (dHeight + offsetY < height) {
			offsetY++;
		}
	}
	
	void screenUp() {
		if (offsetY > 0) {
			offsetY--;
		}
	}
}
