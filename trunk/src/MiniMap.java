import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;


public class MiniMap extends JPanel implements MouseListener{
	public MiniMap(){
		this.setBackground(Color.green);
		this.addMouseListener(this);
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d =(Graphics2D)g;
		g2d.setColor(Color.RED);
		
		//BUILDINGS
		Point p1 = GameState.getHuman().getMainBuilding().getPosition();
		Point p2 = GameState.getComputer().getMainBuilding().getPosition();
		g2d.fillRect(p1.x*200/Config.getWorldHeight(), p1.y*200/Config.getWorldWidth(), 5, 5);
		g2d.fillRect(p2.x*200/Config.getWorldHeight(), p2.y*200/Config.getWorldWidth(), 5, 5);
		
		//UNITS
		for(Unit u : GameState.getUnits().getUnits()){
			int posX = u.getPosition().x*200/Config.getWorldHeight();
			int posY = u.getPosition().x*200/Config.getWorldWidth();
			g2d.draw(new Ellipse2D.Double(posX, posY, 2,2));
		}
		
		//POINT OF VIEW
		g2d.setColor(Color.YELLOW);
		int x = GameState.getMainWindow().getCanvas().getOffsetX()*200/Config.getWorldHeight();
		int y = GameState.getMainWindow().getCanvas().getOffsetY()*200/Config.getWorldWidth();
		int width = GameState.getMainWindow().getCanvas().getWidth()*200/Config.getWorldWidth();
		int height = GameState.getMainWindow().getCanvas().getHeight()*200/Config.getWorldHeight();
		g2d.drawRect(x,y , width , height);
	}
	
	@Override
	public void mousePressed(MouseEvent me) {
		if(me.getSource()==this){
			int x = Config.getWorldHeight()*(me.getX()-GameState.getMainWindow().getCanvas().getHeight()*100/Config.getWorldHeight())/200;
			if(x<0)x=0;
			else if(x+GameState.getMainWindow().getCanvas().getWidth()>Config.getWorldWidth())x=Config.getWorldWidth()-GameState.getMainWindow().getCanvas().getWidth();
			int y = Config.getWorldWidth()*(me.getY()-GameState.getMainWindow().getCanvas().getWidth()*100/Config.getWorldWidth())/200;
			if(y<0)y=0;
			else if(y+GameState.getMainWindow().getCanvas().getHeight()>Config.getWorldWidth())y=Config.getWorldWidth()-GameState.getMainWindow().getCanvas().getHeight();
			GameState.getMainWindow().getCanvas().setOffset(new Point(x, y));
			
		}
		
	}


	public void mouseClicked(MouseEvent arg0) {}


	public void mouseEntered(MouseEvent arg0) {}


	public void mouseExited(MouseEvent arg0) {}


	public void mouseReleased(MouseEvent arg0) {}
	
}
