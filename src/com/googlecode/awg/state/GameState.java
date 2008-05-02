package com.googlecode.awg.state;

import com.googlecode.awg.ai.AI;
import com.googlecode.awg.gui.MainWindow;
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
import com.googlecode.awg.units.Units;

/**
 * This singleton contains the state of the game, represented by objects
 * used globally in the game.
 * It contains the current Map object, Units object, MainWindow object,
 * the Player objects, the AI object and the time since the game started, in milliseconds. 
 * @author Martin T. Sandsmark
 */
public class GameState {
	/*
	 * This contains the current Map object, used globally in the game.
	 */
	private Map map = new Map();
	
	/*
	 * This contains the current Units object.
	 */
	private Units units = new Units();
	
	/*
	 * This contains the MainWindow.
	 */
	private MainWindow mainWindow;
	
	/*
	 * This is not the AI.
	 */
	private Player human = new Player(false, 200);
	
	/*
	 * This, however, is the AI.
	 */
	private Player computer = new Player(true, 200);
	
	/*
	 * This contains the milliseconds since the epoch, when the game started.
	 */
	private long start = System.currentTimeMillis();
	
	/*
	 * This is the AI entity. It is not yet self aware.
	 */
	private AI ai;
	
	/*
	 * This hold the current game state. 
	 */
	private GameState() {}

	/*
	 * This is part of the singleton design. See the Config class for a better explanation.
	 */
	private static class GameStateHolder {
		private final static GameState state = new GameState();
	}
	
	/**
	 * @param mainWindow is the new MainWindow object.
	 */
	public static void setMainWindow(MainWindow mainWindow) {
		GameStateHolder.state.mainWindow = mainWindow;
	}
	
	/**
	 * @return returns the current game state.
	 */
	public static GameState getState() {
		return GameStateHolder.state;
	}

	/**
	 * @return returns the current Map object.
	 */
	public static Map getMap() {
		return GameStateHolder.state.map;
	}

	/**
	 * @return returns the current Units object.
	 */
	public static Units getUnits() {
		return GameStateHolder.state.units;
	}
	
	/**
	 * @return returns the current MainWindow object.
	 */
	public static MainWindow getMainWindow() {
		return GameStateHolder.state.mainWindow;
	}
	
	/**
	 * @return returns the Player object of the human player.
	 */
	public static Player getHuman() {
		return GameStateHolder.state.human;
	}
	
	/**
	 * @return returns the Player object of the AI.
	 */
	public static Player getComputer() {
		return GameStateHolder.state.computer;
	}
	
	/**
	 * @return returns the number of milliseconds elapsed since the game started.
	 */
	public static long getTime() {
		return (System.currentTimeMillis() - GameStateHolder.state.start) ;
	}
	
	/**
	 * @return returns the AI object.
	 */
	public AI getAi() {
		return GameStateHolder.state.ai;
	}
	
	/**
	 * @param ai the new AI object.
	 */
	public void setAi(AI ai) {
		GameStateHolder.state.ai=ai;
	}
}
