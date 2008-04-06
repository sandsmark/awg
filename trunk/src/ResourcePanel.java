import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ResourcePanel extends JPanel{
	JLabel gold;
	
	public ResourcePanel(){
		setLayout(new GridLayout(2,1));
		gold = new JLabel(""+GameState.getHuman().getResources());
		add(gold);
//		add(new JLabel(new ImageIcon("resources/gull.png")));
	}
	
	
	public void update(){
		gold.setText("Gold: "+GameState.getHuman().getResources());
//		System.out.println("Gold human has " +GameState.getHuman().getResources());
		repaint();
		
	}
	
	
	

}
