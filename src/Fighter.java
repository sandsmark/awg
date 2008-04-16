import java.awt.Point;

import javax.swing.JDialog;


public class Fighter extends Unit {
	
	private final static int attackPower = 5;
	

	
	public Fighter(Player player){
		type = "fighter";
		setMaxHealth(100);
		setCurrentHealth(getMaxHealth());
		setDamage(attackPower);
		setRange(25);
		setCurrentAction(0);
		setPosition(new Point(player.mainHouse.getPosition().x +5, player.mainHouse.getPosition().y+5)); // FIXX martin :P
		setPlayer(player);
		if (player.isAI()) sprite = new Sprite(type, 1);
		else sprite = new Sprite(type, 0);
		GameState.getMainWindow().canvas.repaint();
	}
	
	public void move(){
		if(targetUnit == null){
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
			targetUnit = null;
			return;
		} else if(position.distance(getTargetUnit().position)<=35){
			this.dealDamage();
		} else if(targetUnit!= null && position.distance(targetUnit.position)>35){
			this.goTo(targetUnit.position);
		}
			
		super.move();
	}
}
