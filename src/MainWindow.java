


import javax.swing.*;

import java.awt.event.*;
import java.io.IOException;

public class MainWindow implements ActionListener {
	JFrame frame;
	JPanel outer, menu;
	Canvas canvas;
	JButton close, draw, left, right;
	
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
}