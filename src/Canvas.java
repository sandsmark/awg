import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JPanel;

public class Canvas extends JPanel implements Moveable {
	/**
	 * Epic and great canvas, for drawing things (the central graphics thingy...)
	 *  @author Martin Sandsmark
	 */

	/**
	 * TODO: Optimize drawing, "dirty" rectangles. Split up into several "sub-maps", which are re-drawn only when necessary.
	 */
	
	private static final long serialVersionUID = 1L; // Ignorer

	int offsetX = 0;
	int offsetY = 0;
	int width;
	int height;
	int dWidth = 500;
	int dHeight = 500;
	int step = 20;
	BufferedImage internalMap;
	BufferedImage baseMap;
	
 	//Box x/y-coordinates, and height and width
	int bx, by, bw, bh = 0;
	boolean showBox = false;
	
	//Target is the shrinking circle when setting a target for a unit
	boolean showTarget = false;
	Point target;
	
	int targetR = 20;
	boolean dirty = false;
	ReentrantLock lock = new ReentrantLock();
	Condition updated = lock.newCondition();

	public Canvas() {
		Map map = GameState.getMap();
		height = map.getHeight();
		width = map.getWidth();

		internalMap = new BufferedImage(height, width,
				BufferedImage.TYPE_INT_ARGB);
		updateInternal();
	}

	@Override
	public void paintComponent(Graphics g) {
		try {
			if (!lock.tryLock(500, TimeUnit.MILLISECONDS))
				return;
			if (dirty) {
				updateInternal();
				dirty = false;
			}
			Graphics g2 = g;
			dWidth = getSize().width;
			dHeight = getSize().height;
			g2.drawImage(internalMap, 0, 0, dWidth, dHeight, offsetX, offsetY,
					offsetX + dWidth, offsetY + dHeight, null);
			if (showBox)
				g2.drawRect(bx, by, bw, bh);
			if (showTarget) {
				g2.setColor(Color.RED);
				g2.drawOval(target.x - targetR / 2, target.y - targetR / 2, targetR, targetR);
				targetR /= 1.5;
				if (targetR < 2) showTarget = false;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}

	}

	public void updateInternal() {
		Units units = GameState.getUnits();
		Map map = GameState.getMap();
		
		lock.lock();
		Graphics2D ig2 = internalMap.createGraphics();
		ig2.drawImage(GameState.getMap().getBaseMap(), null, 0, 0);
		Unit unit;
		Resource res;
		
		for (int i = 0; i < map.getResourceNum(); i++) {
			res = map.getResource(i);
			ig2.drawImage(res.getSprite(), null, res.getPosition().x, res.getPosition().y);
		}

		Building humanHouse = GameState.getHuman().mainHouse;
		ig2.drawImage(humanHouse.getSprite(), null, humanHouse.getPosition().x, humanHouse.getPosition().y);
		
		Building computerHouse = GameState.getComputer().mainHouse;
		ig2.drawImage(computerHouse.getSprite(), null, computerHouse.getPosition().x, computerHouse.getPosition().y);
		
		for (int i = 0; i < units.getUnitNum(); i++) {
			unit = units.getUnit(i);
			ig2.drawImage(unit.getSprite().pop(), null, unit.getPosition().x, unit.getPosition().y);
			ig2.setColor(Color.BLUE);
			if (units.isSelected(unit))
				ig2.drawRect(unit.getPosition().x, unit.getPosition().y, unit.getCurHealth(), 2);
		}



		lock.unlock();
	}

	@Override
	public Dimension getMinimumSize() {
		return new Dimension(500, 500);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(500, 500);
	}

	@Override
	public int getWidth() {
		return dWidth;
	}

	@Override
	public int getHeight() {
		return dHeight;
	}

	public void move(Direction dir) {
		lock.lock();
		switch (dir) {
		case UP:
			if (offsetY > 0) {
				offsetY -= step;
			}
			break;
		case DOWN:
			if (dHeight + offsetY < height) {
				offsetY += step;
			}
			break;
		case LEFT:
			if (offsetX > 0) {
				offsetX -= step;
			}
			break;
		case RIGHT:
			if (dWidth + offsetX < width) {
				offsetX += step;
			}
			break;
		}
		lock.unlock();
		if (dir != Direction.NONE) {
			repaint();
		}
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

	public void setDirty() {
		dirty = true;
	}
	
	public void showTarget (int x, int y) {
		target = new Point(x, y);
		targetR = 50;
		showTarget = true;
	}
}
