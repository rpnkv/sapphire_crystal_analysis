package capture_subsystem;

import capture_subsystem.interfaces.CapturePerformeable;
import core.interfaces.GUIPanelProvidable;

import javax.swing.*;

public class CaptureFacade implements CapturePerformeable,GUIPanelProvidable {



    @Override
    public void startCapture() {

    }

    @Override
    public void stopCapture() {

    }

    @Override
    public void showSettings() {

    }


    @Override
    public JPanel getGUIPanel() {
        return null;
    }
}
