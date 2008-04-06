import java.awt.Point;
public class Healer extends Unit {

	private int mana;


	public Healer(Player player) {
		type = "healer";
		setMana(100);
		setMaxHealth(75);
		setCurrentHealth(getMaxHealth());
		setCurrentAction(0);
		setDamage(3);
		setPosition(new Point(player.mainHouse.getPosition().x +5, player.mainHouse.getPosition().y+5));
		if (player.isAI()) sprite = new Sprite("healer", 1);
		else sprite = new Sprite(type, 0);
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
