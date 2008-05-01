import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class AIThread extends Thread {
	protected ReentrantLock lock = new ReentrantLock();
	protected Condition update = lock.newCondition();
	protected boolean running = false;
	protected Unit[] units; // units that this AI owns
	AI ai = new AI();
	
	public AIThread() {
		running = true;
		
	}

	public void stopThread() {
		running = false;
	}

	@Override
	public void run() {
		Unit offender;
		try {
			while (running) {
				if(ai.willDefend()) {
				offender = ai.getOffender();
					ai.defendAgainst(offender);
				}
				ai.build();
				if(ai.idleWorkers()) ai.goNext();
				ai.defendWorkers();
				if(ai.willLaunchAttack()) ai.launchAttack();
		 		sleep(2000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
