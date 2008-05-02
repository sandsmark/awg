import java.awt.Point;

/**
 * 
 * @author Magnus Fjell
 *
 */
public class Healer extends Unit {

	private int mana;
	private int maxMana;
	final static int healPower = 20; //Sette denne etter hvor mye den skal heale
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
		this.mana = 100;
		setMaxMana(getMana());
		setMaxHealth(75);
		setCurrentHealth(getMaxHealth());
		setCurrentAction(0);
		setPlayer(player);
		setPosition(new Point(player.mainHouse.getPosition().x +5, player.mainHouse.getPosition().y+5));
		if (player.isAI()) sprite = new Sprite("healer", 1,this);
		else sprite = new Sprite(type, 0,this);
		GameState.getMainWindow().canvas.setDirty(position.x, position.y, position.x + sprite.getWidth(), position.y + sprite.getHeight());
	}

	/**
	 * Used when a healer heals
	 * @param unit
	 */
	public void heal(Unit unit) {
		if (mana >= 10) {
			unit.setCurrentHealth(unit.getCurrentHealth() + healPower);
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
		
		if(timer +1000 < System.currentTimeMillis() ){
			setMana(getMana() + 1);
			timer = System.currentTimeMillis();
		}
		
		if(targetUnit == null){
			for (Unit unit : GameState.getUnits().getUnits()) {
				if(unit.getPlayer().equals(this.getPlayer()) && position.distance(unit.position) < 100 && unit.getCurrentHealth() <= (unit.getMaxHealth()-healPower)){
					heal(unit);
					sprite.setDoing(true);
				} else sprite.setDoing(false);
			}
		}else if(targetUnit.getCurrentHealth() <= (targetUnit.getMaxHealth()-healPower)){
			heal(targetUnit);
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
