package core.gui;

import analysis_subsystem.interfaces.AnalysisPerformable;
import capture_subsystem.interfaces.CapturePerformable;
import core.ProjectCore;

import javax.swing.*;
import java.awt.*;

public class CoreGUI extends JFrame {

	JPanel capturePanel;
	JPanel analysisPanel;


	JPanel rightPanel;

	JMenuBar menuBar;
	JMenu main,capture,analysis, monitoring,info;

	JMenuItem cStart, cStop, cSettings;
	JMenuItem aPerfInst, aPerfIter, aPerf;

	CapturePerformable capturePerformeable;
	AnalysisPerformable analysisPerformable;

	ProjectCore projectCore;

	public CoreGUI(ProjectCore projectCore, JPanel capturePanel, JPanel analysisPanel,
				   CapturePerformable capturePerformeable, AnalysisPerformable analysisPerformable) throws HeadlessException {
		super("Crystal analysis");
		this.projectCore = projectCore;
		this.setSize(1030, 650);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		this.capturePerformeable = capturePerformeable;
		this.analysisPerformable = analysisPerformable;

		initMenu();
		this.capturePanel = capturePanel;
		this.analysisPanel = analysisPanel;
		this.add(capturePanel,BorderLayout.WEST);
		processAnalysisPanelAdding(analysisPanel);

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
		aPerfInst = new JMenuItem("Run instant analysis");
		aPerfInst.addActionListener(e -> analysisPerformable.performInstantAnalysis());

		aPerfIter = new JMenuItem("Run analysis iteration");
		aPerfIter.addActionListener(e -> analysisPerformable.performAnalysisIteration());

		aPerf = new JMenuItem("Perform analysis");
		aPerf.addActionListener(e -> analysisPerformable.performAnalysis());
		analysis.add(aPerfInst);
		analysis.add(aPerfIter);
		analysis.add(aPerf);
	}


	private void monitoringMenuInit() {

	}

	private void infoMenuInit(){

	}
}
