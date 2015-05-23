package analysis_subsystem.auxillary;

import analysis_subsystem.interfaces.CaptureCoordEditable;
import capture_subsystem.interfaces.VideoFlowDecorable;
import core.auxillary.ShapeDrawers.ShapeDrawer;

import java.awt.*;

public class VideoFlowDecorator implements CaptureCoordEditable{

    VideoFlowDecorable videoFlowDecorable;
    ShapeDrawer drawer;

    DrawCaptureAreaImageDecorator meniscusDecorator;
    DrawCaptureAreaImageDecorator deviationDecorator;
    DrawCaptureAreaImageDecorator shaperDecorator;

    private final
    String devDecName = "deviation decorator", menDecName = "meniscus decorator", shpDecName = "shaper decorator";

    public VideoFlowDecorator(VideoFlowDecorable videoFlowDecorable, ShapeDrawer drawer) {
        this.videoFlowDecorable = videoFlowDecorable;
        this.drawer = drawer;
    }

    private void eraseDecorators() {
        videoFlowDecorable.deleteDecorator(devDecName);
        videoFlowDecorable.deleteDecorator(menDecName);
    }

    private void addDeviationDecorator(AreaDescription areaDescription) {
        boolean decoratorIsSet = (deviationDecorator != null);
        deviationDecorator = new DrawCaptureAreaImageDecorator(devDecName,drawer,areaDescription);
        if(decoratorIsSet)
            videoFlowDecorable.deleteDecorator(devDecName);
        videoFlowDecorable.addDecorator(deviationDecorator);
    }

    private void addMeniscusDecorator(AreaDescription areaDescription) {
        boolean decoratorIsSet = (meniscusDecorator != null);
        meniscusDecorator = new DrawCaptureAreaImageDecorator(menDecName,drawer,areaDescription);
        if(decoratorIsSet)
            videoFlowDecorable.deleteDecorator(menDecName);
        videoFlowDecorable.addDecorator(meniscusDecorator);
    }

    @Override
    public void setCaptureCoord(AreaDescription areaDescription) {
        switch (areaDescription.getAreaType()){
            case Meniscus:
                addMeniscusDecorator(areaDescription);
                break;
            case Deviation:
                addDeviationDecorator(areaDescription);
                break;
            case Shaper:
                addShaperDecorator(areaDescription);
            case Erase:
                eraseDecorators();
                break;
        }
    }

    private void addShaperDecorator(AreaDescription areaDescription) {
        boolean decoratorIsSet = (shaperDecorator != null);
        shaperDecorator = new DrawCaptureAreaImageDecorator(shpDecName,drawer,areaDescription);
        if(decoratorIsSet)
            videoFlowDecorable.deleteDecorator(shpDecName);
        videoFlowDecorable.addDecorator(shaperDecorator);
    }
}
