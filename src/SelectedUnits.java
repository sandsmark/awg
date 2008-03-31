import java.awt.Point;
import java.util.ArrayList;


public class SelectedUnits {
	ArrayList<Unit> selectedUnits = new ArrayList<Unit>();
//	Player owner;
	
	public SelectedUnits(){
//		owner = player;
	}
	
	
	
	public void selectUnit(Unit u) {
		if (selectedUnits.contains(u))
			return;
		selectedUnits.add(u);
	}

	public void deselectUnit(Unit u) {
		if (!selectedUnits.contains(u))
			return;
		selectedUnits.remove(u);
	}

	public Unit get(int num) {
		return selectedUnits.get(num);
	}

	public int count() {
		return selectedUnits.size();
	}

	public void select(int x1, int y1, int x2, int y2) {
		Units units = GameState.getUnits();
		if (x1 > x2)
			x1 = (x1 ^= x2) ^ (x2 ^= x1); // swap x1 and x2, ^=xor
		if (y1 > y2)
			y1 = (y1 ^= y2) ^ (y2 ^= y1);
		
		int x, y;
		for (int i = 0; i < units.getUnitNum(); i++) {
			x = units.getUnit(i).getPosition().x;
			y = units.getUnit(i).getPosition().y;
			if ((y1 < y) && (y2 > y) && (x1 < x) && (x2 > x))
				selectUnit(units.getUnit(i));
		}
	}

	public boolean contains (Unit u) {
		return selectedUnits.contains(u);
	}

	public void select(int x, int y) {
		// TODO: Fix this mess, look at how nice his cousin is (selectUnit(int,
		// int, int, int)) kthxbye
		selectedUnits.clear();
		int uHeight = GameState.getConfig().getUnitHeight();
		Units units = GameState.getUnits();
		
		int x1 = x - uHeight;
		int y1 = y - uHeight;
		int x2 = x1 + uHeight;
		int y2 = y1 + uHeight;
		for (int i = 0; i < units.getUnitNum(); i++) {
			if ((y1 < units.getUnit(i).getPosition().y)
					&& (y2 > units.getUnit(i).getPosition().y)
					&& (x1 < units.getUnit(i).getPosition().x)
					&& (x2 > units.getUnit(i).getPosition().x)) {
				selectUnit(units.getUnit(i));
			}
		}
	}

	public void moveSelectedTo(int tarX, int tarY) {
		for (int i = 0; i < this.count(); i++) {
			this.get(i).setTarget(new Point(tarX, tarY));
		}
	}

}
