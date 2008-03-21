import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class MovementThread extends Thread {
	protected ReentrantLock lock = new ReentrantLock();
	protected Condition update = lock.newCondition();
	protected Map map;
	protected Canvas canvas;
	protected boolean running = false;
	
	public MovementThread (Canvas newCanvas){
		map = newCanvas.getMap();
		canvas = newCanvas;
		running = true;
	}

	public void stopThread() {
		running = false;
	}

	public void run(){
		int tarX, tarY, curX, curY;
		try {				
			while (running){
				sleep(50);
				for (int i=0; i<map.getUnitNum(); i++) {
					tarX = map.getUnit(i).getCurrentTargetX();
					tarY = map.getUnit(i).getCurrentTargetY();
					if ((tarX!=-1)&&(tarY!=-1)){
						System.out.println(tarY);
						curX = map.getUnit(i).getX();
						curY = map.getUnit(i).getY();
						if (tarX > curX) map.getUnit(i).setX(curX+1);
						if (tarY > curY) map.getUnit(i).setY(curY+1);
						if (tarX < curX) map.getUnit(i).setX(curX-1);
						if (tarY < curY) map.getUnit(i).setY(curY-1);
						canvas.updateInternal();
						canvas.repaint();
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
