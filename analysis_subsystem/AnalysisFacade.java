package analysis_subsystem;

import analysis_subsystem.gui.FrameAnalysisPanel;
import analysis_subsystem.interfaces.AnalysisSubsystemCommonInterface;
import core.interfaces.GUIPanelProvidable;

import javax.swing.*;

public class AnalysisFacade implements AnalysisSubsystemCommonInterface {

    FrameAnalysisPanel analysisPanel;


    public AnalysisFacade() {
        analysisPanel = new FrameAnalysisPanel();
    }

    @Override
    public JPanel getGUIPanel() {
        return analysisPanel;
    }

    @Override
    public void performInstantAnalysis() {
        System.out.println("instant");
    }

    @Override
    public void performAnalysisIteration() {
        System.out.println("iteration");
    }

    @Override
    public void performAnalysis() {
        System.out.println("analysis");
    }
}
