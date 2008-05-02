import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This panel display the player's current amount of gold.
 * @author Jan Berge Ommedal
 *
 */

public class ResourcePanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel gold;
	
	public ResourcePanel(){
		setLayout(new GridLayout(1,2));
		gold = new JLabel(""+GameState.getHuman().getResources());
		gold.setForeground(Color.WHITE);
		try {
			add(new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("/gold.png")))));
		} catch (IOException e) {
			System.err.println("Could not load gold icon.");
		}
		add(gold);
		this.setMaximumSize(new Dimension(250,30));
	}
	
	/**
	 * This method updates the amount of gold.
	 */
	
	public void update(){
		gold.setText(""+GameState.getHuman().getResources());
		repaint();
	}
	
	
	

}
