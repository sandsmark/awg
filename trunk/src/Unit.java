import java.awt.Point;
import java.awt.image.BufferedImage;

public class Unit {
	private Point position;
	private int maxHealth; //The unit's max health
	private int CurrentHealth; //The unit's current HP
	private int damage; //The damage the unit deals
	private int range; //The unit's attack range
	private int currentAction; //0=stand still, 1 = move to target, 2 = attack target 
	private Path path;
	private int currentTargetX = -1;
	private int currentTargetY = -1;
	Unit targetUnit; //This unit's target unit.
	Resource targetResource;
	private BufferedImage sprite; // Sprite to be drawn. The picture of the unit
	private int faction;
	
	public int getFaction() {
		return faction;
	}
	public void setFaction(int faction) {
		this.faction = faction;
	}
	public int getMaxHealth() {
		return maxHealth;
	}
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
	public int getCurrentHealth() {
		return CurrentHealth;
	}
	public void setCurrentHealth(int currentHealth) {
		CurrentHealth = currentHealth;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
	}
	public int getCurrentAction() {
		return currentAction;
	}
	public void setCurrentAction(int currentAction) {
		this.currentAction = currentAction;
	}
	
	public Path getPath() {
		return path;
	}
	public void setPath(Path path) {
		this.path = path;
	}
	public Unit getTargetUnit() {
		return targetUnit;
	}
	public void setTargetUnit(Unit targetUnit) {
		this.targetUnit = targetUnit;
	}
	public BufferedImage getSprite() {
		return sprite;
	}
	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}


	public Point getPosition () {
		return position;
	}
	public void setPosition (Point p) {
		position = p;
	}
	
	public int getCurrentTargetX() {
		return currentTargetX;
	}
	public void setCurrentTargetX(int currentTargetX) {
		this.currentTargetX = currentTargetX;
	}
	public int getCurrentTargetY() {
		return currentTargetY;
	}
	public void setCurrentTargetY(int currentTargetY) {
		this.currentTargetY = currentTargetY;
	}
}
