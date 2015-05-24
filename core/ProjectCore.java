package core;

import analysis_subsystem.AnalysisFacade;
import capture_subsystem.CaptureFacade;
import core.auxillary.ShapeDrawers.JavaAPIShapeDrawer;
import core.auxillary.ShapeDrawers.ShapeDrawer;
import core.gui.CoreGUI;

public class ProjectCore{
    CoreGUI gui;
    SubsystemsMediator mediator;

    public ProjectCore() {
        ShapeDrawer drawer = new JavaAPIShapeDrawer();
        CaptureFacade captureFacade = new CaptureFacade(drawer);
        AnalysisFacade analysisFacade = new AnalysisFacade(drawer, captureFacade);
        mediator = new SubsystemsMediator(captureFacade, analysisFacade);

        gui = new CoreGUI(captureFacade.getGUIPanel(),analysisFacade.getGUIPanel(),captureFacade,analysisFacade);
        analysisFacade.setActionListenable(mediator,gui);
    }

    public static void main(String[] args) {
        new ProjectCore();
    }

}
