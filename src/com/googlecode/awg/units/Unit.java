package com.googlecode.awg.units;
import java.awt.Point;

import com.googlecode.awg.state.Config;
import com.googlecode.awg.state.GameState;
import com.googlecode.awg.state.Player;


/**
 * 
 * @author Magnus Fjell
 *
 */
public class Unit {
	protected Point position;
	protected int maxHealth; // The unit's max health
	protected int currentHealth; // The unit's current HP
	protected int range; // The unit's attack range
	protected int currentAction; // 0=stand still, 1 = move to target, 2 = attack
	
	public Sprite sprite; 
	
	protected Point target;
	protected double orientation;
	protected Path path;
	
	protected static double damageMultiplier = 1;
	
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
		return currentHealth;
	}

	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}

	public int getDamage() {
		return (int)(getPlayer().getDamage() * damageMultiplier);
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
	
	/**
	 * This uses the path-finding algorithm to find a path to the Point given as parameter.
	 * @param target
	 */
	public void goTo(Point target) { 
		this.path = new Path(this.position, target);
		this.target = path.getNext();
		this.sprite.setDoing(false);
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
		
		if (orientation < -3*Math.PI/4 || orientation > 3*Math.PI/4) sprite.setDirection(Sprite.Direction.LEFT);
		else if (orientation < -Math.PI/4) sprite.setDirection(Sprite.Direction.UP);
		else if (orientation < Math.PI/4) sprite.setDirection(Sprite.Direction.RIGHT);
		else sprite.setDirection(Sprite.Direction.DOWN);
	}
	
	/**
	 * This moves the unit along it's path.
	 */
	public void move() {
		if (speed < 2) sprite.setMoving(false);
		else sprite.setMoving(true);
		
		if (target == null) {
			return;
		}
		
		else if (position.distance(target) < 35) {
			if (path.isEmpty()) {
				target = null;
				speed = 0;
				return;
			} else {
				this.setTarget(path.getNext());
			}
		}
		else if (speed < maxSpeed) speed = Math.abs(speed + accel + Math.random());
		int newX = position.x + Math.round(Math.round(Math.cos(orientation) * this.speed));
		int newY = position.y + Math.round(Math.round(Math.sin(orientation) * this.speed));
		
		/*
		 * Are we lost?
		 */
		if (newX < 0 || newX > Config.getWorldWidth() || newY < 0 || newY > Config.getWorldHeight())
			this.goTo(target);
		
		if (newX != position.x || newY != position.y)
			GameState.getMainWindow().canvas.setDirty(position.x, position.y, position.x + sprite.getWidth(), position.y + sprite.getHeight());
		this.setPosition(new Point(newX, newY));
	}
	
	/**
	 * Used by a unit when it deals damage to another unit.
	 */

	public void dealDamage(){
		targetUnit.hit(this.getDamage());
		targetUnit.setTargetUnit(this); // Physician, defend thyself.
		if(targetUnit.currentHealth<=0){
			GameState.getUnits().removeUnit(targetUnit);
			if(this.targetUnit.getPlayer().isAI()) {
				if(this.targetUnit instanceof Worker){
					GameState.getState().getAi().getWorkers().remove(this.targetUnit);
				}
				if(this.targetUnit instanceof Fighter) {
					if(this.targetUnit.position.distance(GameState.getComputer().getMainBuilding().getPosition())>500){
						GameState.getState().getAi().getFightersdef().remove(this.targetUnit);
					}
					GameState.getState().getAi().getFighters().remove(this.targetUnit);
				}
				if(this.targetUnit instanceof Healer) {
					if(this.targetUnit.position.distance(GameState.getComputer().getMainBuilding().getPosition())>500){
						GameState.getState().getAi().getHealerssdef().remove(this.targetUnit);
					}
					GameState.getState().getAi().getHealers().remove(this.targetUnit);
				}
			}
			this.targetUnit = null;
			this.target = null;			
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public void setTargetResource(Resource r) {}
	
	/**
	 * Used by a Unit when it deals damage to a target.
	 * Deals amount damage given as parameter to this unit.
	 * @param damage
	 */
	
	public void hit(int damage) {
		this.currentHealth -= damage;
		this.sprite.hit();
	}

	public double getCurrentHealthPercent() {
		return currentHealth/maxHealth;
	}
	
	public int getArmor() {
		return 0;
	}
}

