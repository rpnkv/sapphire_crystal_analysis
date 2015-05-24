package analysis_subsystem.interfaces;

import analysis_subsystem.auxillary.areas_analysis.AnalysisConclusion;
import analysis_subsystem.exceptions.AnalysisException;
import capture_subsystem.interfaces.FrameProvideable;

public interface AnalysisResultProcessable extends FrameProvideable{
    void processException(AnalysisException e);
    void processConclusion(AnalysisConclusion conclusion);
}
