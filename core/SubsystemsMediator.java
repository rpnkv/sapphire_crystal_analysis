package core;

import analysis_subsystem.interfaces.AnalysisPerformable;
import analysis_subsystem.interfaces.AnalysisSubsystemCommonInterface;
import capture_subsystem.interfaces.CapturePerformable;
import capture_subsystem.interfaces.CaptureSubsystemCommonInterface;
import capture_subsystem.interfaces.FrameProvidable;
import capture_subsystem.interfaces.ImagePanelActionListenable;
import monitoring_subsystem.MonitoringFacade;
import monitoring_subsystem.interfaces.ConnectionFramesProvidable;
import monitoring_subsystem.interfaces.MonitoringSubsystemCommonInterface;

import javax.swing.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

public class SubsystemsMediator implements ImagePanelActionListenable, AnalysisPerformable,
        CapturePerformable, FrameProvidable, ConnectionFramesProvidable {

    CaptureSubsystemCommonInterface captureFacade;
    AnalysisSubsystemCommonInterface analysisFacade;
    MonitoringSubsystemCommonInterface monitoringFacade;

    public SubsystemsMediator(CaptureSubsystemCommonInterface captureFacade,
                              AnalysisSubsystemCommonInterface analysisFacade, MonitoringFacade monitoringFacade) {
        this.captureFacade = captureFacade;
        this.analysisFacade = analysisFacade;
        this.monitoringFacade = monitoringFacade;
        analysisFacade.setDecorable(captureFacade);
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
    public void performIterativeAnalysis() {
        analysisFacade.performIterativeAnalysis();
    }

    @Override
    public void performAnalysis() {
        analysisFacade.performAnalysis();
    }

    @Override
    public void stopAnalysis() {
        analysisFacade.stopAnalysis();
    }

    @Override
    public void setDefaultCaptureAreas() {
        analysisFacade.setDefaultCaptureAreas();
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

    @Override
    public BufferedImage getFrame() {
        return captureFacade.getFrame();
    }


    @Override
    public void showConnectionFrame() {
        monitoringFacade.showConnectionFrame();
    }

    @Override
    public void showDBFrame() {
        monitoringFacade.showDBFrame();
    }
}
