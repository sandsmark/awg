import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Map {
	public int[][] pathMap;
	public int width;
	public int height;
	public BufferedImage[] sprite;
	public BufferedImage baseMap;
	Resource[] resources;
	private ArrayList<Shape> waters = new ArrayList<Shape>();

	public Map(int nWidth, int nHeight) {
		sprite = new BufferedImage[4];
		height = nHeight;
		width = nWidth;

		try {
			sprite[0] = ImageIO.read(getClass().getResource("/grass.png"));
			sprite[1] = ImageIO.read(getClass().getResource("/water.png"));
			sprite[2] = ImageIO.read(getClass().getResource("/trees.png"));
			sprite[3] = ImageIO.read(getClass().getResource("/tree.png"));
		} catch (IOException e) {
			System.err.println("Could not load sprite!");
			System.exit(1);
		}

		loadWater("/water.map");
		// Add resources to the map
		resources = new Resource[(int) Math.random() * 5 + 5];
		int x,y;
		for (int i = 0; i < resources.length; i++) {
			x = (int) (Math.cos(i) * width / 3) + width / 2;
			y = (int) (Math.sin(i) * width / 3) + width / 2;
			resources[i] = new Resource((int) (Math.random() * 20000) + 20000, x, y);
		}
		
		//Create the base buffer/image of the map
		baseMap = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);

		Graphics2D bg2 = baseMap.createGraphics();

		bg2.setColor(Color.green);
//		bg2.fill(new Rectangle(0, 0, width, height));
		for (x=0; x<width / 30; x++) {
			for (y=0; y<height / 30; y++) {
				if (Math.random() > 0.1) bg2.drawImage(sprite[0], null, x*30, y*30);
				else bg2.drawImage(sprite[3], null, x*30, y*30);
			}
		}
		bg2.setColor(Color.blue);
		for (Shape water : waters) {
			bg2.draw(water);
			bg2.fill(water);
		}

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

	public ArrayList<Shape> getWater() {
		return waters;
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
		/*
		 * TODO: Clean up.
		 */
		if (x < 0 || x > getWidth() || y < 0 || y > getHeight()) return false;
		for (Shape water : waters) {
			if (water.contains(x, y)) return false;
			if (water.contains(x+35, y)) return false;
			if (water.contains(x, y+35)) return false;
			if (water.contains(x+35, y+35)) return false;
		}
		return true;
	}

	public BufferedImage getBaseMap() {
		return baseMap;
	}
	
	public void loadWater(String filename) {
		try {
			BufferedReader file = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filename)));
			
			String line;
			String [] coords; 
			Polygon poly;
			while ((line = file.readLine()) != null) {
				coords = line.split(" ");
				poly = new Polygon();
				for (String coordinate : coords) {
					poly.addPoint(Integer.parseInt(coordinate.split(",")[0]), Integer.parseInt(coordinate.split(",")[1]));
				}
				waters.add(poly);
			}
		} catch (Exception e) {
			e.printStackTrace();
			waters.add(new Ellipse2D.Float(GameState.getConfig().getWorldWidth()/2,GameState.getConfig().getWorldHeight()/2,GameState.getConfig().getWorldWidth()/4,GameState.getConfig().getWorldHeight()/4));
		}
	}
	
public Resource getClosestNode(Point p) {
		double shortestDistance = 1000;
		Resource closestResource = null;
		for (Resource res : GameState.getMap().getResources()) {
			Point resLoc = res.getPosition();
			double check = p.distance(resLoc);
			if(check<shortestDistance) {
				shortestDistance = check;
				closestResource = res;
			}
		}
		return closestResource;
	}
}
