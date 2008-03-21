import java.awt.event.MouseEvent;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class SelectThread extends Thread {
	/*
	 * Thread for selecting.
	 * Copyright 2008 Martin T. Sandsmark m.fl.
	 */
	protected long startT=0;
	protected int startX, startY, endX, endY;
	protected boolean started;
	protected ReentrantLock lock = new ReentrantLock();
	protected Condition moved = lock.newCondition();
	protected boolean running;
	protected Map map;
	protected Canvas canvas;
	
	public SelectThread (Canvas newCanvas) {
		map = newCanvas.getMap();
		canvas = newCanvas;
		running = true;
	}
	
	public void stopThread(){
		running = false;
	}
	
	public void start(MouseEvent m){
		lock.lock();
		startT = System.currentTimeMillis();
		startX = m.getX();
		startY = m.getY();
		started = true;
		moved.signal();
		lock.unlock();
	}
	
	public void stop(MouseEvent m){
		lock.lock();
		endX = m.getX();
		endY = m.getY();
		if (startX != endX && startY != endY){
			map.selectUnits(startX, startY, m.getX(), m.getY());
			canvas.updateInternal();
		} else if (startT != 0) {
			map.selectUnit(m.getX() + canvas.getOffsetX(), m.getY() + canvas.getOffsetY());
			canvas.updateInternal();
		}
		moved.signal();
		startX = startY = endX = endY = 0;
		started = false;
		lock.unlock();
	}
	
	public void moved(MouseEvent m){
		if (!started) return;
		lock.lock();
		endX = m.getX();
		endY = m.getY();
		moved.signal();
		lock.unlock();
	}
	
	public void run() {
		while (running){
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
