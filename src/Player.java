import java.util.ArrayList;


public class Player {
	ArrayList<Unit> units = new ArrayList<Unit>();
	Building mainHouse;
	private boolean isAI;
	private int resources;
	
	public Player(boolean isAI) {
		this.isAI = isAI;
		mainHouse = new Building(this);
	}
	
	
	public void addUnit(Unit u) {
		if (!units.contains(u)) units.add(u);
	}
	
	public void removeUnit(Unit u) {
		if (units.contains(u)) units.remove(u);
	}

	public boolean isAI() {
		return isAI;
	}

	public void setAI(boolean isAI) {
		this.isAI = isAI;
	}
	
	public void increaseResources(int n) {
		resources += n;
	}
	public void decreaseResources(int n){
		resources -= n;
	}
	
	public int getResources() {
		return resources;
	}
	
	public Building getMainBuilding(){
		return mainHouse;
	}
}
