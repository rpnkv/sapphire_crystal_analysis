package capture_subsystem.auxillary.image_decorators;

import analysis_subsystem.interfaces.CaptureCoordEditable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawCaptureCoordImageDecorator extends ImageDecorator implements CaptureCoordEditable{

    public DrawCaptureCoordImageDecorator() {
        super("draw capture coord image decorator");
    }

    @Override
    public void setCaptureCoord(int regionCode, Point beginPoint, int end, int width) {

    }

    @Override
    public void setImage(BufferedImage source) {

    }
}
