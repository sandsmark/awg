
public class Config {
	/*
	 * 2000x2000 breaks the ai, for unknown reasons
	 */
	private static int worldWidth = 3000; // x pixels wide
	private int worldHeight = 3000; // y pixels high
	private int unitWidth = 25; //Ditto
	private int unitHeight = 25;
	private int sleeptime = 50; // in ms.
	public int getSleeptime() {
		return sleeptime;
	}
	public void setSleeptime(int sleeptime) {
		this.sleeptime = sleeptime;
	}
	public int getWorldWidth() {
		return worldWidth;
	}
	public void setWorldWidth(int worldWidth) {
		this.worldWidth = worldWidth;
	}
	public int getWorldHeight() {
		return worldHeight;
	}
	public void setWorldHeight(int worldHeight) {
		this.worldHeight = worldHeight;
	}
	public int getUnitWidth() {
		return unitWidth;
	}
	public void setUnitWidth(int unitWidth) {
		this.unitWidth = unitWidth;
	}
	public int getUnitHeight() {
		return unitHeight;
	}
	public void setUnitHeight(int unitHeight) {
		this.unitHeight = unitHeight;
	} 
	
}
