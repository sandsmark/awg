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
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This holds the sprites of units.
 * @author sandsmark
 */
public class Sprite {
	public enum Direction {
		UP, DOWN, RIGHT, LEFT
	}
	int fps = 10;
	
	private Direction direction = Direction.DOWN;
	private int cycle = 0;
	private boolean isMoving = false;
	private long isHit = -1;
	private boolean isDoing = false;
	private double last;
	private Unit u;
	
	/**
	 * Faction this sprite belongs to, either 0 or 1.
	 */
	private int faction;
	private String[] directionNames = {"back", "forward", "right", "left" };
	
	/*
	 *  sprite[0-1] = faction 0, 1
	 *  sprite[][0-3], direction forward,backward,right,left
	 *  sprite[][][0-1], part of walk cycle
	 *  sprite[][][][0-2], current action (normal, being attacked or attacking/harvesting/healing)
	 *   \0 = normal, 1 = being attacked, 2 = doing stuff
	 */
	private BufferedImage[][][][] sprite = new BufferedImage[2][4][2][3];
	/**
	 * This takes in the name of the unit type, and the faction the unit belongs to.
	 * Then it loads in the appropriate sprites from image files.
	 * @param basename contains either "fighter", "healer" or "worker".
	 * @param faction defines which faction (team/color) the unit is.
	 */
	public Sprite (String basename, int faction, Unit u) {
		this.u=u;
		this.faction = faction;
		try {			
			for (int f=0; f<2; f++){
				for (int dir=0; dir<4; dir++) {
					for (int c=0; c<2; c++){
						String filename = "/" 
							+ basename + "/" + f + "_" + directionNames[dir] + c + ".png";
						sprite[f][dir][c][0] = ImageIO.read(getClass().getResource(filename));
						
						sprite[f][dir][c][1] = new BufferedImage(sprite[f][dir][c][0].getWidth(), sprite[f][dir][c][0].getHeight(), BufferedImage.TYPE_INT_ARGB);
						for (int x=0; x<sprite[f][dir][c][0].getWidth(); x++)
							for (int y=0; y<sprite[f][dir][c][0].getHeight(); y++)
								sprite[f][dir][c][1].setRGB(x, y, sprite[f][dir][c][0].getRGB(x, y) & 0xffff00ff);
						filename = "/" 
							+ basename + "/" + f + "_" + directionNames[dir] +"w" + c + ".png";
						sprite[f][dir][c][2] = ImageIO.read(getClass().getResource(filename)); 
					}
				}
			}
		
		} catch (IOException e) {
			System.err.println("Could not load sprites! : ");
			e.printStackTrace();
			GameState.getMainWindow().exit();
		}
	}
	
	/**
	 * This returns and removes the next sprite in the walk cycle.
	 * @return
	 */
	public BufferedImage pop() {
		if (System.currentTimeMillis() - last > 1000/fps) {
			if (!isMoving && !isDoing) cycle = 1;
			else cycle = (cycle+1) % 2;
			last = System.currentTimeMillis();
		}
		int doing;
		if (isDoing) doing = 2;
		else if (System.currentTimeMillis() - isHit > 1500) doing = 0;
		else doing = 1;
		BufferedImage image = new BufferedImage(getWidth(),getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.createGraphics();
		g.setColor(Color.RED);
		g.fillRect(0, 0, this.getWidth(), 2);
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, Math.round(this.getWidth()*((float)u.getCurrentHealth())/((float)u.getMaxHealth())), 2);
		g.drawImage(sprite[faction][direction.ordinal()][cycle][doing],0,0,null);
		return image;
	}

	/**
	 * This sets which direction the unit is pointing.
	 * @param direction
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	/**
	 * This just returns the current sprite.
	 */
	public BufferedImage get() {
		BufferedImage image = new BufferedImage(getWidth(),getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.createGraphics();
		g.setColor(Color.RED);
		g.fillRect(0, 0, this.getWidth(), 2);
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, Math.round(this.getWidth()*((float)u.getCurrentHealth())/((float)u.getMaxHealth())), 2);
		g.drawImage(sprite[faction][direction.ordinal()][cycle][0],0,0,null);
		return image;
	}
	
	/**
	 * This sets if the sprite should appear to be walking or not.
	 * @param moving
	 */
	public void setMoving(boolean moving) {
		this.isMoving = moving;
	}
	
	/**
	 * This gets called when the unit gets damaged.
	 */
	public void hit() {
		isHit = System.currentTimeMillis();
	}
	
	/**
	 * This sets if the unit is doing anything (killing, mining, healing, et c.)
	 * @param isDoing
	 */
	public void setDoing(boolean isDoing) {
		this.isDoing = isDoing;
	}
	
	/**
	 * This returns the width of the sprite.
	 */
	public int getWidth() {
		return sprite[0][0][0][0].getWidth(); 
	}
	
	/**
	 * This returns the height of the sprite.
	 */
	public int getHeight() {
		return sprite[0][0][0][0].getHeight();
	}
}
