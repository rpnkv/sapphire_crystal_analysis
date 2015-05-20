package core;

import analysis_subsystem.interfaces.AnalysisPerformable;
import analysis_subsystem.interfaces.AnalysisSubsystemCommonInterface;
import capture_subsystem.interfaces.CapturePerformable;
import capture_subsystem.interfaces.CaptureSubsystemCommonInterface;
import capture_subsystem.interfaces.ImagePanelActionListeneable;

import javax.swing.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelListener;

public class SubsystemsMediator implements ImagePanelActionListeneable, AnalysisPerformable, CapturePerformable {

    CaptureSubsystemCommonInterface captureFacade;
    AnalysisSubsystemCommonInterface analysisFacade;

    CapturePerformable capturePerformable;
    AnalysisPerformable analysisPerformable;

    public SubsystemsMediator(CaptureSubsystemCommonInterface captureFacade, AnalysisSubsystemCommonInterface analysisFacade,
                              CapturePerformable capturePerformable, AnalysisPerformable analysisPerformable) {
        this.captureFacade = captureFacade;
        this.analysisFacade = analysisFacade;
        this.capturePerformable = capturePerformable;
        this.analysisPerformable = analysisPerformable;
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
        analysisPerformable.performInstantAnalysis();
    }

    @Override
    public void performAnalysisIteration() {
        analysisPerformable.performAnalysisIteration();
    }

    @Override
    public void performAnalysis() {
        analysisPerformable.performAnalysis();
    }

    @Override
    public void startCapture() {
        capturePerformable.startCapture();
    }

    @Override
    public void stopCapture() {
        capturePerformable.stopCapture();
    }

    @Override
    public void showSettings() {
        capturePerformable.showSettings();
    }
}
