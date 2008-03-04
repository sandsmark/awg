import java.awt.Image;
import java.awt.geom.Point2D;

public class Building {

	private Point2D position;
	public int health; //How many HP the building has
	public Image sprite;
	
	
	
	public Point2D getPosition() {
		return position;
	}
	public void setPosition(Point2D position) {
		this.position = position;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public Image getSprite() {
		return sprite;
	}
	public void setSprite(Image sprite) {
		this.sprite = sprite;
	}
}
