import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class SelectThread extends Thread {
	protected long startT=0;
	protected int startX;
	protected int startY;
	protected boolean started;
	protected ReentrantLock lock = new ReentrantLock();
	protected Condition moved = lock.newCondition();
	protected boolean running;
	protected Map map;
	protected MouseEvent mouse;
	protected Canvas canvas;
	
	public SelectThread (Canvas newCanvas) {
		map = newCanvas.getMap();
		canvas = newCanvas;
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
		if (startT - System.currentTimeMillis() >  10){
			map.selectUnits(startX, startY, m.getX(), m.getY());
		}
		if (startT != 0)
			map.selectUnit(m.getX(), m.getY());
		moved.signal();
		lock.unlock();
	}
	
	public void moved(MouseEvent m){
		if (!started)
			return;
		lock.lock();
		moved.signal();
		mouse = m;
		lock.unlock();
	}
	
	public void run() {
		while (running){
			// TODO: Hide box
			if (started) {
				lock.lock();
				// TODO: Draw box
				canvas.drawSelectBox(startX, startY, mouse.getX(), mouse.getY());
				lock.unlock();
			}
			try {
				moved.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
