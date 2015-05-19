package capture_subsystem.auxillary;

import analysis_subsystem.crystalMathModel.MathModelFrameSource;
import capture_subsystem.frame_sources.FrameSource;
import capture_subsystem.frame_sources.camera.CameraFrameSource;
import capture_subsystem.frame_sources.image.ImageFrameSource;
import org.bytedeco.javacv.FrameGrabber;

import javax.swing.*;

/**
 * Created by ierus on 5/19/15.
 */
public class FrameSourceManager {

    public static final int FPS_MIN = 1;
    public static final int FPS_MAX = 24;
    public static final int FPS_INIT = 19;    //initial frames per second

    FrameSource currentFrameSource;
    FrameSource[] frameSources;
    Integer fps;

    public FrameSourceManager() throws FrameGrabber.Exception {
        fps = FPS_INIT;
        initFrameSources();
        currentFrameSource = frameSources[1];
    }

    private void initFrameSources() throws FrameGrabber.Exception {
        frameSources = new FrameSource[]{new ImageFrameSource(), new CameraFrameSource(),new MathModelFrameSource(640,480)};
    }

    public void showCaptureSettings(){
        SwingUtilities.invokeLater(() ->{

        });
    }

    public Integer getFps(){
        return fps;
    }

    public FrameSource getCurrentFrameSource(){
        return currentFrameSource;
    }
}
