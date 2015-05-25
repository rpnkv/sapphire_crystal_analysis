package monitoring_subsystem;

import analysis_subsystem.interfaces.ConnectionStatusEditable;
import monitoring_subsystem.auxillary.DatabaseIntermediator;
import monitoring_subsystem.gui.ConnectionSettingsFrame;
import monitoring_subsystem.gui.DatabaseFrame;
import monitoring_subsystem.interfaces.MonitoringSubsystemCommonInterface;

import javax.swing.*;

public class MonitoringFacade implements MonitoringSubsystemCommonInterface {
    ConnectionSettingsFrame connFrame;
    DatabaseFrame dbFrame;
    DatabaseIntermediator intermediator;

    public void setConnectionStatusEditable(ConnectionStatusEditable statusEditable){
        intermediator = new DatabaseIntermediator(statusEditable);
    }

    @Override
    public void showConnectionFrame() {
        SwingUtilities.invokeLater(() -> {
            if(connFrame!= null)
                connFrame.dispose();
            connFrame = new ConnectionSettingsFrame(intermediator);
        });
    }

    @Override
    public void showDBFrame() {
        SwingUtilities.invokeLater(() -> {
            if(dbFrame!= null)
                dbFrame.dispose();
            dbFrame = new DatabaseFrame(intermediator);
        });
    }

}
