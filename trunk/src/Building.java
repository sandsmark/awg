import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Building {

	private Point position;
	public int health; // How many HP the building has
	public Image sprite;
	private int buildingLevel; //1 == original, 2 == upgraded
	
	public Building(Player player) throws IOException{
		buildingLevel = 1;
		setHealth(1000);
		
		if(player.isAI()){
			setSprite(ImageIO.read(new File("resources/buildings/start2.gif")));
			position = new Point(100,100); //Sette faste plasser bygningene starter?
		}
		else{
			setSprite(ImageIO.read(new File("resources/buildings/start1.gif")));
			position = new Point(2400,2400); //Sette faste plasser bygningene starter?
		}
		
		
	}
	
	public Point getPosition() {
		return this.position;
	}

	
	public void setPosition(Point position) {
		this.position = position;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public Image getSprite() {
		return sprite;
	}

	public void setSprite(Image sprite) {
		this.sprite = sprite;
	}


	public int getBuildingLevel() {
		return buildingLevel;
	}


	public void setBuildingLevel(int buildingLevel) {
		this.buildingLevel = buildingLevel;
	}
	
	public void spawn (int type, Player owner) { // 1 = Healer, 2 = worker, 3 = Fighter
		Unit unit;
//		if (type == 1) unit = new Healer(owner);
//		else if (type == 2) unit = new Worker(owner);
//		else if (type == 3) unit = new Fighter(owner, position.x, position.y);
//		else return;
//		GameState.getUnits().addUnit(unit);
	}

}