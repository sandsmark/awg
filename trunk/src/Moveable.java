public interface Moveable {
	public enum Direction {
		NONE, UP, DOWN, LEFT, RIGHT
	}

	public void move(Direction dir);
}
