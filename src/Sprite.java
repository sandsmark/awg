import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
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
	private double orientation = 0;
	private long lastShown = System.currentTimeMillis();
	/**
	 * Faction this sprite belongs to, either 0 or 1.
	 */
	private int faction;
	private String[] directionNames = {"back", "forward", "right", "left" };
	
	/**
	 *  sprite[0-1] = faction 0, 1
	 *  sprite[][0-3], direction forward,backward,right,left
	 *  sprite[][][0-1], part of walk cycle
	 *  sprite[][][][0-1], current action (normal, being attacked or attacking/harvesting/healing)
	 */
	private BufferedImage[][][][] sprite = new BufferedImage[2][4][2][2];
	private BufferedImage[] sprites = new BufferedImage[2];
	
	public Sprite (String basename, int faction) { // Basename contains either "fighter", "healer" or "worker"
		try {
			System.out.println("/"+basename + "/0.png");
			sprites[0] = ImageIO.read(getClass().getResource("/"+basename + "/0.png"));
			sprites[1] = ImageIO.read(getClass().getResource("/"+basename + "/1.png"));
			
			for (int f=0; f<2; f++){
				for (int dir=0; dir<4; dir++) {
					for (int c=0; c<2; c++){
						String filename = "/" 
							+ basename + "/" + f + "_" + directionNames[dir] + c + ".png";
						sprite[f][dir][c][0] = ImageIO.read(getClass().getResource(filename));
						sprite[f][dir][c][1] = new BufferedImage(sprite[f][dir][c][0].getWidth(), sprite[f][dir][c][0].getHeight(), BufferedImage.TYPE_INT_ARGB);
						for (int x=0; x<sprite[f][dir][c][0].getWidth(); x++) {
							for (int y=0; y<sprite[f][dir][c][0].getHeight(); y++){
								sprite[f][dir][c][1].setRGB(x, y, sprite[f][dir][c][0].getRGB(x, y) & 0xffff00ff);
							}
						}
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
		BufferedImage ret = new BufferedImage(sprites[0].getWidth(), sprites[0].getHeight(), BufferedImage.TYPE_INT_ARGB);
		AffineTransform tx = AffineTransform.getRotateInstance(orientation, sprites[0].getWidth()/2, sprites[0].getHeight()/2); 
		Graphics2D g2 = ret.createGraphics();

		if (!isMoving) cycle = 1;
		else if (System.currentTimeMillis() - this.lastShown > 1000/this.fps) {
			this.lastShown = System.currentTimeMillis();
			cycle = (cycle+1) % 2;
		}
		g2.drawImage(sprites[cycle], tx, null);
//		if (System.currentTimeMillis() - isHit > 1500) return sprite[faction][direction.ordinal()][cycle][0];
//		else return sprite[faction][direction.ordinal()][cycle][1];
		return ret;
		
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
	
	public void setOrientation(double orientation) {
		this.orientation = orientation;
	}
}
