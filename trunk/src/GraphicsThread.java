import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class GraphicsThread extends Thread {
	protected Moveable m;
	protected Moveable.Direction curdir;
	protected boolean running;
	protected ReentrantLock lock = new ReentrantLock();
	protected Condition updated = lock.newCondition();
	protected long sleeptime = 0;

	GraphicsThread(Moveable m, long s) {
		this.m = m;
		this.sleeptime = s;
		this.running = true;
		this.curdir = Moveable.Direction.NONE;
	}

	public void stopThread() {
		this.running = false;
	}

	@Override
	public void run() {
		while (running) {
			lock.lock();
			if (curdir != Moveable.Direction.NONE) {
				m.move(curdir);
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

	public void setDirection(Moveable.Direction dir) {
		lock.lock();
		curdir = dir;
		updated.signal();
		lock.unlock();

	}

	public Moveable.Direction getDirection() {
		try {
			lock.lock();
			return curdir;
		} catch (Exception e) {
			System.err.println("exception while getting direction");
			return Moveable.Direction.NONE;
		} finally {
			lock.unlock();
		}
	}
}