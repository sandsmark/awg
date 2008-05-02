import java.awt.Color;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Jan Berge Ommedal
 *
 */

public class UnitPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private JPanel group = new JPanel();
	
	private JPanel stats = new JPanel();
	private JPanel iconField = new JPanel();
	//STATS CONTAINS:
	private JLabel icon = new JLabel();
	private JLabel hp = new JLabel();
	private JLabel atk = new JLabel();
	private JLabel armor = new JLabel();
	private JLabel hpIcon;
	private JLabel atkIcon;
	private JLabel armorIcon;
	
	private List<Unit> currentlyDisplayed = Collections.synchronizedList(new ArrayList<Unit>());;
		
	public UnitPanel(){
		this.setLayout(null);
		group.setBounds(10, 10, 180, 230);
		group.setLayout(new GridLayout(5,3));
		group.setBackground(Color.WHITE);
		
		icon.setBounds(85, 10, 30, 30);
		iconField.setBounds(10, 10, 180, 50);
		iconField.setBackground(Color.WHITE);
		iconField.add(icon);
		
		stats.setBounds(10, 60, 180, 180);
		stats.setLayout(new GridLayout(3,2));
		stats.setBackground(Color.WHITE);
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
		
		
		this.add(iconField);
		this.add(stats);
		this.add(group);
	}
	/**
	 * This method readies the panel for new configuration by setting all elements invisible and removing
	 * all units from the list of currently displayed units. 
	 */
	
	public void deselect(){
		stats.setVisible(false);
		group.setVisible(false);
		icon.setVisible(false);
		iconField.setVisible(false);
		currentlyDisplayed.removeAll(currentlyDisplayed);
	}
	
	/**
	 * When this method receives a list of units as parameters, it determines if it is different from
	 * the currently shown units. If so, it determines if the received list contains only a single unit
	 * or is a group of units, and configures the panel accordingly. 
	 * @param selectedUnits
	 */
	
	public synchronized void select(List<Unit> selectedUnits){
		if(!selectedUnits.equals(currentlyDisplayed)){
			deselect();
			for(Unit u : selectedUnits){
				currentlyDisplayed.add(u);
			}
		if(selectedUnits.size()==1){
			singleunitSetup(selectedUnits.get(0));
		}else {
			groupSetup(selectedUnits);
			
		}
		this.setVisible(true);
		}
		
		
	}
	
	/**
	 * This method configures the UnitPanel to view a group of units received as parameter, in the panel. 
	 * @param selectedUnits
	 */
	
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
	
	/**
	 * This method configures the UnitPanel to view a single unit received as parameter, in the panel. 
	 * @param u
	 */

	public void singleunitSetup(Unit u){
		icon.setIcon(new ImageIcon(u.getSprite().get()));
		hp.setText(""+u.getCurrentHealth()+" / "+u.getMaxHealth());
		atk.setText(""+u.getDamage());
		armor.setText(""+u.getArmor());
		stats.setVisible(true);
		iconField.setVisible(true);
		icon.setVisible(true);
	}
	
}
	
	
	