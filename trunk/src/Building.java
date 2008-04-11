import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Building {
	private Point position;
	public int maxHealth; // How many HP the building has
	public int currentHealth;
	public BufferedImage sprite;
	private int buildingLevel; //1 == original, 2 == upgraded
	private Player player; //The owner of the building
	private static final int upgradeCost = 1000;
	
	public Building(Player player) {
		buildingLevel = 1;
		setMaxHealth(1000);
		setPlayer(player);
		try {
			if(player.isAI()){
				setSprite(ImageIO.read(getClass().getResource("/buildings/start2.png")));
				position = new Point(Config.getWorldWidth() - 100, Config.getUnitHeight() - 100);
			}
			else{
				setSprite(ImageIO.read(getClass().getResource("/buildings/start1.gif")));
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

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int health) {
		this.maxHealth = health;
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
	
	public void upgradeBuilding(){
		if(buildingLevel == 1 && getPlayer().getResources()>upgradeCost){
			try{
				if(!player.isAI()){
					GameState.getHuman().getMainBuilding().setSprite(ImageIO.read(new File("resources/buildings/end1.gif")));
				}else if(player.isAI()){
					GameState.getComputer().getMainBuilding().setSprite(ImageIO.read(new File("resources/buildings/end2.gif")));
				}
				buildingLevel = 2;
				getPlayer().decreaseResources(upgradeCost);
			}catch(IOException IOE){
				System.out.println("Error loading updated building image");
			}
			GameState.getMainWindow().getCanvas().updateInternal();
			System.out.println("Upgraded building");
		}
	}
	

	public void takeDamage(int damage){
		this.currentHealth -= damage;
		if(currentHealth <= 0){
			System.out.println("OMG WIN");
			setSprite(null);
		}
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}

	public static int getUpgradeCost() {
		return upgradeCost;
	}

}
