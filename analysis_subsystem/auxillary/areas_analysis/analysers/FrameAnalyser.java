package analysis_subsystem.auxillary.areas_analysis.analysers;


import analysis_subsystem.auxillary.areas_analysis.AnalysisConclusion;
import analysis_subsystem.auxillary.capture_regions_management.AreaDescription;
import analysis_subsystem.auxillary.capture_regions_management.AreaTypes;
import analysis_subsystem.exceptions.AnalysisException;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class FrameAnalyser {
    Map itermediateValues = new HashMap<AreaTypes, long[]>();
    ArrayList areaDescriptions;

    protected int iterCount;

    public void initAnalyser(AreaDescription... areaDescriptions){
        this.areaDescriptions = new ArrayList<AreaDescription>();
        for(AreaDescription area : areaDescriptions) {
            this.areaDescriptions.add(area);
            this.itermediateValues.put(area.getAreaType(),new long[area.getLenght()]);
        }
    }

    public void addImage(BufferedImage image) throws AnalysisException{
        iterCount++;
        this.itermediateValues.forEach((name,array) ->{
            try {
                addNewValuesFromImage(image,name,array);
            } catch (AnalysisException e) {
                System.out.println("adding new values failed");
            }
        });
    }

    abstract void addNewValuesFromImage(BufferedImage image, Object name, Object array) throws AnalysisException;
    public AnalysisConclusion getResults(int iterCount){
        long[] men = (long[]) this.itermediateValues.get(AreaTypes.Meniscus);
        long[] dev = (long[]) this.itermediateValues.get(AreaTypes.Deviation);
        return new AnalysisConclusion(calcMeniscusHeight(),calcCrystalXDeviation(),
                getTotalBrightness(men),getTotalBrightness(dev));
    }



    abstract int calcMeniscusHeight();
    abstract int calcCrystalXDeviation();
    abstract short[] getTotalBrightness(long[] intermediateValues);
}
