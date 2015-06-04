package capture_subsystem.auxillary.frame_sources.camera;

import capture_subsystem.auxillary.frame_sources.FrameSource;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;

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
        return grabber.grab().getBufferedImage();
    }

    @Override
    protected JPanel completeSettingsPanel() {
        return null;
    }
}
