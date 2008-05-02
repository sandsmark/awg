package com.googlecode.awg.state;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.googlecode.awg.units.Resource;

public class Map {
	public int[][] pathMap;
	public int width;
	public int height;
	private BufferedImage tree,grass,waterImg;
	public BufferedImage baseMap;
	Resource[] resources;
	private Shape water;

	public Map() {
		height = Config.getWorldHeight();
		width = Config.getWorldWidth();

		try {
			grass = ImageIO.read(getClass().getResource("/grass.png"));
			tree = ImageIO.read(getClass().getResource("/tree.png"));
			waterImg = ImageIO.read(getClass().getResource("/water.png"));
		} catch (IOException e) {
			System.err.println("Could not load sprite!");
			System.exit(1);
		}


		water = new Ellipse2D.Float(Config.getWorldWidth()/2 - Config.getWorldWidth()/6,Config.getWorldHeight()/2 - Config.getWorldHeight()/6,
				Config.getWorldWidth()/3,Config.getWorldHeight()/3);

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
		bg2.fill(new Rectangle(0, 0, width, height));

		//Draw grass, with trees here and there 
		for (x=0; x<width / 30; x++) {
			for (y=0; y<height / 30; y++) {
				if (Math.random() > 0.01) bg2.drawImage(grass, null, x*30, y*30);
				else bg2.drawImage(tree, null, x*30, y*30);
				if (water.contains(new Point(x*30 + 15,y*30+15))) bg2.drawImage(waterImg, null, x*30, y*30);
			}
		}
		bg2.setColor(Color.blue);
//		bg2.draw(water);
//		bg2.fill(water);
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
		return water;
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
		if (water.contains(x, y)) return false;
		if (water.contains(x+35, y)) return false;
		if (water.contains(x, y+35)) return false;
		if (water.contains(x+35, y+35)) return false;
		return true;
	}

	public BufferedImage getBaseMap() {
		return baseMap;
	}
	
	
public Resource getClosestNode(Point p) {
		double shortestDistance = 1000;
		Resource closestResource = null;
		for (Resource res : GameState.getMap().getResources()) {
			Point resLoc = res.getPosition();
			double check = p.distance(resLoc);
			if(check<shortestDistance && check>=1) {
				shortestDistance = check;
				closestResource = res;
			}
		}
		return closestResource;
	}
}
