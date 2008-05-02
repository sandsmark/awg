import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;



public class SplashScreen extends JPanel {
	/**
	 * @author Martin T. Sandsmark
	 */
	private static final long serialVersionUID = 1L; 
	private int x, y;
	private BufferedImage img;
	
	public SplashScreen(Frame f) {
		x = (Toolkit.getDefaultToolkit().getScreenSize().width / 2) - 320;
		y = (Toolkit.getDefaultToolkit().getScreenSize().height / 2) - 240;

		this.setBackground(Color.BLACK);
		try { img = ImageIO.read(getClass().getResource("/loading.png"));
		} catch (IOException e) {
			e.printStackTrace();
		} 
		this.repaint();
	}

	@Override
	public synchronized void paintComponent(Graphics g) {
		if (img == null) return;
		g.drawImage(img, x, y, null);
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
