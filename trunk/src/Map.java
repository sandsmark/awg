

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Map {
	public int [][] pathMap;
	public int width;
	public int height;
	public BufferedImage[] sprite;
	int uWidth=25; // Unit width in pixels
	int uHeight=25;
	ArrayList<Unit> units = new ArrayList<Unit>();
	ArrayList<Unit> selectedUnits = new ArrayList<Unit>();
	Resource[] resources;
	Polygon waterShape;
	
	public Map (int nWidth, int nHeight) throws IOException {
		sprite = new BufferedImage[3];
		height = nHeight;
		width = nWidth; 
		units = new ArrayList<Unit>();
		pathMap = new int[width/10][height/10];
		int x,y;
		for (x=0; x<pathMap.length; x++) for (y=0; y<pathMap[0].length; y++) pathMap[x][y] = 0;
		
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
		
		for (long i=0;i<6.28;i++) {
			x = (int)(Math.cos(i)*width/4)+width/2;
			y = (int)(Math.sin(i)*height/4)+height/2;
			waterShape.addPoint(x,y);
		}
		
		for (x=0; x<pathMap.length; x++) {
			for (y=0; y<pathMap[0].length; y++){
				if (waterShape.contains(new Point(x,y))) pathMap[x][y] = -1;
				if (waterShape.contains(new Point(x+10,y))) pathMap[x][y] = -1;
				if (waterShape.contains(new Point(x,y+10))) pathMap[x][y] = -1;
				if (waterShape.contains(new Point(x+10,y+10))) pathMap[x][y] = -1;
			}
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
	
	public int [][] getPathMap (){
		return pathMap;
	}
	
	public Shape getWater(){
		return waterShape;
	}
	
	public void addUnit(Unit unit) {
		if (units.contains(unit)) return;
		units.add(unit);
	}
	
	public void removeUnit(Unit unit) {
		if (!units.contains(unit)) return;
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
	
	public void selectUnit(Unit u){
		if (selectedUnits.contains(u)) return;
		selectedUnits.add(u);
	}
	
	public void deselectUnit(Unit u){
		if (!selectedUnits.contains(u)) return;
		selectedUnits.remove(u);
	}
	
	public Unit getSelectedUnit(int num){
		return selectedUnits.get(num);
	}
	
	public int getSelectedUnitNum() {
		return selectedUnits.size();
	}
	
	public void selectUnits(int x1, int y1, int x2, int y2){
		if (x1>x2) x1=(x1^=x2)^(x2^=x1); // swap x1 and x2, ^=xor
		if (y1>y2) y1=(y1^=y2)^(y2^=y1);
		int x,y;
		for (int i=0; i<getUnitNum(); i++){
			x = getUnit(i).getPosition().x;
			y = getUnit(i).getPosition().y;
			if ((y1<y)&&(y2>y)&&(x1<x)&&(x2>x))
				selectUnit(getUnit(i));
		}
	}
	
	public boolean hasSelectedUnit(Unit u){
		return selectedUnits.contains(u);
	}
	
	public void selectUnit(int x, int y) {
		//TODO: Fix this mess, look at how nice his cousin is (selectUnit(int, int, int, int)) kthxbye
		selectedUnits.clear();
		int x1 = x - uHeight;
		int y1 = y - uHeight;
		int x2 = x1 + uHeight;
		int y2 = y1 + uHeight;
//		System.out.println("x1:" + x1 + " y1: " + y1 + " x2: " + x2 + " y2: " + y2);
		for (int i=0; i<getUnitNum(); i++){
//			System.out.println("unit :: x:" + getUnit(i).getX() + " y:" + getUnit(i).getY());
			if ((y1 < getUnit(i).getPosition().y) && (y2 > getUnit(i).getPosition().y) && 
					(x1 < getUnit(i).getPosition().x) && (x2 > getUnit(i).getPosition().x)){
//				System.out.println("o hai");
				selectUnit(getUnit(i));
			}
		}
	}
	
	public void moveSelectedTo(int tarX, int tarY){
		Path path;
		for (int i=0;i<getSelectedUnitNum(); i++){
			path = new Path(this, getSelectedUnit(i).getPosition().x, getSelectedUnit(i).getPosition().y, tarX, tarY);
			getSelectedUnit(i).setPath(path);
		}
	}
	
}
