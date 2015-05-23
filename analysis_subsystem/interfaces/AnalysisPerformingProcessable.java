package analysis_subsystem.interfaces;

import analysis_subsystem.auxillary.areas_analysis.AnalysisConclusion;
import analysis_subsystem.exceptions.AnalysisException;

public interface AnalysisPerformingProcessable {
    void processException(AnalysisException e);
    void processConclusion(AnalysisConclusion conclusion);
}
