package capture_subsystem.frame_sources.image;


import capture_subsystem.frame_sources.FrameSource;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class ImageFrameSource extends FrameSource {

    public ImageFrameSource() {
        super("Image");
    }

    @Override
    public BufferedImage getFrame() {
        return null;
    }

    @Override
    protected JPanel completeSettingsPanel() {
        return new ImgSettingsPanel();
    }
}
