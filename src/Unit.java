

import java.awt.Image;

public class Unit {
	public int xCoord; //The unit's
	public int yCoord; //coordinates
	public int health; //The unit's HP
	public int damage; //The damage the unit deals
	public int range; //The unit's attackrange
	public int currentAction; //0=stand still, 1 = move to target, 2 = attack target 
	public int [] currentPathX; // Path to current target
	public int [] currentPathY;
	Unit targetUnit; //This unit's target unit.
	public Image sprite; // Sprite to be drawn. The picture of the unit
	
}
