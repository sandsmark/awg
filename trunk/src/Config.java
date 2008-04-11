import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Config {
	
	/*
	 * 2000x2000 breaks the ai, for unknown reasons
	 */
	private int worldWidth = 1500; // x pixels wide
	private int worldHeight = 1500; // y pixels high
	private int unitWidth = 25; //Ditto
	private int unitHeight = 25;
	private int sleeptime = 50; // in ms.
	private int maskSize = 15;
	private int aiAggressiveness = 0; // How aggressive should the AI be?
	
	private Config() {
		try {
			int worldWidth;
			int worldHeight;
			int sleeptime;
			int maskSize;
			int aiAggressiveness;
			
			BufferedReader file = new BufferedReader(new FileReader("config.ini"));
			String line;
			line = file.readLine();
			String[] values = line.split("¤");
			worldWidth = Integer.parseInt(values[0]);
			worldHeight = Integer.parseInt(values[1]);
			sleeptime = Integer.parseInt(values[2]);
			maskSize = Integer.parseInt(values[3]);
			aiAggressiveness = Integer.parseInt(values[4]);
			
			this.worldWidth = worldWidth;
			this.worldHeight = worldHeight;
			this.sleeptime = sleeptime;
			this.maskSize = maskSize;
			this.aiAggressiveness = aiAggressiveness;
			
		} catch (Exception e) {
			System.err.println("Could not load config.");
		}

	}
	
	private static class ConfigHolder {
		private final static Config config = new Config();
	}
	
	/**
	 * @return the worldWidth
	 */
	public static int getWorldWidth() {
		return ConfigHolder.config.worldWidth;
	}
	public static void setWorldWidth(int w) {
		ConfigHolder.config.worldWidth = w;
	}
	/**
	 * @return the worldHeight
	 */
	public static int getWorldHeight() {
		return ConfigHolder.config.worldHeight;
	}
	public static void setWorldHeight(int h){
		ConfigHolder.config.worldHeight = h;
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
	public static void setSleeptime(int sleeptime) {
		ConfigHolder.config.sleeptime = sleeptime;
	}
	
	
	public static int getMaskSize() {
		return ConfigHolder.config.maskSize;
	}
	public static void setMaskSize(int maskSize) {
		ConfigHolder.config.maskSize = maskSize;
	}
	
	public static int getAggressiveness() {
		return ConfigHolder.config.aiAggressiveness;
	}
	public static void setAggressiveness(int a) {
		ConfigHolder.config.aiAggressiveness = a;
	}
	public static void saveConfig(String width, String height, int masksize, int sleeptime, int aggressiveness) {	
		try {
			String output = width + "¤";
			output += height + "¤";
			output += masksize + "¤";
			output += sleeptime + "¤";
			output += aggressiveness;	
			FileWriter file = new FileWriter("config.ini");
			file.write(output);
			file.close();
			System.out.println(output);
		} catch (IOException e) {
			System.err.println("Error writing config file!");
		}
	}
	
	public static void resetConfig() {
		ConfigHolder.config.worldWidth = 1500;
		ConfigHolder.config.worldHeight = 1500;
		ConfigHolder.config.sleeptime = 50;
		ConfigHolder.config.maskSize = 15;
		ConfigHolder.config.aiAggressiveness = 0;
	}
}
