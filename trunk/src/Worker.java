import java.io.IOException;
import java.awt.Point;

public class Worker extends Unit {
	// Bevegelses- og idrettsvitenskap for Dragvoll
	// Bygg og milj�teknikk for Dragvoll

	private int carrying; // How much resources the worker is carrying, when
							// == 10, go home or something
	Resource targetResource;
	private int maxCarrying = 10;
	private int harvestMax = 5;
	
	public Worker(Player player) throws IOException {
		setMaxHealth(50);
		setCurrentHealth(getMaxHealth());
		setDamage(1);
		setRange(25);
		setCurrentAction(0);
		setPosition(new Point(player.mainHouse.getPosition().x +5, player.mainHouse.getPosition().y+5));
		setPlayer(player);
		if (player.isAI()) sprite = new Sprite("worker", 1);
		else sprite = new Sprite("worker", 0);
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
		if (targetResource != null) {
			if (this.getCarrying()>0 && this.getPlayer().mainHouse.getPosition().distance(position) < 5) {
				deliverResource();
				this.target = targetResource.position;
			} else if (this.getCarrying() >= maxCarrying) {
				this.target = this.getPlayer().mainHouse.getPosition();
			} else if (this.targetResource.position.distance(this.position) < 5) {
				this.target = null;
				this.setCarrying(this.getCarrying() + targetResource.harvest(harvestMax));
			}
		}
		return super.move();
	}
}
