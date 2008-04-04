import java.awt.Point;

public class Worker extends Unit {
	// Bevegelses- og idrettsvitenskap for Dragvoll
	// Bygg og milj�teknikk for Dragvoll

	private int carrying; // How much resources the worker is carrying, when
							// == 10, go home or something
	Resource targetResource;
	private int maxCarrying = 500;
	private int harvestMax = 5;
	
	public Worker(Player player){
		type = "worker";
		setMaxHealth(50);
		setCurrentHealth(getMaxHealth());
		setDamage(1);
		setRange(25);
		setCurrentAction(0);
		setPosition(new Point(player.mainHouse.getPosition().x +5, player.mainHouse.getPosition().y+5));
		setPlayer(player);
		if (player.isAI()) sprite = new Sprite(type, 1);
		else sprite = new Sprite(type, 0);
	}

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

	@Override
	public String toString() {
		return "Worker(x:" + getPosition().x + ",y" + getPosition().y + ")";
	}
	
	@Override
	public int move() {
		Point mainPos = getPlayer().mainHouse.getPosition();
		if (targetResource != null) {
			if (this.getCarrying() > 0 && mainPos.distance(position) < 25 && !targetResource.position.equals(target)) {
				deliverResource();
				this.setTarget(targetResource.position);
			} else if (this.getCarrying() >= maxCarrying && !getPlayer().mainHouse.getPosition().equals(target)) {
				this.setTarget(this.getPlayer().mainHouse.getPosition());
			} else if (this.targetResource.position.distance(this.position) < 25) {
				/**
				 * Harvest!
				 */
				this.target = null;
				this.setCarrying(this.getCarrying() + targetResource.harvest(harvestMax));
				if (targetResource.getRemaining() <= 0) targetResource = null;
			}
		} else if (this.getCarrying() != 0 && !getPlayer().mainHouse.getPosition().equals(target)) { 
			this.setTarget(this.getPlayer().mainHouse.getPosition());
		}
		return super.move();
	}

	public Resource getTargetResource() {
		return targetResource;
	}

	public void setTargetResource(Resource targetResource) {
		this.targetResource = targetResource;
	}
}
