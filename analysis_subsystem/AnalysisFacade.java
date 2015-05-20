package analysis_subsystem;

import analysis_subsystem.auxillary.RegionSettingManager;
import analysis_subsystem.gui.FrameAnalysisPanel;
import analysis_subsystem.interfaces.AnalysisSubsystemCommonInterface;
import capture_subsystem.interfaces.ImagePanelActionListeneable;
import core.interfaces.GUIPanelProvidable;

import javax.swing.*;
import java.awt.event.ActionListener;

public class AnalysisFacade implements AnalysisSubsystemCommonInterface {

    FrameAnalysisPanel analysisPanel;
    RegionSettingManager regionSettingManager;


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

    @Override
    public void setActionListeneable(ImagePanelActionListeneable actionListeneable) {
        regionSettingManager = new RegionSettingManager(actionListeneable);
    }
}
