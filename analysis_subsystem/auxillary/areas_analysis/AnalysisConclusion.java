package analysis_subsystem.auxillary.areas_analysis;

public class AnalysisConclusion extends BasicAnalysisConclusion{
    private double meniscusWidth;
    private double crystalXDeviation;

    public AnalysisConclusion(double meniscusHeight, double crystalXDeviation, short[] meniscusBr, short[] deviationBr){
        super(meniscusBr,deviationBr);
        this.meniscusWidth = meniscusHeight;
        this.crystalXDeviation = crystalXDeviation;
    }

    public double getMeniscusWidth() {
        return meniscusWidth;
    }

    public double getCrystalXDeviation() {
        return crystalXDeviation;
    }

    @Override
    public String toString() {
        return "Analysis conclusion: menWidth:" + meniscusWidth + ", crystal deviation by x: " + crystalXDeviation +
                ".";
    }
}
