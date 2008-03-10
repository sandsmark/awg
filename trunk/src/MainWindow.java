


import javax.swing.*;

import java.awt.MouseInfo;
import java.awt.event.*;
import java.io.IOException;

public class MainWindow implements ActionListener, MouseMotionListener {
	JFrame frame;
	JPanel outer, menu;
	Canvas canvas;
	JButton close, draw, left, right;
	boolean isMoving = false;
	
	MainWindow() throws IOException{
		outer = new JPanel();
		canvas = new Canvas(new Map(100,100));
		menu = new JPanel();
		 
		close = new JButton("Close");
		close.addActionListener(this);
		menu.add(close);

		draw = new JButton("Draw");
		draw.addActionListener(this);
		menu.add(draw);
		
		left = new JButton("<");
		left.addActionListener(this);
		menu.add(left);
		
		right = new JButton(">");
		right.addActionListener(this);
		menu.add(right);
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
		} else if (e.getSource() == draw){
			canvas.repaint();
		} else if (e.getSource() == left){
			canvas.screenLeft();
		} else if (e.getSource() == right){
			canvas.screenRight();
		}
	}

	public void exit() {
		System.out.println("EPIC WIN!");
		frame.dispose();
		System.exit(0); 
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (isMoving) return; 
		isMoving = true;
		try {
			System.out.println("epic");
			while (MouseInfo.getPointerInfo().getLocation().x>450){
				canvas.screenRight();
				canvas.repaint();
//				Thread.sleep(1);
			}
			
			while (MouseInfo.getPointerInfo().getLocation().x<50){
				canvas.screenLeft();
				canvas.repaint();
//				Thread.sleep(1);
			}

		} finally {
			isMoving = false;
		}
	}
	
	public void moveLeft(MouseInfo m) {
		if (isMoving) return; 
		isMoving = true;
		
		isMoving = false;
	}
}