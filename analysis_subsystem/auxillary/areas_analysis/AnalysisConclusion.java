package analysis_subsystem.auxillary.areas_analysis;

public class AnalysisConclusion extends BasicAnalysisConclusion{
    private int meniscusWidth;
    private int crystalXDeviation;

    public AnalysisConclusion(int meniscusHeight, int crystalXDeviation, int[] meniscusBr, int[] deviationBr){
        super(meniscusBr,deviationBr);
        this.meniscusWidth = meniscusHeight;
        this.crystalXDeviation = crystalXDeviation;
    }

    public int getMeniscusWidth() {
        return meniscusWidth;
    }

    public int getCrystalXDeviation() {
        return crystalXDeviation;
    }

    @Override
    public String toString() {
        return "Analysis conclusion: menWidth:" + meniscusWidth + ", crystal deviation by x: " + crystalXDeviation +
                ".";
    }
}
