import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class AIThread extends Thread {
	protected ReentrantLock lock = new ReentrantLock();
	protected Condition update = lock.newCondition();
	protected Canvas canvas;
	protected Map map;
	protected boolean running = false;
	protected Unit[] units; // units that this AI owns

	public AIThread (Canvas ncanvas){
		canvas = ncanvas;
		map = canvas.getMap();
		running = true;
	}
	
	public void stopThread() {
		running = false;
	}

	public void run(){
		try {				
			while (running){
				canvas.updateInternal();
				System.out.println(map.getSelectedUnitNum());
				sleep(2000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
