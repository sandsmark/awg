package com.googlecode.awg;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.googlecode.awg.state.Config;
import com.googlecode.awg.state.GameState;
import com.googlecode.awg.state.Player;

/**
 * 
 * @author Magnus Fjell
 *
 */
public class Building {
	private Point position;
	public int maxHealth; // How many HP the building has
	public int currentHealth;
	public BufferedImage sprite;
	private int buildingLevel; //1 == original, 2 == upgraded
	private Player player; //The owner of the building
	private static final int upgradeCost = 10000;
	
	/**
	 * The constructor for Building, setting values to variables
	 * and adds the building to the game.
	 * Sets the player given as parameter as owner.
	 * @param player
	 */
	public Building(Player player) {
		buildingLevel = 1;
		setMaxHealth(100000);
		setPlayer(player);
		try {
			if(player.isAI()){
				setSprite(ImageIO.read(getClass().getResource("/buildings/start2.png")));
				position = new Point(Config.getWorldWidth() - 100, Config.getWorldHeight() - 100);
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
		this.currentHealth = health;
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
	
	/**
	 * Used by a building when it upgrades
	 */
	public void upgradeBuilding(){
		if(buildingLevel == 1 && getPlayer().getResources()>upgradeCost){
			try{
				if(!player.isAI()){
					GameState.getHuman().getMainBuilding().setSprite(ImageIO.read(new File("resources/buildings/end1.gif")));
				}else if(player.isAI()){
					GameState.getComputer().getMainBuilding().setSprite(ImageIO.read(new File("resources/buildings/end2.gif")));
				}
				buildingLevel = 2;
				getPlayer().setDamage(getPlayer().getDamage() * 2);
				getPlayer().decreaseResources(upgradeCost);
			}catch(IOException IOE){
				System.out.println("Error loading updated building image");
			}
			GameState.getMainWindow().getCanvas().updateInternal();
			System.out.println("Upgraded building");
		}
	}
	

	/**
	 * used by a building when it takes damage
	 * @param damage
	 */
	public void takeDamage(int damage){
		this.currentHealth -= damage;
		if(currentHealth <= 0 && getSprite() != null){
			GameState.getMainWindow().canvas.setDirty(this.position.x, this.position.y, this.position.x + this.sprite.getWidth(), this.position.y + this.sprite.getHeight());
			setSprite(null);
			GameState.getMainWindow().victory(this.getPlayer().isAI());
			
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
	
	public boolean isTouching(int x, int y) {
		return x > position.x && x < position.x + sprite.getWidth() &&
		y > position.y && y < position.y + sprite.getHeight(); 
	}

}
