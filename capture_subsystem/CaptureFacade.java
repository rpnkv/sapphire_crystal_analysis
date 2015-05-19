package capture_subsystem;

import capture_subsystem.auxillary.CapturePerformer;
import capture_subsystem.auxillary.FrameSourceManager;
import capture_subsystem.gui.CapturePanel;
import capture_subsystem.interfaces.CaptureSubsystemCommonInterface;
import org.bytedeco.javacv.FrameGrabber;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class CaptureFacade implements CaptureSubsystemCommonInterface {
    CapturePanel subsystemGUI;
    CapturePerformer capturePerformer;
    FrameSourceManager frameSourceManager;
    Thread videoCaptureThread;

    public CaptureFacade() {
        try {
            subsystemGUI = new CapturePanel();
            frameSourceManager = new FrameSourceManager();
            capturePerformer = new CapturePerformer
                    (subsystemGUI,frameSourceManager.getCurrentFrameSource(),frameSourceManager.getFps());
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void startCapture() {
        videoCaptureThread = new Thread(capturePerformer, "Video capture thread");
        videoCaptureThread.start();
    }

    @Override
    public void stopCapture() {
        capturePerformer.stopCapture();
        videoCaptureThread = null;
    }

    @Override
    public void showSettings() {
        frameSourceManager.showCaptureSettings();
    }


    @Override
    public JPanel getGUIPanel() {
        return subsystemGUI;
    }

    @Override
    public BufferedImage getFrame() {
        return null;
    }
}
