package org.izanaar.sca.monitoring_subsystem.interfaces;


import org.izanaar.sca.monitoring_subsystem.auxillary.Measure;

public interface MeasureSavable {
    boolean saveMeasure(Measure measure);
    boolean isReadyToAnalysisLogging();
}
