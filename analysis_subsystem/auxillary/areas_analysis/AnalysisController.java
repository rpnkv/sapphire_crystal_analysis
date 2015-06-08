package analysis_subsystem.auxillary.areas_analysis;

import analysis_subsystem.AnalysisFacade;
import analysis_subsystem.auxillary.analysis_result_visualisation.GraphInfo;
import analysis_subsystem.auxillary.analysis_result_visualisation.GraphTypes;
import analysis_subsystem.auxillary.areas_analysis.analysers.BasicFrameAnalyser;
import analysis_subsystem.auxillary.areas_analysis.analysers.FrameAnalyser;
import analysis_subsystem.auxillary.capture_regions_management.AreaDescription;
import analysis_subsystem.exceptions.AnalysisException;
import analysis_subsystem.gui.AnalysisSettingFrame;
import analysis_subsystem.interfaces.AnalysisResultProcessable;
import analysis_subsystem.interfaces.CaptureCoordEditable;
import monitoring_subsystem.auxillary.Measure;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.LinkedList;

public class AnalysisController implements AnalysisResultProcessable, CaptureCoordEditable{

    private AnalysisPerformer analysisPerformer;
    private AnalysisFacade facade;
    private Thread analysisThread;
    private boolean saveCoordToDB, analysisPerforming = false;

    private LinkedList<FrameAnalyser> frameAnalysers;

    private AnalysisSettingFrame settingFrame;
    private AnalysisConclusion previousConclusuion;
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
        LinkedList<GraphInfo> graphInfos = new LinkedList<>();

        graphInfos.add(new GraphInfo(conclusion.getMeniscusBrightness(),analysisPerformer.getMeniscus().getBegin().y,
                GraphTypes.CurrentMeniscusGraph));
        graphInfos.add(new GraphInfo(conclusion.getDeviationBrightness(),analysisPerformer.getDeviation().getBegin().x,
                GraphTypes.CurrentDeviationGraph));

        if(previousConclusuion != null){
            graphInfos.add(new GraphInfo(previousConclusuion.getMeniscusBrightness(),analysisPerformer.getMeniscus().getBegin().y,
                    GraphTypes.PreviousMeniscusGraph));
            graphInfos.add(new GraphInfo(previousConclusuion.getDeviationBrightness(),analysisPerformer.getDeviation().getBegin().x,
                    GraphTypes.PreviousDeviationGraph));
        }
        facade.getDiagramPanel().drawGraphs(graphInfos);
        int prevMeniscus,prevDeviation;

        try{
            prevMeniscus = previousConclusuion.getMeniscusHeight();
            prevDeviation = previousConclusuion.getCrystalXDeviation();
        }catch (NullPointerException e){
            prevMeniscus = conclusion.getMeniscusHeight();
            prevDeviation = conclusion.getCrystalXDeviation();
        }


        facade.viewResult(conclusion.toString() +
                "\n\u0394meniscus = " + String.valueOf(conclusion.getMeniscusHeight() - prevMeniscus) +", " +
                "Δdeviation = " + String.valueOf(conclusion.getCrystalXDeviation() - prevDeviation));
        previousConclusuion = conclusion;
        if (!saveCoordToDB)
            return;
        Measure measure = new Measure(conclusion.getMeniscusHeight(), conclusion.getCrystalXDeviation(),
                analysisPerformer.getMeniscus(), analysisPerformer.getDeviation(),
                analysisPerformer.getShaper());
        facade.getMeasureSaver().saveMeasure(measure);
    }

    @Override
    public BufferedImage getFrame() {
        return facade.getFrameProvider().getFrame();
    }

    @Override
    public void setCaptureCoord(AreaDescription areaDescription) {
        analysisPerformer.setCaptureCoord(areaDescription);
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
        analysisPerforming = false;
        previousConclusuion = null;
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
        analysisPerforming = true;
        facade.viewResult("Instant analysis started..");
        new Thread(()->{
            try {
                Thread.sleep(900);
                facade.viewResult("Instant analysis completed");
                analysisPerformer.forbidAnalysis();
                analysisPerforming = false;
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
        analysisPerforming = true;
        facade.viewResult("Iterative analysis started with parameters: iteration period = "+
                analysisPerformer.getIterLength() + "sec.," +
                "\n frame picking per iteration = " + analysisPerformer.getFrames() +" frames");
    }

    public void initTunableAnalysis() {
        if (settingFrame != null)
            settingFrame.dispose();
        SwingUtilities.invokeLater(() -> settingFrame =  new AnalysisSettingFrame(this));
    }

    public void startAnalysis(int lenght, int frames){
        analysisPerformer = new AnalysisPerformer(facade.getCurrentCaptureCoord(),frameAnalysers.get(0),this,lenght,frames);
        analysisThread = new Thread(analysisPerformer,"Analysis thread");
        facade.addCaptureCoordEditable(analysisPerformer);
        analysisPerformer.permitAnalysis();
        analysisThread.start();
        analysisPerforming = true;
        facade.viewResult("Custom analysis started with parameters: iteration period = "+
                analysisPerformer.getIterLength() + "sec.," +
                "\n frame picking per iteration = " + analysisPerformer.getFrames() +" frames");
    }

    public LinkedList<FrameAnalyser> getFrameAnalysers() {
        return frameAnalysers;
    }

    public void setDefaultAnalyser (FrameAnalyser analyser){
        frameAnalysers.set(0,analyser);
    }

    public void setDefaultAnalyser(String analyserName){
        frameAnalysers.forEach(analyser ->{
            if(analyser.toString().equals(analyserName))
                setDefaultAnalyser(analyser);
        });
    }

    public boolean setSaveCoordToDB(boolean saveCoordToDB) {
        if(facade.getMeasureSaver().isReadyToAnalysisLogging()){
            this.saveCoordToDB = saveCoordToDB;
            return saveCoordToDB;
        }else
            return false;
    }

    public boolean isSaveCoordToDB() {
        return saveCoordToDB;
    }

    public boolean isAnalysisPerforming() {
        return analysisPerforming;
    }
}
