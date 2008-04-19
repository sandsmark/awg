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
					if (units.getSelectedUnits().size() > 0){
						window.setSelectedUnit(units.getSelectedUnits());
					}
					else{
						window.delSeletectedUnit(); 
					}
					GameState.getMainWindow().resPan.update();
					units.lock.unlock();
				}
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
