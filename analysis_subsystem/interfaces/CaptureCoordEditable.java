package analysis_subsystem.interfaces;

import analysis_subsystem.auxillary.AreaTypes;

import java.awt.*;


public interface CaptureCoordEditable {
    void setCaptureCoord(AreaTypes type, Point beginPoint, int end, int width);
}
