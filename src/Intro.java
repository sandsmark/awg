import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;



public class Intro extends JWindow implements MouseListener {
	/**
	 * @author Martin T. Sandsmark
	 */
	private static final long serialVersionUID = 1L;
	private JLabel image;
	ReentrantLock lock = new ReentrantLock();
	private Condition clicked = lock.newCondition();

	
	
	public Intro(Frame f) {
		super(f);
		try {
			image = new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("/intro/soiheard.png"))));
			image.addMouseListener(this);
			getContentPane().add(image, BorderLayout.CENTER);
			this.pack();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension imageSize = image.getPreferredSize();
			this.setLocation(screenSize.width/2 - imageSize.width/2, screenSize.height/2 - imageSize.height/2);
			this.setVisible(true);
			lock.lock();
			clicked.await(2, TimeUnit.SECONDS);
			lock.unlock();

			lock.lock();
			image.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/intro/iliketurtles.png"))));
			clicked.await(2, TimeUnit.SECONDS);
			lock.unlock();

			lock.lock();
			image.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/intro/diefoolishmortal.png"))));
			clicked.await(2, TimeUnit.SECONDS);
			lock.unlock();
			
			this.setVisible(false);
			this.dispose();
		} catch (Exception e) { e.printStackTrace(); }
	}

	public void mouseClicked(MouseEvent e) {
		lock.lock();
		clicked.signal();
		lock.unlock();
	}

	public void mouseEntered(MouseEvent e) { }

	public void mouseExited(MouseEvent e) { 	}

	public void mousePressed(MouseEvent e) {	}

	public void mouseReleased(MouseEvent e) {	}
}
