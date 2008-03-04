

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Map {
	public int [][] map;
	public BufferedImage[] sprite;
	
	public Map (int width, int height) throws IOException {
		sprite = new BufferedImage[3];
		map = new int[width][height];
		try {
		    sprite[0] = ImageIO.read(new File("resources/grass.png"));
		    sprite[1] = ImageIO.read(new File("resources/water.png"));
		    sprite[2] = ImageIO.read(new File("resources/trees.png"));
		} catch (IOException e) {
			System.err.println("Could not load sprite!");
		}
		
		for (int x=0; x<width; x++){
			for (int y=0; y<height; y++){
				map[x][y] = (int)(Math.random() * 3);
			}
		}
	}
	
	public int getHeight () {
		return map[0].length;
	}
	
	public int getWidth () {
		return map.length;
	}
	
	public int getNode(int x, int y){
		if (x<0||x > getWidth()) {
			System.err.println(x);
			return 0;
		}
		if (y<0||y > getHeight()) {
			System.err.println(y);
			return 0;
		}
		return map[x][y];
	}
}
