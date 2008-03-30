import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class WindowThread extends Thread {
	protected ReentrantLock lock = new ReentrantLock();
	protected Condition update = lock.newCondition();
	protected Canvas canvas;
	protected Map map;
	protected MainWindow window;
	protected boolean running = false;

	public WindowThread(MainWindow newWin) {
		window = newWin;
		canvas = window.getCanvas();
		map = canvas.getMap();
		running = true;
	}

	public void stopThread() {
		running = false;
	}

	@Override
	public void run() {
		SelectedUnits selectedUnits = GameState.getSelectedUnits();
		try {
			while (running) {
				window.delSeletectedUnit();
				for (int i = 0; i < selectedUnits.count(); i++)
					window.setSeletectedUnit(selectedUnits.get(i));
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
