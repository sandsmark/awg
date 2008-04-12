import java.awt.Point;


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
		Unit unit;
		if(this.getPlayer() == GameState.getHuman()){
//			System.out.println(targetUnit);
		}
		if(targetUnit == null){
//			System.out.println("Inside targetunit ==null");
			for (int i = 0; i < GameState.getUnits().count(); i++) {
				unit = GameState.getUnits().getUnit(i);
//				System.out.println(unit);
				if(unit.getPlayer() != this.getPlayer() && position.distance(unit.position) < 100){
//					System.out.println("Sat target" + unit);
					setTargetUnit(unit);
					goTo(unit.position);
				}
			}
			if(targetUnit == null && position.distance(GameState.getComputer().getMainBuilding().getPosition())<10 && getPlayer() != GameState.getComputer()){
				GameState.getComputer().getMainBuilding().takeDamage(this.getDamage());
			}else if(targetUnit == null && position.distance(GameState.getHuman().getMainBuilding().getPosition())<10 && getPlayer() != GameState.getHuman()){
				GameState.getHuman().getMainBuilding().takeDamage(this.getDamage());
			}
		}else if(position.distance(getTargetUnit().position)<35){
			dealDamage();
		}else if(targetUnit!= null && position.distance(targetUnit.position)<200){
			setTargetUnit(null);
		}
		else if	(targetUnit!= null ){
			goTo(getTargetUnit().getPosition());
		}
			
		super.move();
	}
}
