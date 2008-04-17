import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Sprite {
	public enum Direction {
		BACK, FORWARD, RIGHT, LEFT
	}
	int fps = 10;
	
	private Direction direction = Direction.FORWARD;
	private int cycle = 0;
	private boolean isMoving = false;
	private long isHit = -1;
	private boolean isDoing = false;
	
	/**
	 * Faction this sprite belongs to, either 0 or 1.
	 */
	private int faction;
	private String[] directionNames = {"back", "forward", "right", "left" };
	
	/**
	 *  sprite[0-1] = faction 0, 1
	 *  sprite[][0-3], direction forward,backward,right,left
	 *  sprite[][][0-1], part of walk cycle
	 *  sprite[][][][0-2], current action (normal, being attacked or attacking/harvesting/healing)
	 *   \0 = normal, 1 = being attacked, 2 = doing stuff
	 */
	private BufferedImage[][][][] sprite = new BufferedImage[2][4][2][3];
	
	public Sprite (String basename, int faction) { // Basename contains either "fighter", "healer" or "worker"
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
						//The following is hacked to work only for finished sprites
						if (basename.equals("worker") && f == 0) sprite[f][dir][c][2] = ImageIO.read(getClass().getResource(filename)); 
					}
				}
			}
		
		} catch (IOException e) {
			System.err.println("Could not load sprites! : ");
			e.printStackTrace();
			GameState.getMainWindow().exit();
		}
	}
	
	public BufferedImage pop() {
		if (!isMoving && !isDoing) cycle = 1;
		else cycle = (cycle+1) % 2;
		
		if (isDoing) return sprite[faction][direction.ordinal()][cycle][2];
		else if (System.currentTimeMillis() - isHit > 1500) return sprite[faction][direction.ordinal()][cycle][0];
		else return sprite[faction][direction.ordinal()][cycle][1];

		
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public BufferedImage get() {
		return sprite[faction][direction.ordinal()][cycle][0];
	}
	
	public void setMoving(boolean moving) {
		this.isMoving = moving;
	}
	
	public void hit() {
		isHit = System.currentTimeMillis();
	}
	
	public void setDoing(boolean isDoing) {
		this.isDoing = isDoing;
	}
}
