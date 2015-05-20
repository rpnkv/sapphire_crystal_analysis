package core;

import analysis_subsystem.interfaces.AnalysisPerformable;
import analysis_subsystem.interfaces.AnalysisSubsystemCommonInterface;
import capture_subsystem.interfaces.CapturePerformable;
import capture_subsystem.interfaces.CaptureSubsystemCommonInterface;
import capture_subsystem.interfaces.ImagePanelActionListenable;

import javax.swing.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelListener;

public class SubsystemsMediator implements ImagePanelActionListenable, AnalysisPerformable, CapturePerformable {

    CaptureSubsystemCommonInterface captureFacade;
    AnalysisSubsystemCommonInterface analysisFacade;

    public SubsystemsMediator(CaptureSubsystemCommonInterface captureFacade,
                              AnalysisSubsystemCommonInterface analysisFacade) {
        this.captureFacade = captureFacade;
        this.analysisFacade = analysisFacade;

    }

    @Override
    public void addMouseListener(MouseListener mouseListener) {
        captureFacade.addMouseListener(mouseListener);
    }

    @Override
    public void addMouseWheelListener(MouseWheelListener wheelListener) {
        captureFacade.addMouseWheelListener(wheelListener);
    }

    @Override
    public void removeMouseWheelListener(MouseWheelListener wheelListener) {
        captureFacade.removeMouseWheelListener(wheelListener);
    }

    @Override
    public void setComponentPopupMenu(JPopupMenu popupMenu) {
        captureFacade.setComponentPopupMenu(popupMenu);
    }

    @Override
    public void performInstantAnalysis() {
        analysisFacade.performInstantAnalysis();
    }

    @Override
    public void performAnalysisIteration() {
        analysisFacade.performAnalysisIteration();
    }

    @Override
    public void performAnalysis() {
        analysisFacade.performAnalysis();
    }

    @Override
    public void startCapture() {
        captureFacade.startCapture();
    }

    @Override
    public void stopCapture() {
        captureFacade.stopCapture();
    }

    @Override
    public void showSettings() {
        captureFacade.showSettings();
    }
}
