package analysis_subsystem;

import analysis_subsystem.auxillary.analysis_result_visualisation.DiagramPanel;
import analysis_subsystem.auxillary.areas_analysis.AnalysisController;
import analysis_subsystem.auxillary.areas_analysis.analysers.BasicFrameAnalyser;
import analysis_subsystem.auxillary.areas_analysis.analysers.FrameAnalyser;
import analysis_subsystem.auxillary.capture_regions_management.AreaDescription;
import analysis_subsystem.auxillary.capture_regions_management.RegionSettingManager;
import analysis_subsystem.auxillary.capture_regions_management.VideoFlowDecorator;
import analysis_subsystem.gui.FrameAnalysisPanel;
import analysis_subsystem.interfaces.AnalysisSubsystemCommonInterface;
import analysis_subsystem.interfaces.CaptureCoordEditable;
import analysis_subsystem.interfaces.CaptureRegionsViewable;
import capture_subsystem.interfaces.FrameProvidable;
import capture_subsystem.interfaces.ImagePanelActionListenable;
import capture_subsystem.interfaces.VideoFlowDecorable;
import core.auxillary.ShapeDrawers.ShapeDrawer;
import monitoring_subsystem.interfaces.MeasureSavable;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AnalysisFacade implements AnalysisSubsystemCommonInterface {

    FrameAnalysisPanel componentGUI; //component's GUI panel
    RegionSettingManager regionSettingManager;//controls region setting
    AnalysisController analysisController;
    ShapeDrawer drawer;//bridge
    VideoFlowDecorator videoFlowDecorator;//
    FrameAnalyser frameAnalyser;
    FrameProvidable frameProvider;
    MeasureSavable measureSaver;

    public AnalysisFacade(ShapeDrawer drawer, FrameProvidable frameProvider) {
        componentGUI = new FrameAnalysisPanel(drawer);
        this.drawer = drawer;
        frameAnalyser = new BasicFrameAnalyser();
        this.frameProvider = frameProvider;
        analysisController = new AnalysisController(this);
    }

    public void setMeasureSaver(MeasureSavable measureSaver) {
        this.measureSaver = measureSaver;
    }

    @Override
    public JPanel getGUIPanel() {
        return componentGUI;
    }

    @Override
    public void performInstantAnalysis() {
        analysisController.instantAnalysis();
    }

    @Override
    public void performIterativeAnalysis() {
        analysisController.iterativeAnalysis();
    }

    @Override
    public void setAnalysisParams() {
        analysisController.initTunableAnalysis();
    }

    @Override
    public void stopAnalysis() {
        analysisController.abortAnalysis();
    }

    @Override
    public void setDefaultCaptureAreas() {
        regionSettingManager.setDefaultCaptureRegions();
    }

    @Override
    public void setActionListenable(ImagePanelActionListenable actionListenable, CaptureRegionsViewable regionsViewable) {
        regionSettingManager = new RegionSettingManager(actionListenable, regionsViewable);
        regionSettingManager.addCaptureCoordEditable(videoFlowDecorator);
    }

    public void setDecorable(VideoFlowDecorable decorable) {
        videoFlowDecorator = new VideoFlowDecorator(decorable, drawer);
    }

    public void viewResult(String text) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date now = new Date();
        String message = "[" + sdf.format(now) + "]" + text +".\n";
        componentGUI.getLogPanel().append(message);
    }

    public FrameProvidable getFrameProvider() {
        return frameProvider;
    }

    public DiagramPanel getDiagramPanel() {
        return componentGUI.getDiagramPanel();
    }

    public ArrayList<AreaDescription> getCurrentCaptureCoord(){
        ArrayList<AreaDescription> areaDescriptions = new ArrayList<>(2);
        areaDescriptions.add(regionSettingManager.getMeniscusInf());
        areaDescriptions.add(regionSettingManager.getDeviationInf());
        areaDescriptions.add(regionSettingManager.getShaperInf());
        return areaDescriptions;
    }

    public void addCaptureCoordEditable(CaptureCoordEditable coordEditable){
        regionSettingManager.addCaptureCoordEditable(coordEditable);
    }

    public boolean removeCaptureCoordEditable(CaptureCoordEditable coordEditable){
        return regionSettingManager.removeCaptureCoordEditable(coordEditable);
    }
}
