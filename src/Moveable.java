public interface Moveable {
	enum Direction {
		NONE, UP, DOWN, LEFT, RIGHT
	}

	public void move(Direction dir);
}
