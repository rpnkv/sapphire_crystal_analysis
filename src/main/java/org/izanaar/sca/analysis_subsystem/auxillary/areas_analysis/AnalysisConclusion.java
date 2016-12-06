package org.izanaar.sca.analysis_subsystem.auxillary.areas_analysis;

public class AnalysisConclusion extends BasicAnalysisConclusion{
    private int meniscusHeight;
    private int crystalXDeviation;

    public AnalysisConclusion(int meniscusHeight, int crystalXDeviation, int[] meniscusBr, int[] deviationBr){
        super(meniscusBr,deviationBr);
        this.meniscusHeight = meniscusHeight;
        this.crystalXDeviation = crystalXDeviation;
    }

    public int getMeniscusHeight() {
        return meniscusHeight;
    }

    public int getCrystalXDeviation() {
        return crystalXDeviation;
    }

    @Override
    public String toString() {
        return "Conclusion: menisk height:" + meniscusHeight + ", edge deviation by x: " + crystalXDeviation;
    }
}
