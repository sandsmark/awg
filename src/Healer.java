import java.awt.image.BufferedImage;

public class Healer extends Unit {

	private int mana;

	public Healer(Player owner, int buildingXcoord, int buildingYcoord) {
		int xCoord = buildingXcoord + 5;
		int yCoord = buildingYcoord + 5;
		setMana(100);
		setMaxHealth(75);
		setCurrentHealth(getMaxHealth());
		setCurrentAction(0);
		setDamage(10);
//		if (ownaer == GameState.getHuman()) // GameState.getPlayers();
//			setSprite(new BufferedImage(0, 0, 0)); // Gl�shaugen
//		else if (faction == 2)
//			setSprite(new BufferedImage(0, 0, 0)); // Dragvoll

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
