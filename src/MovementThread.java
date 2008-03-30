import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MovementThread extends Thread {
	protected ReentrantLock lock = new ReentrantLock(); // This lock makes sure
														// threading works okay
	protected Condition update = lock.newCondition(); // This synchronises
														// things nicely
	protected Map map; // This holds the current Map object
	protected Canvas canvas; // This holds the current canvas object
	protected boolean running = false; // This is true while this thread is
										// running

	public MovementThread(Canvas newCanvas) {
		map = newCanvas.getMap();
		canvas = newCanvas;
		running = true;
	}

	public void stopThread() {
		running = false;
	}

	@Override
	public void run() {
		try {
			while (running) {
				sleep(150);
				for (int i = 0; i < map.getUnitNum(); i++) {
					if (map.getUnit(i).move() != 0)
						canvas.setDirty();
					canvas.repaint();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
