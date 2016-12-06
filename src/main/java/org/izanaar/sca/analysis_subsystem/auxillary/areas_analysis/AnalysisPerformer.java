package org.izanaar.sca.analysis_subsystem.auxillary.areas_analysis;

import org.izanaar.sca.analysis_subsystem.auxillary.areas_analysis.analysers.FrameAnalyser;
import org.izanaar.sca.analysis_subsystem.auxillary.capture_regions_management.AreaDescription;
import org.izanaar.sca.analysis_subsystem.exceptions.AnalysisException;
import org.izanaar.sca.analysis_subsystem.interfaces.AnalysisResultProcessable;
import org.izanaar.sca.analysis_subsystem.interfaces.CaptureCoordEditable;

import java.util.ArrayList;

public class AnalysisPerformer implements Runnable, CaptureCoordEditable {
    private AreaDescription meniscus;
    private AreaDescription deviation;
    private AreaDescription shaper;

    private AreaDescription tempMeniscus;
    private AreaDescription tempDeviation;
    private AreaDescription tempShaper;

    boolean analysisIsPerforming;
    private AnalysisResultProcessable resultProcessor;
    private FrameAnalyser frameAnalyser;

    private int iterLength, frames;

    public AnalysisPerformer(ArrayList<AreaDescription> areaDescriptions, FrameAnalyser frameAnalyser,
                             AnalysisResultProcessable resultProcessor, int ... params) throws NullPointerException{
        this.tempMeniscus = areaDescriptions.get(0);
        this.tempDeviation = areaDescriptions.get(1);
        this.tempShaper = areaDescriptions.get(2);
        this.resultProcessor = resultProcessor;
        this.frameAnalyser = frameAnalyser;
        if (params == null || params.length <2){
            iterLength = 4;
            frames = 16;
        }else{
            iterLength = params[0];
            frames = params[1];
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
            try{
                performFrameSample();
            } catch (InterruptedException e) {
                resultProcessor.viewException("Analysis thread interrupted.",e.getMessage());
                return;
            } catch (AnalysisException e) {
                resultProcessor.processException(e);
                return;
            }

            resultProcessor.processConclusion(frameAnalyser.getResults(0));
        }while(analysisIsPerforming);
    }

    private void performFrameSample() throws InterruptedException, AnalysisException {
        final int limit = frames, sleepLenght = iterLength*1000/frames;
        int framesCount = 0;
        do{
            framesCount++;
            checkoutForAreasChange();
            frameAnalyser.addImage(resultProcessor.getFrame());
            Thread.sleep(sleepLenght);
        }while (analysisIsPerforming && framesCount<=limit);
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

    public AreaDescription getMeniscus() {
        return meniscus;
    }

    public AreaDescription getDeviation() {
        return deviation;
    }

    public AreaDescription getShaper() {
        return shaper;
    }

    public int getIterLength() {
        return iterLength;
    }

    public int getFrames() {
        return frames;
    }

    @Override
    public void setCaptureCoord(AreaDescription areaDescription) {
        switch (areaDescription.getAreaType()){
            case Deviation:
                tempDeviation = areaDescription;
                break;
            case Meniscus:
                tempMeniscus = areaDescription;
                break;
            case Shaper:
                tempShaper = areaDescription;
                break;
            case Erase:
                break;
        }
    }
}
