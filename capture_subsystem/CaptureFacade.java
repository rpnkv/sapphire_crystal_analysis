package capture_subsystem;

import capture_subsystem.auxillary.CapturePerformer;
import capture_subsystem.auxillary.FrameSourceManager;
import capture_subsystem.auxillary.image_decorators.DrawGridImageDecorator;
import capture_subsystem.auxillary.image_decorators.GrayScaleImageDecorator;
import capture_subsystem.auxillary.image_decorators.ImageDecorator;
import capture_subsystem.gui.CapturePanel;
import capture_subsystem.gui.ImagePanel;
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
            frameSourceManager = new FrameSourceManager(this);
            } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void startCapture() {
        capturePerformer = new CapturePerformer
                (subsystemGUI,frameSourceManager,frameSourceManager.getFPS());

        videoCaptureThread = new Thread(capturePerformer, "Video capture thread");
        addDecorator(new GrayScaleImageDecorator());
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

    @Override
    public void addDecorator(ImageDecorator decorator) {
        if( capturePerformer.getImageSetable() instanceof ImagePanel){
            decorator.setInnerDecorator(capturePerformer.getImageSetable());
            capturePerformer.setImageSetable(decorator);
        }
            else {
            ImageDecorator decorator1 = (ImageDecorator) capturePerformer.getImageSetable();
            decorator1.addDecorator(decorator);
            capturePerformer.setImageSetable(decorator1);
        }
    }

    @Override
    public void deleteDecorator(String alias) {
        try {
            if(capturePerformer.getImageSetable() instanceof ImageDecorator){
                ImageDecorator upperDecorator = (ImageDecorator) capturePerformer.getImageSetable();
                if(upperDecorator.toString().equals(alias))
                    capturePerformer.setImageSetable(upperDecorator.getInnerDecorator());
                else
                    upperDecorator.deleteDecorator(alias);
                System.out.println("decorator \'" +alias +"\' has been deleted.");
            }else
                System.out.println("no decorators");

        }catch (NullPointerException e){
            System.out.println("no such decorator");
        }

    }
}
