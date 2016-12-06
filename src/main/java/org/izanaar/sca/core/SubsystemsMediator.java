package org.izanaar.sca.core;

import org.izanaar.sca.analysis_subsystem.interfaces.AnalysisPerformable;
import org.izanaar.sca.analysis_subsystem.interfaces.AnalysisSubsystemCommonInterface;
import org.izanaar.sca.capture_subsystem.interfaces.CapturePerformable;
import org.izanaar.sca.capture_subsystem.interfaces.CaptureSubsystemCommonInterface;
import org.izanaar.sca.capture_subsystem.interfaces.FrameProvidable;
import org.izanaar.sca.capture_subsystem.interfaces.ImagePanelActionListenable;
import org.izanaar.sca.monitoring_subsystem.MonitoringFacade;
import org.izanaar.sca.monitoring_subsystem.auxillary.Measure;
import org.izanaar.sca.monitoring_subsystem.interfaces.ConnectionFramesProvidable;
import org.izanaar.sca.monitoring_subsystem.interfaces.MeasureSavable;
import org.izanaar.sca.monitoring_subsystem.interfaces.MonitoringSubsystemCommonInterface;

import javax.swing.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

public class SubsystemsMediator implements ImagePanelActionListenable, AnalysisPerformable,
        CapturePerformable, FrameProvidable, ConnectionFramesProvidable, MeasureSavable {

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
    public void setAnalysisParams() {
      analysisFacade.setAnalysisParams();
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

    @Override
    public boolean saveMeasure(Measure measure) {
        return monitoringFacade.saveMeasure(measure);
    }

    @Override
    public boolean isReadyToAnalysisLogging() {
        return monitoringFacade.isReadyToAnalysisLogging();
    }

}
