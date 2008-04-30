import java.awt.Point;

/**
 * 
 * @author Magnus Fjell
 *
 */
public class Healer extends Unit {

	private int mana;
	private int maxMana;
	final static int healPower = 50; //Sette denne etter hvor mye den skal heale
	private double timer = System.currentTimeMillis();
	protected static double damageMultiplier = 0.5;
	public static int cost = 750;


	/**
	 * Constructor setting values to variables and adding the unit to the game.
	 * Sets the owner of the unit to the player given as parameter
	 * @param player
	 */
	public Healer(Player player) {
		player.decreaseResources(cost);
		type = "healer";
		timer = System.currentTimeMillis();
		setMana(100);
		setMaxMana(getMana());
		setMaxHealth(75);
		setCurrentHealth(getMaxHealth());
		setCurrentAction(0);
		setDamage(3);
		setPlayer(player);
		setPosition(new Point(player.mainHouse.getPosition().x +5, player.mainHouse.getPosition().y+5));
		if (player.isAI()) sprite = new Sprite("healer", 1);
		else sprite = new Sprite(type, 0);
		GameState.getMainWindow().canvas.setDirty(position.x, position.y, position.x + sprite.getWidth(), position.y + sprite.getHeight());
	}

	/**
	 * Used when a healer heals
	 */
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
		if(this.mana + mana >= getMaxMana()){
			this.mana = getMaxMana();
			return;
		}else if (mana <=0){
			this.mana = 0;
			return;
		}
		
		this.mana = mana;
	}
	
	/**
	 * The move method special for healer. Regens mana, and searches for units to heal
	 */
	public void move(){
		Unit unit;
		
		if(timer +1000 < System.currentTimeMillis() ){
			setMana(getMana() + 1);
			timer = System.currentTimeMillis();
		}
		
		if(targetUnit == null){
			for (int i = 0; i < GameState.getUnits().count(); i++) {
				unit = GameState.getUnits().getUnit(i);
				if(unit.getPlayer() == this.getPlayer() && position.distance(unit.position) < 100 && unit.getCurrentHealth() <= (unit.getMaxHealth()-healPower)){
					targetUnit = unit;
					heal();
					targetUnit = null;
					sprite.setDoing(true);
				} else sprite.setDoing(false);
			}
		}
		super.move();
	}

	public int getMaxMana() {
		return maxMana;
	}

	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}
}
