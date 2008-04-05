import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Building {
	private Point position;
	public int health; // How many HP the building has
	public BufferedImage sprite;
	private int buildingLevel; //1 == original, 2 == upgraded
	private Player player; //The owner of the building
	
	public Building(Player player) {
		buildingLevel = 1;
		setHealth(1000);
		setPlayer(player);
		try {
			if(player.isAI()){
				setSprite(ImageIO.read(new File("resources/buildings/start2.gif")));
				position = new Point(900,900); //Sette faste plasser bygningene starter?
			}
			else{
				setSprite(ImageIO.read(new File("resources/buildings/start1.png")));
				position = new Point(100,100); //Sette faste plasser bygningene starter?
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
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

	public BufferedImage getSprite() {
		return sprite;
	}

	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}

	public int getBuildingLevel() {
		return buildingLevel;
	}

	public void setBuildingLevel(int buildingLevel) {
		this.buildingLevel = buildingLevel;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
//	public void spawn (String type, Player owner) { // 1 = Healer, 2 = worker, 3 = Fighter
//		Unit unit;
//		if (type.equalsIgnoreCase("healer")) unit = new Healer(owner);
//		else if (type.equalsIgnoreCase("worker")) unit = new Worker(owner);
//		else if (type.equalsIgnoreCase("fighter")) unit = new Fighter(owner);
//		else return;
//		GameState.getUnits().addUnit(unit);
//	}
	

	/*
	 * Sets that the building menu should be 
	 * displayed when it is selected.
	 * How we do that, martin fixxxes,
	 * prolly something with listeners and 
	 * if selected == building and stuff.
	 */
	

}
