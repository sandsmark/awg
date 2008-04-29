import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This is responsible for moving us around the map.
 * @authors freqmod, sandsmark
 *
 */
public class GraphicsThread extends Thread {
	protected Moveable.Direction curdir = Moveable.Direction.NONE;
	protected boolean running;
	protected ReentrantLock lock = new ReentrantLock();
	protected Condition updated = lock.newCondition();
	protected long sleeptime = 0;

	/**
	 * This creates a new thread, and makes it start running.
	 */
	GraphicsThread() {
		this.sleeptime = Config.getSleeptime();
		this.running = true;
		this.curdir = Moveable.Direction.NONE;
	}

	/**
	 * This stops the running thread.
	 */
	public void stopThread() {
		this.running = false;
	}

	/**
	 * This is the main method of this thread, and is responsible for interacting 
	 * with the Canvas to move the displayed area of the map.
	 */
	@Override
	public void run() {
		Canvas canvas = GameState.getMainWindow().getCanvas(); 
		while (running) {
			lock.lock();
			if (curdir != Moveable.Direction.NONE) {
				canvas.move(curdir);
				lock.unlock();
				try {
					sleep(sleeptime);
				} catch (InterruptedException e) {
					System.err.println("Interrupted sleep");
				}
			} else {
				try {
					updated.await();
				} catch (InterruptedException e) {
					System.err.println("Interrupted wait");
				}
				lock.unlock();
			}
		}
	}

	/**
	 * This gets called when the displayed area gets moved.
	 * @param dir the direction in which to move.
	 */
	public void setDirection(Moveable.Direction dir) {
		lock.lock();
		curdir = dir;
		updated.signal();
		lock.unlock();

	}

	/**
	 * @return returns the current direction.
	 */
	public Moveable.Direction getDirection() {
		try {
			lock.lock();
			return curdir;
		} catch (Exception e) {
			System.err.println("Exception while getting direction.");
			return Moveable.Direction.NONE;
		} finally {
			lock.unlock();
		}
	}
}
