/*
Copyright (C) 2008 Martin T. Sandsmark

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


/**
 * Epic and great canvas, for drawing things (the central graphics thingy...)
 *  @author Martin Sandsmark
 */
public class Canvas extends JPanel implements Moveable {

	private static final long serialVersionUID = -8405062922089886955L;
	
	/*
	 * Offsets inside the map.
	 */
	private int offsetX = 0;
	private int offsetY = 0;
	
	/*
	 * Dimensions of this widget.
	 */
	private int width = 500;
	private int height = 500;
	
	/*
	 * How much to scroll in each direction.
	 */
	private int scrollStep = 20;

	/*
	 * Internal cache of the current view of the map.
	 */
	BufferedImage mapCache;
	
	/*
	 * This is for the selection box that is drawn on screen.
	 */
	private int bx, by, bw, bh = 0;
	private boolean showBox = false;
	
	/*
	 * This is for the targeting circle shown on screen. 
	 */
	private Point targetPosition;
	private int targetRadius = 20;

	/*
	 * This decides if the internal map cache needs to be updated.
	 */
	private boolean dirty = true;

	/**
	 * This constructs a new canvas, and creates a new internal map cache.
	 */
	public Canvas() {
		int mapHeight = GameState.getMap().getHeight();
		int mapWidth = GameState.getMap().getWidth();
		
		mapCache = new BufferedImage(mapHeight, mapWidth, BufferedImage.TYPE_INT_ARGB); // Overwrite the old internal map
		updateInternal();
	}

	/**
	 * This overrides the JPanel paintComponent(), and renders what is on the
	 * actual screen, using the internal map cache, so we don't have to redraw
	 * everything manually.
	 * @param g Graphicsobject to be drawn on.
	 */
	@Override
	public synchronized void paintComponent(Graphics g) {
		try {
			if (dirty) {
				updateInternal();
				dirty = false;
			}
			
			if (width != getSize().width) {
				width = getSize().width;
				dirty = true;
			}
			if (height != getSize().height) {
				height = getSize().height;
				dirty = true;
			}
			g.drawImage(mapCache, 0, 0, null);
			
			if (showBox)
				g.drawRect(bx, by, bw, bh);
			
			if (targetPosition != null) {
				g.setColor(Color.RED);
				g.drawOval(targetPosition.x - targetRadius / 2, targetPosition.y - targetRadius / 2, targetRadius, targetRadius);
				targetRadius /= 1.5;
				if (targetRadius < 2) targetPosition = null;
			}
		} catch (Exception e) {
			/*
			 * Exceptions happening here should not affect the overall gamestate.
			 */
			e.printStackTrace();
		}
	}
	
	/**
	 * This method updates the internal map cache.
	 */
	public synchronized void updateInternal() {
		Units units = GameState.getUnits();
		Map map = GameState.getMap();
		try {
			Graphics2D ig2 = mapCache.createGraphics();
			ig2.drawImage(GameState.getMap().getBaseMap(), 0, 0, width, height, offsetX, offsetY,
					offsetX + width, offsetY + height, null);
			
			for (Resource res : map.getResources()) {
				if (!this.isInView(res.getPosition().x, res.getPosition().y)) continue;
				ig2.drawImage(res.getSprite(), null, res.getPosition().x - offsetX, res.getPosition().y - offsetY);
			}
			
			Building humanHouse = GameState.getHuman().mainHouse;

			if (this.isInView(humanHouse.getPosition().x, humanHouse.getPosition().y)||
					this.isInView(humanHouse.getPosition().x + humanHouse.getSprite().getWidth(), humanHouse.getPosition().y + humanHouse.getSprite().getHeight())) 
				ig2.drawImage(humanHouse.getSprite(), null, humanHouse.getPosition().x - offsetX, humanHouse.getPosition().y - offsetY);
			
			Building computerHouse = GameState.getComputer().mainHouse;
			if (this.isInView(computerHouse.getPosition().x, computerHouse.getPosition().y) ||
					this.isInView(computerHouse.getPosition().x + computerHouse.getSprite().getWidth(), computerHouse.getPosition().y + computerHouse.getSprite().getHeight())) 
				ig2.drawImage(computerHouse.getSprite(), null, computerHouse.getPosition().x - offsetX, computerHouse.getPosition().y - offsetY);
			
			if (units.getUnitNum() > 0) { 
				for (Unit unit : units.getUnits()) {
					if (!this.isInView(unit.getPosition().x, unit.getPosition().y)) continue;
					ig2.drawImage(unit.getSprite().pop(), null, unit.getPosition().x - offsetX, unit.getPosition().y - offsetY);
					ig2.setColor(Color.BLUE);
					if (units.isSelected(unit))
						ig2.drawRect(unit.getPosition().x - offsetX, unit.getPosition().y - offsetY, (int)(unit.getCurrentHealthPercent() * 25), 2);
				}
			}
		} catch (Exception e) {
			/*
			 * Exceptions happening here are not fatal.
			 */
			e.printStackTrace();
		}
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
	 * This code is a bit hairy, but necessary, and it should be rather clean.
	 * @param dir Which direction to move in. 
	 */
	public void move(Direction dir) {
		int mapHeight = GameState.getMap().getHeight();
		int mapWidth = GameState.getMap().getWidth();
		switch (dir) {
		case UP:
			if (offsetY > 0) {
				offsetY -= scrollStep;
				this.moveInternal(0, -scrollStep);
			}
			break;
		case UP_RIGHT:
			if (offsetY > 0 && width + offsetX < mapWidth) {
				offsetY -= scrollStep;
				offsetX += scrollStep;
				this.moveInternal(scrollStep, -scrollStep);
			}
			break;
		case UP_LEFT:
			if (offsetY > 0 && offsetX > 0) {
				offsetY -= scrollStep;
				offsetX -= scrollStep;
				this.moveInternal(-scrollStep, -scrollStep);
			}
			break;
		case DOWN:
			if (height + offsetY < mapHeight) {
				offsetY += scrollStep;
				this.moveInternal(0, scrollStep);
			}
			break;
		case DOWN_RIGHT:
			if (height + offsetY < mapHeight && width + offsetX < mapWidth) {
				offsetY += scrollStep;
				offsetX += scrollStep;
				this.moveInternal(scrollStep, scrollStep);
			}
			break;
		case DOWN_LEFT:
			if (height + offsetY < mapHeight && offsetX > 0) {
				offsetY += scrollStep;
				offsetX -= scrollStep;
				this.moveInternal(-scrollStep, scrollStep);
			}
			break;
		case LEFT:
			if (offsetX > 0) {
				offsetX -= scrollStep;
				this.moveInternal(-scrollStep, scrollStep);
			}
			break;
		case RIGHT:
			if (width + offsetX < mapWidth) {
				offsetX += scrollStep;
				this.moveInternal(scrollStep, 0);
			}
			break;
		}
		if (dir != Direction.NONE) {
			this.dirty = true;
		}
	}

	/**
	 * This moves the internal map cache in the direction specified 
	 * by the supplied delta x/y-coordinates.
	 * @param dx How far to move in the X-plane.
	 * @param dy How far to move in the Y-plane.
	 */
	private void moveInternal(int dx, int dy) {
		Graphics2D g2d = this.mapCache.createGraphics();
		g2d.drawImage(mapCache, dx, dy, null);
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
	 * @param x1 Corner one, x coordinate.
	 * @param y1 Corner one, y coordinate.
	 * @param x2 Corner two, x coordinate. 
	 * @param y2 Corner two, y coordinate.
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
	 * This hides the selection box if it is currently displayed,
	 * and forces an update of the internal map cache.
	 */
	public void hideSelectBox() {
		if (!showBox) return;
		showBox = false;
		this.dirty = true;
	}

	/**
	 * This specifies that the map has been updated somehow (for example a unit
	 * has moved), and that we need to update our internal representation.
	 * @param x1 First corner, X coordinate.
	 * @param y1 First corner, Y coordinate.
	 * @param x2 Second corner, X coordinate.
	 * @param y2 Second corner, Y coordinate. 
	 */
	public void setDirty(int x1, int y1, int x2, int y2) {
		GameState.getMainWindow().miniMap.dirty = true;
		this.dirty = this.dirty || 
		this.isInView(x1, y1) || 
		this.isInView(x2, y2) ||
		this.isInView(x1, y2) || 
		this.isInView(x2, y1) ;
	}

	/**
	 * This returns true if the coordinates supplied are currently in view. 
	 * @param x X coordinate to be checked.
	 * @param y Y coordinate to be checked.
	 * @return true if supplied x and y coordinates are in view.
	 */
	private boolean isInView(int x, int y){
		return (x >= offsetX - 25 && y >= offsetY - 25&& x <= offsetX + width && y <= offsetY + height);
	}
	
	/**
	 * This sets up the coordinates and radius for the target circle, and
	 * therefore makes it be displayed.
	 * @param x X coordinate where the target circle should be shown.
	 * @param y Y coordinate where the target circle should be shown.
	 */
	public void showTarget (int x, int y) {
		targetPosition = new Point(x, y);
		targetRadius = 50;
	}
	
	/**
	 * This manually sets the offset to the X and Y coordinates specified.
	 * @param p This contains the X and Y coordinates.
	 */
	public void setOffset(Point p){
		offsetX = p.x;
		offsetY = p.y;
		this.dirty = true;
	}
}



