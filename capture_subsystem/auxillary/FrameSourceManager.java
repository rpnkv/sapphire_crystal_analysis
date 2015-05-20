package capture_subsystem.auxillary;

import analysis_subsystem.crystalMathModel.MathModelFrameSource;
import capture_subsystem.frame_sources.FrameSource;
import capture_subsystem.frame_sources.camera.CameraFrameSource;
import capture_subsystem.frame_sources.image.ImageFrameSource;
import capture_subsystem.gui.CaptureSettingsFrame;
import capture_subsystem.interfaces.FrameProvideable;
import capture_subsystem.interfaces.VideoFlowDecorable;
import org.bytedeco.javacv.FrameGrabber;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class FrameSourceManager implements FrameProvideable{

    public static final int FPS_MIN = 1;
    public static final int FPS_MAX = 24;
    public static final int FPS_INIT = 19;    //initial frames per second

    private CaptureSettingsFrame settingsFrame;
    private FrameSource currentFrameSource;
    private ArrayList<FrameSource> frameSources;
    private Integer fps;
    private VideoFlowDecorable decorable;

    public FrameSourceManager(VideoFlowDecorable decorable) throws FrameGrabber.Exception {
        this.decorable = decorable;
        fps = FPS_INIT;
        initFrameSources();
        currentFrameSource = frameSources.get(0);
    }

    private synchronized void initFrameSources() throws FrameGrabber.Exception {
        frameSources = new ArrayList<>();
        frameSources.add(new ImageFrameSource());
        frameSources.add(new MathModelFrameSource(640,480));
        new Thread(() -> {
            try {
                frameSources.add(new CameraFrameSource());
            } catch (FrameGrabber.Exception e) {
                System.out.println(e);
            }
        }).start();
    }

    public void showCaptureSettings(){
        SwingUtilities.invokeLater(() ->{
            if(settingsFrame != null)
                settingsFrame.dispose();
            settingsFrame = new CaptureSettingsFrame(this);
        });
    }

    public Integer getFPS(){
        return fps;
    }

    public void setFps(Integer fps) {
        this.fps = fps;
    }

    public String[] getFrameSourcesNames() {
        String sourceNames[] = new String[frameSources.size()];
        for(int i = 0; i < frameSources.size(); i++)
            sourceNames[i] = frameSources.get(i).toString();
        return sourceNames;
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

    public JPanel getSettingsPanelByName(String name){
        JPanel[] settingsPanel = {null};
        frameSources.forEach((frameSource) -> {
            if(frameSource.toString().equals(name))
                settingsPanel[0] = frameSource.getSettingsPanel();
        });
        return settingsPanel[0];
    }

    public VideoFlowDecorable getDecorable() {
        return decorable;
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

    @Override
    public String toString() {
        return currentFrameSource.toString();
    }
}
