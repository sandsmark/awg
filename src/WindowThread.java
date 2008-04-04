import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class WindowThread extends Thread {
	protected ReentrantLock lock = new ReentrantLock();
	protected Condition update = lock.newCondition();
	protected MainWindow window;
	protected boolean running = false;

	public WindowThread(MainWindow newWin) {
		window = newWin;
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
				window.delSeletectedUnit();
				for (Unit selected : units.getSelectedUnits())
					window.setSeletectedUnit(selected);
				GameState.getMainWindow().resPan.update();
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
