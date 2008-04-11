
public class Config {
	/*
	 * 2000x2000 breaks the ai, for unknown reasons
	 */
	private int worldWidth = 1500; // x pixels wide
	private int worldHeight = 1500; // y pixels high
	private int unitWidth = 25; //Ditto
	private int unitHeight = 25;
	private int sleeptime = 50; // in ms.

	private static class ConfigHolder {
		private final static Config config = new Config();
	}
	
	/**
	 * @return the worldWidth
	 */
	public static int getWorldWidth() {
		return ConfigHolder.config.worldWidth;
	}
	/**
	 * @return the worldHeight
	 */
	public static int getWorldHeight() {
		return ConfigHolder.config.worldHeight;
	}
	/**
	 * @return the unitWidth
	 */
	public static int getUnitWidth() {
		return ConfigHolder.config.unitWidth;
	}
	/**
	 * @return the unitHeight
	 */
	public static int getUnitHeight() {
		return ConfigHolder.config.unitHeight;
	}
	/**
	 * @return the sleeptime
	 */
	public static int getSleeptime() {
		return ConfigHolder.config.sleeptime;
	}
}
