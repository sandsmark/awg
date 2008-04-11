import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle;
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
public class ConfigDialog extends javax.swing.JDialog implements ActionListener {

	private JSlider ai_aggressiveness;
	private JButton close;
	private JSpinner map_height;
	private JSpinner map_width;
	private JSlider path_masksize;
	private JButton reset;
	private JButton save;
	private JSeparator separator;
	private JLabel t_ai_aggressive;
	private JLabel t_configuration;
	private JLabel t_height;
	private JLabel t_high;
	private JLabel t_large;
	private JLabel t_long;
	private JLabel t_low;
	private JLabel t_mapsize;
	private JLabel t_masksize;
	private JLabel t_masksize_info;
	private JLabel t_short;
	private JLabel t_small;
	private JLabel t_thread_sleeptime;
	private JLabel t_width;
	private JSlider thread_sleeptime;
	private static final long serialVersionUID = 2369799712390186767L;

	/** Creates new form ConfigDialog */
	public ConfigDialog(Frame parent) {
		super(parent);
		initComponents();
	}

	private void initComponents() {
		t_low = new JLabel();
		t_high = new JLabel();
		t_configuration = new JLabel();
		t_width = new JLabel();
		t_height = new JLabel();
		t_mapsize = new JLabel();
		t_masksize = new JLabel();
		t_small = new JLabel();
		t_large = new JLabel();
		t_masksize_info = new JLabel();
		t_thread_sleeptime = new JLabel();
		t_short = new JLabel();
		t_long = new JLabel();
		t_ai_aggressive = new JLabel();
		separator = new JSeparator();

		map_width = new JSpinner(new SpinnerNumberModel(1500, 500, 5000, 500));
		map_height = new JSpinner(new SpinnerNumberModel(1500, 500, 5000, 500));
		
		path_masksize = new JSlider();
		path_masksize.setMinimum(5);
		path_masksize.setMaximum(35);
		
		thread_sleeptime = new JSlider();
		thread_sleeptime.setMinimum(0);
		thread_sleeptime.setMaximum(5000);

		ai_aggressiveness = new JSlider();
		ai_aggressiveness.setMinimum(1);
		ai_aggressiveness.setMaximum(10);

		save = new JButton();
		save.setText("Save");
		save.addActionListener(this);
		reset = new JButton();
		reset.setText("Reset");
		reset.addActionListener(this);
		close = new JButton();
		close.setText("Close");
		close.addActionListener(this);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);


		
		
		
		t_configuration.setText("Configuration");
		t_width.setText("Width:");
		t_height.setText("Height:");
		t_mapsize.setFont(new java.awt.Font("Dialog", 1, 11));
		t_mapsize.setText("Map size:");
		t_masksize.setFont(new java.awt.Font("Dialog", 1, 11));
		t_masksize.setText("Path-finding mask size:");
		t_small.setText("Small");
		t_large.setText("Large");
		t_masksize_info.setText("Smaller mask size equals more CPU usage.");
		t_thread_sleeptime.setFont(new java.awt.Font("Dialog", 1, 11));
		t_thread_sleeptime.setText("Thread sleep time:");
		t_short.setText("Short");
		t_long.setText("Long");
		t_ai_aggressive.setFont(new java.awt.Font("Dialog", 1, 11));
		t_ai_aggressive.setText("AI aggressiveness:");
		t_low.setText("Low");
		t_high.setText("High");

		/**
		 * Here there be layout code, do not touch, under any circumstance.
		 * It works.
		 */
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout
				.setHorizontalGroup(layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												layout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addComponent(
																				separator,
																				GroupLayout.DEFAULT_SIZE,
																				434,
																				Short.MAX_VALUE)
																		.addContainerGap())
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addGroup(
																				layout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addGroup(
																								GroupLayout.Alignment.TRAILING,
																								layout
																										.createSequentialGroup()
																										.addComponent(
																												t_configuration)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED,
																												341,
																												Short.MAX_VALUE))
																						.addGroup(
																								layout
																										.createSequentialGroup()
																										.addGroup(
																												layout
																														.createParallelGroup(
																																GroupLayout.Alignment.LEADING)
																														.addComponent(
																																t_mapsize)
																														.addGroup(
																																layout
																																		.createSequentialGroup()
																																		.addComponent(
																																				t_width)
																																		.addPreferredGap(
																																				LayoutStyle.ComponentPlacement.RELATED)
																																		.addComponent(
																																				map_width,
																																				GroupLayout.PREFERRED_SIZE,
																																				GroupLayout.DEFAULT_SIZE,
																																				GroupLayout.PREFERRED_SIZE)))
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												t_height)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												map_height,
																												GroupLayout.PREFERRED_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								layout
																										.createSequentialGroup()
																										.addComponent(
																												t_masksize)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED,
																												280,
																												Short.MAX_VALUE))
																						.addGroup(
																								layout
																										.createSequentialGroup()
																										.addComponent(
																												t_masksize_info)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED,
																												190,
																												Short.MAX_VALUE))
																						.addGroup(
																								layout
																										.createSequentialGroup()
																										.addComponent(
																												t_thread_sleeptime)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED,
																												312,
																												Short.MAX_VALUE))
																						.addGroup(
																								layout
																										.createSequentialGroup()
																										.addComponent(
																												t_ai_aggressive)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED,
																												312,
																												Short.MAX_VALUE))
																						.addGroup(
																								GroupLayout.Alignment.TRAILING,
																								layout
																										.createSequentialGroup()
																										.addGroup(
																												layout
																														.createParallelGroup(
																																GroupLayout.Alignment.TRAILING)
																														.addGroup(
																																layout
																																		.createSequentialGroup()
																																		.addComponent(
																																				reset)
																																		.addPreferredGap(
																																				LayoutStyle.ComponentPlacement.RELATED,
																																				225,
																																				Short.MAX_VALUE)
																																		.addComponent(
																																				close)
																																		.addPreferredGap(
																																				LayoutStyle.ComponentPlacement.RELATED)
																																		.addComponent(
																																				save))
																														.addGroup(
																																GroupLayout.Alignment.LEADING,
																																layout
																																		.createSequentialGroup()
																																		.addComponent(
																																				t_small)
																																		.addPreferredGap(
																																				LayoutStyle.ComponentPlacement.RELATED)
																																		.addComponent(
																																				path_masksize,
																																				GroupLayout.DEFAULT_SIZE,
																																				376,
																																				Short.MAX_VALUE)
																																		.addPreferredGap(
																																				LayoutStyle.ComponentPlacement.RELATED))
																														.addGroup(
																																GroupLayout.Alignment.LEADING,
																																layout
																																		.createSequentialGroup()
																																		.addComponent(
																																				t_low)
																																		.addPreferredGap(
																																				LayoutStyle.ComponentPlacement.RELATED)
																																		.addComponent(
																																				ai_aggressiveness,
																																				GroupLayout.DEFAULT_SIZE,
																																				383,
																																				Short.MAX_VALUE))
																														.addGroup(
																																GroupLayout.Alignment.LEADING,
																																layout
																																		.createSequentialGroup()
																																		.addComponent(
																																				t_short)
																																		.addPreferredGap(
																																				LayoutStyle.ComponentPlacement.RELATED)
																																		.addComponent(
																																				thread_sleeptime,
																																				GroupLayout.DEFAULT_SIZE,
																																				375,
																																				Short.MAX_VALUE)
																																		.addPreferredGap(
																																				LayoutStyle.ComponentPlacement.RELATED)))
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)))
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				layout
																						.createParallelGroup(
																								GroupLayout.Alignment.TRAILING)
																						.addComponent(
																								t_long,
																								GroupLayout.PREFERRED_SIZE,
																								27,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								t_large)
																						.addComponent(
																								t_high))))));
		layout
				.setVerticalGroup(layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(t_configuration)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(separator,
												GroupLayout.PREFERRED_SIZE, 10,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(t_mapsize)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												layout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(t_width)
														.addComponent(
																map_width,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(t_height)
														.addComponent(
																map_height,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(t_masksize)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												layout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addGroup(
																				layout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addComponent(
																								t_small)
																						.addComponent(
																								path_masksize,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				t_masksize_info)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				t_thread_sleeptime))
														.addComponent(t_large))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												layout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addComponent(t_short)
														.addComponent(t_long)
														.addComponent(
																thread_sleeptime,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(t_ai_aggressive)
										.addGap(8, 8, 8)
										.addGroup(
												layout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addGroup(
																				layout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addComponent(
																								t_low)
																						.addComponent(
																								ai_aggressiveness,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED,
																				173,
																				Short.MAX_VALUE)
																		.addGroup(
																				layout
																						.createParallelGroup(
																								GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								reset)
																						.addComponent(
																								save)
																						.addComponent(
																								close)))
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addComponent(
																				t_high)
																		.addContainerGap()))));

		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == save) {
			this.saveConfig();
		} else if (e.getSource() == close){
			this.setVisible(false);
		} else if (e.getSource() == reset) {
			Config.resetConfig();
			this.loadConfig();
		}
		

	}

	private void saveConfig() {
		Config.saveConfig(map_width.getValue().toString(), map_height.getValue().toString(), path_masksize.getValue(), thread_sleeptime.getValue(), ai_aggressiveness.getValue());
	}
	
	public void setVisible(boolean visible){
		if (visible) loadConfig();
		super.setVisible(visible);
	}
	
	private void loadConfig() {
		map_width.setValue(Config.getWorldWidth());
		map_height.setValue(Config.getWorldHeight());
		path_masksize.setValue(Config.getMaskSize());
		thread_sleeptime.setValue(Config.getSleeptime());
		ai_aggressiveness.setValue(Config.getAggressiveness());
	}
}
