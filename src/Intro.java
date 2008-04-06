import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;



public class Intro extends JWindow{
	/**
	 * @author Martin T. Sandsmark
	 */
	private static final long serialVersionUID = 1L;

	public Intro(Frame f) {
		super(f);
		try {
			JLabel image = new JLabel(new ImageIcon("resources/intro/soiheard.png"));
			getContentPane().add(image, BorderLayout.CENTER);
			this.pack();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension imageSize = image.getPreferredSize();
			this.setLocation(screenSize.width/2 - imageSize.width/2, screenSize.height/2 - imageSize.height/2);
			this.setVisible(true);
			Thread.sleep(2000); 
			

			image.setIcon(new ImageIcon("resources/intro/iliketurtles.png"));
			Thread.sleep(2000);
			

			image.setIcon(new ImageIcon("resources/intro/diefoolishmortal.png"));
			Thread.sleep(2000);
			
			this.setVisible(false);
			this.dispose();
		} catch (InterruptedException e) { e.printStackTrace(); }
	}
}
