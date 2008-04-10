import java.awt.Point;

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
	protected Path path;
	
	protected double speed = 0; //How many pixels the unit moves in 1 tick
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
		this.goTo(this.targetUnit.position);
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
	
	public void goTo(Point target) { 
		this.path = new Path(this.position, target);
		this.target = path.getNext();
	}
	
	/**
	 * This sets the current target, and turns the unit in the right direction.
	 * @param target
	 */
	private void setTarget(Point target) { 
		this.target = target;
		float distX = target.x - position.x;
		float distY = target.y - position.y;
		orientation = Math.atan2(distY, distX);
		
		orientation += Math.PI; // TODO: Ugly hack
		if (orientation < Math.PI/4 || orientation > 7 * Math.PI / 4) sprite.setDirection(Sprite.Direction.LEFT);
		else if (orientation > Math.PI/4 && orientation < 3 * Math.PI / 4) sprite.setDirection(Sprite.Direction.BACK);
		else if (orientation >  3 * Math.PI/4 && orientation < 5 * Math.PI / 4) sprite.setDirection(Sprite.Direction.RIGHT);
		else if (orientation >  5 * Math.PI/4 && orientation < 7 * Math.PI / 4) sprite.setDirection(Sprite.Direction.FORWARD);
		orientation -= Math.PI;
	}
	
	/**
	 * This moves the unit along it's path.
	 * @return
	 */
	public void move() {
		if (speed < 2) sprite.setMoving(false);
		else sprite.setMoving(true);
		
		if (target == null) {
			return;
		}
		
		
		else if (position.distance(target) <25) {
			if (path.isEmpty()) {
				target = null;
				speed = 0;
				return;
			} else {
				this.setTarget(path.getNext());
			}
		} 
		else if (position.distance(target) < 15) speed = speed / 1.5;
		else if (speed < maxSpeed) speed = Math.abs(speed + accel);
		
		int newX = position.x + Math.round(Math.round(Math.cos(orientation) * this.speed));
		int newY = position.y + Math.round(Math.round(Math.sin(orientation) * this.speed));
		
		this.setPosition(new Point(newX, newY));
		GameState.getMainWindow().canvas.setDirty(newX, newY);
		
		return;
	}
	
	

	/*
	 * Skal brukes p� en unit som har funnet et target. 
	 * Kan kj�res hver gang den beveger seg, og se om den er innenfor range,
	 * om den er det, gj�r den skade p� uniten den har som targetUnit
	 */
	
	public void dealDamage(){
		targetUnit.CurrentHealth -= damage;
		if(targetUnit.CurrentHealth<=0){
			GameState.getUnits().removeUnit(targetUnit);
			this.targetUnit = null;
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public void setTargetResource(Resource r) {}
}

