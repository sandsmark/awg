/*
Copyright (C) 2008 Martin T. Sandsmark

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This is the central configuration storage. It is a singleton, 
 * with mechanisms for saving and loading the configuration from file.
 *  @author Martin T. Sandsmark
 */
public class Config {
	/*
	 * Size of the map. 
	 */
	private int worldWidth = 1500;
	private int worldHeight = 1500;
	
	/*
	 * Size of individual units.
	 */
	private int unitWidth = 25;
	private int unitHeight = 25;
	
	/*
	 * Generic sleeptime for threads.
	 */
	private int sleeptime = 50;
	
	/*
	 * This defines the crudeness of the path finding algorithm.
	 */
	private int maskSize = 15;
	
	/*
	 * This decides if we should play the intro.
	 */
	private boolean intro = true;
	
	/*
	 * This decides if music should be turned on or off.
	 */
	private boolean music = true;
	
	/*
	 * Frames per seconds that should be tried to be forced (through the MovementThread).
	 */
	private int fps = 25;
	
	/**
	 * This constructor tries to load in configured values from a file.
	 * If it fails, the values are not saved into the object, and the default values
	 * specified above are used.  
	 */
	private Config() {
		try {
			int worldWidth, worldHeight, maskSize, fps;
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
			
			fps = Integer.parseInt(values[5]);
			if (fps < 1 || fps > 100) throw new Exception();
			
			this.worldWidth = worldWidth;
			this.worldHeight = worldHeight;
			this.maskSize = maskSize;
			this.music = music;
			this.intro = intro;

		} catch (Exception e) {
			/*
			 * This is not fatal, and could happen for any number of reasons.
			 * When this occurs, the default values should be used.
			 */
			System.err.println("Could not load config from file.");
		}

	}
	
	/**
	 * This is the base for the configuration singleton.
	 * This static class only contains one Config object, which is the 
	 * config that should be used at all times.
	 * @author Martin T. Sandsmark
	 *
	 */
	private static class ConfigHolder {
		private final static Config config = new Config();
	}
	
	/**
	 * @return the map width
	 */
	public static int getWorldWidth() {
		return ConfigHolder.config.worldWidth;
	}
	/**
	 * @param w the new map width
	 */
	public static void setWorldWidth(int w) {
		ConfigHolder.config.worldWidth = w;
	}
	
	
	/**
	 * @return the map height.
	 */
	public static int getWorldHeight() {
		return ConfigHolder.config.worldHeight;
	}
	/**
	 * @param h the new map height. 
	 */
	public static void setWorldHeight(int h){
		ConfigHolder.config.worldHeight = h;
	}
	
	
	/**
	 * @return the unit width
	 */
	public static int getUnitWidth() {
		return ConfigHolder.config.unitWidth;
	}
	
	/**
	 * @return the unit height
	 */
	public static int getUnitHeight() {
		return ConfigHolder.config.unitHeight;
	}
	
	/**
	 * @return the sleeptime.
	 */
	public static int getSleeptime() {
		return ConfigHolder.config.sleeptime;
	}
	/**
	 * @param sleeptime Generic thread sleep time.
	 */
	public static void setSleeptime(int sleeptime) {
		ConfigHolder.config.sleeptime = sleeptime;
	}
	
	/**
	 * @return Crudeness of the path finding algorithm.
	 */
	public static int getMaskSize() {
		return ConfigHolder.config.maskSize;
	}
	/**
	 * @param maskSize The new crudeness of the path finding algorithm.
	 */
	public static void setMaskSize(int maskSize) {
		ConfigHolder.config.maskSize = maskSize;
	}
	
	/**
	 * This saves the new config to the config file.
	 * @param width The new map width.
	 * @param height The new map height.
	 * @param maskSize The new mask size/crudeness for the path finding algorithm.
	 * @param music This decides if music should be played.
	 * @param intro This sets if the intro should be played.
	 * @param fps This is the frames per second that should be tried for.
	 */
	public static void saveConfig(String width, String height, int maskSize,
			boolean music, boolean intro, int fps) {
			try {
				String output = width + "¤";
				output += height + "¤";
				output += maskSize + "¤";
				output += music + "¤";
				output += intro + "¤";
				output += fps;
				FileWriter file = new FileWriter("config.ini");
				file.write(output);
				file.close();
				System.out.println(output);
		} catch (IOException e) {
			System.err.println("Error writing config file!");
		}
	}
	
	/**
	 * This resets the configuration to more or less sane values.
	 */
	public static void resetConfig() {
		ConfigHolder.config.worldWidth = 1500;
		ConfigHolder.config.worldHeight = 1500;
		ConfigHolder.config.maskSize = 15;
		ConfigHolder.config.intro = true;
		ConfigHolder.config.music = true;
		ConfigHolder.config.fps = 15;
	}
	
	/**
	 * @return this returns true if the intro should be played.
	 */
	public static boolean getIntro() {
		return ConfigHolder.config.intro;
	}

	/**
	 * @return this returns true if music should be played.
	 */
	public static boolean getMusic() {
		return ConfigHolder.config.music;
	}
	
	/**
	 * @return this returns the frames per second that should be tried for.
	 */
	public static int getFPS() {
		return ConfigHolder.config.fps;
	}
}
