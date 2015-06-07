package analysis_subsystem.auxillary.areas_analysis.analysers;


import analysis_subsystem.auxillary.capture_regions_management.AreaDescription;
import analysis_subsystem.auxillary.capture_regions_management.AreaTypes;
import analysis_subsystem.exceptions.AnalysisException;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class BasicFrameAnalyser extends FrameAnalyser{
    public BasicFrameAnalyser() {
        super("Basic frame analyser");
    }

    @Override
    void addNewValuesFromImage(BufferedImage image, AreaTypes type, long[] intermediateArray) throws AnalysisException {
        AreaDescription area = getAreaDescription(type);

        switch (area.getAreaType()){
            case Meniscus:
                concatValues(collectVerticalLine(image,area.getBegin(),area.getLenght()),intermediateArray);
                break;
            case Deviation:
                concatValues(collectHorizontalLine(image, area.getBegin(), area.getLenght()),intermediateArray);
                break;

            case Shaper:
                concatValues(collectHorizontalLine(image,area.getBegin(),area.getLenght()),intermediateArray);
                break;
        }
    }

    @Override
    int calcMeniscusHeight() {
        int[] meniscusBrightness = getTotalBrightness((long[]) itermediateValues.get(AreaTypes.Meniscus));
        int crystalBodyEnd = getCrystalBodyEnd(meniscusBrightness);
        int meniscusBegin = getMeniscusBegin(meniscusBrightness,crystalBodyEnd);
        int meniscusEnd = getMeniscusEnd(meniscusBrightness,meniscusBegin);
        int shaperBegin = getShaperBegin(meniscusBrightness,meniscusEnd);

        int averageCurveLeft = getCurveAverageValue(crystalBodyEnd,meniscusBegin, meniscusBrightness),
                averageCurveRight = getCurveAverageValue(meniscusEnd,shaperBegin, meniscusBrightness);

        int meniscusLeft = getXPointOnCurve(crystalBodyEnd,meniscusBegin,averageCurveLeft, meniscusBrightness),
                meniscusRight = getXPointOnCurve(meniscusEnd,shaperBegin,averageCurveRight, meniscusBrightness);

        return meniscusRight - meniscusLeft;
    }

    private int getShaperBegin(int[] meniscusBrightness, int meniscusEnd) {
        int value = meniscusBrightness[meniscusBrightness.length-1];
        for(int i = meniscusBrightness.length-2; i > meniscusEnd; i--){
            if (meniscusBrightness[i] != value)
                return i;
        }
        return 0;
    }

    private int getXPointOnCurve(int beginPoint, int endPoint, int value, int[] brightness) {
        for (int i = beginPoint; i <= endPoint; i++)
            if(brightness[i] == value)
                return i;

        int prevVal = brightness[0];
        for (int i = beginPoint+1; i <= endPoint; i++){
            if((prevVal < value && brightness[i] > value)||(prevVal > value && brightness[i] < value))
                return i;
            else prevVal = brightness[i];
        }
        return 0;
    }

    private int getCurveAverageValue(int x1, int x2, int[] values) {
        int count = x2-x1;
        long value = 0;
        for(int i = x1; i<x2;i++)
            value+=values[i];
        return (int) (value/count);
    }

    private int getMeniscusEnd(int[] meniscusBrightness, int meniscusBegin) {
        int value;
        for(int i = meniscusBegin+1; i < meniscusBrightness.length; i++){
            value = meniscusBrightness[i+1];
            if (meniscusBrightness[i] != value)
                return i;
        }
        return  0;
    }

    private int getMeniscusBegin(int[] meniscusBrightness, int crystalBodyEnd) {
        int value;
        for(int i = crystalBodyEnd+1; i < meniscusBrightness.length; i++){
            value = meniscusBrightness[i+1];
            if (meniscusBrightness[i] == value)
                return i;
        }
        return 0;
    }

    private int getCrystalBodyEnd(int[] meniscusBrightness) {
        int  value = meniscusBrightness[0];

        for(int i = 0; i < meniscusBrightness.length;i++)
            if (meniscusBrightness[i] != value)
            return i-1;

        return 0;
    }

    @Override
    int calcCrystalEdgeXCoord() {
        int[] brightness = getTotalBrightness((long[]) itermediateValues.get(AreaTypes.Deviation));
        int maxValueIndex = 0, maxValue = brightness[maxValueIndex];
        for(int i = 0; i< brightness.length;i++)
            if(brightness[i] > maxValue){
                maxValueIndex = i;
                maxValue = brightness[i];
            }
        return maxValueIndex + getAreaDescription(AreaTypes.Deviation).getBegin().x;
    }

    @Override
    int[] getTotalBrightness(long[] intermediateValues) {
        int brightness[] = new int[intermediateValues.length];
        for(int i = 0; i < brightness.length; i++)
            brightness[i] = (short) (intermediateValues[i]/iterCount);
        return brightness;
    }

    @Override
    public JPanel getSettingsPanel() {
        return null;
    }
}
