import java.io.File;
import java.io.IOException;
import java.awt.geom.Point2D;

import javax.imageio.ImageIO;

public class Worker extends Unit {
//	Bevegelses- og idrettsvitenskap for Dragvoll
//	Bygg og milj�teknikk for Dragvoll
	
	private int carrying; //How much resources the worker is carrying, when == 10, go home or something

	public Worker(int faction, int nx, int ny) throws IOException{
		setMaxHealth(50);
		setCurrentHealth(getMaxHealth());
		setDamage(5);
		setRange(25);
		setCurrentAction(0);
		setPosition(nx+2, ny+2); //FIXX martin :P
		
//		if(faction == 1) 
		setSprite(ImageIO.read(new File("resources/dragvoll-noe.gif"))); //Gl�shaugen
//		else if(faction ==2)setSprite(TheSpriteThatWillRepresentTheUnit); //Dragvoll
	}
	
	public void deliverResource(){
		//GOTO mainhouse
//		if(getX() == mainhouse coord +- 25 || getPosition().getY() == mainhousecoord +-25){
//			int delivered = getCarrying();
//			//increase resource counter with delivered
//			setCarrying(0);
//		}
	}

	public int getCarrying() {
		return carrying;
	}

	public void setCarrying(int carrying) {
		this.carrying = carrying;
	}
	
	public String toString(){
		return "Worker(x:"+getX()+",y"+getY()+ ")";
	}
}
