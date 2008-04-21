import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;



public class Intro extends JPanel implements MouseListener{
	/**
	 * @author Martin T. Sandsmark
	 */
	private static final long serialVersionUID = 1L;
	private float alpha = 1f; 
	private int x, y;
	private BufferedImage img1, img2;
	private boolean running = true;
	
	public Intro(Frame f) {
		this.addMouseListener(this);
		this.setBackground(Color.BLACK);
	}

	public void mouseClicked(MouseEvent e) {
		this.running = false;
	}

	@Override
	public synchronized void paintComponent(Graphics g) {
		if (img1 == null || img2 == null) return;
		BufferedImage output = new BufferedImage(img1.getWidth(), img1.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = output.createGraphics();
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g2d.drawImage(img1, null, 0, 0);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1-alpha));
		g2d.drawImage(img2, null, 0, 0);
		g.drawImage(output, x, y, null);
	}
	
	public void mouseEntered(MouseEvent e) {}
	
	public void mouseExited(MouseEvent e) {}
	
	public void mousePressed(MouseEvent e) {}
	
	public void mouseReleased(MouseEvent e) {}
	
	public void play() {
		try {
			img2 = ImageIO.read(getClass().getResource("/intro/0.png"));
			
			x = (Toolkit.getDefaultToolkit().getScreenSize().width / 2) - (img2.getWidth() / 2);
			y = (Toolkit.getDefaultToolkit().getScreenSize().height / 2) - (img2.getHeight() / 2);
			BufferedImage blank = new BufferedImage(img2.getWidth(),img2.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D graphics = blank.createGraphics();
			graphics.setColor(Color.BLACK);
			graphics.fill(new Rectangle(0,0,img2.getWidth(),img2.getHeight()));
			
			
			img1 = blank;
			
			fade();
			if (!this.running) return;
			Thread.sleep(500);
			img1 = ImageIO.read(getClass().getResource("/intro/0.png"));
			img2 = blank;
			fade();
			
			img2 = ImageIO.read(getClass().getResource("/intro/1.png"));
			img1 = blank;
			fade();
			if (!this.running) return;
			Thread.sleep(2000);
			img1 = ImageIO.read(getClass().getResource("/intro/1.png"));
			img2 = blank;
			fade();
			
			
		} catch (Exception e) { e.printStackTrace();}
			
		
	}
	
	private void fade() {
		for (float a = 1f; a>0; a -= .05){
			if (!this.running) return;
			this.alpha = a;
			this.repaint();	
			try { Thread.sleep(50); } catch (InterruptedException e) { }
		}
	}
	
	/**
	 * This returns the minimum size for this component.
	 */
	@Override
	public Dimension getMinimumSize() {
		return Toolkit.getDefaultToolkit().getScreenSize();
	}

	/**
	 * This returns the preferred size for this component.
	 */
	@Override
	public Dimension getPreferredSize() {
		return Toolkit.getDefaultToolkit().getScreenSize();
	}
}
