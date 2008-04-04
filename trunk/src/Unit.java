import java.awt.Point;
import java.awt.image.BufferedImage;

public class Unit {
	protected Point position;
	protected int maxHealth; // The unit's max health
	protected int CurrentHealth; // The unit's current HP
	protected int damage; // The damage the unit deals
	protected int range; // The unit's attack range
	protected int currentAction; // 0=stand still, 1 = move to target, 2 = attack
	
	protected Sprite sprite; 
	
	protected Point target;
	protected double orientation;
	
	protected double speed = 10; //How many pixels the unit moves in 1 tick
	protected double maxSpeed = 5;
	protected double accel = 1.5;
	
	protected String type;
	
	Unit targetUnit; // This unit's target unit.
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


	public Point getPosition() {
		return position;
	}

	public void setPosition(Point p) {
		position = p;
	}

	public Sprite getSprite() {
		return sprite;
	}
	
	public void setTarget(Point target) { 
		this.target = target;
		float distX = target.x - position.x;
		float distY = target.y - position.y;
		if (distX == 0) distX = 1;
		orientation = Math.atan2(distY, distX);
		
		orientation += Math.PI; // TODO: Ugly hack
		if (orientation < Math.PI/4 || orientation > 7 * Math.PI / 4) sprite.setDirection(Sprite.Direction.LEFT);
		else if (orientation > Math.PI/4 && orientation < 3 * Math.PI / 4) sprite.setDirection(Sprite.Direction.BACK);
		else if (orientation >  3 * Math.PI/4 && orientation < 5 * Math.PI / 4) sprite.setDirection(Sprite.Direction.RIGHT);
		else if (orientation >  5 * Math.PI/4 && orientation < 7 * Math.PI / 4) sprite.setDirection(Sprite.Direction.FORWARD);
		orientation -= Math.PI;
		
		speed = 0;
	}
	
	public int move() {
		if (target == null) {
			return 0;
		}
		else if (position.distance(target) < 5) {
			
			target = null;
			return 0;
		} 
		else if (position.distance(target) < 15) speed = speed / 1.5;
		else if (speed < maxSpeed) speed = Math.abs(speed + accel);
		
		int newX = position.x + (int)(Math.cos(orientation) * this.speed);
		int newY = position.y + (int)(Math.sin(orientation) * this.speed);
		
		if (GameState.getMap().canMove(newX, newY)) this.setPosition(new Point(newX, newY));
		else target = null;
		
		if (speed < 2) sprite.setMoving(false);
		else sprite.setMoving(true);
		
		return 1;
	}
	
	
	/* 
	 * Brukes på en unit som skal ta skade av en annen. Kjøres fra dealDamage.
	 */
	public void takeDamage(int damage){
		this.CurrentHealth -= damage;
		if(this.CurrentHealth<=0){
			GameState.getUnits().removeUnit(this);
		}
	}
	/*
	 * Skal brukes p� en unit som har funnet et target. 
	 * Kan kj�res hver gang den beveger seg, og se om den er innenfor range,
	 * om den er det, gj�r den skade p� uniten den har som targetUnit
	 */
	
	public void dealDamage(){
		if((position.distance(targetUnit.position.getX(), targetUnit.position.getY())<5) && targetUnit.player != this.player){
			targetUnit.takeDamage(this.damage);
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public int getCurHealth() {
		return getMaxHealth() - getDamage();
	}
}

