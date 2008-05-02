package com.googlecode.awg.units;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import com.googlecode.awg.gui.UnitButton;
import com.googlecode.awg.state.Config;
import com.googlecode.awg.state.GameState;
import com.googlecode.awg.state.Player;

/**
 * 
 * @author Magnus Fjell
 *
 */

public class Units implements ActionListener {
	List<Unit> units = Collections.synchronizedList(new ArrayList<Unit>());
	List<Unit> selectedUnits = Collections.synchronizedList(new ArrayList<Unit>());
	public ReentrantLock lock = new ReentrantLock();
	
	public Units () {
		units = new ArrayList<Unit>();
	}
	
	public void addUnit(Unit unit) {
		if (units.contains(unit)) return;
		units.add(unit);
	}

	public void removeUnit(Unit unit) {
		if (!units.contains(unit))	return;
		units.remove(unit);
		this.deselectUnit(unit);
	}

	public Unit getUnit(int i) {
		return units.get(i);
	}
	
	public synchronized List<Unit> getUnits() {
		return units;
	}

	public int getUnitNum() {
		return units.size();
	}
	
	public synchronized void selectUnit(Unit u) {
		if (selectedUnits.contains(u)) return;
		if (u.getPlayer().isAI()) return;
		selectedUnits.add(u);
	}

	public void deselectUnit(Unit u) {
		if (!selectedUnits.contains(u)) return;
		selectedUnits.remove(u);
	}

	public Unit getFromSelected(int num) {
		return selectedUnits.get(num);
	}

	public int count() {
		return selectedUnits.size();
	}

	
	/**
	 * Selects the units inside the box given by the input coordinates,
	 * and puts them inside the arraylist selectedUnits.
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void select(int x1, int y1, int x2, int y2) {
		selectedUnits.clear();
		if (x1 > x2)
			x1 = (x1 ^= x2) ^ (x2 ^= x1); // swap x1 and x2, ^=xor
		if (y1 > y2)
			y1 = (y1 ^= y2) ^ (y2 ^= y1);
		
		int x, y;
		for (int i = 0; i < this.getUnitNum(); i++) {
			x = this.getUnit(i).getPosition().x;
			y = this.getUnit(i).getPosition().y;
			if ((y1 < y) && (y2 > y) && (x1 < x) && (x2 > x)){
				selectUnit(this.getUnit(i));
			}
		}
	}

	public boolean isSelected (Unit u) {
		return selectedUnits.contains(u);
	}

	/**
	 * Selects the unit at the coordinates given as parameters.
	 * If there is more than one unit, it selects the first found at the given coordinate
	 * in the units ArrayList and adds it to selectedUnits.
	 * @param x
	 * @param y
	 */
	public void select(int x, int y) {
		selectedUnits.clear();
		Point clicked = new Point(x - Config.getUnitWidth()/2, y - Config.getUnitHeight()/2);
		for (int i = 0; i < this.getUnitNum(); i++) {
			if (this.getUnit(i).getPosition().distance(clicked) < 15){
				selectUnit(this.getUnit(i));
				return;
			}
		}
	}

	/**
	 * Moves the selected unit(s) to the coordinates given as parameters
	 * @param tarX
	 * @param tarY
	 */
	
	public void moveSelectedTo(int tarX, int tarY) {
		for (Unit unit : this.selectedUnits)
			unit.goTo(new Point(tarX, tarY));
	}

	public List<Unit> getSelectedUnits() {
		return selectedUnits;
	}
	
	public boolean selectedOnlyContains(String type) {
		for (Unit unit : selectedUnits)
			if (!unit.getType().equals(type)) return false;
		return true;
	}
	
	public void setTargetResource(Resource resource) {
		for (Unit unit : selectedUnits)
			if (unit instanceof Worker) ((Worker)unit).setTargetResource(resource);	
	}
	
	public void setTargetUnit(Unit target) {
		for (Unit unit : selectedUnits)	unit.setTargetUnit(target);
	}
	
	
	/**
	 * Upgrades the units belonging to the player given as parameter.
	 * @param player
	 */
	public void upgradeUnits(Player player){
		Unit unit;
		for (int i = 0; i < GameState.getUnits().count(); i++) {
			unit = GameState.getUnits().getUnit(i);
			if(unit.getPlayer() == player){
				if(unit instanceof Worker){
					Worker worker = (Worker)unit;
					worker.setMaxCarrying(worker.getMaxCarrying() * 2);
					worker.setMaxHealth(worker.getMaxHealth() * 2);
					worker.setHarvestMax(worker.getHarvestMax()*2);
				} else if(unit instanceof Fighter) {
					Fighter fighter = (Fighter)unit;
					fighter.setMaxHealth(fighter.getMaxHealth() *2);
				} else if(unit instanceof Healer) {
					Healer healer = (Healer)unit;
					healer.setMana(healer.getMana() * 2);
					healer.setMaxHealth(healer.getMaxHealth() *2);
				}
			}
			
		}
	}

	
	/**
	 * Sets target to the given x/y coordinates, but only workers.
	 * If selectedUnits only contains workers, sets a resource as target
	 * @param x
	 * @param y
	 */
	public synchronized void target(int x, int y) {
		if (GameState.getUnits().selectedOnlyContains("worker")){
			for (Resource resource : GameState.getMap().getResources()){
				if (resource.position.distance(new Point(x - 10, y - 10)) < 10) {
					GameState.getUnits().setTargetResource(resource);
					resource.startHighlight();
				}
			}
		}
		GameState.getUnits().moveSelectedTo(x, y);
		
	}
	
	private synchronized void deselectAllUnits() {
		selectedUnits.removeAll(selectedUnits);
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		
		if(ev.getSource() instanceof UnitButton){
			if ((ev.getModifiers()&ActionEvent.CTRL_MASK) == ActionEvent.CTRL_MASK) this.deselectUnit(((UnitButton)ev.getSource()).getUnit());
			else {
				System.out.println("deselect");
				deselectAllUnits();
				selectUnit(((UnitButton)ev.getSource()).getUnit());
			}
		}
		
	}

	
}
