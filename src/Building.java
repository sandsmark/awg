import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Point2D;

public class Building {

	private Point position;
	public int health; // How many HP the building has
	public Image sprite;
	
	
	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
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
	
	public void spawn (int type, Player owner) { // 1 = Healer, 2 = worker, 3 = Fighter
		Unit unit;
		if (type == 1) unit = new Healer(owner, position.x, position.y);
		else if (type == 2) unit = new Worker(owner, position.x, position.y);
//		else if (type == 3) unit = new Fighter(owner, position.x, position.y);
		else return;
		GameState.getUnits().addUnit(unit);
	}
}
