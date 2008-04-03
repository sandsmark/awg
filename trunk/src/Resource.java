import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Resource {
	int resourcesLeft;
	BufferedImage sprite;
	int x, y;

	public Resource(int initialVal, int nx, int ny) {
		x = nx;
		y = ny;
		try {
			sprite = ImageIO.read(new File("resources/gull.png"));
		} catch (IOException e) {
			System.err.println("Could not load resource sprite!");
			System.exit(1);
		}
	}

	public int harvest(int max) {
		if (resourcesLeft == 0)
			return 0;
		int harvested = (int) Math.random() * max;
		if (harvested > resourcesLeft) {
			harvested = harvested % resourcesLeft;
			resourcesLeft = 0;
			return harvested;
		}
		resourcesLeft -= harvested;
		return harvested;
	}

	public BufferedImage getSprite() {
		return sprite;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
