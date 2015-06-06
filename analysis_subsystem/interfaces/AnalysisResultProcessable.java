package analysis_subsystem.interfaces;

import analysis_subsystem.auxillary.areas_analysis.AnalysisConclusion;
import analysis_subsystem.exceptions.AnalysisException;
import capture_subsystem.interfaces.FrameProvidable;

public interface AnalysisResultProcessable extends FrameProvidable {
    void processException(AnalysisException e);
    void viewException(String type, String text);
    void viewException(Exception e);
    void processConclusion(AnalysisConclusion conclusion);
}
