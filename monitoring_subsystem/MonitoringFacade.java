package monitoring_subsystem;

import analysis_subsystem.interfaces.ConnectionStatusEditable;
import monitoring_subsystem.auxillary.DataBaseIntermediator;
import monitoring_subsystem.gui.ConnectionSettingsFrame;
import monitoring_subsystem.gui.DatabaseFrame;
import monitoring_subsystem.interfaces.MonitoringSubsystemCommonInterface;

import javax.swing.*;

public class MonitoringFacade implements MonitoringSubsystemCommonInterface {
    ConnectionSettingsFrame connFrame;
    DatabaseFrame dbFrame;
    DataBaseIntermediator intermediator;


    public void setConnectionStatusEditable(ConnectionStatusEditable statusEditable){
        intermediator = new DataBaseIntermediator(statusEditable, this);
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

    @Override
    public void append(String text) {
        try{
            dbFrame.getOutpArea().append(text);
        }catch (NullPointerException e){
            System.out.println("View component isn't initialized. Your message:\n" + text);
        }

    }
}
