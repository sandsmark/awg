package com.googlecode.awg.gui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.googlecode.awg.state.Config;
import com.googlecode.awg.state.GameState;
import com.googlecode.awg.units.Unit;
/**
 * This panel display a minimap containing a small version of the map, red markers representing
 * buildings and units and a yellow rectangle representing the point of view relative to the map.
 * @author Jan Berge Ommedal
 *
 */

public class MiniMap extends JPanel implements MouseListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BufferedImage cache;
	public boolean dirty = false;
	private int width, height;
	
	public MiniMap(int width, int height){
		this.width = width;
		this.height = height;
//		this.setBackground(Color.green);
		this.addMouseListener(this);
		this.updateCache();
	}
	
	/**
	 *	This method paints the cache to the component. If the cache needs update(is dirty), 
	 *	the updateCache method is executed in advance. 	 
	 */
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if (dirty) this.updateCache();
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(cache, null, 0, 0);
	}
	
	
	/**
	 * This method updates the internal cache.
	 */
	private void updateCache() {
		this.dirty=false;
		cache = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = cache.createGraphics();
		
		
		g2d.drawImage(GameState.getMap().getBaseMap(), 0, 0, width, height, 0, 0, GameState.getMap().getBaseMap().getWidth(), GameState.getMap().getBaseMap().getWidth(), null);
		
		//BUILDINGS
		Point p1 = GameState.getHuman().getMainBuilding().getPosition();
		Point p2 = GameState.getComputer().getMainBuilding().getPosition();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(p1.x*200/Config.getWorldHeight(), p1.y*200/Config.getWorldWidth(), 5, 5);
		g2d.setColor(Color.RED);
		g2d.fillRect(p2.x*200/Config.getWorldHeight(), p2.y*200/Config.getWorldWidth(), 5, 5);
		
		//UNITS
		for(Unit u : GameState.getUnits().getUnits()){
			int posY = u.getPosition().y*200/Config.getWorldHeight();
			int posX = u.getPosition().x*200/Config.getWorldWidth();
			if (u.getPlayer().isAI()) g2d.setColor(Color.RED);
			else g2d.setColor(Color.WHITE);
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
