import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;


public class UnitButton extends JButton{
	private Unit u;	
	
	public UnitButton(Unit u) {
		this.u=u;
		this.setIcon(new ImageIcon(u.getSprite().get()));
		this.addActionListener(GameState.getUnits());
		this.setBackground(Color.WHITE);
	}

	public void setUnit(Unit u){
		this.u=u;
	}
	
	public Unit getUnit(){
		return u;
	}
	
	
}
