package analysis_subsystem.auxillary.areas_analysis;

public class BasicAnalysisConclusion {
    int[] meniscusBrightness, deviationBrightness;

    public BasicAnalysisConclusion(int[] meniscusBrightness, int[] deviationBrightness) {
        this.meniscusBrightness = meniscusBrightness;
        this.deviationBrightness = deviationBrightness;
    }

    public int[] getDeviationBrightness() {
        return deviationBrightness;
    }

    public int[] getMeniscusBrightness() {
        return meniscusBrightness;
    }
}
