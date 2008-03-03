import java.io.IOException;


public class Worker extends Unit {
//	Bevegelses- og idrettsvitenskap for Dragvoll
//	Bygg og miljøteknikk for Dragvoll
	
	public int carrying; //How much resources the worker is carrying, when == 10, go home or something

	public Worker(int faction, int mainHouseXcoord, int mainHouseYcoord){
		maxHealth = 50;
		CurrentHealth = maxHealth;
		damage = 5;
		xCoord = mainhouse coord + 0;
		yCoord = mainHouse coord + 5;
		range = 25;
		currentAction = 0;
		if(faction == 1) setSprite(sprite); //Gløshaugen
		else if(faction ==2)setSprite(sprite); //Dragvoll
	}
	
	public void deliverResource(){
		//GOTO mainhouse
		if(xCoord == mainhouse coord +- 25 || yCoord == mainhousecoord +-25){
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
