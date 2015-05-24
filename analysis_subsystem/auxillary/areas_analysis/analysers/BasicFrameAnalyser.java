package analysis_subsystem.auxillary.areas_analysis.analysers;


import analysis_subsystem.auxillary.capture_regions_management.AreaDescription;
import analysis_subsystem.auxillary.capture_regions_management.AreaTypes;
import analysis_subsystem.exceptions.AnalysisException;

import java.awt.image.BufferedImage;

public class BasicFrameAnalyser extends FrameAnalyser{

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
        return 15;
    }

    @Override
    int calcCrystalXDeviation() {
        return 2;
    }

    @Override
    int[] getTotalBrightness(long[] intermediateValues) {
        int brightness[] = new int[intermediateValues.length];
        for(int i = 0; i < brightness.length; i++)
            brightness[i] = (short) (intermediateValues[i]/iterCount);
        return brightness;
    }
}
