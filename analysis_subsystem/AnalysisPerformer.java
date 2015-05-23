package analysis_subsystem;

import analysis_subsystem.auxillary.capture_regions_management.AreaDescription;
import analysis_subsystem.interfaces.CaptureCoordEditable;

public class AnalysisPerformer implements CaptureCoordEditable, Runnable {
    private AreaDescription meniscus;
    private AreaDescription deviation;
    private AreaDescription shaper;

    boolean analysisIsPerforming;

    public AnalysisPerformer(AreaDescription meniscus, AreaDescription deviation, AreaDescription shaper) {
        this.meniscus = meniscus;
        this.deviation = deviation;
        this.shaper = shaper;
    }

    @Override
    public void setCaptureCoord(AreaDescription areaDescription) {

    }

    public void permitAnalysis(){
        analysisIsPerforming = true;
    }

    public void forbidAnalysis(){
        analysisIsPerforming = false;
    }

    @Override
    public void run() {
        do{

        }while (analysisIsPerforming);
    }
}
