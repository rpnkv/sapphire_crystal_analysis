package org.izanaar.sca.analysis_subsystem.interfaces;

import org.izanaar.sca.analysis_subsystem.auxillary.analysis_result_visualisation.GraphInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public interface GraphDrawable {

    void drawGraphs(Collection<GraphInfo> graphSInfo);
    void drawGraph(GraphInfo graphInfo);
}
