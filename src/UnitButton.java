import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *	This class is used by UnitPanel when displaying groups of units. 
 *	It is JButton that also contains a specific unit. When the button is pressed, it sends a ActionEvent
 *	to the class GameState and the contained unit is selected.
 *	
 *  @author Jan Berge Ommedal
 *
 */
public class UnitButton extends JButton{
	private Unit u;	
	
	public UnitButton(Unit u) {
		this.u=u;
		updateIcon();
		this.addActionListener(GameState.getUnits());
		this.setBackground(Color.WHITE);
	}

	public void setUnit(Unit u){
		this.u=u;
	}
	
	public Unit getUnit(){
		return u;
	}
	
	public void updateIcon(){
		BufferedImage image = u.getSprite().get();
		Graphics g = image.createGraphics();
		g.setColor(Color.RED);
		g.drawRect(0, this.HEIGHT-10, this.WIDTH, 10);
		g.setColor(Color.GREEN);
		g.drawRect(0, this.HEIGHT-10, this.WIDTH*u.getCurrentHealth()/u.getMaxHealth(), 10);
		this.setIcon(new ImageIcon(image));
	}
	
	
}
