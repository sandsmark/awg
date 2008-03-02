

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
	int widthX;
	int widthY;
	int tileX=25;
	int tileY=25;
	
	public Canvas (Map newMap){
		map = newMap;
	}

	@Override
	public void paintComponent(Graphics g) {
		widthX = 500 / tileX;
		widthY = 500 / tileY;
		if (widthX+offsetX > map.getLength()) offsetX = map.getLength() - widthX;
		if (widthY+offsetY > map.getHeight()) offsetY = map.getHeight() - widthY;
		
		Graphics2D g2 = (Graphics2D) g;
		
		for (int x=0; x < widthX;x++){
			for (int y=0; y < widthY;y++){
				g2.drawImage(map.sprite[map.getNode(x+offsetX,y+offsetY)],null,	x*25, y*25);
			}
		}
	}
	
	@Override
	public Dimension getMinimumSize(){
		return new Dimension(500,500);
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(500,500);
	}
	
	void screenRight() {
		if (widthX+offsetX < map.getLength()) {
			offsetX++;
		}
	}
	
	void screenLeft() {
		if (offsetX > 0) {
			offsetX--;
		}
	}
	
	void screenDown() {
		if (widthY+offsetY < map.getLength()) {
			offsetY++;
		}
	}
	
	void screenUp() {
		if (offsetY > 0) {
			offsetY--;
		}
	}
}
