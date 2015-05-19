package capture_subsystem.gui;

import capture_subsystem.auxillary.FrameSourceManager;
import capture_subsystem.frame_sources.FrameSource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CaptureSettingsPanel extends JPanel{

	//components of sourceSelPanel
	JLabel lblSource = new JLabel("Frames source:");
	JComboBox <String> frameSourcesCB;
	JCheckBox drawGrid;

	//components of lower panel
	JSpinner spinner;
	JLabel lblFPS = new JLabel("FPS: ");


	JButton btnSettings,btnSet;
	FrameSourceManager frameSourceManager;

	public CaptureSettingsPanel(FrameSourceManager frameSourceManager) {
		this.frameSourceManager = frameSourceManager;
		initFrameSourcesBox();
		add(crtSourceSelPanel());
		add(crtLowerPanel());
		setLayout(new FlowLayout(FlowLayout.LEFT,2,5));
	}

	private Component crtSpinner() {
		spinner =
				new JSpinner(
						new SpinnerNumberModel((int)frameSourceManager.getFPS(),
								frameSourceManager.FPS_MIN,frameSourceManager.FPS_MAX,1));
		JFormattedTextField ampmspin=((JSpinner.DefaultEditor)spinner.getEditor()).getTextField(); ampmspin.setEditable(false);
		return spinner;
	}

	private JPanel crtSourceSelPanel(){
		JPanel sourceSelectionPanel = new JPanel();
		sourceSelectionPanel.add(lblSource);
		sourceSelectionPanel.add(frameSourcesCB);
		drawGrid = new JCheckBox("draw grid");
		/*drawGrid.setSelected(drawGridSelectable.isDrawGrid());
		drawGrid.addActionListener((e) ->

				drawGridSelectable.setDrawGrid(drawGrid.isSelected())

		);*/
		sourceSelectionPanel.add(drawGrid);
		return sourceSelectionPanel;
	}

	private JPanel crtLowerPanel(){
		JPanel panel = new JPanel();

		panel.add(crtFpsPanel());
		panel.add(crtBtnPanel());

		return panel;
	}

	private JPanel crtFpsPanel() {
		JPanel panel = new JPanel();
		panel.add(lblFPS);
		panel.add(crtSpinner());

		return panel;
	}

	private JPanel crtBtnPanel() {
		JPanel panel = new JPanel();
		initButtons();
		panel.add(btnSettings);
		panel.add(btnSet);
		return panel;
	}

	private void initButtons(){
		btnSet = new JButton("Set");
		btnSet.addActionListener(e -> saveSettings());
		btnSettings = new JButton("Source settings");
	}

	private void initFrameSourcesBox() {
		String[] sourceNames = new String[frameSourceManager.getSourcesAmount()];
		for (int i = 0; i < sourceNames.length; i++)
			sourceNames[i] = frameSourceManager.getFrameSources()[i].toString();
		frameSourcesCB = new JComboBox<>(sourceNames);
		frameSourcesCB.setSelectedItem(frameSourceManager.getCurrentFrameSource().toString());
	}

	private void saveSettings(){
		frameSourceManager.setCurrentFrameSource((String) frameSourcesCB.getSelectedItem());
		frameSourceManager.setFps((Integer) spinner.getValue());
	}
}
