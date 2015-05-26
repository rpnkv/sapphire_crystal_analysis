package analysis_subsystem.auxillary.areas_analysis;

import analysis_subsystem.auxillary.areas_analysis.analysers.FrameAnalyser;
import analysis_subsystem.auxillary.capture_regions_management.AreaDescription;
import analysis_subsystem.exceptions.AnalysisException;
import analysis_subsystem.interfaces.AnalysisResultProcessable;
import analysis_subsystem.interfaces.AnalysisResultViewable;
import analysis_subsystem.interfaces.CaptureCoordEditable;

public class AnalysisPerformer implements CaptureCoordEditable, Runnable {
    private AreaDescription meniscus;
    private AreaDescription deviation;
    private AreaDescription shaper;

    private AreaDescription tempMeniscus;
    private AreaDescription tempDeviation;
    private AreaDescription tempShaper;

    boolean analysisIsPerforming;
    private AnalysisResultProcessable resultProcessor;
    private AnalysisResultViewable resultViewer;
    private FrameAnalyser frameAnalyser;

    int iterLength, frames;

    public AnalysisPerformer(AreaDescription meniscus, AreaDescription deviation,
                              AreaDescription shaper, AnalysisResultProcessable performingProcessor,
                             FrameAnalyser frameAnalyser, AnalysisResultViewable resultViewer,
                             int ... params) throws NullPointerException{
        this.tempMeniscus = meniscus;
        this.tempDeviation = deviation;
        this.tempShaper = shaper;
        this.resultProcessor = performingProcessor;
        this.frameAnalyser = frameAnalyser;
        this.resultViewer = resultViewer;
        if (params == null || params.length <2){
            iterLength = 500;
            frames = 1;
        }else{
            iterLength = params[0];
            frames = params[1];
        }
    }

    @Override
    public void setCaptureCoord(AreaDescription areaDescription) {
       switch (areaDescription.getAreaType()){
           case Meniscus:
                tempMeniscus = areaDescription;
               break;
           case Deviation:
                tempDeviation = areaDescription;
               break;
           case Shaper:
                tempShaper = areaDescription;
               break;
       }
    }

    public void permitAnalysis(){
        analysisIsPerforming = true;
    }

    public void forbidAnalysis(){
        analysisIsPerforming = false;
    }

    @Override
    public void run() {
        if(!checkAreasForValid())
            return;
        do{
            checkoutForAreasChange();
            try {
                frameAnalyser.addImage(resultProcessor.getFrame());
            } catch (AnalysisException e) {
                resultProcessor.processException(e);
            }
                resultProcessor.processConclusion(frameAnalyser.getResults(0));
            try {
                Thread.sleep(1000/iterLength);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }while(analysisIsPerforming);
        resultProcessor.processConclusion(frameAnalyser.getResults(0));
    }

    private void checkoutForAreasChange(){
        if (tempMeniscus != null)
            meniscus = tempMeniscus;
        if (tempDeviation != null)
            deviation = tempDeviation;
        if (tempShaper != null)
            shaper = tempShaper;
        frameAnalyser.initAnalyser(meniscus,deviation,shaper);
    }

    private boolean checkAreasForValid(){
        try {
        if (tempMeniscus == null)
            throw new AnalysisException("Meniscus coordinates aren't set");
        if (tempDeviation == null)
            throw new AnalysisException("Deviation coordinates aren't set");
        if (tempShaper == null)
            throw new AnalysisException("Shaper coordinates aren't set");


        }catch (AnalysisException e){
            resultProcessor.processException(e);
            return false;
        }
    return true;
    }
}
