package com.googlecode.awg.ai;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.googlecode.awg.units.Unit;

public class AIThread extends Thread {
	protected ReentrantLock lock = new ReentrantLock();
	protected Condition update = lock.newCondition();
	protected boolean running = false;
	protected Unit[] units; // units that this AI owns
	AI ai = new AI();
	protected int sleeptime = 4000; // Sleeptime in milliseconds
	protected long lastSlept = System.currentTimeMillis();

	
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
				ai.updateTime();
				if(ai.willDefend()) {
				offender = ai.getOffender();
					ai.defendAgainst(offender);
				}
				ai.build();
				if(ai.idleWorkers()) ai.goNext();
				ai.defendWorkers();
				if(ai.willLaunchAttack()) ai.launchAttack();
				if (sleeptime - (System.currentTimeMillis() - lastSlept) > 0) sleep(sleeptime - (System.currentTimeMillis() - lastSlept));
				this.lastSlept = System.currentTimeMillis();
		 		
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
