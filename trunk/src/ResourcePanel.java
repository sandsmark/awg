import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ResourcePanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel gold;
	
	public ResourcePanel(){
		setLayout(new GridLayout(1,2));
		gold = new JLabel(""+GameState.getHuman().getResources());
		try {
			add(new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("/gold.png")))));
		} catch (IOException e) {
			System.err.println("Could not load gold icon.");
		}
		add(gold);
		//this.setBackground(Color.RED);
		this.setMaximumSize(new Dimension(250,30));
	}
	
	
	public void update(){
		gold.setText(""+GameState.getHuman().getResources());
		repaint();
		
	}
	
	
	

}
