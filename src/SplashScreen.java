import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;


public class SplashScreen extends JWindow {
	/**
	 * @author Martin T. Sandsmark
	 */
	private static final long serialVersionUID = 1L;

	public SplashScreen(Frame f) {
		super(f);
		try {
			JLabel image;
			image = new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("/loading.png"))));
			getContentPane().add(image, BorderLayout.CENTER);
			this.pack();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension imageSize = image.getPreferredSize();
			this.setLocation(screenSize.width/2 - imageSize.width/2, screenSize.height/2 - imageSize.height/2);
			this.setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void destroy() {
		this.setVisible(false);
		this.dispose();
	}
}
