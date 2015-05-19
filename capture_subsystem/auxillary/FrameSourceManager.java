package capture_subsystem.auxillary;

import analysis_subsystem.crystalMathModel.MathModelFrameSource;
import capture_subsystem.frame_sources.FrameSource;
import capture_subsystem.frame_sources.camera.CameraFrameSource;
import capture_subsystem.frame_sources.image.ImageFrameSource;
import capture_subsystem.gui.CaptureSettingsPanel;
import capture_subsystem.interfaces.FrameProvideable;
import capture_subsystem.interfaces.SettingsPanelProvideable;
import org.bytedeco.javacv.FrameGrabber;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


/**
 * Created by ierus on 5/19/15.
 */
public class FrameSourceManager implements FrameProvideable{

    public static final int FPS_MIN = 1;
    public static final int FPS_MAX = 24;
    public static final int FPS_INIT = 19;    //initial frames per second

    private JFrame settingsFrame;
    private FrameSource currentFrameSource;
    private ArrayList<FrameSource> frameSources;
    private Integer fps;

    public FrameSourceManager() throws FrameGrabber.Exception {
        fps = FPS_INIT;
        initFrameSources();
        currentFrameSource = frameSources.get(0);
    }

    private void initFrameSources() throws FrameGrabber.Exception {
        frameSources = new ArrayList<>();
        frameSources.add(new CameraFrameSource());
        frameSources.add(new ImageFrameSource());
        frameSources.add(new MathModelFrameSource(640,480));
    }

    public void showCaptureSettings(){
        SwingUtilities.invokeLater(() ->{
            if(settingsFrame != null)
                settingsFrame.dispose();
            CaptureSettingsPanel settingsPanel = new CaptureSettingsPanel(this);
            settingsFrame = new JFrame("Source settings");
            settingsFrame.setSize(350, 400);
            settingsFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            settingsFrame.add(settingsPanel);
            settingsFrame.setVisible(true);
        });
    }

    public Integer getFPS(){
        return fps;
    }

    public void setFps(Integer fps) {
        this.fps = fps;
    }

    public SettingsPanelProvideable[] getFrameSources() {
        return frameSources.toArray(new SettingsPanelProvideable[3]);
    }

    public int getSourcesAmount(){
        return frameSources.size();
    }

    public FrameSource getCurrentFrameSource(){
        return currentFrameSource;
    }

    public void setCurrentFrameSource(String sourceName) {
        frameSources.forEach((frameSource) ->{
            if(frameSource.toString().equals(sourceName)){
                currentFrameSource = frameSource;
            }
        });
    }

    @Override
    public BufferedImage getFrame() {
        try {
            return currentFrameSource.getFrame();
        } catch (Exception e) {
            e.printStackTrace();
            return new BufferedImage(640,480,BufferedImage.TYPE_BYTE_BINARY);
        }
    }
}
