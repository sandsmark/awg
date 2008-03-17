

import java.awt.Polygon;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Map {
	public int [][] map;
	public int width;
	public int height;
	public BufferedImage[] sprite;
	ArrayList<Unit> units;
	Resource[] resources;
	Polygon waterShape;
	
	public Map (int nWidth, int nHeight) throws IOException {
		sprite = new BufferedImage[3];
		height = nHeight;
		width = nWidth; 
		units = new ArrayList<Unit>();
		
		try {
		    sprite[0] = ImageIO.read(new File("resources/grass.png"));
		    sprite[1] = ImageIO.read(new File("resources/water.png"));
		    sprite[2] = ImageIO.read(new File("resources/trees.png"));
		} catch (IOException e) {
			System.err.println("Could not load sprite!");
			System.exit(1);
		}
		System.out.println(width + "."+height);
		// Add water to the map
		waterShape = new Polygon();
		int x,y;
		for (long i=0;i<6.28;i++) {
			x = (int)(Math.cos(i)*width/4)+width/2;
			y = (int)(Math.sin(i)*height/4)+height/2;
			waterShape.addPoint(x,y);
		}
		
		// Add resources to the map
		resources = new Resource[(int)Math.random()*5+5];
		for (int i=0;i<resources.length;i++){
			x = (int)(Math.cos(i)*width/3)+width/2;
			y = (int)(Math.sin(i)*width/3)+width/2;
			resources[i] = new Resource((int)Math.random()*20+20,x,y);
		}
	}
	
	public int getHeight () {
		return height;
	}
	
	public int getWidth () {
		return width;
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
	
	public Shape getWater(){
		return waterShape;
	}
	
	public void addUnit(Unit unit) {
		units.add(unit);
	}
	
	public void removeUnit(Unit unit) {
		units.remove(unit);
	}
	
	public Unit getUnit(int i){
		return units.get(i);
	}
	
	public int getUnitNum(){
		return units.size();
	}
	
	public Resource getResource(int i){
		return resources[i];
	}
	
	public int getResourceNum(){
		return resources.length;
	}
}
