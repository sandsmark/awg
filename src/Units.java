import java.util.ArrayList;


public class Units {
	ArrayList<Unit> units = new ArrayList<Unit>();
	
	public Units () {
		units = new ArrayList<Unit>();
	}
	
	public void addUnit(Unit unit) {
		if (units.contains(unit))
			return;
		units.add(unit);
	}

	public void removeUnit(Unit unit) {
		if (!units.contains(unit))
			return;
		units.remove(unit);

	}

	public Unit getUnit(int i) {
		return units.get(i);
	}

	public int getUnitNum() {
		return units.size();
	}
}
