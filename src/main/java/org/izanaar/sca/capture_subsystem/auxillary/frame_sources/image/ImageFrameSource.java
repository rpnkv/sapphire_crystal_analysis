package org.izanaar.sca.capture_subsystem.auxillary.frame_sources.image;



import org.izanaar.sca.capture_subsystem.auxillary.frame_sources.FrameSource;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageFrameSource extends FrameSource {

    private String path;

    public ImageFrameSource() {
        super("Image");
        path = "/home/ierus/Pictures/opcv/me.jpg";
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public BufferedImage getFrame() throws IOException {
        return ImageIO.read(new File(path));
    }

    @Override
    protected JPanel completeSettingsPanel() {
        return new ImgSettingsPanel(this);
    }
}
