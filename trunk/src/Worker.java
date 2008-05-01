import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * @author Magnus Fjell
 *
 */
public class Worker extends Unit {

	private int carrying; // How much resources the worker is carrying, when
							// == 10, go home or something
	Resource targetResource;
	private int maxCarrying = 500;
	private int harvestMax = 5;
	private boolean delivering = false;
	protected static double damageMultiplier = 1;
	public static int cost = 200;
	
	/**
	 * Constructor setting values to variables and adding the unit to the game.
	 * Sets the owner of the unit to the player given as parameter
	 * @param player
	 */
	public Worker(Player player){
		player.decreaseResources(cost);
		
		type = "worker";
		setMaxHealth(500);
		setCurrentHealth(getMaxHealth());
		setRange(25);
		setCurrentAction(0);
		setPosition(new Point(player.mainHouse.getPosition().x +5, player.mainHouse.getPosition().y+5));
		setPlayer(player);
		if (player.isAI()) sprite = new Sprite(type, 1);
		else sprite = new Sprite(type, 0);
		GameState.getMainWindow().canvas.setDirty(position.x, position.y, position.x + sprite.getWidth(), position.y + sprite.getHeight());
	}

	/**
	 * Used by the worker when he delivers resources at the mainbuilding
	 */
	public void deliverResource() {
		this.getPlayer().increaseResources(carrying);
		carrying = 0;
	}

	public int getCarrying() {
		return carrying;
	}

	public void setCarrying(int carrying) {
		this.carrying = carrying;
	}

	
	public String toString() {
		return "Worker(x:" + getPosition().x + ",y" + getPosition().y + ")";
	}
	
	/**
	 * The move method specific to Worker
	 * Harvests the resource node sat as targetResource
	 * and delivers when full
	 */
	public void move() {
		Point house = getPlayer().mainHouse.getPosition();
		if (targetResource != null) {
			if (this.getCarrying() > 0 && house.distance(position) < 55 && delivering) {
				deliverResource();
				this.goTo(targetResource.position);
				delivering = false;
				sprite.setDoing(false);
			} else if (this.getCarrying() >= maxCarrying && !delivering) {
				sprite.setDoing(false);
				this.goTo(this.getPlayer().mainHouse.getPosition());
				delivering = true;
			} else if (this.targetResource.position.distance(this.position) < 55 && !delivering){
				/**
				 * Harvest!
				 */
				sprite.setDoing(true);
				GameState.getMainWindow().canvas.setDirty(position.x, position.y, position.x + sprite.getWidth(), position.y + sprite.getHeight());
				this.setCarrying(this.getCarrying() + targetResource.harvest(harvestMax));
				if (targetResource.getRemaining() <= 0){
					try{
						targetResource.setSprite(ImageIO.read(new File("resources/grass.png")));
					}catch(IOException IOE){
						System.err.println("Could not load empty resource sprite!");
					}
					targetResource = null;
					sprite.setDoing(false);
				}
			}
		} else if (this.getCarrying() != 0 && !delivering) {
			this.goTo(house);
			sprite.setDoing(false);
			delivering = true;
		}
		
		super.move();
	}

	public Resource getTargetResource() {
		return targetResource;
	}

	public void setTargetResource(Resource targetResource) {
		this.targetResource = targetResource;
	}

	public int getMaxCarrying() {
		return maxCarrying;
	}

	public void setMaxCarrying(int maxCarrying) {
		this.maxCarrying = maxCarrying;
	}

	public int getHarvestMax() {
		return harvestMax;
	}

	public void setHarvestMax(int harvestMax) {
		this.harvestMax = harvestMax;
	}

	public int getDamage() {
		return 0;
	}
}
