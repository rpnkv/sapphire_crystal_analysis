package org.izanaar.sca.analysis_subsystem.interfaces;


import org.izanaar.sca.capture_subsystem.interfaces.VideoFlowDecorable;
import org.izanaar.sca.core.interfaces.GUIPanelProvidable;

public interface AnalysisSubsystemCommonInterface extends
        GUIPanelProvidable, AnalysisPerformable, ImagePanelListenersProvideable {

        void setDecorable(VideoFlowDecorable decorable);

}
