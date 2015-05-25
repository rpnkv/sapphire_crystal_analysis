package monitoring_subsystem.interfaces;

import analysis_subsystem.interfaces.ConnectionStatusEditable;

public interface ConnectionFramesProvidable {

    void showConnectionFrame(ConnectionStatusEditable statusEditable);
    void showDBFrame();

}
