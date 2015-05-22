package analysis_subsystem.interfaces;

import capture_subsystem.interfaces.ImagePanelActionListenable;

public interface ImagePanelListenersProvideable {
    void setActionListenable(ImagePanelActionListenable actionListeneable, CaptureRegionsViewable regionsViewable);
}
