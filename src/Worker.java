import java.io.File;
import java.io.IOException;
import java.awt.Point;
import javax.imageio.ImageIO;

public class Worker extends Unit {
	// Bevegelses- og idrettsvitenskap for Dragvoll
	// Bygg og milj�teknikk for Dragvoll

	private int carrying; // How much resources the worker is carrying, when
							// == 10, go home or something
	Resource targetResource;

	public Worker(Player player) throws IOException {
		setMaxHealth(50);
		setCurrentHealth(getMaxHealth());
		setDamage(1);
		setRange(25);
		setCurrentAction(0);
		setPosition(new Point(player.mainHouse.getPosition().x +5, player.mainHouse.getPosition().y+5));
		setPlayer(player);
		try {
	//		if (getFaction() == 0)
			setSprite(ImageIO.read(new File("resources/dragvoll-noe.png"))); // Gl�shaugen
	//		else
	//			setSprite(ImageIO.read(new File("resources/gls-worker.png"))); // Dragvoll
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	public void deliverResource() {
		// GOTO mainhouse
		// if(getX() == mainhouse coord +- 25 || getPosition().getY() ==
		// mainhousecoord +-25){
		// int delivered = getCarrying();
		// //increase resource counter with delivered
		// setCarrying(0);
		// }
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
}
