package analysis_subsystem;

import analysis_subsystem.auxillary.RegionSettingManager;
import core.auxillary.ShapeDrawers.JavaAPIShapeDrawer;
import core.auxillary.ShapeDrawers.ShapeDrawer;
import analysis_subsystem.gui.FrameAnalysisPanel;
import analysis_subsystem.interfaces.AnalysisSubsystemCommonInterface;
import capture_subsystem.interfaces.ImagePanelActionListenable;

import javax.swing.*;

public class AnalysisFacade implements AnalysisSubsystemCommonInterface {

    JPanel componentGUI;
    RegionSettingManager regionSettingManager;
    AnalysisPerformer analysisPerformer;
    ShapeDrawer drawer;

    public AnalysisFacade(ShapeDrawer drawer) {
        componentGUI = new FrameAnalysisPanel();
        this.drawer = new JavaAPIShapeDrawer();
    }

    @Override
    public JPanel getGUIPanel() {
        return componentGUI;
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
    public void setActionListeneable(ImagePanelActionListenable actionListeneable) {
        regionSettingManager = new RegionSettingManager(actionListeneable);
    }

    @Override
    public ShapeDrawer getShapeDrawer() {
        return drawer;
    }
}
