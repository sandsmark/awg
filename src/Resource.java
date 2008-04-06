import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Resource {
	protected int resourcesLeft;
	protected BufferedImage sprite;
	protected Point position;
	protected BufferedImage selectedSprite;
	protected long highlightTime;
	
	public Resource(int initialVal, int x, int y) {
		resourcesLeft = initialVal;
		position = new Point(x,y);
		try {
			int num = (int)(Math.random()*2);
			sprite = ImageIO.read(new File("resources/rocks/" + num + ".png"));
			selectedSprite = ImageIO.read(new File("resources/rocks/" + num + "s.png")); 
		} catch (IOException e) {
			System.err.println("Could not load resource sprite!");
			System.exit(1);
		}
	}

	public int harvest(int max) {
		if (resourcesLeft == 0)
			return 0;
		int harvested = (int) (Math.random() * max) + 1;
		if (harvested > resourcesLeft) {
			harvested = harvested % resourcesLeft;
			resourcesLeft = 0;
			return harvested;
		}
		resourcesLeft -= harvested;
		return harvested;
	}

	public BufferedImage getSprite() {
		if (System.currentTimeMillis() - highlightTime > 1000) highlightTime = 0;
		if (highlightTime != 0) return selectedSprite;
		else return sprite;
	}

	public Point getPosition() {
		return position;
	}
	
	public int getRemaining() {
		return resourcesLeft;
	}
	
	public void startHighlight() {
		highlightTime = System.currentTimeMillis();
	}

	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}
	
}
