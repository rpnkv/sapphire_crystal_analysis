package capture_subsystem.gui;

import capture_subsystem.auxillary.FrameSourceManager;

import javax.swing.*;
import java.awt.*;

public class CaptureSettingsFrame extends JFrame{

	//components of sourceSelPanel
	JLabel lblSource = new JLabel("Frames source:");
	JComboBox <String> frameSourcesCB;
	JCheckBox drawGrid;

	private int defaultWidth = 430, defaultHeight = 135;

	//components of lower panel
	JSpinner spinner;
	JLabel lblFPS = new JLabel("FPS: ");

	JButton btnApply, btnOK, btnExit;
	FrameSourceManager frameSourceManager;

	public CaptureSettingsFrame(FrameSourceManager frameSourceManager) {
		super("Source settings");
		this.frameSourceManager = frameSourceManager;
		initFrameSourcesBox();
		defineContentPane(frameSourceManager.getCurrentFrameSource().toString());
		setSize(defaultWidth,defaultHeight);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	private Component crtSpinner() {
		spinner =
				new JSpinner(
						new SpinnerNumberModel((int)frameSourceManager.getFPS(),
								frameSourceManager.FPS_MIN,frameSourceManager.FPS_MAX,1));
		JFormattedTextField ampmspin=
				((JSpinner.DefaultEditor)spinner.getEditor()).getTextField(); ampmspin.setEditable(false);
		return spinner;
	}

	private JPanel crtUpperPanel(){
		JPanel panel = new JPanel();
		panel.add(lblSource);
		panel.add(frameSourcesCB);
		drawGrid = new JCheckBox("grid");
		/*drawGrid.setSelected(drawGridSelectable.isDrawGrid());
		drawGrid.addActionListener((e) ->
				drawGridSelectable.setDrawGrid(drawGrid.isSelected())
		);*/
		panel.add(crtFpsPanel());
		panel.add(drawGrid);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		return panel;
	}

	private JPanel crtLowerPanel(){
		JPanel panel = new JPanel();
		initButtons();
		panel.add(btnApply);
		panel.add(btnOK);
		panel.add(btnExit);
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		return panel;
	}

	private JPanel crtFpsPanel() {
		JPanel panel = new JPanel();
		panel.add(lblFPS);
		panel.add(crtSpinner());
		return panel;
	}


	private void initButtons(){
		btnApply = new JButton("Apply");
		btnApply .addActionListener(e -> saveSettings());
		btnOK = new JButton("OK");
		btnOK.addActionListener(e -> {
			saveSettings();
			dispose();
		});
		btnExit = new JButton("Exit");
		btnExit.addActionListener(e -> dispose());
	}

	private void initFrameSourcesBox() {
		frameSourcesCB = new JComboBox<>(frameSourceManager.getFrameSourcesNames());
		frameSourcesCB.setSelectedItem(frameSourceManager.getCurrentFrameSource().toString());
		frameSourcesCB.addActionListener(e -> initSettingsPanel((String) frameSourcesCB.getSelectedItem()));
	}

	private JPanel initSettingsPanel(String name){
		JPanel settingsPanel = frameSourceManager.getSettingsPanelByName(name);
		if (settingsPanel == null)
		{
			settingsPanel = new JPanel();
			settingsPanel.setSize(defaultWidth,20);
			settingsPanel.add(new JLabel("Selected frame source has no settings available."));
			settingsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		}
		return settingsPanel;
	}

	private void defineContentPane(String sourceName){
		getContentPane().removeAll();
		getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
		getContentPane().add(crtUpperPanel());
		JPanel settingsPanel = initSettingsPanel(sourceName);
		getContentPane().add(initSettingsPanel(sourceName));
		getContentPane().add(crtLowerPanel());
		update(getGraphics());
	}

	private void saveSettings(){
		frameSourceManager.setCurrentFrameSource((String) frameSourcesCB.getSelectedItem());
		frameSourceManager.setFps((Integer) spinner.getValue());
	}
}
