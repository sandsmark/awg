
public class GameState {

	private Config config = new Config();
	private SelectedUnits selectedUnits = new SelectedUnits();
	private Map map = new Map(1000,1000);
	private Units units = new Units();
	private MainWindow mainWindow;
	
	private GameState() {}

	private static class GameStateHolder {
		private final static GameState state = new GameState();
	}
	
	public static void setMainWindow(MainWindow mainWindow) {
		GameStateHolder.state.mainWindow = mainWindow;
	}
	
	public static GameState getState() {
		return GameStateHolder.state;
	}
	
	public static Map getMap() {
		return GameStateHolder.state.map;
	}

	
	public static Config getConfig() {
		return GameStateHolder.state.config;
	}

	public static Units getUnits() {
		return GameStateHolder.state.units;
	}
	
	public static SelectedUnits getSelectedUnits() {
		return GameStateHolder.state.selectedUnits;
	}
	
	public static MainWindow getMainWindow() {
		return GameStateHolder.state.mainWindow;
	}
}
