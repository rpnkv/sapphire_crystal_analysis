package capture_subsystem.auxillary.frame_sources;

import capture_subsystem.interfaces.SettingsPanelProvidable;

import javax.swing.*;
import java.awt.image.BufferedImage;


public abstract class FrameSource implements SettingsPanelProvidable {

    final String alias;

    protected FrameSource(String alias) {
        this.alias = alias;
    }

    public abstract BufferedImage getFrame() throws Exception;

    protected abstract JPanel completeSettingsPanel();

    @Override
    public final JPanel getSettingsPanel(){
        return completeSettingsPanel();
    }

    @Override
    public String toString() {
        return alias;
    }
}
