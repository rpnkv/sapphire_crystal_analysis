package core.gui;

import analysis_subsystem.interfaces.AnalysisPerformable;
import analysis_subsystem.interfaces.CaptureRegionsViewable;
import analysis_subsystem.interfaces.ConnectionStatusEditable;
import capture_subsystem.interfaces.CapturePerformable;
import monitoring_subsystem.interfaces.ConnectionFramesProvidable;

import javax.swing.*;
import java.awt.*;

public class CoreGUI extends JFrame implements ConnectionStatusEditable, CaptureRegionsViewable{

	private final String connectionEstablished = "Connection status:  established ",
			connectionDisconnected = "Connection status: disconnected ";

	JPanel rightPanel, lowerPanel, analysisPanel, capturePanel;

	JMenuBar menuBar;
	JMenu main,capture,analysis, monitoring,info;

	JMenuItem cStart, cStop, cSettings;
	JMenuItem aPerfInst, aPerfIter, aPerf, aStop, aSetDefault;
	JMenuItem mConfConn, mShowMonForm;

	CapturePerformable capturePerformable;
	AnalysisPerformable analysisPerformable;
	ConnectionFramesProvidable monitoringPerformable;

	JLabel lblConnectionStatus, lblRegionInteractive;

	public CoreGUI(JPanel capturePanel, JPanel analysisPanel,
				   CapturePerformable capturePerformable,
				   AnalysisPerformable analysisPerformable, ConnectionFramesProvidable monitoringPerformable) throws HeadlessException {
		super("Crystal analysis");
		this.setSize(1030, 600);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		this.capturePerformable = capturePerformable;
		this.analysisPerformable = analysisPerformable;
		this.monitoringPerformable = monitoringPerformable;

		initMenu();
		this.capturePanel = capturePanel;
		this.analysisPanel = analysisPanel;
		this.add(capturePanel,BorderLayout.WEST);
		processAnalysisPanelAdding(analysisPanel);
		constructLowerPanel();
		this.add(lowerPanel,BorderLayout.SOUTH);
		this.setVisible(true);
	}

	private void constructLowerPanel() {
		Font font = new Font("Lucida sans", Font.BOLD, 11);
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new BorderLayout());


		lblConnectionStatus = new JLabel();
		lblConnectionStatus.setFont(font);
		setConnectionStatus(false);

		lblRegionInteractive = new JLabel();
		lblRegionInteractive.setFont(font);
		updateCaptureRegions("Capture regions aren't set.");

		lowerPanel.add(lblRegionInteractive,BorderLayout.WEST);
		lowerPanel.add(lblConnectionStatus,BorderLayout.EAST);
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
		cStart.addActionListener((e) -> capturePerformable.startCapture());
		cStop = new JMenuItem("Stop");
		cStop.addActionListener((e) -> capturePerformable.stopCapture());
		cSettings = new JMenuItem("Settings");
		cSettings.addActionListener((e) -> capturePerformable.showSettings());
		capture.add(cStart);
		capture.add(cStop);
		capture.add(cSettings);
	}

	private void analysisMenuInit(){
		aPerfInst = new JMenuItem("Run instant analysis");
		aPerfInst.addActionListener(e -> analysisPerformable.performInstantAnalysis());

		aPerfIter = new JMenuItem("Run iterative analysis");
		aPerfIter.addActionListener(e -> analysisPerformable.performIterativeAnalysis());

		aPerf = new JMenuItem("Perform analysis");
		aPerf.addActionListener(e -> analysisPerformable.setAnalysisParams());

		aStop = new JMenuItem("Stop analysis");
		aStop.addActionListener(e -> analysisPerformable.stopAnalysis());

		aSetDefault = new JMenuItem("Set default regions");
		aSetDefault.addActionListener(e -> analysisPerformable.setDefaultCaptureAreas());

		analysis.add(aPerfInst);
		analysis.add(aPerfIter);
		analysis.add(aPerf);
		analysis.add(aStop);
		analysis.add(aSetDefault);
	}


	private void monitoringMenuInit() {
		mConfConn = new JMenuItem("Configurate connection");
		mConfConn.addActionListener(e -> monitoringPerformable.showConnectionFrame());

		mShowMonForm = new JMenuItem("Show monitoring frame");
		mShowMonForm.addActionListener(e -> monitoringPerformable.showDBFrame());

		monitoring.add(mConfConn);
		monitoring.add(mShowMonForm);
	}

	private void infoMenuInit(){

	}

	@Override
	public void setConnectionStatus(boolean status) {
		if(status)
			lblConnectionStatus.setText(connectionEstablished);
		else
			lblConnectionStatus.setText(connectionDisconnected);
	}

	@Override
	public void updateCaptureRegions(String text) {
		lblRegionInteractive.setText(text);
	}
}
