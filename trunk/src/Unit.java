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
	private Point currentTarget;
	Unit targetUnit; //This unit's target unit.
	Resource targetResource;
	private BufferedImage sprite; // Sprite to be drawn. The picture of the unit
	private int faction;
	private double speed = 2.5;
	private double lastMove = 0;
	
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
		lastMove = System.currentTimeMillis();
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
	
	
	public int move () {
		if (path == null ||path.getPath() == null) return 0; // Return codes: 0 = Not moved, do not repaint, 1 = repaint
		int dMove = (int)((System.currentTimeMillis() - lastMove) / 100 * speed);
		lastMove = System.currentTimeMillis();
		if (currentTarget == null) currentTarget = path.pop();
		if (currentTarget.distance(position) < 5) {
			if (path.getLength() > 0)
				currentTarget = path.pop();
			else {
				path = null;
				return 0;
			}
		}
		
		int newX = position.x;
		int newY = position.y;
		
		if (currentTarget.x * 10 > position.x) newX = position.x + dMove;
		if (currentTarget.x * 10 < position.x) newX = position.x - dMove;
		if (currentTarget.y * 10 > position.y) newY = position.y + dMove;
		if (currentTarget.y * 10 < position.y) newY = position.y - dMove;
		System.out.println("moving unit::::::");
		
		setPosition (new Point(newX, newY));
		if (newX != position.x || newY != position.y) return 1;
		else return 0;
	}
}
