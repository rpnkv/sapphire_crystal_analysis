package analysis_subsystem.interfaces;

public interface AnalysisPerformable extends ControliableAnalysisPerformable{

    void performInstantAnalysis();
    void performIterativeAnalysis();
    void setAnalysisParams();
    void stopAnalysis();
    void setDefaultCaptureAreas();
}
