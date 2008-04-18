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

/*
 * ConfigDialog.java
 *
 * Created on April 11, 2008, 2:38 PM
 */

/**
 * 
 * @author sandsmark
 */
public class ConfigDialog extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2348679567504348977L;
	
	JButton close, reset, save;
	JSpinner height, width;
	
	JSlider masksize;
	
	JPanel bottom;
	JPanel options;
	JPanel content;
	
	JFrame frame;
	JCheckBox music;
	JCheckBox intro;

	public ConfigDialog (JFrame frame) {
		super(frame);
		bottom = new JPanel();
		options = new JPanel();
		content = new JPanel();
		
		close = new JButton("Close");
		close.addActionListener(this);
		bottom.add(close);
		
		reset = new JButton("Reset");
		reset.addActionListener(this);
		bottom.add(reset);
		
		save = new JButton("Save");
		save.addActionListener(this);
		bottom.add(save);
	
		JPanel masksize_panel = new JPanel();
		JLabel masksize_text = new JLabel("Mask-size for pathfinding (smaller=slower):");
		masksize = new JSlider(1, 50, Config.getMaskSize());
		masksize_panel.add(masksize_text);
		masksize_panel.add(masksize);
		options.add(masksize_panel);
		
		
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
		
		JPanel music_panel = new JPanel();
		JLabel music_text = new JLabel("Enable music:");
		music = new JCheckBox();
		music.addActionListener(this);
		music_panel.add(music_text);
		music_panel.add(music);
		options.add(music_panel);
		
		
		JPanel intro_panel = new JPanel();
		JLabel intro_text = new JLabel("Enable intro:");
		intro = new JCheckBox();
		intro_panel.add(intro_text);
		intro_panel.add(intro);
		options.add(intro_panel);
		
		options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
		
		content.add(options);
		content.add(bottom);
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		
		this.setContentPane(content);
		this.pack();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	
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
		}
		

	}

	private void saveConfig() {
		Config.saveConfig(width.getValue().toString(), height.getValue().toString(), masksize.getValue(), music.isSelected(), intro.isSelected());
	}
	
	public void setVisible(boolean visible){
		if (visible) loadConfig();
		super.setVisible(visible);
	}
	
	private void loadConfig() {
		this.width.setValue(Config.getWorldWidth());
		this.height.setValue(Config.getWorldHeight());
		this.masksize.setValue(Config.getMaskSize());
		this.music.setSelected(Config.getMusic());
		this.intro.setSelected(Config.getIntro());
	}
}
