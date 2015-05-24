package analysis_subsystem.interfaces;

import analysis_subsystem.auxillary.analysis_result_visualisation.GraphInfo;

import java.util.ArrayList;

public interface GraphDrawable {

    void drawGraphs(ArrayList<GraphInfo> graphSInfo);
    void drawGraph(GraphInfo graphInfo);
}
