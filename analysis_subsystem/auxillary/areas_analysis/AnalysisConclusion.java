package analysis_subsystem.auxillary.areas_analysis;

public class AnalysisConclusion {
    private double meniscusWidth;
    private double crystalXDeviation;
    private double shaperXDeviation;

    public AnalysisConclusion(double meniscusWidth, double crystalXDeviation, double shaperXDeviation) {
        this.meniscusWidth = meniscusWidth;
        this.crystalXDeviation = crystalXDeviation;
        this.shaperXDeviation = shaperXDeviation;
    }

    public double getMeniscusWidth() {
        return meniscusWidth;
    }

    public double getCrystalXDeviation() {
        return crystalXDeviation;
    }

    public double getShaperXDeviation() {
        return shaperXDeviation;
    }

    @Override
    public String toString() {
        return "Analysis conclusion: menWidth:" + meniscusWidth + ", crystan deviation by x: " + crystalXDeviation +
                "\nshaper deviation by x:" + shaperXDeviation+".";
    }
}
