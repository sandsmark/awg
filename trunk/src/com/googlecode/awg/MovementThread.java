package com.googlecode.awg;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.googlecode.awg.gui.Canvas;
import com.googlecode.awg.state.Config;
import com.googlecode.awg.state.GameState;
import com.googlecode.awg.units.Units;

public class MovementThread extends Thread {
	protected ReentrantLock lock = new ReentrantLock(); // This lock makes sure
														// threading works okay
	protected Condition update = lock.newCondition(); // This synchronises
														// things nicely
	protected boolean running = false; // This is true while this thread is
										// running
	
	protected int sleeptime = 1000 / Config.getFPS(); // Sleeptime in milliseconds
	protected long lastSlept = System.currentTimeMillis();
	
	public MovementThread() {
		running = true;
	}

	public void stopThread() {
		running = false;
	}

	@Override
	public void run() {
		Units units = GameState.getUnits();
		Canvas canvas = GameState.getMainWindow().getCanvas(); // This holds the current canvas object
		try {
			while (running) {
				if (sleeptime - (System.currentTimeMillis() - lastSlept) > 0) sleep(sleeptime - (System.currentTimeMillis() - lastSlept));
				this.lastSlept = System.currentTimeMillis();
				for (int i = 0; i < units.getUnitNum(); i++) {
					units.getUnit(i).move();

				}
				canvas.repaint();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
