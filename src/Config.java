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
	private boolean intro = true;
	private boolean music = true;
	
	private Config() {
		try {
			int worldWidth, worldHeight, maskSize;
			boolean music, intro;
			
			BufferedReader file = new BufferedReader(new FileReader("config.ini"));
			String line = file.readLine();
			
			String[] values = line.split("¤");

			worldWidth = Integer.parseInt(values[0]);
			if (worldWidth < 500 || worldWidth > 5000) throw new Exception();
			
			worldHeight = Integer.parseInt(values[1]);
			if (worldHeight < 500 || worldHeight > 5000) throw new Exception();
			
			maskSize = Integer.parseInt(values[2]);
			if (maskSize < 1 || maskSize > 50) throw new Exception();
			
			if (values[3].equals("true")) music = true;
			else if (values[3].equals("false")) music = false;
			else throw new Exception();


			if (values[4].equals("true")) intro = true;
			else if (values[4].equals("false")) intro = false;
			else throw new Exception();
			
			this.worldWidth = worldWidth;
			this.worldHeight = worldHeight;
			this.maskSize = maskSize;
			this.music = music;
			this.intro = intro;

			
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
	
	public static void saveConfig(String width, String height, int maskSize,
			boolean music, boolean intro) {
			try {
				String output = width + "¤";
				output += height + "¤";
				output += maskSize + "¤";
				output += music + "¤";
				output += intro;	
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
		ConfigHolder.config.maskSize = 15;
		ConfigHolder.config.intro = true;
		ConfigHolder.config.music = true;
	}
	
	public static boolean getIntro() {
		return ConfigHolder.config.intro;
	}

	public static boolean getMusic() {
		return ConfigHolder.config.music;
	}
}
