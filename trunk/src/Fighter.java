import java.awt.Point;


public class Fighter extends Unit {
	protected static double damageMultiplier = 2;
	public static int cost = 500;
	
	
	/**
	 * Constructor setting values to variables and adding the unit to the game.
	 * Sets the owner of the unit to the player given as parameter
	 * @param player
	 */
	public Fighter(Player player){
		player.decreaseResources(cost);
		
		type = "fighter";
		setMaxHealth(100);
		setCurrentHealth(getMaxHealth());
		setRange(25);
		setCurrentAction(0);
		setPosition(new Point(player.mainHouse.getPosition().x +5, player.mainHouse.getPosition().y+5)); // FIXX martin :P
		setPlayer(player);
		if (player.isAI()) sprite = new Sprite(type, 1);
		else sprite = new Sprite(type, 0);
		GameState.getMainWindow().canvas.setDirty(position.x, position.y, position.x + sprite.getWidth(), position.y + sprite.getHeight());
	}
	
	
	/**
	 * The move method specific for Fighter. Searches for units to attack,
	 * and attacks the mainbuilding of the opponent if there are no enemy units nearby
	 */
	public void move(){
		super.move();
		if(targetUnit == null) {
			sprite.setDoing(false);
			for (Unit unit : GameState.getUnits().getUnits()) {
				if(unit.getPlayer() != this.getPlayer() && position.distance(unit.position) < 100){
					setTargetUnit(unit);
				}
			}
			if(targetUnit == null && position.distance(GameState.getComputer().getMainBuilding().getPosition())<75 && getPlayer() != GameState.getComputer()){
				GameState.getComputer().getMainBuilding().takeDamage(this.getDamage());
			}else if(targetUnit == null && position.distance(GameState.getHuman().getMainBuilding().getPosition())<75 && getPlayer() != GameState.getHuman()){
				GameState.getHuman().getMainBuilding().takeDamage(this.getDamage());
			}

		} else if (targetUnit.getCurrentHealth() <= 0){
			sprite.setDoing(false);
			targetUnit = null;
			GameState.getMainWindow().canvas.setDirty(position.x, position.y, position.x + sprite.getWidth(), position.y + sprite.getHeight());
			return;
		} else if(position.distance(getTargetUnit().position) <= 35){
			sprite.setDoing(true);
			this.dealDamage();
			if (targetUnit.getPlayer() != null) targetUnit.getPlayer().attacked();
			GameState.getMainWindow().canvas.setDirty(position.x, position.y, position.x + sprite.getWidth(), position.y + sprite.getHeight());
		} else if (this.path.isEmpty()){
			sprite.setDoing(false);
			this.goTo(targetUnit.position);
		}
	}
}
