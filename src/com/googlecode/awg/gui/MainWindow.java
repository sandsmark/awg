package com.googlecode.awg.gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.googlecode.awg.ai.AIThread;
import com.googlecode.awg.gui.Moveable.Direction;
import com.googlecode.awg.state.Config;
import com.googlecode.awg.state.GameState;
import com.googlecode.awg.state.Map;
import com.googlecode.awg.units.Building;
import com.googlecode.awg.units.Fighter;
import com.googlecode.awg.units.Healer;
import com.googlecode.awg.units.MovementThread;
import com.googlecode.awg.units.Worker;

public class MainWindow implements ActionListener, MouseMotionListener,
		MouseListener, KeyListener {
	JFrame frame;
	JPanel outer, menu, buttons;
	public Canvas canvas;
	JButton close;
	JButton config;
	
	MiniMap miniMap;
	
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
	
	String cheatCode = "";

	MainWindow() throws IOException { 
		if (!GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().isFullScreenSupported()) { 
			JOptionPane.showMessageDialog(null, "Could not acquire full screen mode.\nIf you are on linux, this is probably due to a known bug regarding Java Swing and Xinerama.\nSee bug #6532373 at http://bugs.sun.com/.");
			this.exit();
			return;
		}
		
		frame = new JFrame();
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(ImageIO.read(getClass().getResource("/icon.png")));
		frame.setTitle("Awesome WarGame is Awesome!");

		frame.setVisible(true);
		if (Config.getIntro()) {
			Intro intro = new Intro(frame);
			frame.setContentPane(intro);
			frame.pack();
			frame.setBackground(Color.BLACK);
			intro.play();
		}
		
		frame.setContentPane(new SplashScreen(frame));
		
		GameState.setMainWindow(this);
		map = GameState.getMap();
		outer = new JPanel();
		canvas = new Canvas();
		menu = new JPanel();
		menu.setBackground(Color.BLACK);
		outer.setBackground(Color.BLACK);
		
		miniMap = new MiniMap(200,200);
		miniMap.setMaximumSize(new Dimension(200, 200));
		miniMap.setMinimumSize(new Dimension(200, 200));
		miniMap.setSize(new Dimension(200, 200));
		
		resPan = new ResourcePanel();
		resPan.setBackground(Color.BLACK);
		
		uPan = new UnitPanel();
		uPan.setBackground(Color.BLACK);
		uPan.setMaximumSize(new Dimension(200,250));
		uPan.setMinimumSize(new Dimension(200,250));
	
		
		
		buttons = new JPanel();
		buttons.setMaximumSize(new Dimension(200,300));

		
		menu.add(miniMap);
		menu.add(resPan);
		menu.add(uPan);
		menu.add(buttons);
		setupButtons();
		
		menu.setPreferredSize(new Dimension(200,600));
		menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
		outer.setLayout(new BoxLayout(outer, BoxLayout.X_AXIS));

		outer.add(canvas);
		outer.add(menu);
		
		
		configDialog = new ConfigDialog(frame);
		
		// Start threads
		gThread = new GraphicsThread();
		sThread = new SelectThread();
		aiThread = new AIThread();
		wThread = new WindowThread();
		mThread = new MovementThread();
		
		canvas.addMouseMotionListener(this);
		canvas.addMouseListener(this);
		frame.addKeyListener(this);
		
		gThread.start();
		sThread.start();
		mThread.start();
		wThread.start();
		aiThread.start();		
		
		frame.setContentPane(outer);
		
		GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
		
		
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
		int resources = GameState.getHuman().getResources();
		if (e.getSource() == close) {
			exit();
			/*
			 * Upgrades units if the upgradeBuilding-button is pushed
			 * as well as the unit sprite.
			 */
		} else if(e.getSource() == upgradeBuilding){
			GameState.getHuman().getMainBuilding().upgradeBuilding();
			GameState.getUnits().upgradeUnits(GameState.getHuman());
		} else if(e.getSource() == worker && resources >= Worker.cost){
			GameState.getUnits().addUnit(new Worker(GameState.getHuman()));
		} else if(e.getSource() == fighter && resources >= Fighter.cost){
			GameState.getUnits().addUnit(new Fighter(GameState.getHuman()));
		} else if(e.getSource() == healer && resources >= Healer.cost){
			GameState.getUnits().addUnit(new Healer(GameState.getHuman()));
		} else if (e.getSource() == config) {
			configDialog.setAlwaysOnTop(true);
			configDialog.setVisible(true);
		}
	}

	public void exit() {
		frame.dispose();
		System.exit(0);
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
	
	private void setupButtons(){
		buttons.setLayout(new GridLayout(6,1));
		buttons.setBackground(Color.BLACK);
		
		upgradeBuilding = new JButton("Upgrade (" + Building.getUpgradeCost() + ")" );
		worker = new JButton("Worker (" + Worker.cost + ")");
		fighter = new JButton("Fighter (" + Fighter.cost + ")");
		healer = new JButton("Healer (" + Healer.cost + ")");
		
		buttons.add(upgradeBuilding);
		buttons.add(worker);
		buttons.add(fighter);
		buttons.add(healer);
		buttons.addKeyListener(this);
		Dimension d = new Dimension(300,50);
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
		
		config = new JButton("Configure...");
		config.addActionListener(this);
		config.setMaximumSize(new Dimension(200,50));
		
		close = new JButton("Close");
		close.addActionListener(this);
		close.setMaximumSize(new Dimension(200,50));
		
		buttons.add(close);
		buttons.add(config);
	}

	
	public void keyPressed(KeyEvent e) {	}
	public void keyReleased(KeyEvent e) {	}

	
	/*
	 * Where we type different cheatcodes.
	 */
	public void keyTyped(KeyEvent e) {
		cheatCode += e.getKeyChar();
		if(cheatCode.contains("awesome")){
			GameState.getHuman().increaseResources(10000);
			cheatCode = "";
		}else if(cheatCode.contains("lulz")){
			for (int i = 0; i < 10; i++) {
				GameState.getUnits().addUnit(new Fighter(GameState.getHuman()));
			}
			cheatCode = "";
			GameState.getHuman().increaseResources(5000);
		}
	}

	public void victory(boolean victorious) {
		frame.setVisible(false);
		if(victorious)
			JOptionPane.showMessageDialog(null, "Epic WIN!");
		else JOptionPane.showMessageDialog(null, "Epic FLAIL!");
		GameState.getMainWindow().exit();
		
	}
}