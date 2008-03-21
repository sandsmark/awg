import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class MovementThread extends Thread {
	protected ReentrantLock lock = new ReentrantLock();
	protected Condition update = lock.newCondition();
	protected Map map;
	protected boolean running = false;
	
	public MovementThread (Map newMap){
		map = newMap;
		running = true;
	}

	public void stopThread() {
		running = false;
	}

	public void run(){
		int tarX, tarY, curX, curY;
		try {				
			while (running){
				for (int i=0; i<map.getUnitNum(); i++) {
					tarX = map.getUnit(i).getCurrentTargetX();
					tarY = map.getUnit(i).getCurrentTargetY();
					if ((tarX!=-1)&&(tarY!=-1)){
						curX = map.getUnit(i).getX();
						curY = map.getUnit(i).getY();
						if (tarX > curX) map.getUnit(i).setX(curX+1);
						if (tarY > curY) map.getUnit(i).setY(curY+1);
						if (tarX < curX) map.getUnit(i).setX(curX-1);
						if (tarY < curY) map.getUnit(i).setY(curY-1);
					}
				sleep(100);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
