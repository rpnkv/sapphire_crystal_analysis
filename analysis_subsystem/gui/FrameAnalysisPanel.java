package analysis_subsystem.gui;

import analysis_subsystem.auxillary.capture_regions_management.DiagramPanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class FrameAnalysisPanel extends JPanel {

	DiagramPanel diagramPanel;

	JTextArea iterationsLog;
	JScrollPane scrollPane;

	public FrameAnalysisPanel() {
		this.add(initDiagramPanel());
		this.add(initLogPanel());
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

	}

	private JPanel initLogPanel() {
		JPanel logPanel = new JPanel();
		logPanel.setLayout(new BorderLayout());
		iterationsLog = new JTextArea(5,41);
		iterationsLog.setFocusable(false);
		scrollPane = new JScrollPane(iterationsLog);

		iterationsLog.setLineWrap(true);
		Border border = BorderFactory.createTitledBorder("Analysis iterations log:");
		logPanel.setBorder(border);
		logPanel.add(scrollPane);
		return logPanel;
	}

	private JPanel initDiagramPanel() {
		JPanel diagramPanelWrap = new JPanel();
		Border border = BorderFactory.createTitledBorder("Selection analysis result:");
		diagramPanel = new DiagramPanel(495,315);
		diagramPanelWrap.add(diagramPanel);
		diagramPanelWrap.setBorder(border);
		return diagramPanelWrap;
	}

	public JTextArea getLogPanel(){
		return iterationsLog;
	}

	public DiagramPanel getDiagramPanel() {
		return diagramPanel;
	}
}
