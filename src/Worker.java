import java.io.IOException;
import java.awt.geom.Point2D;

public class Worker extends Unit {
//	Bevegelses- og idrettsvitenskap for Dragvoll
//	Bygg og miljøteknikk for Dragvoll
	
	private int carrying; //How much resources the worker is carrying, when == 10, go home or something

	public Worker(int faction, Point2D mainBuilding){
		setMaxHealth(50);
		setCurrentHealth(getMaxHealth());
		setDamage(5);
		setRange(25);
		setCurrentAction(0);
		setPosition(mainBuilding +25); //FIXX martin :P
		
		if(faction == 1) setSprite(TheSpriteThatWillRepresentTheUnit); //Gløshaugen
		else if(faction ==2)setSprite(TheSpriteThatWillRepresentTheUnit); //Dragvoll
	}
	
	public void deliverResource(){
		//GOTO mainhouse
		if(getPosition().getX() == mainhouse coord +- 25 || getPosition().getY() == mainhousecoord +-25){
			int delivered = getCarrying();
			//increase resource counter with delivered
			setCarrying(0);
		}
	}

	public int getCarrying() {
		return carrying;
	}

	public void setCarrying(int carrying) {
		this.carrying = carrying;
	}
}
