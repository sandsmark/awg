import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class WindowThread extends Thread {
	protected ReentrantLock lock = new ReentrantLock();
	protected Condition update = lock.newCondition();
	protected Canvas canvas;
	protected Map map;
	protected MainWindow window;
	protected boolean running = false;
	
	public WindowThread (MainWindow newWin){
		window = newWin;
		canvas = window.getCanvas();
		map = canvas.getMap();
		running = true;
	}

	public void stopThread() {
		running = false;
	}

	public void run(){
		String curText;
		try {				
			while (running){
				curText = "[";
				for (int i=0; i<map.getSelectedUnitNum(); i++)
					window.setSeletectedUnit(map.getSelectedUnit(i));
				
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
