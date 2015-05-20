package core;

import analysis_subsystem.AnalysisFacade;
import analysis_subsystem.interfaces.AnalysisSubsystemCommonInterface;
import capture_subsystem.CaptureFacade;
import capture_subsystem.interfaces.CaptureSubsystemCommonInterface;
import capture_subsystem.interfaces.ImagePanelActionListeneable;
import core.gui.CoreGUI;
import monitoring_subsystem.MonitoringFacade;

import javax.swing.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelListener;

public class ProjectCore{
    CoreGUI gui;
    SubsystemsMediator mediator;


    MonitoringFacade monitoringFacade;

    public ProjectCore(CaptureSubsystemCommonInterface captureFacade, AnalysisSubsystemCommonInterface analysisFacade) {
        mediator = new SubsystemsMediator(captureFacade,analysisFacade, captureFacade, analysisFacade);
        monitoringFacade = new MonitoringFacade();

        gui = new CoreGUI(this,captureFacade.getGUIPanel(),analysisFacade.getGUIPanel(),captureFacade,analysisFacade);
        analysisFacade.setActionListeneable(mediator);
    }

    public static void main(String[] args) {
        new ProjectCore(new CaptureFacade(), new AnalysisFacade());
    }

}
