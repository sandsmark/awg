
public class GameState {
	private Map map;
	private Canvas canvas;
	
	private GameState() {}

	private static class GameStateHolder {
		private final static GameState state = new GameState();
	}
	
	public static GameState getState() {
		return GameStateHolder.state;
	}
	
	public static Map getMap() {
		return GameStateHolder.state.map;
	}
	
	public static Canvas getCanvas() {
		return GameStateHolder.state.canvas;
	}
	
	
}
