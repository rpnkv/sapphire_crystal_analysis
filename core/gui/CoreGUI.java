package core.gui;

import capture_subsystem.interfaces.CapturePerformeable;
import core.ProjectCore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by ierus on 4/1/15.
 */
public class CoreGUI extends JFrame {

	JPanel capturePanel;
	JPanel analysisPanel;
	JPanel monitoringPanel;

	JPanel rightPanel;

	JMenuBar menuBar;
	JMenu main,capture,analysis, monitoring,info;

	JMenuItem mCapture, mPreformAnalysistest ,mExit;
	JMenuItem cStart, cStop, cSettings;
	JMenuItem aSetMM, aSetCaptureRegions, confDiagramPanel;
	JMenuItem mnInitConnection, mnStartMonitoring,mnShowLog;

	CapturePerformeable capturePerformeable;

	ProjectCore projectCore;

	public CoreGUI(ProjectCore projectCore, JPanel capturePanel, JPanel analysisPanel,
				   CapturePerformeable capturePerformeable) throws HeadlessException {
		super("Crystal analysis");
		this.projectCore = projectCore;
		this.setSize(1030, 650);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		this.capturePerformeable = capturePerformeable;

		initMenu();
		this.capturePanel = capturePanel;
		//analysisPanel = analysisPanel;
		this.add(capturePanel,BorderLayout.WEST);
		//processAnalysisPanelAdding(analysisPanel);

		this.setVisible(true);
	}

	private void processAnalysisPanelAdding(JPanel analysisPanel) {
		rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.add(analysisPanel);
		this.add(rightPanel, BorderLayout.EAST);
	}


	private void initMenu(){
		menuBar = new JMenuBar();
		main = new JMenu("Main");
		capture = new JMenu("Capture");
		analysis = new JMenu("Analysis");
		monitoring = new JMenu("Logging");
		info = new JMenu("Info");
		mainMenuInit();
		captureMenuInit();
		analysisMenuInit();
		monitoringMenuInit();
		infoMenuInit();
		menuBar.add(main);
		menuBar.add(capture);
		menuBar.add(analysis);
		menuBar.add(monitoring);
		menuBar.add(info);
		this.setJMenuBar(menuBar);
	}

	private void mainMenuInit(){

	}

	private void captureMenuInit(){
		cStart = new JMenuItem("Start");
		cStart.addActionListener((e) -> capturePerformeable.startCapture());
		cStop = new JMenuItem("Stop");
		cStop.addActionListener((e) -> capturePerformeable.stopCapture());
		cSettings = new JMenuItem("Settings");
		cSettings.addActionListener((e) -> capturePerformeable.showSettings());
		capture.add(cStart);
		capture.add(cStop);
		capture.add(cSettings);
	}

	private void analysisMenuInit(){

	}


	private void monitoringMenuInit() {

	}

	private void infoMenuInit(){

	}
}
