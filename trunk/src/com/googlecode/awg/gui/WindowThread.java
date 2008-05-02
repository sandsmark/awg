package com.googlecode.awg.gui;

import com.googlecode.awg.state.GameState;
import com.googlecode.awg.units.Units;

public class WindowThread extends Thread {
	protected boolean running = false;

	public WindowThread() {
		running = true;
	}

	public void stopThread() {
		running = false;
	}

	@Override
	public void run() {
		Units units = GameState.getUnits();
		try {
			while (running) {
				if (units.lock.tryLock()) {
					if (units.getSelectedUnits().size() > 0){
						GameState.getMainWindow().uPan.select(units.getSelectedUnits());
					}
					else{
						GameState.getMainWindow().uPan.deselect();
					}
					GameState.getMainWindow().resPan.update();
					GameState.getMainWindow().miniMap.repaint();
					units.lock.unlock();
				}
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
