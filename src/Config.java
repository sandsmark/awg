
public class Config {
	/*
	 * 2000x2000 breaks the ai, for unknown reasons
	 */
	private static int worldWidth = 3000; // x pixels wide
	private static int worldHeight = 3000; // y pixels high
	private static int unitWidth = 25; //Ditto
	private static int unitHeight = 25;
	private static int sleeptime = 50; // in ms.

	/**
	 * @return the worldWidth
	 */
	public static int getWorldWidth() {
		return worldWidth;
	}
	/**
	 * @return the worldHeight
	 */
	public static int getWorldHeight() {
		return worldHeight;
	}
	/**
	 * @return the unitWidth
	 */
	public static int getUnitWidth() {
		return unitWidth;
	}
	/**
	 * @return the unitHeight
	 */
	public static int getUnitHeight() {
		return unitHeight;
	}
	/**
	 * @return the sleeptime
	 */
	public static int getSleeptime() {
		return sleeptime;
	}
	/**
	 * @param worldWidth the worldWidth to set
	 */
	public static void setWorldWidth(int worldWidth) {
		Config.worldWidth = worldWidth;
	}
	/**
	 * @param worldHeight the worldHeight to set
	 */
	public static void setWorldHeight(int worldHeight) {
		Config.worldHeight = worldHeight;
	}
	/**
	 * @param unitWidth the unitWidth to set
	 */
	public static void setUnitWidth(int unitWidth) {
		Config.unitWidth = unitWidth;
	}
	/**
	 * @param unitHeight the unitHeight to set
	 */
	public static void setUnitHeight(int unitHeight) {
		Config.unitHeight = unitHeight;
	}
	/**
	 * @param sleeptime the sleeptime to set
	 */
	public static void setSleeptime(int sleeptime) {
		Config.sleeptime = sleeptime;
	}
}
