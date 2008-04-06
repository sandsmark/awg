import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JPanel;


/**
 * Epic and great canvas, for drawing things (the central graphics thingy...)
 *  @author Martin Sandsmark
 */
public class Canvas extends JPanel implements Moveable {

	/**
	 * TODO: Optimize drawing, "dirty" rectangles. Split up into several "sub-maps", which are re-drawn only when necessary.
	 */
	
	private static final long serialVersionUID = 1L; // Ignorer

	int offsetX = 0;
	int offsetY = 0;
	
	int width = 500;
	int height = 500;

	int step = 20;

	BufferedImage internalMap;
	
	int bx, by, bw, bh = 0;
	boolean showBox = false;
	
	Point target;
	int targetR = 20;

	boolean dirty = false;

	ReentrantLock lock = new ReentrantLock();
	Condition updated = lock.newCondition();

	/**
	 * This constructs a new canvas, and creates a new internal map.
	 */
	public Canvas() {
		int mapHeight = GameState.getMap().getHeight();
		int mapWidth = GameState.getMap().getWidth();
		
		internalMap = new BufferedImage(mapHeight, mapWidth, BufferedImage.TYPE_INT_ARGB); // Overwrite the old internal map
		updateInternal();
	}

	/**
	 * This overrides the JPanel paintComponent(), and renders what is on the
	 * actual screen. 
	 */
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
			width = getSize().width;
			height = getSize().height;
			g2.drawImage(internalMap, 0, 0, width, height, offsetX, offsetY,
					offsetX + width, offsetY + height, null);
			if (showBox)
				g2.drawRect(bx, by, bw, bh);
			if (target != null) {
				g2.setColor(Color.RED);
				g2.drawOval(target.x - targetR / 2, target.y - targetR / 2, targetR, targetR);
				targetR /= 1.5;
				if (targetR < 2) target = null;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}

	}
	
	/**
	 * This method updates the internal map representation/cache, which
	 * contains all layers.
	 */
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

	/**
	 * This returns the minimum size for this component.
	 */
	@Override
	public Dimension getMinimumSize() {
		return new Dimension(500, 500);
	}

	/**
	 * This returns the preferred size for this component.
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(500, 500);
	}

	/**
	 * This returns the current width of this component.
	 */
	@Override
	public int getWidth() {
		return width;
	}

	/**
	 * This returns the current height of this component.
	 */
	@Override
	public int getHeight() {
		return height;
	}

	/**
	 * This moves the offset of the internal map, in a given direction.
	 */
	public void move(Direction dir) {
		int mapHeight = GameState.getMap().getHeight();
		int mapWidth = GameState.getMap().getWidth();
		lock.lock();
		switch (dir) {
		case UP:
			if (offsetY > 0) {
				offsetY -= step;
			}
			break;
		case UP_RIGHT:
			if (offsetY > 0 && width + offsetX < mapWidth) {
				offsetY -= step;
				offsetX += step;
			}
			break;
		case UP_LEFT:
			if (offsetY > 0 && offsetX > 0) {
				offsetY -= step;
				offsetX -= step;
			}
			break;
		case DOWN:
			if (height + offsetY < mapHeight) {
				offsetY += step;
			}
			break;
		case DOWN_RIGHT:
			if (height + offsetY < mapHeight && width + offsetX < mapWidth) {
				offsetY += step;
				offsetX += step;
			}
			break;
		case DOWN_LEFT:
			if (height + offsetY < mapHeight && offsetX > 0) {
				offsetY += step;
				offsetX -= step;
			}
			break;
		case LEFT:
			if (offsetX > 0) {
				offsetX -= step;
			}
			break;
		case RIGHT:
			if (width + offsetX < mapWidth) {
				offsetX += step;
			}
			break;
		}
		lock.unlock();
		if (dir != Direction.NONE) {
			repaint();
		}
	}

	/**
	 * This returns the current x-offset of the internal map relative to the
	 * display on screen. 
	 */
	public int getOffsetX() {
		return offsetX;
	}

	/**
	 * This returns the current y-offset of the internal map relative to the
	 * display on screen. 
	 */
	public int getOffsetY() {
		return offsetY;
	}

	/**
	 * This sets the coordinates for the selection box used for selecting units.
	 * These coordinates are used by the paintComponent()-function. 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
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

	/**
	 * This hides the selection box.
	 */
	public void hideSelectBox() {
		showBox = false;
		repaint();
	}

	/**
	 * This specifies that the map has been updated somehow (for example a unit
	 * has moved), and that we need to update our internal representation.
	 * This saves us some repaints.
	 */
	public void setDirty() {
		dirty = true;
	}
	
	/**
	 * This sets up the coordinates and radius for the target-circle, and
	 * therefore makes it be displayed.
	 * @param x
	 * @param y
	 */
	public void showTarget (int x, int y) {
		target = new Point(x, y);
		targetR = 50;
	}
}
