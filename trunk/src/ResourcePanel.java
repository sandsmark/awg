import java.awt.Color;
import java.awt.Dimension;
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
		setLayout(new GridLayout(1,2));
		gold = new JLabel(""+GameState.getHuman().getResources());
		add(new JLabel(new ImageIcon("resources/gull.png")));
		add(gold);
		//this.setBackground(Color.RED);
		this.setMaximumSize(new Dimension(250,30));
	}
	
	
	public void update(){
		gold.setText(""+GameState.getHuman().getResources());
		repaint();
		
	}
	
	
	

}
