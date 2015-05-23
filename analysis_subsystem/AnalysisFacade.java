package analysis_subsystem;

import analysis_subsystem.auxillary.RegionSettingManager;
import analysis_subsystem.auxillary.VideoFlowDecorator;
import analysis_subsystem.gui.FrameAnalysisPanel;
import analysis_subsystem.interfaces.AnalysisSubsystemCommonInterface;
import analysis_subsystem.interfaces.CaptureRegionsViewable;
import capture_subsystem.interfaces.ImagePanelActionListenable;
import capture_subsystem.interfaces.VideoFlowDecorable;
import core.auxillary.ShapeDrawers.ShapeDrawer;

import javax.swing.*;

public class AnalysisFacade implements AnalysisSubsystemCommonInterface {

    JPanel componentGUI;
    RegionSettingManager regionSettingManager;
    AnalysisPerformer analysisPerformer;
    ShapeDrawer drawer;
    VideoFlowDecorator videoFlowDecorator;

    public AnalysisFacade(ShapeDrawer drawer) {
        componentGUI = new FrameAnalysisPanel();
        this.drawer = drawer;
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
    public void setActionListenable(ImagePanelActionListenable actionListenable, CaptureRegionsViewable regionsViewable) {
        regionSettingManager = new RegionSettingManager(actionListenable, regionsViewable);
        regionSettingManager.addCaptureCoordEditable(videoFlowDecorator);
    }

    public void setDecorable(VideoFlowDecorable decorable) {
        videoFlowDecorator = new VideoFlowDecorator(decorable, drawer);
    }

    @Override
    public ShapeDrawer getShapeDrawer() {
        return drawer;
    }
}
