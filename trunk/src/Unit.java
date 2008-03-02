

import java.awt.Image;

public class Unit {
	public int xCoord; //Koordinatene
	public int yCoord; //til uniten selv
	public int health; //Hvor mye hp uniten har
	public int damage; //Damage uniten tar
	public int range; //
	public int currentAction; //0=stand still, 1 = move to target, 2 = attack target 
	public int [] currentPathX; // Path to current target
	public int [] currentPathY;
	Unit targetUnit; //Uniten den har som target. Her kommer currentTarget til �v�e targetUnit.[x|y]Coord
	public Image sprite; // Sprite to be drawn
	
}
