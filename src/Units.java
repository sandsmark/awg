import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;


public class Units {
	ArrayList<Unit> units = new ArrayList<Unit>();
	ArrayList<Unit> selectedUnits = new ArrayList<Unit>();
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
	}

	public Unit getUnit(int i) {
		return units.get(i);
	}
	
	public ArrayList<Unit> getUnits() {
		return units;
	}

	public int getUnitNum() {
		return units.size();
	}
	
	public void selectUnit(Unit u) {
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

	public void select(int x, int y) {
		selectedUnits.clear();
		Point clicked = new Point(x - GameState.getConfig().getUnitWidth()/2, y - GameState.getConfig().getUnitHeight()/2);
		for (int i = 0; i < this.getUnitNum(); i++) {
			if (this.getUnit(i).getPosition().distance(clicked) < 15){
				selectUnit(this.getUnit(i));
				return;
			}
		}
	}

	public void moveSelectedTo(int tarX, int tarY) {
		for (Unit unit : this.selectedUnits)
			unit.goTo(new Point(tarX, tarY));
	}

	public ArrayList<Unit> getSelectedUnits() {
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
	
	public void upgradeUnits(Player player){
		Unit unit;
		for (int i = 0; i < GameState.getUnits().count(); i++) {
			unit = GameState.getUnits().getUnit(i);
			if(unit.getPlayer() == player){
				if(unit instanceof Worker){
					Worker worker = (Worker)unit;
					worker.setMaxCarrying(worker.getMaxCarrying() * 2);
					worker.setMaxHealth(worker.getMaxHealth() * 2);
					worker.setDamage(worker.getDamage() *2);
					worker.setHarvestMax(worker.getHarvestMax()*2);
				}else if(unit instanceof Fighter){
					Fighter fighter = (Fighter)unit;
					fighter.setDamage(fighter.getDamage() * 2);
					fighter.setMaxHealth(fighter.getMaxHealth() *2);
				}else if(unit instanceof Healer){
					Healer healer = (Healer)unit;
					healer.setMana(healer.getMana() * 2);
					healer.setMaxHealth(healer.getMaxHealth() *2);
					healer.setDamage(healer.getDamage() *2);
				}
			}
			
		}
	}

	public void target(int x, int y) {
		if (GameState.getUnits().selectedOnlyContains("worker"))
			for (Resource resource : GameState.getMap().getResources()){
				if (resource.position.distance(new Point(x - 10, y - 10)) < 10) {
					GameState.getUnits().setTargetResource(resource);
					resource.startHighlight();
				}
			}
		else if (this.selectedOnlyContains("fighter"))
			for (Unit unit : this.getUnits()){
				if (unit.position.distance(new Point(x - 10, y - 10)) < 10) {
					if (unit.getPlayer().equals(GameState.getComputer())) this.setTargetUnit(unit);
				}
			}
		GameState.getUnits().moveSelectedTo(x, y);
		
	}
}
