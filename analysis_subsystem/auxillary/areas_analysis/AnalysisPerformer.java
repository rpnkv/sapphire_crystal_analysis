package analysis_subsystem.auxillary.areas_analysis;

import analysis_subsystem.auxillary.areas_analysis.analysers.FrameAnalyser;
import analysis_subsystem.auxillary.capture_regions_management.AreaDescription;
import analysis_subsystem.exceptions.AnalysisException;
import analysis_subsystem.interfaces.AnalysisResultProcessable;
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
    private FrameAnalyser frameAnalyser;

    public AnalysisPerformer(AreaDescription meniscus, AreaDescription deviation,
                              AreaDescription shaper, AnalysisResultProcessable performingProcessor, FrameAnalyser frameAnalyser) {
        this.tempMeniscus = meniscus;
        this.tempDeviation = deviation;
        this.tempShaper = shaper;
        this.resultProcessor = performingProcessor;
        this.frameAnalyser = frameAnalyser;
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

            try {
                Thread.sleep(1000);
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
