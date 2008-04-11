import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
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
	JLabel curUnitText; // Obsolete
	JLabel curUnitIcon; // Obsolete
	
	ResourcePanel resPan;
	UnitPanel uPan;

	JButton upgradeBuilding, worker, fighter, healer;
	
	
	
	GraphicsThread gThread;
	SelectThread sThread;
	AIThread aiThread;
	WindowThread wThread;
	MovementThread mThread;
	
	Map map;

	MainWindow() throws IOException {
		frame = new JFrame();
//		new Intro(frame);
		SplashScreen splash = new SplashScreen(frame);
		
		GameState.setMainWindow(this);
		GameState.setMap(new Map(GameState.getConfig().getWorldWidth(), GameState.getConfig().getWorldHeight()));
		map = GameState.getMap();
		outer = new JPanel();
		canvas = new Canvas();
		menu = new JPanel();
		
		resPan = new ResourcePanel();
		menu.add(resPan);
		
		uPan = new UnitPanel();
		uPan.setMaximumSize(new Dimension(300,200));
		menu.add(uPan);
		
		setupBuildingGUI();
		
		
		
		curUnitIcon = new JLabel();
		//menu.add(curUnitIcon);

		curUnitText = new JLabel("[]");
		//menu.add(curUnitText);

		close = new JButton("Close");
		close.addActionListener(this);
		menu.add(close);
		

		menu.setPreferredSize(new Dimension(150,600));
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
		frame.setIconImage(ImageIO.read(getClass().getResource("/icon.png")));
		
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

		wThread = new WindowThread();
		wThread.start();

		mThread = new MovementThread();
		mThread.start();
		
//		new Music("/music.ogg");
		

		// Add testing unit
//		GameState.getUnits().addUnit(new Worker(GameState.getComputer())); AI gj�r dette selv
		GameState.getUnits().addUnit(new Worker(GameState.getHuman()));
//		GameState.getUnits().addUnit(new Fighter(GameState.getComputer())); AI gj�r dette selv
		
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
			/*
			 * Upgrades units if the upgradeBuilding-button is pushed
			 * as well as the unit sprite. Need to add costs for upgrades, but at a later point.
			 */
		}else if(e.getSource() == upgradeBuilding){
			GameState.getHuman().getMainBuilding().upgradeBuilding();
			GameState.getUnits().upgradeUnits(GameState.getHuman());
		}else if(e.getSource() == worker){
			GameState.getUnits().addUnit(new Worker(GameState.getHuman()));
		}else if(e.getSource() == fighter){
			GameState.getUnits().addUnit(new Fighter(GameState.getHuman()));
		}else if(e.getSource() == healer){
			GameState.getUnits().addUnit(new Healer(GameState.getHuman()));
		}
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
	
	public void setMenu(JPanel menu){
		this.menu = menu;
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