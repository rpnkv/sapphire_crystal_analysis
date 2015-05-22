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

    private final String devDecName = "deviation decorator", menDecName = "meniscus decorator";

    public VideoFlowDecorator(VideoFlowDecorable videoFlowDecorable, ShapeDrawer drawer) {
        this.videoFlowDecorable = videoFlowDecorable;
        this.drawer = drawer;
    }

    @Override
    public void setCaptureCoord(AreaTypes type, Point beginPoint, int end, int width) {
        switch (type){
            case Meniscus:
                addMeniscusDecorator(beginPoint,end,width);
                break;
            case Deviation:
                addDeviationDecorator(beginPoint,end,width);
                break;
            case Erase:
                eraseDecorators();
                break;
        }
    }

    private void eraseDecorators() {
        videoFlowDecorable.deleteDecorator(devDecName);
        videoFlowDecorable.deleteDecorator(menDecName);
    }

    private void addDeviationDecorator(Point beginPoint, int end, int width) {
        deviationDecorator = new DrawCaptureAreaImageDecorator(devDecName,drawer,
                new CaptureArea(beginPoint,end,width, CaptureArea.Kind.Deviation));
        if(deviationDecorator != null)
            videoFlowDecorable.deleteDecorator(devDecName);
        videoFlowDecorable.addDecorator(deviationDecorator);
    }

    private void addMeniscusDecorator(Point beginPoint, int end, int width) {
        meniscusDecorator = new DrawCaptureAreaImageDecorator(menDecName,drawer,
                new CaptureArea(beginPoint,end,width, CaptureArea.Kind.Meniscus));
        if(meniscusDecorator != null)
            videoFlowDecorable.deleteDecorator(menDecName);
        videoFlowDecorable.addDecorator(meniscusDecorator);
    }
}
