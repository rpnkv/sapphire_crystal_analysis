package capture_subsystem.auxillary;

import core.auxillary.ShapeDrawers.ShapeDrawer;
import analysis_subsystem.crystalMathModel.MathModelFrameSource;
import capture_subsystem.frame_sources.FrameSource;
import capture_subsystem.frame_sources.camera.CameraFrameSource;
import capture_subsystem.frame_sources.image.ImageFrameSource;
import capture_subsystem.gui.CaptureSettingsFrame;
import capture_subsystem.interfaces.FrameProvidable;
import capture_subsystem.interfaces.VideoFlowDecorable;
import org.bytedeco.javacv.FrameGrabber;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

//класс, выполняющий управление источниками видеосигнала и предоставляющий
//по требованию кадры
public class FrameSourceManager implements FrameProvidable {

    public static final int FPS_MIN = 1;
    public static final int FPS_MAX = 24;
    public static final int FPS_INIT = 19;    //initial frames per second

    private CaptureSettingsFrame settingsFrame;//форма с настройками захвата
    private FrameSource currentFrameSource;//текущий источник видеосигнала
    private ArrayList<FrameSource> frameSources;//все источники видеосигнала
    private Integer fps;//количество кадров в секунду во время захвата
    private VideoFlowDecorable decorable;//ссылка на интерфейс, который "украшается"
    private ShapeDrawer drawer;//ссылка на класс, выполняющий отрисовку примитивов

    public FrameSourceManager(VideoFlowDecorable decorable, ShapeDrawer drawer) throws FrameGrabber.Exception {
        this.decorable = decorable;
        this.drawer = drawer;
        fps = FPS_INIT;
        initFrameSources();
        currentFrameSource = frameSources.get(1);
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
            settingsFrame = new CaptureSettingsFrame(this,drawer);
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
