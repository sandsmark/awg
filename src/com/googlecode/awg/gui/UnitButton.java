package com.googlecode.awg.gui;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.googlecode.awg.state.GameState;
import com.googlecode.awg.units.Unit;

/**
 *	This class is used by UnitPanel when displaying groups of units. 
 *	It is JButton that also contains a specific unit. When the button is pressed, it sends a ActionEvent
 *	to the class GameState and the contained unit is selected.
 *	
 *  @author Jan Berge Ommedal
 *
 */
public class UnitButton extends JButton{
	private static final long serialVersionUID = 1L;
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
