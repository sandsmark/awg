import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow implements ActionListener, MouseMotionListener,
		MouseListener {
	JFrame frame;
	JPanel outer, menu;
	Canvas canvas;
	JButton close;
	JButton config;
//	JTextArea console; 
	
	ResourcePanel resPan;
	UnitPanel uPan;

	JButton upgradeBuilding, worker, fighter, healer;
	
	ConfigDialog configDialog;
	
	GraphicsThread gThread;
	SelectThread sThread;
	AIThread aiThread;
	WindowThread wThread;
	MovementThread mThread;
	
	Map map;
	
	Music music;

	MainWindow() throws IOException {
		frame = new JFrame();
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(ImageIO.read(getClass().getResource("/icon.png")));
		frame.setTitle("Awesome WarGame is Awesome!");
		
		
		SplashScreen splash = new SplashScreen(frame);
		
		GameState.setMainWindow(this);
		map = GameState.getMap();
		outer = new JPanel();
		canvas = new Canvas();
		menu = new JPanel();
		
		resPan = new ResourcePanel();
		
		uPan = new UnitPanel();
		uPan.setMaximumSize(new Dimension(300,200));
		
		configDialog = new ConfigDialog(frame);
		
		
		
		config = new JButton("Configure...");
		config.addActionListener(this);
		config.setMaximumSize(new Dimension(500,50));
		
		close = new JButton("Close");
		close.addActionListener(this);
		close.setMaximumSize(new Dimension(500,50));
		
		menu.add(resPan);
		menu.add(uPan);
		setupBuildingGUI();
		menu.add(close);
		menu.add(config);
		
		menu.setPreferredSize(new Dimension(150,600));
		menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
		outer.setLayout(new BoxLayout(outer, BoxLayout.X_AXIS));

		canvas.addMouseMotionListener(this);
		canvas.addMouseListener(this);

		outer.add(canvas);
		outer.add(menu);

		// Start threads
		gThread = new GraphicsThread();
		sThread = new SelectThread();
		aiThread = new AIThread();
		wThread = new WindowThread();
		mThread = new MovementThread();
		
		splash.destroy();
		frame.setVisible(true);
		if (Config.getIntro()) {
			frame.setVisible(true);
			Intro intro = new Intro(frame);
			frame.setContentPane(intro);
			frame.pack();
			this.fullscreen();
			intro.play();
		}
		
		frame.setContentPane(outer);
		
		frame.pack();
		this.fullscreen();
		
		gThread.start();
		sThread.start();
		mThread.start();
		wThread.start();
		aiThread.start();		
		if (Config.getMusic()) music = new Music("/music.ogg");
	}

	public static void main(String args[]) {
		try {
			new MainWindow();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == close) {
			exit();
			/*
			 * Upgrades units if the upgradeBuilding-button is pushed
			 * as well as the unit sprite. Need to add costs for upgrades, but at a later point.
			 */
		} else if(e.getSource() == upgradeBuilding){
			GameState.getHuman().getMainBuilding().upgradeBuilding();
			GameState.getUnits().upgradeUnits(GameState.getHuman());
		} else if(e.getSource() == worker){
			GameState.getUnits().addUnit(new Worker(GameState.getHuman()));
		} else if(e.getSource() == fighter){
			GameState.getUnits().addUnit(new Fighter(GameState.getHuman()));
		} else if(e.getSource() == healer){
			GameState.getUnits().addUnit(new Healer(GameState.getHuman()));
		} else if (e.getSource() == config) {
			configDialog.setVisible(true);
		}
		canvas.repaint();
	}

	public void exit() {
		frame.dispose();
		System.exit(0);
	}

	/* Problem: 
	 * - Unit seems to be selected/deselected each "tick".
	 * - If several different units is selected, the panel switches between them.
	 * 
	 */ 
	
	//These methods is now obsolete. uPan should be called directly
	public void delSeletectedUnit() {
		uPan.deselect();
		//curUnitText.setText(".");
		//curUnitIcon.setIcon(new ImageIcon());
	}

	public void setSelectedUnit(Unit u) {
		uPan.select(u);
		//curUnitText.setText(u.toString());
		//curUnitIcon.setIcon(new ImageIcon(u.getSprite().get()));
	}

	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (x > canvas.getWidth() - 50 && y < 50) {
			gThread.setDirection(Moveable.Direction.UP_RIGHT);
		} else if (x < 50 && y < 50) {
			gThread.setDirection(Moveable.Direction.UP_LEFT);
		} else if (x < 50 && y > canvas.getHeight() - 50) {
			gThread.setDirection(Moveable.Direction.DOWN_LEFT);
		} else if (x > canvas.getWidth() - 50 && y > canvas.getHeight() - 50) {
			gThread.setDirection(Moveable.Direction.DOWN_RIGHT);	
		} else if (x > canvas.getWidth() - 50) {
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
				GameState.getUnits().target(x,y);
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
	
	private void setupBuildingGUI(){
		upgradeBuilding = new JButton("Upgrade");
		worker = new JButton("Worker");
		fighter = new JButton("Fighter");
		healer = new JButton("Healer");
		
		Dimension d = new Dimension(500,50);
		upgradeBuilding.setMaximumSize(d);
		worker.setMaximumSize(d);
		fighter.setMaximumSize(d);
		healer.setMaximumSize(d);
		
		try {
			fighter.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/fighter/0_forward0.png"))));
			worker.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/worker/0_forward0.png"))));
			healer.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/healer/0_forward0.png"))));
		} catch (IOException e) {
			System.err.println("Could not load sprites for buttons.");
		}
		
		upgradeBuilding.addActionListener(GameState.getMainWindow());
		worker.addActionListener(GameState.getMainWindow());
		fighter.addActionListener(GameState.getMainWindow());
		healer.addActionListener(GameState.getMainWindow());
		
		menu.add(upgradeBuilding);
		menu.add(worker);
		menu.add(fighter);
		menu.add(healer);
	}
	
	private void fullscreen() {
		GraphicsDevice graphics = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		if (!graphics.isFullScreenSupported()){
			System.err.println("Could not acquire fullscreen mode, falling back to maximizing.");
			frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		} else {
			graphics.setFullScreenWindow(frame);
		}
	}
}