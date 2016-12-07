package org.izanaar.sca.capture_subsystem.auxillary.frame_sources.camera;

import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.izanaar.sca.capture_subsystem.auxillary.frame_sources.FrameSource;

import javax.swing.*;
import java.awt.image.BufferedImage;


public class CameraFrameSource extends FrameSource {

    FrameGrabber grabber;

    public CameraFrameSource() throws FrameGrabber.Exception {
        super("Camera");
        grabber = new OpenCVFrameGrabber("");
        grabber.start();
    }

    @Override
    public BufferedImage getFrame() throws FrameGrabber.Exception {
        return new Java2DFrameConverter().convert(grabber.grab());
    }

    @Override
    protected JPanel completeSettingsPanel() {
        return null;
    }
}
