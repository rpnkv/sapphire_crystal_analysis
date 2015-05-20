package capture_subsystem.interfaces;

import capture_subsystem.gui.ImagePanel;

import javax.swing.*;

/**
 * Created by ierus on 5/19/15.
 */
public interface CaptureGUIComponentsProvideable{

    JTextArea getOutpArea();
    ImageSetable imageSetable();
}
