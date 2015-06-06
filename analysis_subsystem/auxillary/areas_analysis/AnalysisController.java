package analysis_subsystem.auxillary.areas_analysis;

import analysis_subsystem.AnalysisFacade;
import analysis_subsystem.auxillary.analysis_result_visualisation.GraphInfo;
import analysis_subsystem.auxillary.areas_analysis.analysers.BasicFrameAnalyser;
import analysis_subsystem.auxillary.areas_analysis.analysers.FrameAnalyser;
import analysis_subsystem.auxillary.capture_regions_management.AreaDescription;
import analysis_subsystem.exceptions.AnalysisException;
import analysis_subsystem.interfaces.AnalysisResultProcessable;
import analysis_subsystem.interfaces.CaptureCoordEditable;
import monitoring_subsystem.auxillary.Measure;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;

public class AnalysisController implements AnalysisResultProcessable, CaptureCoordEditable{

    private AnalysisPerformer analysisPerformer;
    private AnalysisFacade facade;
    private Thread analysisThread;
    private boolean saveCoordToDB;

    private LinkedList<FrameAnalyser> frameAnalysers;

    public AnalysisController(AnalysisFacade facade) {
        this.facade = facade;
        saveCoordToDB = false;
        initFrameAnalysers();
    }

    private void initFrameAnalysers() {
        frameAnalysers = new LinkedList<>();
        frameAnalysers.add(new BasicFrameAnalyser());
    }

    @Override
    public void processException(AnalysisException e) {
        viewException("Analysis error :: exception",e.getMessage());
    }

    @Override
    public void processConclusion(AnalysisConclusion conclusion) {
        ArrayList<GraphInfo> graphInfos = new ArrayList<>();
        graphInfos.add(new GraphInfo(conclusion.getMeniscusBrightness(),
                analysisPerformer.getMeniscus().getBegin().y, Color.red, (short) 2));
        graphInfos.add(new GraphInfo(conclusion.getDeviationBrightness(),
                analysisPerformer.getDeviation().getBegin().x, Color.blue, (short) 2));
        facade.getDiagramPanel().drawGraphs(graphInfos);
        facade.viewResult(conclusion.toString());
        if (!saveCoordToDB)
            return;
        Measure measure = new Measure(conclusion.getMeniscusHeight(), conclusion.getCrystalXDeviation(),
                analysisPerformer.getMeniscus(), analysisPerformer.getDeviation(),
                analysisPerformer.getShaper());

    }

    @Override
    public BufferedImage getFrame() {
        return facade.getFrameProvider().getFrame();
    }

    @Override
    public void setCaptureCoord(AreaDescription areaDescription) {

    }

    public void abortAnalysis(){
        /*new Thread(()->{
            if (analysisThread != null){analysisPerformer.forbidAnalysis();
            try {
                Thread.sleep(analysisPerformer.iterLength);
            } catch (InterruptedException e) {
                viewException("Analysis stop has been interrupted",e.getMessage());
            }
            analysisPerformer = null;
            analysisThread = null;}
        }, "analysisStoppingThread").start();*/
        analysisPerformer.forbidAnalysis();
        if(!facade.removeCaptureCoordEditable(analysisPerformer))
            viewException("Analysis error","Analysis performer wasn't registered.");
        facade.viewResult("Analysis stopped");
    }

    @Override
    public void viewException(Exception e) {
        JOptionPane.showMessageDialog(null,
                e.toString() + "\n" + e.getMessage(),
                "Exception!",
                JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void viewException(String type, String text){
        JOptionPane.showMessageDialog(null,
                text, type, JOptionPane.ERROR_MESSAGE);
    }

    public void instantAnalysis() {
        analysisPerformer = new AnalysisPerformer(facade.getCurrentCaptureCoord(),frameAnalysers.get(0),this);
        analysisThread = new Thread(analysisPerformer,"Analysis thread");
        analysisThread.start();
        facade.viewResult("Instant analysis started..");
        new Thread(()->{
            try {
                Thread.sleep(900);
                facade.viewResult("Instant analysis completed");
                analysisPerformer.forbidAnalysis();
            } catch (InterruptedException e) {
                viewException(e);
            }
        },"instant analysis thread").start();
    }

    public void iterativeAnalysis() {
        analysisPerformer = new AnalysisPerformer(facade.getCurrentCaptureCoord(),frameAnalysers.get(0),this);
        analysisThread = new Thread(analysisPerformer,"Analysis thread");
        facade.addCaptureCoordEditable(analysisPerformer);
        analysisPerformer.permitAnalysis();
        analysisThread.start();
        facade.viewResult("Iterative analysis started with parameters: iteration period = "+
                analysisPerformer.getIterLength() + "sec.," +
                "\n frame picking per iteration = " + analysisPerformer.getFrames() +" frames");
    }

    public void initTunableAnalysis() {
        System.out.println("tuneable");
    }

    public LinkedList<FrameAnalyser> getFrameAnalysers() {
        return frameAnalysers;
    }

    public void setDefaultAnalyser (FrameAnalyser analyser){
        frameAnalysers.set(0,analyser);
    }
}
