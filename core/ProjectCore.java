package core;

import analysis_subsystem.AnalysisFacade;
import capture_subsystem.CaptureFacade;
import core.gui.CoreGUI;
import monitoring_subsystem.MonitoringFacade;

public class ProjectCore {

    CoreGUI gui;
    CaptureFacade captureFacade;
    AnalysisFacade analysisFacade;
    MonitoringFacade monitoringFacade;

    public ProjectCore() {
        captureFacade = new CaptureFacade();
        analysisFacade = new AnalysisFacade();
        monitoringFacade = new MonitoringFacade();

        gui = new CoreGUI(this,captureFacade.getGUIPanel(),analysisFacade.getGUIPanel());
    }

    public static void main(String[] args) {
        new ProjectCore();
    }

}
