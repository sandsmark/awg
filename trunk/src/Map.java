
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Map {
	public int[][] pathMap;
	public int width;
	public int height;
	public BufferedImage[] sprite;
	public BufferedImage baseMap;
	Resource[] resources;
	Polygon waterShape;

	public Map(int nWidth, int nHeight) {
		sprite = new BufferedImage[3];
		height = nHeight;
		width = nWidth;
		pathMap = new int[width / 10][height / 10];
		int x, y;
		for (x = 0; x < pathMap.length; x++)
			for (y = 0; y < pathMap[0].length; y++)
				pathMap[x][y] = 0;

		try {
			sprite[0] = ImageIO.read(new File("resources/grass.png"));
			sprite[1] = ImageIO.read(new File("resources/water.png"));
			sprite[2] = ImageIO.read(new File("resources/trees.png"));
		} catch (IOException e) {
			System.err.println("Could not load sprite!");
			System.exit(1);
		}
		System.out.println(width + "." + height);
		// Add water to the map
		waterShape = new Polygon();

		for (long i = 0; i < 6.28; i++) {
			x = (int) (Math.cos(i) * width / 4) + width / 2;
			y = (int) (Math.sin(i) * height / 4) + height / 2;
			waterShape.addPoint(x, y);
		}

		for (x = 0; x < pathMap.length; x++)
			for (y = 0; y < pathMap[0].length; y++)
				pathMap[x][y] = 1;
		
		for (x = 0; x < pathMap.length; x++) {
			for (y = 0; y < pathMap[0].length; y++) {
				if (waterShape.contains(new Point(x, y)))
					pathMap[x][y] = 200;
				if (waterShape.contains(new Point(x + 10, y)))
					pathMap[x][y] = 200;
				if (waterShape.contains(new Point(x, y + 10)))
					pathMap[x][y] = 200;
				if (waterShape.contains(new Point(x + 10, y + 10)))
					pathMap[x][y] = 200;
			}
		}

		// Add resources to the map
		resources = new Resource[(int) Math.random() * 5 + 5];
		for (int i = 0; i < resources.length; i++) {
			x = (int) (Math.cos(i) * width / 3) + width / 2;
			y = (int) (Math.sin(i) * width / 3) + width / 2;
			resources[i] = new Resource((int) Math.random() * 20 + 20, x, y);
		}
		
		//Create the base buffer/image of the map
		baseMap = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);

		Graphics2D bg2 = baseMap.createGraphics();

		bg2.setColor(Color.green);
		bg2.fill(new Rectangle(0, 0, width, height));
		bg2.setColor(Color.blue);
		bg2.draw(this.getWater());
		bg2.fill(this.getWater());

	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int[][] getPathMap() {
		return pathMap;
	}

	public Shape getWater() {
		return waterShape;
	}

	public Resource getResource(int i) {
		return resources[i];
	}

	public Resource[] getResources() {
		return resources;
	}
	public int getResourceNum() {
		return resources.length;
	}

	public boolean canMove(int x, int y) {
		boolean can = true;
		if (waterShape.contains(x, y)) can = false;
		if (waterShape.contains(x+35, y)) can = false;
		if (waterShape.contains(x, y+35)) can = false;
		if (waterShape.contains(x+35, y+35)) can = false;
		return can;
	}

	public BufferedImage getBaseMap() {
		return baseMap;
	}
}
