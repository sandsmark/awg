public class WindowThread extends Thread {
	protected boolean running = false;

	public WindowThread() {
		running = true;
	}

	public void stopThread() {
		running = false;
	}

	@Override
	public void run() {
		Units units = GameState.getUnits();
		MainWindow window = GameState.getMainWindow();
		try {
			while (running) {
				if (units.lock.tryLock()) {
					window.delSeletectedUnit();
					for (Unit selected : units.getSelectedUnits())
						window.setSelectedUnit(selected);
					GameState.getMainWindow().resPan.update();
					units.lock.unlock();
				}
				Thread.sleep(250);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
