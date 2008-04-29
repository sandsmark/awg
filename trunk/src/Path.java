/*
Copyright (C) 2008 Martin T. Sandsmark

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * This class contains a path, and methods for finding new paths. 
 * @author Martin T. Sandsmark
 */
public class Path implements Comparable<Path> {
	LinkedList<Point> path = new LinkedList<Point>();

	
	/**
	 * The factor of which to downscale, for optimization.
	 */
	private static int factor = Config.getMaskSize();
	
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
				path.add(new Point(point.x*factor, point.y*factor));
		}
	}
	
	/**
	 * These variables are used for finding a path
	 */
	double left; // How much distance to target
	int length; // How long this path is

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

	/**
	 * This returns the length of the path.
	 * @return
	 */
	public int getLength() {
		return length;
	}
	
	/**
	 * This compares this path to another path.
	 */
	public int compareTo(Path other) {
		if (other.getWeight() > this.getWeight()) return -1;
		else if (other.getWeight() < this.getWeight()) return 1;
		else return 0;
	}

	/**
	 * Return the weight of this path (length + distance to target)
	 */
	public double getWeight() {
		return left + length; 
	}
	
	/**
	 * @return returns true if this path is empty.
	 */
	public boolean isEmpty() {
		return path.isEmpty();
	}
	
	/**
	 * Returns and removes next point in the path.
	 */
	public Point getNext() {
		if (!path.isEmpty()) return path.remove();
		return null;
	}
	
	/**
	 * @return returns the last point in the path.
	 */
	public Point getLast() {
		return path.get(path.size() - 1);
	}
	
	/**
	 * Utilizes A* to find a Path from Point start to Point target.
	 * @param target is the target point.
	 * @param start is the point of origin.
	 * @return returns shortest path from start to target.
	 */
	public static Path findPath (Point start, Point target) {
		long startTime = System.currentTimeMillis();
		
		int width = GameState.getMap().getWidth() / factor;
		int height= GameState.getMap().getHeight() / factor;
		
		start = new Point(start.x/factor, start.y/factor);
		target = new Point(target.x/factor, target.y/factor);
		
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
			if (startTime - System.currentTimeMillis() > 1000) {
				System.err.println("Pathfinding timed out!");
				return null;
			}
			path = queue.first();
			queue.remove(path);
			point = path.getLast();
			
			if (closed.contains(point)) continue;
			if (point.equals(target)) return path;
			
			closed.add(point);	
			
			// Add neighouring points to the queue.
			for (int dx = -1; dx <= 1; dx++)
				for (int dy = -1; dy <= 1; dy ++)
					if (!(dx == 0 && dy == 0)) 
						if (m.canMove(((point.x + dx) * factor) + factor, ((point.y + dy) * factor) + factor))
							if (path.getLength() < Config.getWorldHeight() + Config.getWorldWidth())
								queue.add(new Path(path, map[point.x + dx][point.y + dy], target));	
		}
		return null;
	}
}
