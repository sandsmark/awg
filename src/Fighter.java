import java.awt.Point;


public class Fighter extends Unit {

	
	public Fighter(Player player){
		type = "fighter";
		setMaxHealth(100);
		setCurrentHealth(getMaxHealth());
		setDamage(5);
		setRange(25);
		setCurrentAction(0);
		setPosition(new Point(player.mainHouse.getPosition().x +5, player.mainHouse.getPosition().y+5)); // FIXX martin :P
		setPlayer(player);
		if (player.isAI()) sprite = new Sprite(type, 1);
		else sprite = new Sprite(type, 0);
		GameState.getMainWindow().canvas.repaint();
	}
	
	public int move(){
		Unit unit;
		if(targetUnit == null){
			for (int i = 0; i < GameState.getUnits().count(); i++) {
				unit = GameState.getUnits().getUnit(i);
				if(unit.getPlayer() != this.getPlayer() && position.distance(unit.position) < 100){
					setTargetUnit(unit);
					goTo(unit.position);
				}
			}
		}else if(position.distance(getTargetUnit().position)<35){
			dealDamage();
		}
		return super.move();
	}
}
