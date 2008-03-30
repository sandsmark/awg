import java.util.ArrayList;
import java.util.Stack;
import java.util.TreeSet;

public class Path implements Comparable<Path> {
	Stack<WeightedNode> path;
	int cur;
	int tarX, tarY; // For a-star to work correctly, we need to know the
					// distance to the target node

	public Path(Map map, int curX, int curY, int tarX, int tarY) {
		System.out.println("generating path...");
		curX /= 10; // Divide by ten, hairy, but works
		curY /= 10;
		tarX /= 10;
		tarY /= 10;
		path = new Stack<WeightedNode>();
		int[][] pmap = map.getPathMap();
		WeightedNode[][] nmap = new WeightedNode[pmap.length][pmap[0].length];

		for (int x = 0; x < pmap.length; x++)
			for (int y = 0; y < pmap[0].length; y++)
				nmap[x][y] = new WeightedNode(x, y, pmap[x][y]);

		ArrayList<WeightedNode> closed = new ArrayList<WeightedNode>();// Closed
		TreeSet<Path> queue = new TreeSet<Path>(); // Queue of paths
		queue.add(new Path(curX, curY, pmap[curX][curY], tarX, tarY)); // Add
																		// path
																		// with
																		// only
																		// starting
																		// point

		Path p;
		WeightedNode point;
		while (!queue.isEmpty()) {
			queue.remove(p = queue.last()); // The last is the one with the
											// lowest weight.
			point = p.getEnd();
			if (closed.contains(point))
				continue;
			if (point.x == tarX && point.y == tarY) {
				System.out.println(p.getPath());
				path = p.getPath();
				return;
			}
//			System.out.println(p);
			closed.add(point);
			if (point.x < pmap.length - 1) {
				queue.add(new Path(p, nmap[point.x + 1][point.y]));
				if (point.y > 0)
					queue.add(new Path(p, nmap[point.x + 1][point.y - 1]));
				if (point.y < pmap[0].length - 1)
					queue.add(new Path(p, nmap[point.x + 1][point.y + 1]));
			}
			if (point.y < pmap[0].length - 1)
				queue.add(new Path(p, nmap[point.x][point.y + 1]));
			if (point.x > 0) {
				queue.add(new Path(p, nmap[point.x - 1][point.y]));
				if (point.y > 0)
					queue.add(new Path(p, nmap[point.x - 1][point.y - 1]));
				if (point.y < pmap[0].length - 1)
					queue.add(new Path(p, nmap[point.x - 1][point.y + 1]));
			}
			if (point.y > 0)
				queue.add(new Path(p, nmap[point.x][point.y - 1]));
		}
		System.out.println("did not find a path, quitting");
		path = null;
	}

	public WeightedNode pop() {
		return path.pop();
	}

	public Path(int x, int y, int weight, int tarX, int tarY) { // Create a path
																// with only one
																// node/point.
		this.tarX = tarX;
		this.tarY = tarY;
		path = new Stack<WeightedNode>();
		path.add(new WeightedNode(x, y, weight));
	}

	public Path(Path old, WeightedNode endPoint) { // Create new path based on
													// old path and a new
													// endpoint
		path = new Stack<WeightedNode>();
		path.addAll(old.getPath());
		path.add(endPoint);
	}

	public Path() {
		path = new Stack<WeightedNode>();
	}

	public Stack<WeightedNode> getPath() {
		return path;
	}

	public void addPoint(WeightedNode p) {
		path.add(p);
	}

	public WeightedNode getEnd() {
		return path.lastElement();
	}

	public int getWeight() {
		int sum = 0;
		for (int i = 0; i < path.size(); i++) {
			sum += path.get(i).weight;
		}
		sum += Math.sqrt(Math.pow(tarX - path.lastElement().x, 2)
				+ Math.pow(tarY - path.lastElement().y, 2)); // Pythagoras
		// ^ Square root ((Target x - current x)² + (Target y - current y)²)
		return sum;
	}

	public int compareTo(Path p) {
		int oWeight = p.getWeight();
		int tWeight = this.getWeight();
		if (oWeight > tWeight)
			return -1;
		else if (oWeight < tWeight)
			return 1;
		else
			return 0;
	}

	public int getLength() {
		return path.size();
	}
}
