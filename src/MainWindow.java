import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainWindow implements ActionListener, MouseMotionListener,
		MouseListener {
	JFrame frame;
	JPanel outer, menu;
	Canvas canvas;
	JButton close;
	JLabel curUnitText;
	JLabel curUnitIcon;

	GraphicsThread gThread;
	SelectThread sThread;
	AIThread aiThread;
	WindowThread wThread;
	MovementThread mThread;
	Music music;
	
	Map map;

	MainWindow() throws IOException {
		frame = new JFrame();
		SplashScreen splash = new SplashScreen(frame);
		
		GameState.setMainWindow(this);
		map = GameState.getMap();
		outer = new JPanel();
		canvas = new Canvas();
		menu = new JPanel();

		curUnitIcon = new JLabel();
		menu.add(curUnitIcon);

		curUnitText = new JLabel("[]");
		menu.add(curUnitText);

		close = new JButton("Close");
		close.addActionListener(this);
		menu.add(close);

		menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
		outer.setLayout(new BoxLayout(outer, BoxLayout.X_AXIS));

		canvas.addMouseMotionListener(this);
		canvas.addMouseListener(this);

		outer.add(canvas);
		outer.add(menu);


		frame.setContentPane(outer);

		frame.pack();
		frame.setAlwaysOnTop(true);
//		frame.setUndecorated(false);
		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());

		canvas.repaint();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Start threads
		gThread = new GraphicsThread();
		gThread.start();

		sThread = new SelectThread();
		sThread.start();

		aiThread = new AIThread();
		aiThread.start();

		wThread = new WindowThread(this);
		wThread.start();

		mThread = new MovementThread();
		mThread.start();
		
//		music = new Music("resources/music.ogg");
//		music.start();
		

		// Add testing unit
		GameState.getUnits().addUnit(new Worker(GameState.getComputer()));
		GameState.getUnits().addUnit(new Worker(GameState.getHuman()));
		canvas.updateInternal(); // Should be called whenever the map updates
		splash.destroy();
		frame.setVisible(true);
	}

	public static void main(String args[]) {
		try {
			new MainWindow();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {
		canvas.repaint();
		if (e.getSource() == close) {
			exit();
		}
	}

	public void exit() {
		frame.dispose();
		System.exit(0);
	}

	public void delSeletectedUnit() {
		curUnitText.setText(".");
		curUnitIcon.setIcon(new ImageIcon());
	}

	public void setSeletectedUnit(Unit u) {
		curUnitText.setText(u.toString());
		curUnitIcon.setIcon(new ImageIcon(u.getSprite()));
	}

	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (x > canvas.getWidth() - 50) {
			gThread.setDirection(Moveable.Direction.RIGHT);
		} else if (x < 50) {
			gThread.setDirection(Moveable.Direction.LEFT);
		} else if (y > canvas.getHeight() - 50) {
			gThread.setDirection(Moveable.Direction.DOWN);
		} else if (y < 50) {
			gThread.setDirection(Moveable.Direction.UP);
		} else {
			if (gThread.getDirection() != Moveable.Direction.NONE)
				gThread.setDirection(Moveable.Direction.NONE);
		}
	}

	public void mouseDragged(MouseEvent e) {
		sThread.moved(e);
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent m) {
	}

	public void mousePressed(MouseEvent m) {
		if (m.getButton() == MouseEvent.BUTTON3) {
			GameState.getSelectedUnits().moveSelectedTo(m.getX() + canvas.getOffsetX(), m.getY() + canvas.getOffsetY());
			canvas.showTarget(m.getX(), m.getY());
			return;
		}
		sThread.start(m);

	}

	public void mouseReleased(MouseEvent m) {
		sThread.stop(m);
	}

	public Canvas getCanvas() {
		return canvas;
	}
}