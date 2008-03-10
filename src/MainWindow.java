


import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow implements ActionListener, MouseMotionListener {
	JFrame frame;
	JPanel outer, menu;
	Canvas canvas;
	JButton close;
	boolean isMoving = false;
	GraphicsThread gThread;
	
	MainWindow() throws IOException{
		outer = new JPanel();
		canvas = new Canvas(new Map(100,100));
		menu = new JPanel();
		 
		close = new JButton("Close");
		close.addActionListener(this);
		menu.add(close);

		canvas.addMouseMotionListener(this);

		outer.add(canvas);
		outer.add(menu);

		frame = new JFrame();
		frame.setContentPane(outer);
		
		frame.pack();
		frame.setVisible(true);
		
		canvas.repaint();
		canvas.repaint();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gThread = new GraphicsThread(canvas, 50);
		gThread.start();
	}

	public static void main (String args[]) {
		try {
			new MainWindow();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {
		canvas.repaint();
		if (e.getSource() == close){
			exit();
		}
	}

	public void exit() {
		System.out.println("EPIC WIN!");
		frame.dispose();
		System.exit(0); 
	}

	public void mouseMoved(MouseEvent e) {
		int x = MouseInfo.getPointerInfo().getLocation().x;
		int y = MouseInfo.getPointerInfo().getLocation().y;
		if (x>canvas.getWidth() - 50) {
			gThread.setDirection(Moveable.Direction.RIGHT);
		} else if (x<50) {
			gThread.setDirection(Moveable.Direction.LEFT);
		} else if (y>canvas.getHeight() - 50) {
			gThread.setDirection(Moveable.Direction.DOWN);
		} else if (y<50) {
			gThread.setDirection(Moveable.Direction.UP);
		} else{
			if(gThread.getDirection()!=Moveable.Direction.NONE)
				gThread.setDirection(Moveable.Direction.NONE);
		}
	}

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}