package org.izanaar.sca.analysis_subsystem.interfaces;

import org.izanaar.sca.analysis_subsystem.auxillary.areas_analysis.AnalysisConclusion;
import org.izanaar.sca.analysis_subsystem.exceptions.AnalysisException;
import org.izanaar.sca.capture_subsystem.interfaces.FrameProvidable;

public interface AnalysisResultProcessable extends FrameProvidable {
    void processException(AnalysisException e);
    void viewException(String type, String text);
    void viewException(Exception e);
    void processConclusion(AnalysisConclusion conclusion);
}
