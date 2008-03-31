import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class AIThread extends Thread {
	protected ReentrantLock lock = new ReentrantLock();
	protected Condition update = lock.newCondition();
	protected boolean running = false;
	protected Unit[] units; // units that this AI owns

	public AIThread() {
		running = true;
	}

	public void stopThread() {
		running = false;
	}

	@Override
	public void run() {
		try {
			while (running) {
				sleep(2000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
