import java.awt.Point;
public class Healer extends Unit {

	private int mana;
	final static int healPower = 50; //Sette denne etter hvor mye den skal heale

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

	public void heal() {
		if (mana >= 10) {
			targetUnit.setCurrentHealth(targetUnit.getCurrentHealth() + healPower);
			this.setMana(getMana() - 10);
		}
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}
	
	public int move(){
		Unit unit;
		if(targetUnit == null){
			for (int i = 0; i < GameState.getUnits().count(); i++) {
				unit = GameState.getUnits().getUnit(i);
				if(unit.getPlayer() == this.getPlayer() && position.distance(unit.position) < 100 && unit.getCurrentHealth() < (unit.getMaxHealth()-healPower)){
					targetUnit = unit;
					heal();
					targetUnit = null;
				}
			}
		}
		return super.move();
	}
}
