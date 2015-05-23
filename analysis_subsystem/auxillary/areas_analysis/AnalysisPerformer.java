package analysis_subsystem.auxillary.areas_analysis;

import analysis_subsystem.auxillary.capture_regions_management.AreaDescription;
import analysis_subsystem.exceptions.AnalysisException;
import analysis_subsystem.interfaces.AnalysisPerformingProcessable;
import analysis_subsystem.interfaces.CaptureCoordEditable;

public class AnalysisPerformer implements CaptureCoordEditable, Runnable {
    private static AnalysisPerformer instance;

    private AreaDescription meniscus;
    private AreaDescription deviation;
    private AreaDescription shaper;

    private AreaDescription tempMeniscus;
    private AreaDescription tempDeviation;
    private AreaDescription tempShaper;

    boolean analysisIsPerforming;
    private AnalysisPerformingProcessable performingProcessor;

    private AnalysisPerformer(AreaDescription meniscus, AreaDescription deviation,
                              AreaDescription shaper, AnalysisPerformingProcessable performingProcessor) {
        this.tempMeniscus = meniscus;
        this.tempDeviation = deviation;
        this.tempShaper = shaper;
        this.performingProcessor = performingProcessor;
        analysisIsPerforming = false;
    }

    public static AnalysisPerformer getInstance(AreaDescription _meniscus, AreaDescription _deviation,
                                         AreaDescription _shaper, AnalysisPerformingProcessable _performingProcessor){
        if(instance == null)
            instance = new AnalysisPerformer(_meniscus, _deviation, _shaper, _performingProcessor);
        return instance;
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

            System.out.println("abalysis is performing");
            System.out.println(meniscus);
            System.out.println(deviation);
            System.out.println(shaper);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }while(analysisIsPerforming);
    }

    private void checkoutForAreasChange(){
        if (tempMeniscus != null)
            meniscus = tempMeniscus;
        if (tempDeviation != null)
            deviation = tempDeviation;
        if (tempShaper != null)
            shaper = tempShaper;
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
            performingProcessor.processException(e);
            return false;
        }
    return true;
    }
}
