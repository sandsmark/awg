import java.io.IOException;


public class Healer extends Unit {
	
	private int mana;

	public Healer(int faction, int buildingXcoord, int buildingYcoord){
		xCoord = buildingXcoord +5;
		yCoord = buildingYcoord +5;
		setMana(100);
		setMaxHealth(75);
		setCurrentHealth(getMaxHealth());
		setCurrentAction(0);
		setDamage(10);
		if(faction == 1) setSprite(sprite); //Gløshaugen
		else if(faction ==2)setSprite(sprite); //Dragvoll
		
	}
	
	
	public void heal(Unit target){
		if(mana>=10){
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
