import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeSet;


public class Path implements Comparable<Path> {
	LinkedList<Point> path = new LinkedList<Point>();

	/**
	 * This should be used when creating a new path.
	 * @param start
	 * @param target
	 */
	public Path(Point start, Point target) {
		Path tmpath = findPath(start, target);
		if (tmpath!=null){
			LinkedList<Point> tmp = tmpath.path;
			for (Point point: tmp)
				path.add(new Point(point.x*10, point.y*10));
			System.out.println(path);
		}
	}
	
	/**
	 * These variables are used for finding a path
	 */
	double left; // How much distance to target
	double length; // How long this path is

	/**
	 * Used by the findPath-routine.
	 * @param p
	 * @param point
	 * @param target
	 */
	private Path(Path old, Point point, Point target) {
		path.addAll(old.path);
		path.add(point);
		left = point.distance(target);
		length = old.length + 1; // Change this 1 to a variable somewhere in case we want to prefer some paths/terrain
	}
	
	/**
	 * Used by the findPath-routine, for adding the initial start-path.
	 * @param point
	 */
	private Path(Point point) { 
		path.add(point);
		left = 0;
		length = 0;
	}

	@Override
	public int compareTo(Path other) {
		if (other.getWeight() > this.getWeight()) return -1;
		else if (other.getWeight() < this.getWeight()) return 1;
		else return 0;
	}

	public double getWeight() {
		return left + length; 
	}
	
	public boolean isEmpty() {
		return path.isEmpty();
	}
	
	public Point getNext() {
		return path.pop();
	}
	
	public Point getLast() {
		return path.get(path.size() - 1);
	}
	
	/**
	 * Utilizes A* to find a Path from Point start to Point target.
	 * @param target
	 * @param start
	 * @param canvas
	 * @return
	 */
	public static Path findPath (Point start, Point target) {
		System.out.println("FIND ZE SEKRET BATH");
		
		int width = GameState.getMap().getWidth() / 10;
		int height= GameState.getMap().getHeight() / 10;
		
		start = new Point(start.x/10, start.y/10);
		target = new Point(target.x/10, target.y/10);
		
		Point[][] map = new Point[width][height];
		Map m = GameState.getMap();
		
		for (int x=0;x<width;x++)
			for (int y=0;y<height;y++)
				map[x][y] = new Point(x,y);
		
		map[start.x][start.y] = start;
		map[target.x][target.y] = target;
		
		TreeSet<Path> queue = new TreeSet<Path>();
		queue.add(new Path(start));
		ArrayList<Point> closed = new ArrayList<Point>();
		Path path;
		Point point;
		while (!queue.isEmpty()) {
			path = queue.first();
			queue.remove(path);
			point = path.getLast();
			
			if (closed.contains(point)) continue;
			if (point.equals(target)) return path;
			
			closed.add(point);	
			
			/**
			 * TODO: Fix up and optimize.
			 */
			if (m.canMove((point.x - 1)*10, point.y*10))
				queue.add(new Path(path, map[point.x - 1][point.y], target));
			if (m.canMove((point.x - 1)*10, (point.y - 1)*10)) 
					queue.add(new Path(path, map[point.x - 1][point.y - 1], target));
			if (m.canMove((point.x - 1)*10, (point.y + 1)*10)) 
					queue.add(new Path(path, map[point.x - 1][point.y + 1], target));
			

			if (m.canMove((point.x + 1)*10, point.y*10)) 
				queue.add(new Path(path, map[point.x + 1][point.y], target));
			if (m.canMove((point.x + 1)*10, (point.y - 1)*10)) 
				queue.add(new Path(path, map[point.x + 1][point.y - 1], target));
			if (m.canMove((point.x + 1)*10, (point.y + 1)*10))
				queue.add(new Path(path, map[point.x + 1][point.y + 1], target));
						
			if (m.canMove(point.x*10, (point.y - 1)*10))
				queue.add(new Path(path, map[point.x][point.y - 1], target));

			if (m.canMove(point.x*10, (point.y + 1)*10))
				queue.add(new Path(path, map[point.x][point.y + 1], target));

			//Finished adding new points
	
		}
		return null;
	}
}
