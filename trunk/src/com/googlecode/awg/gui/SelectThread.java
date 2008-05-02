package com.googlecode.awg.gui;
import java.awt.event.MouseEvent;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.googlecode.awg.state.GameState;
import com.googlecode.awg.units.Units;

public class SelectThread extends Thread {
	/*
	 * Thread for selecting. Copyright 2008 Martin T. Sandsmark m.fl.
	 */
	protected long startT = 0;
	protected int startX, startY, endX, endY;
	protected boolean started;
	protected ReentrantLock lock = new ReentrantLock();
	protected Condition moved = lock.newCondition();
	protected boolean running;

	public SelectThread() {
		running = true;
	}

	public void stopThread() {
		running = false;
	}

	public void start(MouseEvent m) {
		lock.lock();
		startT = System.currentTimeMillis();
		startX = m.getX();
		startY = m.getY();
		started = true;
		moved.signal();
		lock.unlock();
	}

	public void stop(MouseEvent m) {
		Canvas canvas = GameState.getMainWindow().getCanvas();
		Units units = GameState.getUnits();
		lock.lock();
		endX = m.getX();
		endY = m.getY();
		if (startX != endX && startY != endY) {
			units.select(startX + canvas.getOffsetX(), startY + canvas.getOffsetY(), m.getX() + canvas.getOffsetX(), m.getY() + canvas.getOffsetY());
			canvas.updateInternal();
		} else if (startT != 0) {
			units.select(m.getX() + canvas.getOffsetX(), m.getY() + canvas.getOffsetY());
			canvas.updateInternal();
		}
		moved.signal();
		startX = startY = endX = endY = 0;
		started = false;
		lock.unlock();
	}

	public void moved(MouseEvent m) {
		if (!started)
			return;
		lock.lock();
		endX = m.getX();
		endY = m.getY();
		moved.signal();
		lock.unlock();
	}

	@Override
	public void run() {
		Canvas canvas = GameState.getMainWindow().getCanvas();
		while (running) {
			lock.lock();
			if (started && (System.currentTimeMillis() - startT > 100)) {
				canvas.drawSelectBox(startX, startY, endX, endY);
			} else {
				canvas.hideSelectBox();
			}
			try {
				moved.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			lock.unlock();
		}
	}
}
