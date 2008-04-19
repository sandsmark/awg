import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;


public class MiniMap extends JPanel{

	public MiniMap(){
		this.setBackground(Color.BLACK);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d =(Graphics2D)g;
		g2d.setColor(Color.RED);
		for(Unit u : GameState.getUnits().getUnits()){
			g2d.draw(new Ellipse2D.Double(u.getPosition().x*this.getHeight()/Config.getWorldHeight(), u.getPosition().y*this.getWidth()/Config.getWorldWidth(), 2,2));
		}
	}
}
