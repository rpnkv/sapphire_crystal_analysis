package monitoring_subsystem.interfaces;


import monitoring_subsystem.auxillary.Measure;

public interface MeasureSaveable {
    boolean saveMeasure(Measure measure);
    boolean isReadyToAnalysisLogging();
}
