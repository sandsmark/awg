import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Building {

	JPanel menu;
	JLabel upgradeBuilding, worker, fighter, healer;
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
				setSprite(ImageIO.read(new File("resources/buildings/start2.png")));
				position = new Point(100,100); //Sette faste plasser bygningene starter?
			}
			else{
				setSprite(ImageIO.read(new File("resources/buildings/start1.gif")));
				position = new Point(900,900); //Sette faste plasser bygningene starter?
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		menu = new JPanel();
		upgradeBuilding = new JLabel();
		worker = new JLabel();
		fighter = new JLabel();
		healer = new JLabel();
		
		/*
		 * Sets the menu according to which "faction" the player is.
		 */
		if(player.isAI()){
			upgradeBuilding.setIcon(new ImageIcon("resources/buildings/end2.gif"));
			fighter.setIcon(new ImageIcon("resources/fighter/1_forward0.png"));
			worker.setIcon(new ImageIcon("resources/worker/1_forward0.png"));
			healer.setIcon(new ImageIcon("resources/healer/1_forward0.png"));
			menu.add(upgradeBuilding);
			menu.add(fighter);
			menu.add(worker);
			menu.add(healer);
		}else if(!player.isAI()){
			upgradeBuilding.setIcon(new ImageIcon("resources/buildings/end1.gif"));
			fighter.setIcon(new ImageIcon("resources/fighter/0_forward0.png"));
			worker.setIcon(new ImageIcon("resources/worker/0_forward0.png"));
			healer.setIcon(new ImageIcon("resources/healer/0_forward0.png"));
			menu.add(upgradeBuilding);
			menu.add(fighter);
			menu.add(worker);
			menu.add(healer);
	
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
	
	public void selected(){
		GameState.getMainWindow().setMenu(menu);
	}
	

}
