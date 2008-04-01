import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Healer extends Unit {

	private int mana;


	public Healer(Player player) throws IOException {
		
		setMana(100);
		setMaxHealth(75);
		setCurrentHealth(getMaxHealth());
		setCurrentAction(0);
		setDamage(3);
		setPosition(new Point(player.mainHouse.getPosition().x +5, player.mainHouse.getPosition().y+5));
		if (player.isAI())
			setSprite(ImageIO.read(new File("resources/dragvoll-noe.png")));
		else
			setSprite(ImageIO.read(new File("resources/dragvoll-noe.png")));
	}

	public void heal(Unit target) {
		if (mana >= 10) {
			target.setCurrentHealth(target.getCurrentHealth() + 50);
			this.setMana(getMana() - 10);
		}
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}
}
