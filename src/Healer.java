import java.io.IOException;


public class Healer extends Unit {
	
	public int mana;

	public Healer(int faction, int buildingXcoord, int buildingYcoord){
		xCoord = buildingXcoord +5;
		yCoord = buildingYcoord +5;
		mana = 100;
		maxHealth = 75;
		CurrentHealth = maxHealth;
		currentAction = 0;
		damage = 10;
		if(faction == 1) setSprite(sprite); //Gløshaugen
		else if(faction ==2)setSprite(sprite); //Dragvoll
		
	}
	
	
	public void heal(Unit target){
		if(mana>10){
			target.setCurrentHealth(target.getCurrentHealth()+50);
			this.setMana(getMana()-10);
		}
	}


	public int getMana() {
		return mana;
	}


	public void setMana(int mana) {
		this.mana = mana;
	}
}
