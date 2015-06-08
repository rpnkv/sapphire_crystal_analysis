package analysis_subsystem.auxillary.analysis_result_visualisation;

public class GraphInfo {

    int[] brightnessValues;
    int beginPixel;
    GraphTypes graphType;

    public GraphInfo(int[] brightnessValues, int beginPixel, GraphTypes graphType) {
        this.brightnessValues = brightnessValues;
        this.beginPixel = beginPixel;
        this.graphType = graphType;
    }

    public int[] getBrightnessValues() {
        return brightnessValues;
    }

    public int getBeginPixel() {
        return beginPixel;
    }

    public GraphTypes getGraphType() {
        return graphType;
    }
}

