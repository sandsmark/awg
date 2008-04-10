import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MovementThread extends Thread {
	protected ReentrantLock lock = new ReentrantLock(); // This lock makes sure
														// threading works okay
	protected Condition update = lock.newCondition(); // This synchronises
														// things nicely
	protected boolean running = false; // This is true while this thread is
										// running

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
				sleep(50);
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
