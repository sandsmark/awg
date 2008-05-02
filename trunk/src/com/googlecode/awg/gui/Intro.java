package com.googlecode.awg.gui;
/*
Copyright (C) 2008 Martin T. Sandsmark

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


/**
 * This displays the pretty fading intro.
 * @author Martin T. Sandsmark
 */
public class Intro extends JPanel implements MouseListener{
	private static final long serialVersionUID = 1L;
	
	/*
	 * The current alpha value of the displayed slide.
	 */
	private float alpha = 1f;
	
	/*
	 * Coordinates of the displayed slide.
	 */
	private int x, y;
	
	/*
	 * The foreground and background slide.
	 */
	private BufferedImage img1, img2;
	
	/*
	 * This decides if this intro should be running.
	 */
	private boolean running = true;

	/**
	 * This sets up the intro window.
	 * @param f the parent JFrame.
	 */
	public Intro(Frame f) {
		this.addMouseListener(this);
		this.setBackground(Color.BLACK);
	}

	/**
	 * This stops the intro when a mousebutton is pressed.
	 */
	public void mouseClicked(MouseEvent e) {
		this.running = false;
	}

	/**
	 * This draws the specified images on screen with proper alpha.
	 */
	@Override
	public synchronized void paintComponent(Graphics g) {
		if (img1 == null || img2 == null) return;
		BufferedImage output = new BufferedImage(img1.getWidth(), img1.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = output.createGraphics();
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g2d.drawImage(img1, null, 0, 0);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1-alpha));
		g2d.drawImage(img2, null, 0, 0);
		g.drawImage(output, x, y, null);
	}
	
	public void mouseEntered(MouseEvent e) {}
	
	public void mouseExited(MouseEvent e) {}
	
	public void mousePressed(MouseEvent e) {}
	
	public void mouseReleased(MouseEvent e) {}
	
	/**
	 * This plays of the intro, setting the appropriate slides, and fading.
	 */
	public void play() {
		try {
			x = (Toolkit.getDefaultToolkit().getScreenSize().width / 2) - 320;
			y = (Toolkit.getDefaultToolkit().getScreenSize().height / 2) - 240;
			
			BufferedImage blank = new BufferedImage(640, 480, BufferedImage.TYPE_INT_ARGB);
			Graphics2D graphics = blank.createGraphics();
			graphics.setColor(Color.BLACK);
			graphics.fill(new Rectangle(0, 0, 640, 480));
			
			img1 = blank;
			for (int i=0; i<=3; i++) {
				img2 = ImageIO.read(getClass().getResource("/intro/" + i + ".png"));
				fade();
				img1 = img2;
				sleep(500);
				if (!this.running) return;
			}
			sleep(2000);
			img2 = blank;
			fade();
		} catch (Exception e) { e.printStackTrace();}
			
		
	}
	
	/**
	 * Sleeps for the specified number of seconds, unless interrupted.
	 * @param i
	 * @throws InterruptedException 
	 */
	private void sleep(int i) throws InterruptedException {
		double start = System.currentTimeMillis();
		while (running&&(System.currentTimeMillis() - start) < i){
			if (!running) return;
			Thread.sleep(10);
		}
	}

	private void fade() {
		for (float a = 1f; a>0; a -= .05){
			if (!this.running) return;
			this.alpha = a;
			this.repaint();	
			try { Thread.sleep(50); } catch (InterruptedException e) { }
		}
	}
	
	/**
	 * This returns the minimum size for this component.
	 */
	@Override
	public Dimension getMinimumSize() {
		return Toolkit.getDefaultToolkit().getScreenSize();
	}

	/**
	 * This returns the preferred size for this component.
	 */
	@Override
	public Dimension getPreferredSize() {
		return Toolkit.getDefaultToolkit().getScreenSize();
	}
}
