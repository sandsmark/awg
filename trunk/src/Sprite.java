import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Sprite {
	public enum Direction {
		BACK, FORWARD, RIGHT, LEFT
	}
	
	private Direction direction = Direction.FORWARD;
	private int cycle = 0;
	
	/**
	 * Faction this sprite belongs to, either 0 or 1.
	 */
	private int faction;
	private String[] directionNames = {"back", "forward", "right", "left" };
	
	/**
	 *  sprite[0-1] = faction 0, 1
	 *  sprite[][0-3], direction forward,backward,right,left
	 *  sprite[][][0-1], part of walk cycle
	 */
	private BufferedImage[][][] sprite = new BufferedImage[2][4][2];

	public Sprite (String basename, int faction) { // Basename contains either "fighter", "healer" or "worker"
		try {
			for (int f=0; f<2; f++){
				for (int dir=0; dir<4; dir++) {
					for (int c=0; c<2; c++){
						String filename = "resources/" 
							+ basename + f + "_" + directionNames[dir] + c + ".gif";
						sprite[f][dir][c] = ImageIO.read(new File(filename)); 
					}
				}
			}
		
		} catch (IOException e) {
			System.err.println("Could not load sprites!");
			GameState.getMainWindow().exit();
		}
	}
	
	public BufferedImage popSprite() {
		cycle = cycle++ % 2;
		return sprite[faction][direction.ordinal()][cycle];
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public BufferedImage get() {
		return sprite[faction][direction.ordinal()][cycle];
	}
}
