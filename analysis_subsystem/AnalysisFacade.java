package analysis_subsystem;

import analysis_subsystem.auxillary.analysis_result_visualisation.GraphInfo;
import analysis_subsystem.auxillary.areas_analysis.AnalysisConclusion;
import analysis_subsystem.auxillary.areas_analysis.AnalysisPerformer;
import analysis_subsystem.auxillary.areas_analysis.analysers.BasicFrameAnalyser;
import analysis_subsystem.auxillary.areas_analysis.analysers.FrameAnalyser;
import analysis_subsystem.auxillary.capture_regions_management.RegionSettingManager;
import analysis_subsystem.auxillary.capture_regions_management.VideoFlowDecorator;
import analysis_subsystem.exceptions.AnalysisException;
import analysis_subsystem.gui.AnalysisParamSetupFrame;
import analysis_subsystem.gui.FrameAnalysisPanel;
import analysis_subsystem.interfaces.AnalysisResultProcessable;
import analysis_subsystem.interfaces.AnalysisSubsystemCommonInterface;
import analysis_subsystem.interfaces.CaptureRegionsViewable;
import capture_subsystem.interfaces.FrameProvidable;
import capture_subsystem.interfaces.ImagePanelActionListenable;
import capture_subsystem.interfaces.VideoFlowDecorable;
import core.auxillary.ShapeDrawers.ShapeDrawer;
import monitoring_subsystem.auxillary.Measure;
import monitoring_subsystem.interfaces.MeasureSavable;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AnalysisFacade implements AnalysisSubsystemCommonInterface, AnalysisResultProcessable {

    FrameAnalysisPanel componentGUI; //component's GUI panel
    RegionSettingManager regionSettingManager;//controls region setting
    AnalysisPerformer analysisPerformer;//performs analysis
    ShapeDrawer drawer;//bridge
    VideoFlowDecorator videoFlowDecorator;//
    Thread analysisThread;
    FrameAnalyser frameAnalyser;
    FrameProvidable frameProvider;
    MeasureSavable measureSaver;
    boolean saveResults = false;

    public AnalysisFacade(ShapeDrawer drawer, FrameProvidable frameProvider) {
        componentGUI = new FrameAnalysisPanel(drawer);
        this.drawer = drawer;
        frameAnalyser = new BasicFrameAnalyser();
        this.frameProvider = frameProvider;
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
        analysisPerformer = new AnalysisPerformer(regionSettingManager.getMeniscusInf(), regionSettingManager.getDeviationInf(),
                regionSettingManager.getShaperInf(), this,frameAnalyser, this);
        analysisThread = new Thread(analysisPerformer,"analysis thread");
        regionSettingManager.addCaptureCoordEditable(analysisPerformer);
        analysisThread.start();
    }

    @Override
    public void performIterativeAnalysis() {
        try{
        analysisPerformer = new AnalysisPerformer(regionSettingManager.getMeniscusInf(), regionSettingManager.getDeviationInf(),
                regionSettingManager.getShaperInf(),this, frameAnalyser, this);
        analysisThread = new Thread(analysisPerformer,"analysis thread");
        regionSettingManager.addCaptureCoordEditable(analysisPerformer);
        analysisPerformer.permitAnalysis();
        analysisThread.start();
        }catch (NullPointerException e){
            analysisPerformer.forbidAnalysis();
            viewException("Null pointer exception",e.getMessage());
        }catch (Exception e){
            analysisPerformer.forbidAnalysis();
            viewException(e);
        }
    }

    @Override
    public void setAnalysisParams() {
        new AnalysisParamSetupFrame(this);
    }

    @Override
    public void performAnalysis(int iterLength, int frameNumber) {
        try{
            analysisPerformer = new AnalysisPerformer(regionSettingManager.getMeniscusInf(), regionSettingManager.getDeviationInf(),
                    regionSettingManager.getShaperInf(),this, frameAnalyser, this, iterLength,frameNumber);
            analysisThread = new Thread(analysisPerformer,"analysis thread");
            regionSettingManager.addCaptureCoordEditable(analysisPerformer);
            analysisPerformer.permitAnalysis();
            saveResults = true;
            analysisThread.start();
        }catch (NullPointerException e){
            saveResults = true;
            analysisPerformer.forbidAnalysis();
            viewException("Null pointer exception",e.getMessage());
        }catch (Exception e){
            saveResults = true;
            analysisPerformer.forbidAnalysis();
            viewException(e);
        }
    }

    @Override
    public void stopAnalysis() {
        saveResults = true;
        analysisPerformer.forbidAnalysis();
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

    @Override
    public void processException(AnalysisException e) {
        JOptionPane.showMessageDialog(null,e.getMessage(), "Analysis exception",
                JOptionPane.ERROR_MESSAGE);
        analysisPerformer.forbidAnalysis();
    }

    @Override
    public void processConclusion(AnalysisConclusion conclusion) {
        ArrayList<GraphInfo> graphInfos = new ArrayList<>();
        graphInfos.add(new GraphInfo(conclusion.getMeniscusBrightness(),
                regionSettingManager.getMeniscusInf().getBegin().y, Color.red));
        graphInfos.add(new GraphInfo(conclusion.getDeviationBrightness(),
                regionSettingManager.getDeviationInf().getBegin().x, Color.blue));
        componentGUI.getDiagramPanel().drawGraphs(graphInfos);
        if (!saveResults)
            return;
        Measure measure = new Measure(conclusion.getMeniscusWidth(), conclusion.getCrystalXDeviation(),
                regionSettingManager.getMeniscusInf(), regionSettingManager.getDeviationInf(),
                regionSettingManager.getShaperInf());
        try{
            if (!measureSaver.isReadyToAnalysisLogging())
                processException(new AnalysisException("No connection"));
            measureSaver.saveMeasure(measure);
        }catch (NullPointerException e){
            processException(new AnalysisException("Monitoring subsystem isnt' initialized."));
        }
    }

    @Override
    public BufferedImage getFrame() {
        return frameProvider.getFrame();
    }

    @Override
    public void viewResult(String text) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date now = new Date();
        String message = "[" + sdf.format(now) + "]" + text +".\n";
    }

    public void viewException(String type, String text){
        JOptionPane.showMessageDialog(null,
                text,
                type,
                JOptionPane.ERROR_MESSAGE);
    }


    private void viewException(Exception e) {
        JOptionPane.showMessageDialog(null,
                e.toString() + "\n"+e.getMessage(),
                "Exception!",
                JOptionPane.ERROR_MESSAGE);
    }
}
