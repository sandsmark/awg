

import java.awt.Image;
import java.awt.geom.Point2D;

public class Unit {
	private Point2D position;
	private int maxHealth; //The unit's max health
	private int CurrentHealth; //The unit's current HP
	private int damage; //The damage the unit deals
	private int range; //The unit's attackrange
	private int currentAction; //0=stand still, 1 = move to target, 2 = attack target 
	private int [] currentPathX; // Path to current target
	private int [] currentPathY;
	Unit targetUnit; //This unit's target unit.
	private Image sprite; // Sprite to be drawn. The picture of the unit
	
	
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
	public int[] getCurrentPathX() {
		return currentPathX;
	}
	public void setCurrentPathX(int[] currentPathX) {
		this.currentPathX = currentPathX;
	}
	public int[] getCurrentPathY() {
		return currentPathY;
	}
	public void setCurrentPathY(int[] currentPathY) {
		this.currentPathY = currentPathY;
	}
	public Unit getTargetUnit() {
		return targetUnit;
	}
	public void setTargetUnit(Unit targetUnit) {
		this.targetUnit = targetUnit;
	}
	public Image getSprite() {
		return sprite;
	}
	public void setSprite(Image sprite) {
		this.sprite = sprite;
	}
	public Point2D getPosition() {
		return position;
	}
	public void setPosition(Point2D position) {
		this.position = position;
	}
	
}
