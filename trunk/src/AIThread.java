import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class AIThread extends Thread {
	protected ReentrantLock lock = new ReentrantLock();
	protected Condition update = lock.newCondition();
	protected boolean running = false;
	protected Unit[] units; // units that this AI owns
	AI ai;
	
	public AIThread() {
		running = true;
		ai = new AI();
	}

	public void stopThread() {
		running = false;
	}

	@Override
	public void run() {
		Unit offender;
		try {
			while (running) {
				offender = ai.getOffender();
				if (offender != null) ai.defendAgainst(offender);
				ai.build();
				
		 		sleep(2000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
