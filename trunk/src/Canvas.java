import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

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
	int bx, by, bw, bh = 0 ;
	boolean showBox = false;
	ReentrantLock lock = new ReentrantLock();
	Condition updated = lock.newCondition();
	
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
		updateInternal();
	}

	@Override
	public void paintComponent(Graphics g) {
		try {
			if (!lock.tryLock(500, TimeUnit.MILLISECONDS)) return;
			Graphics g2 = (Graphics2D) g;
			dWidth = getSize().width;
			dHeight = getSize().height;
			g2.drawImage(internalMap, 0, 0, dWidth, dHeight, offsetX, offsetY, offsetX + dWidth, offsetY + dHeight, null);
			if (showBox) g2.drawRect(bx, by, bw, bh);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}

	}
	
	public void updateInternal() {
		lock.lock();
		Graphics2D ig2 = internalMap.createGraphics();
		ig2.drawImage(baseMap, null, 0, 0);
		Unit unit;
		Resource res;

		for (int i=0;i<map.getUnitNum();i++){
			unit = map.getUnit(i);	
			ig2.drawImage(unit.getSprite(), null, unit.getX(), unit.getY());
		}
		
		for (int i=0;i<map.getResourceNum();i++){
			res = map.getResource(i);
			ig2.drawImage(res.getSprite(), null, res.getX(), res.getY());
		}
		lock.unlock();
	}
	
	@Override
	public Dimension getMinimumSize(){
		return new Dimension(500, 500);
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(500, 500);
	}
	
	public int getWidth() {
		return dWidth;
	}
	
	public int getHeight() {
		return dHeight;
	}
	
	
	public void move(Direction dir){
		lock.lock();
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
		lock.unlock();
		if (dir != Direction.NONE){
			repaint();
		}
	}

	public Map getMap(){
		return map;
	}
	
	public int getOffsetX() {
		return offsetX;
	}
	
	public int getOffsetY() {
		return offsetY;
	}
	
	public void drawSelectBox(int x1, int y1, int x2, int y2) {
		if (x1 < x2) 
			bx = x1;
		else
			bx = x2;
		if (y1 < y2)
			by = y1;
		else
			by = y2;
		bw = Math.abs(x2 - x1);
		bh = Math.abs(y2 - y1);
		showBox = true;
		repaint();
	}
	
	public void hideSelectBox() {
		showBox = false;
		repaint();
	}
}
