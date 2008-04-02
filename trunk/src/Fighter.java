import java.awt.Point;


public class Fighter extends Unit {

	
	public Fighter(Player player){
		setMaxHealth(100);
		setCurrentHealth(getMaxHealth());
		setDamage(5);
		setRange(25);
		setCurrentAction(0);
		setPosition(new Point(player.mainHouse.getPosition().x +5, player.mainHouse.getPosition().y+5)); // FIXX martin :P
		setPlayer(player);
	}
}
