
public class GameState {
	private Map map = new Map();
	private Units units = new Units();
	private MainWindow mainWindow;
	private Player human = new Player(false, 200); // is not AI
	private Player computer = new Player(true, 200); // this, however, is AI
	private long start = System.currentTimeMillis();
	private AI ai;
	
	


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

	public static Units getUnits() {
		return GameStateHolder.state.units;
	}
	
	public static MainWindow getMainWindow() {
		return GameStateHolder.state.mainWindow;
	}
	
	public static Player getHuman() {
		return GameStateHolder.state.human;
	}
	
	public static Player getComputer() {
		return GameStateHolder.state.computer;
	}
	
	public static long getTime() {
		return (System.currentTimeMillis() - GameStateHolder.state.start) ;
	}
	public AI getAi() {
		return GameStateHolder.state.ai;
	}
	public void setAi(AI ai) {
		GameStateHolder.state.ai=ai;
	}


}
