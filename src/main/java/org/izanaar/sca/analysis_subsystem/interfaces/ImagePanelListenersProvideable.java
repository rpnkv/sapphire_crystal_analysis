package org.izanaar.sca.analysis_subsystem.interfaces;


import org.izanaar.sca.capture_subsystem.interfaces.ImagePanelActionListenable;

public interface ImagePanelListenersProvideable {
    void setActionListenable(ImagePanelActionListenable actionListeneable, CaptureRegionsViewable regionsViewable);
}
