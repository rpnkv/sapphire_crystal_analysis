package analysis_subsystem.auxillary.areas_analysis;

public class BasicAnalysisConclusion {
    short[] meniscusBrightness, deviationBrightness;

    public BasicAnalysisConclusion(short[] meniscusBrightness, short[] deviationBrightness) {
        this.meniscusBrightness = meniscusBrightness;
        this.deviationBrightness = deviationBrightness;
    }

    public short[] getDeviationBrightness() {
        return deviationBrightness;
    }

    public short[] getMeniscusBrightness() {
        return meniscusBrightness;
    }
}
