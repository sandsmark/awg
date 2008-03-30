import java.awt.Point;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class MovementThread extends Thread {
	protected ReentrantLock lock = new ReentrantLock();	// This lock makes sure threading works okay
	protected Condition update = lock.newCondition();	//This synchronises things nicely
	protected Map map;					//This holds the current Map object
	protected Canvas canvas;			//This holds the current canvas object
	protected boolean running = false;	//This is true while this thread is running
	protected double speed = 2.5;		//Movement speed, in pixels per 0.1 second. 
	protected double last = System.currentTimeMillis();
	
	public MovementThread (Canvas newCanvas){
		map = newCanvas.getMap();
		canvas = newCanvas;
		running = true;
	}

	public void stopThread() {
		running = false;
	}

	public void run(){
		int tarX, tarY, curX, curY; // Target X&Y, and current X&Y, resp.
		int dMove;	//Delta move, how far to move, this grows linearly with the time since last movement
		try {
			while (running){
				sleep(150);
				dMove = (int)((System.currentTimeMillis() - last) / 100 * speed);
				last = System.currentTimeMillis();
				for (int i=0; i<map.getUnitNum(); i++) {
					tarX = map.getUnit(i).getCurrentTargetX();
					tarY = map.getUnit(i).getCurrentTargetY();
					if ((tarX!=-1)&&(tarY!=-1)){
						curX = map.getUnit(i).getPosition().x;
						curY = map.getUnit(i).getPosition().y;
						if (tarX > curX) map.getUnit(i).setPosition(new Point(curX + dMove, curY));
						if (tarY > curY) map.getUnit(i).setPosition(new Point(curX, curY + dMove));
						if (tarX < curX) map.getUnit(i).setPosition(new Point(curX - dMove, curY));
						if (tarY < curY) map.getUnit(i).setPosition(new Point(curX, curY - dMove));
						
						canvas.setDirty();
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
