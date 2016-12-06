package org.izanaar.sca.analysis_subsystem.interfaces;

public interface AnalysisPerformable{

    void performInstantAnalysis();
    void performIterativeAnalysis();
    void setAnalysisParams();
    void stopAnalysis();
    void setDefaultCaptureAreas();
}
