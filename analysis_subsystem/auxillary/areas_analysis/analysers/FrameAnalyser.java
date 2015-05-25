package analysis_subsystem.auxillary.areas_analysis.analysers;


import analysis_subsystem.auxillary.areas_analysis.AnalysisConclusion;

import analysis_subsystem.auxillary.capture_regions_management.AreaDescription;
import analysis_subsystem.auxillary.capture_regions_management.AreaTypes;
import analysis_subsystem.exceptions.AnalysisException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class FrameAnalyser {
    Map itermediateValues = new HashMap<AreaTypes, long[]>();
    ArrayList<AreaDescription> areaDescriptions;

    protected int iterCount;

    public void initAnalyser(AreaDescription... areaDescriptions){
        this.areaDescriptions = new ArrayList<>();
        for(AreaDescription area : areaDescriptions) {
            this.areaDescriptions.add(area);
            this.itermediateValues.put(area.getAreaType(),new long[area.getLenght()]);
        }
        iterCount = 0;
    }


    public void addImage(BufferedImage image) throws AnalysisException{
        iterCount++;
        this.itermediateValues.forEach((areaType,intermediateArray) ->{
            try {
                AreaTypes type = (AreaTypes) areaType;
                long[] array = (long[]) intermediateArray;
                addNewValuesFromImage(image,type,array);
            } catch (AnalysisException e) {
                System.out.println("adding new values failed");
            }
        });
    }

    abstract void addNewValuesFromImage(BufferedImage image, AreaTypes type, long[] intermediateArray)
            throws AnalysisException;

    public AnalysisConclusion getResults(int iterCount){
        long[] men = (long[]) this.itermediateValues.get(AreaTypes.Meniscus);
        long[] dev = (long[]) this.itermediateValues.get(AreaTypes.Deviation);
        return new AnalysisConclusion(calcMeniscusHeight(),calcCrystalXDeviation(),
                getTotalBrightness(men),getTotalBrightness(dev));

    }


    protected int[] collectVerticalLine(BufferedImage image, Point start, int length){
        int[] collection = new int[length];

        for(int i = start.y; i < length+start.y; i++)
            collection[i-start.y] = new Color(image.getRGB(i,i)).getRed();
        return collection;
    }

    protected int[] collectHorizontalLine(BufferedImage image, Point start, int length){
        int[] collection = new int[length];

        for(int i = start.x; i < length+start.x; i++)
            collection[i-start.x] =  new Color(image.getRGB(i,start.y)).getRed();
        return collection;
    }

    protected AreaDescription getAreaDescription(AreaTypes type){
        for (AreaDescription area : areaDescriptions) {
            if(area.getAreaType().equals(type))
                return area;
        }
        return null;
    }

    protected void concatValues(int[] arr1, long[] arr2){
        for(int i = 0; i < arr1.length; i++)
            arr2[i] +=arr1[i];
    }

    abstract int calcMeniscusHeight();
    abstract int calcCrystalXDeviation();
    abstract int[] getTotalBrightness(long[] intermediateValues);

}
