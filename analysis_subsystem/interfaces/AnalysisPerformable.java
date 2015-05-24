package analysis_subsystem.interfaces;

public interface AnalysisPerformable {

    void performInstantAnalysis();
    void performIterativeAnalysis();
    void performAnalysis();
    void stopAnalysis();
    void setDefaultCaptureAreas();
}
