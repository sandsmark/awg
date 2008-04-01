import java.awt.Point;
import java.awt.image.BufferedImage;

public class Unit {
	private Point position;
	private int maxHealth; // The unit's max health
	private int CurrentHealth; // The unit's current HP
	private int damage; // The damage the unit deals
	private int range; // The unit's attack range
	private int currentAction; // 0=stand still, 1 = move to target, 2 = attack
								// target
//	private Path path;
	
	private Point target;
	private double orientation;
	
	private double speed = 10; //How many pixels the unit moves in 1 tick
	private double maxSpeed = 10;
	private double accel = 1.5;
	
	Unit targetUnit; // This unit's target unit.
	private BufferedImage sprite; // Sprite to be drawn. The picture of the
									// unit
	private Player player;

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
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

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point p) {
		position = p;
	}

	public void setTarget(Point target) { 
		this.target = target;
		float distX = target.x - position.x;
		float distY = target.y - position.y;
		if (distX == 0) distX = 1;
		orientation = Math.atan2(distY, distX);
		speed = 0;
	}
	
	public int move() {
		if (target == null) return 0;
		else if (position.distance(target) < 2) {
			target = null;
			return 0;
		} else if (position.distance(target) < 50) speed = speed / accel;
		else if (speed < maxSpeed) speed = Math.abs(speed + accel);
		
		int newX = position.x + (int)(Math.cos(orientation) * this.speed);
		int newY = position.y + (int)(Math.sin(orientation) * this.speed);

		if (GameState.getMap().canMove(newX, newY)) this.setPosition(new Point(newX, newY));
		else target = null;
		return 1;
	}
}
