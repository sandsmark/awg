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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;

/**
 * This implements the configuration dialog. It works closely with the Config
 * singleton for storage and retrieval of current configuration. 
 * @author Martin T. Sandsmark
 */
public class ConfigDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = -2348679567504348977L;
	
	JButton close, reset, save, resetAI;
	JSpinner height, width;
	
	JSlider masksize, fps;
	
	JPanel bottom;
	JPanel options;
	JPanel content;
	
	JFrame frame;
	JCheckBox music;
	JCheckBox intro;

	/**
	 * This constructor sets up the window, but doesn't show it.
	 * @param frame this is the parent JFrame.
	 */
	public ConfigDialog (JFrame frame) {
		super(frame);
		bottom = new JPanel();
		options = new JPanel();
		content = new JPanel();
		
		/*
		 * Buttons at the bottom
		 */
		close = new JButton("Close");
		close.addActionListener(this);
		bottom.add(close);
		
		reset = new JButton("Reset");
		reset.addActionListener(this);
		bottom.add(reset);
		
		resetAI = new JButton("Reset AI settings");
		resetAI.addActionListener(this);
		bottom.add(resetAI);
		
		save = new JButton("Save");
		save.addActionListener(this);
		bottom.add(save);
	
		/*
		 * Mask size for pathfinding
		 */
		JPanel masksize_panel = new JPanel();
		JLabel masksize_text = new JLabel("Mask-size for pathfinding (smaller=slower):");
		masksize = new JSlider(1, 50, Config.getMaskSize());
		masksize_panel.add(masksize_text);
		masksize_panel.add(masksize);
		options.add(masksize_panel);
		
		/*
		 * Map size.
		 */
		JPanel size = new JPanel();
		JLabel size_textH = new JLabel("Height:");
		height = new JSpinner(new SpinnerNumberModel(Config.getWorldHeight(), 500, 5000, 500));
		JLabel size_textW = new JLabel("Width:");
		width = new JSpinner(new SpinnerNumberModel(Config.getWorldWidth(), 500, 5000, 500));
		size.add(size_textH);
		size.add(height);
		size.add(size_textW);
		size.add(width);
		options.add(size);
		
		/*
		 * Enable/disable music checkbox
		 */
		JPanel music_panel = new JPanel();
		JLabel music_text = new JLabel("Enable music:");
		music = new JCheckBox();
		music.addActionListener(this);
		music_panel.add(music_text);
		music_panel.add(music);
		options.add(music_panel);
		
		
		/*
		 * Enable/disable Intro checkbox 
		 */
		JPanel intro_panel = new JPanel();
		JLabel intro_text = new JLabel("Enable intro:");
		intro = new JCheckBox();
		intro_panel.add(intro_text);
		intro_panel.add(intro);
		options.add(intro_panel);
		
		
		/*
		 * FPS slider 
		 */
		JPanel fps_panel = new JPanel();
		JLabel fps_text = new JLabel("Try FPS:");
		fps = new JSlider(1,100, Config.getFPS());
		fps_panel.add(fps_text);
		fps_panel.add(fps);
		options.add(fps_panel);
		
		options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
		
		content.add(options);
		content.add(bottom);
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		
		this.setContentPane(content);
		this.pack();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	
	/**
	 * This gets called when buttons are pushed, and handles them appropriately. 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == save) {
			this.saveConfig();
		} else if (e.getSource() == close){
			this.setVisible(false);
		} else if (e.getSource() == reset) {
			Config.resetConfig();
			this.loadConfig();
		} else if (e.getSource() == music) {
			if (GameState.getMainWindow().music != null && !music.isSelected()) 
				GameState.getMainWindow().music.stop_sound();
			if (music.isSelected()) GameState.getMainWindow().music = new Music("/music.ogg");
		} else if (e.getSource() == resetAI) {
			AIConfig.reset();
		}
		

	}

	/**
	 * This uses the Config singleton to save the configuration to file.
	 */
	private void saveConfig() {
		Config.saveConfig(width.getValue().toString(), height.getValue().toString(), masksize.getValue(), music.isSelected(), intro.isSelected(), fps.getValue());
	}
	
	/**
	 * This sets the visibility of this window.
	 */
	public void setVisible(boolean visible){
		if (visible) loadConfig();
		super.setVisible(visible);
	}
	
	/**
	 * This loads the configuration from the Config singleton into this GUI.
	 */
	private void loadConfig() {
		this.width.setValue(Config.getWorldWidth());
		this.height.setValue(Config.getWorldHeight());
		this.masksize.setValue(Config.getMaskSize());
		this.music.setSelected(Config.getMusic());
		this.intro.setSelected(Config.getIntro());
	}
}
