import java.awt.GridLayout;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class UnitPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel stats = new JPanel();
	private JLabel icon = new JLabel();
	
	
	
	public UnitPanel(){
		this.setLayout(null);
		icon.setBounds(60, 10, 30, 30);
		stats.setBounds(10, 40, 150, 150);
		stats.setLayout(new GridLayout(3,2));
		this.add(icon);
		this.add(stats);
	}
	
	public void deselect(){
		stats.setVisible(false);
		icon.setIcon(new ImageIcon());
	}
	
	public void select(Unit u){
		stats.removeAll();
		icon.setIcon(new ImageIcon(u.getSprite().get()));
		try {
			stats.add(new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("/hp.png")))));
			stats.add(new JLabel(""+u.getCurrentHealth()+" / "+u.getMaxHealth()));
			stats.add(new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("/dmg.png")))));
			stats.add(new JLabel(""+u.getDamage()));
			stats.add(new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("/armor.png")))));
			stats.add(new JLabel(""+u.getDamage()));
		} catch (IOException e) {
			System.err.println("Couldn't load status icons");
		}
		stats.setVisible(true);
	}
}
	
	
	