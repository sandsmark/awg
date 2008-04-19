import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class UnitPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel group = new JPanel();
	
	private JPanel stats = new JPanel();
	//STATS CONTAINS:
	private JLabel icon = new JLabel();
	private JLabel hp = new JLabel();
	private JLabel atk = new JLabel();
	private JLabel armor = new JLabel();
	private JLabel hpIcon;
	private JLabel atkIcon;
	private JLabel armorIcon;
	
	
	
	
	public UnitPanel(){
		this.setLayout(null);
		group.setBounds(10, 10, 150, 180);
		group.setLayout(new GridLayout(5,3));
		
		icon.setBounds(60, 10, 30, 30);
		stats.setBounds(10, 40, 150, 150);
		stats.setLayout(new GridLayout(3,2));
		try {
			hpIcon=new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("/hp.png"))));
			atkIcon=new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("/dmg.png"))));
			armorIcon=new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("/armor.png"))));
		} catch (IOException e) {
			System.err.println("Couldn't load status icons");
		}
		
		stats.add(hpIcon);
		stats.add(hp);
		stats.add(atkIcon);
		stats.add(atk);
		stats.add(armorIcon);
		stats.add(armor);
		
		this.add(icon);
		this.add(stats);
		this.add(group);
		
	}
	
	public void deselect(){
		stats.setVisible(false);
		group.setVisible(false);
		icon.setVisible(false);
	}
	
	public synchronized void select(List<Unit> selectedUnits){
		if(selectedUnits.size()==1){
			group.setVisible(false);
			singleunitSetup(selectedUnits.get(0));
		}else {
			
			icon.setVisible(false);
			stats.setVisible(false);
			groupSetup(selectedUnits);
			
		}
		this.setVisible(true);
		
		
	}
	
	private void groupSetup(List<Unit> selectedUnits) {
		group.removeAll();
		if (selectedUnits.size()<16){
			for(Unit u : selectedUnits){
				group.add(new UnitButton(u));
			}
		}else{
			for(int i=0; i<14; i++){
				group.add(new UnitButton(selectedUnits.get(i)));
			}
			group.add(new JLabel("....."));
		}	
		group.setVisible(true);
	}

	public void singleunitSetup(Unit u){
		icon.setIcon(new ImageIcon(u.getSprite().get()));
		hp.setText(""+u.getCurrentHealth()+" / "+u.getMaxHealth());
		atk.setText(""+u.getDamage());
		armor.setText(""+u.getDamage());

		stats.setVisible(true);
		icon.setVisible(true);
	}
	
	@Override
	public void repaint(){
		
	}
}
	
	
	