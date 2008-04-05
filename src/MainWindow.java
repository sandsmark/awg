import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
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
	ResourcePanel resPan;

	JButton upgradeBuilding, worker, fighter, healer;
	
	
	
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
		
		resPan = new ResourcePanel();
		menu.add(resPan);
		
		setupBuildingGUI();

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
//		frame.setAlwaysOnTop(true);
//		frame.setUndecorated(false);
//		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		frame.setIconImage(ImageIO.read(new File("resources/icon.png")));
		frame.setTitle("Awesome WarGame is Awesome!");

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
		
		music = new Music("resources/music.ogg");
		music.start();
		

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
		}else if(e.getSource() == upgradeBuilding){
			//UPgradeBUILDING!!!!!!
		}else if(e.getSource() == worker){
			GameState.getUnits().addUnit(new Worker(GameState.getHuman()));
		}else if(e.getSource() == fighter){
			//CONSTRUCT FIGHETER
		}else if(e.getSource() == healer){
			//CONSTRUCT HEALER
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
		curUnitIcon.setIcon(new ImageIcon(u.getSprite().get()));
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

	public void mouseDragged(MouseEvent m) {
		if (m.getButton() == 0) sThread.moved(m);
	}

	public void mouseClicked(MouseEvent m) {	}

	public void mouseEntered(MouseEvent m) {	}

	public void mouseExited(MouseEvent m) {
		/**
		 * When mouse exits canvas, stop scrolling.
		 */
		if (m.getSource() == canvas) 
			gThread.setDirection(Moveable.Direction.NONE);
	}

	public void mousePressed(MouseEvent m) {
		
		if (m.getSource() == canvas){
			int x = m.getX() + GameState.getMainWindow().canvas.getOffsetX();
			int y = m.getY() + GameState.getMainWindow().canvas.getOffsetY();
			if (m.getButton() == MouseEvent.BUTTON3) {
				if (GameState.getUnits().selectedOnlyContains("worker"))
					for (Resource resource : GameState.getMap().getResources()){
						if (resource.position.distance(new Point(x - 10, y - 10)) < 10) {
							GameState.getUnits().setTargetResource(resource);
							System.out.println("ohaio");
						}
					}
				GameState.getUnits().moveSelectedTo(m.getX() + canvas.getOffsetX(), m.getY() + canvas.getOffsetY());
				canvas.showTarget(m.getX(), m.getY());
			} else if (m.getButton() == MouseEvent.BUTTON1) sThread.start(m);
		}
	}

	public void mouseReleased(MouseEvent m) {
		if (m.getButton() == 1) sThread.stop(m);
	}

	public Canvas getCanvas() {
		return canvas;
	}
	
	public void setMenu(JPanel menu){
		this.menu = menu;
	}
	
	private void setupBuildingGUI(){
		upgradeBuilding = new JButton("Upgrade");
		worker = new JButton("Worker");
		fighter = new JButton("Fighter");
		healer = new JButton("Healer");
		
		Dimension d = new Dimension(400,100);
		upgradeBuilding.setIcon(new ImageIcon("resources/buildings/end1.png"));
		fighter.setIcon(new ImageIcon("resources/fighter/0_forward0.png"));
		worker.setIcon(new ImageIcon("resources/worker/0_forward0.png"));
		healer.setIcon(new ImageIcon("resources/healer/0_forward0.png"));
		
		upgradeBuilding.addActionListener(GameState.getMainWindow());
		worker.addActionListener(GameState.getMainWindow());
		fighter.addActionListener(GameState.getMainWindow());
		healer.addActionListener(GameState.getMainWindow());
		
		menu.add(upgradeBuilding);
		menu.add(worker);
		menu.add(fighter);
		menu.add(healer);
	}
}