import java.awt.Point;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;


public class Path implements Comparable<Path> {
	Stack<NodePoint> path;
	int cur;
	public Path (Map map, int curX, int curY, int tarX, int tarY) {
		curX /= 10; // Divide by ten, hairy, but works
		curY /= 10;
		tarX /= 10;
		tarY /= 10;
		path = new Stack<NodePoint>();
		int [][] pmap = map.getPathMap();
		NodePoint[][] nmap = new NodePoint[pmap.length][pmap[0].length];
		
		for (int x=0; x<pmap.length; x++)
			for (int y=0; y<pmap[0].length; y++) 
				nmap[x][y] = new NodePoint(x,y,pmap[x][y]);
		
		
		Path fpath;
		ArrayList<NodePoint> closed = new ArrayList<NodePoint>();// Closed
		TreeSet<Path> queue = new TreeSet<Path>();			 //Queue of paths
		queue.add(new Path(curX, curY, pmap[curX][curY]));	// Add path with only starting point
		
		Path curP;
		NodePoint point;
		while (!queue.isEmpty()) {
			queue.remove(curP = queue.last()); // The last is the one with the lowest weight.
			point = curP.getEnd();
			if (closed.contains(point)) continue;
			if (point.x == tarX && point.y == tarY) {
				path = curP.getPath();
				return;
			}
			closed.add(point);
			if (point.x < pmap.length - 1) {
				queue.add(new Path(curP, nmap[point.x+1][point.y]));
				if (point.y > 0)
					queue.add(new Path(curP, nmap[point.x+1][point.y-1]));
				if (point.y < pmap[0].length - 1)
					queue.add(new Path(curP, nmap[point.x+1][point.y+1]));
			}
			if (point.y < pmap[0].length - 1) queue.add(new Path(curP, nmap[point.x][point.y+1]));
			if (point.x > 0) {
				queue.add(new Path(curP, nmap[point.x-1][point.y]));
				if (point.y > 0)
					queue.add(new Path(curP, nmap[point.x-1][point.y-1]));
				if (point.y < pmap[0].length - 1)
					queue.add(new Path(curP, nmap[point.x-1][point.y+1]));
			}
			if (point.y > 0) queue.add(new Path(curP, nmap[point.x][point.y - 1]));
		}
		path = new Path(curX, curY, pmap[curX][curY]).getPath(); // Gief aus, close to an empty path
		return;
	}
	
	public NodePoint pop() {
		return path.pop();
	}
	
	public Path(int x, int y, int weight) { // Create a path with only one node/point.
		path = new Stack<NodePoint>();
		path.add(new NodePoint(x, y, weight));
	}
	
	public Path (Path old, NodePoint endPoint) { // Create new path based on old path and a new endpoint
		path = new Stack<NodePoint>();
		path.addAll(old.getPath());
		path.add(endPoint);
	}
	
	public Path(){
		path = new Stack<NodePoint>();
	}
	
	public Stack<NodePoint> getPath() {
		return path;
	}
	
	public void addPoint(NodePoint p) {
		path.add(p);
	}
	
	public NodePoint getEnd() {
		return path.lastElement();
	}
	
	public int getWeight() {
		int sum=0;
		for (int i=0; i<path.size(); i++) {
			sum += path.get(i).weight;
		}
		return sum;
	}

	@Override
	public int compareTo(Path p) {
		int oWeight = p.getWeight();
		int tWeight = this.getWeight();
		if (oWeight > tWeight) return -1;
		else if (oWeight < tWeight) return 1;
		else return 0;
	}
}
