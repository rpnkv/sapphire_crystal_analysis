package core.gui;

import core.ProjectCore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

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
	JMenuItem cStart, cStop, cShowOrig, cSettings;
	JMenuItem aSetMM, aSetCaptureRegions, confDiagramPanel;
	JMenuItem mnInitConnection, mnStartMonitoring,mnShowLog;

	ProjectCore projectCore;

	public CoreGUI(ProjectCore projectCore, JPanel capturePanel, JPanel analysisPanel) throws HeadlessException {
		super("Crystal analysis");
		this.projectCore = projectCore;
		this.setSize(1030, 650);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		/*initMenu();
		this.capturePanel = capturePanel;
		this.analysisPanel = analysisPanel;
		this.add(capturePanel,BorderLayout.WEST);
		processAnalysisPanelAdding(analysisPanel);*/

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
		mainMenuFill();
		captureMenuFill();
		analysisMenuFill();
		monitoringMenuFill();
		infoMenuFill();
		this.setJMenuBar(menuBar);
	}

	private void mainMenuFill(){

	}

	private void captureMenuFill(){

	}

	private void analysisMenuFill(){

	}


	private void monitoringMenuFill() {

	}

	private void infoMenuFill(){

	}
}
