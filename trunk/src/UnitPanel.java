import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class UnitPanel extends JPanel{
	private JPanel stats = new JPanel();
	private JLabel icon = new JLabel();
	
	
	
	public UnitPanel(){
		this.setLayout(null);
		icon.setBounds(60, 10, 30, 30);
		stats.setBounds(10, 40, 150, 150);
		stats.setLayout(new GridLayout(3,2));
		this.add(icon);
		this.add(stats);
		//this.setBackground(Color.red);
		
	}
	
	public void deselect(){
		stats.setVisible(false);
		icon.setIcon(new ImageIcon());
	}
	
	public void select(Unit u){
		stats.removeAll();
		icon.setIcon(new ImageIcon(u.getSprite().get()));
		stats.add(new JLabel(new ImageIcon("resources/hp.png")));
		stats.add(new JLabel(""+u.getCurrentHealth()+" / "+u.getMaxHealth()));
		stats.add(new JLabel(new ImageIcon("resources/dmg.png")));
		stats.add(new JLabel(""+u.getDamage()));
		stats.add(new JLabel(new ImageIcon("resources/armor.png")));
		stats.add(new JLabel(""+u.getDamage()));
		stats.setVisible(true);
		}
}
	
	
	