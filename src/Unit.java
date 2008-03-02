

import java.awt.Image;

public class Unit {
	public int xCoord; //The unit's
	public int yCoord; //coordinates
	public int maxHealth; //The unit's max health
	public int CurrentHealth; //The unit's current HP
	public int damage; //The damage the unit deals
	public int range; //The unit's attackrange
	public int currentAction; //0=stand still, 1 = move to target, 2 = attack target 
	public int [] currentPathX; // Path to current target
	public int [] currentPathY;
	Unit targetUnit; //This unit's target unit.
	public Image sprite; // Sprite to be drawn. The picture of the unit
	
	
	public int getXCoord() {
		return xCoord;
	}
	public void setXCoord(int coord) {
		xCoord = coord;
	}
	public int getYCoord() {
		return yCoord;
	}
	public void setYCoord(int coord) {
		yCoord = coord;
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
	
}
