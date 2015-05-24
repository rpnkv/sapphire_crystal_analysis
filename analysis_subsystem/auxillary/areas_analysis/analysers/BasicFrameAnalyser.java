package analysis_subsystem.auxillary.areas_analysis.analysers;


import analysis_subsystem.exceptions.AnalysisException;

import java.awt.image.BufferedImage;

public class BasicFrameAnalyser extends FrameAnalyser{

    @Override
    public void addNewValuesFromImage(BufferedImage image, Object name, Object array) throws AnalysisException{

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
    short[] getTotalBrightness(long[] intermediateValues) {
        short brightness[] = new short[intermediateValues.length];
        for(int i = 0; i < brightness.length; i++)
            brightness[i] = (short) (intermediateValues[i]/iterCount);

        return brightness;
    }
}
