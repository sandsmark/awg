import java.util.ArrayList;


public class Player {
	ArrayList<Unit> units = new ArrayList<Unit>();
	SelectedUnits selected = new SelectedUnits();
	
	
	public void addUnit(Unit u) {
		if (!units.contains(u)) units.add(u);
	}
	
	public void removeUnit(Unit u) {
		if (units.contains(u)) units.remove(u);
	}
}
