
public class GameState {
	
	private Canvas canvas;
	private Config config = new Config();
	private SelectedUnits selectedUnits = new SelectedUnits();
	private Map map = new Map(1000,1000);
	
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
	
	public static Config getConfig() {
		return GameStateHolder.state.config;
	}
	
	public static SelectedUnits getSelectedUnits() {
		return GameStateHolder.state.selectedUnits;
	}
}
