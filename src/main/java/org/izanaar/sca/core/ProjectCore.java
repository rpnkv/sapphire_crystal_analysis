package org.izanaar.sca.core;

import org.izanaar.sca.analysis_subsystem.AnalysisFacade;
import org.izanaar.sca.capture_subsystem.CaptureFacade;
import org.izanaar.sca.core.auxillary.ShapeDrawers.JavaAPIShapeDrawer;
import org.izanaar.sca.core.auxillary.ShapeDrawers.ShapeDrawer;
import org.izanaar.sca.core.gui.CoreGUI;
import org.izanaar.sca.monitoring_subsystem.MonitoringFacade;

public class ProjectCore{
    CoreGUI gui;
    SubsystemsMediator mediator;

    public ProjectCore() {
        ShapeDrawer drawer = new JavaAPIShapeDrawer();
        CaptureFacade captureFacade = new CaptureFacade(drawer);
        AnalysisFacade analysisFacade = new AnalysisFacade(drawer, captureFacade);
        MonitoringFacade monitoringFacade = new MonitoringFacade();
        mediator = new SubsystemsMediator(captureFacade, analysisFacade, monitoringFacade);

        gui = new CoreGUI(captureFacade.getGUIPanel(),analysisFacade.getGUIPanel(),captureFacade,analysisFacade, mediator);
        analysisFacade.setActionListenable(mediator,gui);
        analysisFacade.setMeasureSaver(mediator);
        monitoringFacade.setConnectionStatusEditable(gui);
    }

    public static void main(String[] args) {
        new ProjectCore();
    }

}
