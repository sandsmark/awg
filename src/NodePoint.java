import java.awt.Point;


public class NodePoint extends Point{
	/**
	 * 
	 */
	private static final long serialVersionUID = -379851182051760208L;
	public int weight;
	
	public NodePoint (int x, int y, int weight) {
		this.x = x;
		this.y = y;
		this.weight = weight;
	}
}
