package analysis_subsystem.auxillary;

import capture_subsystem.auxillary.image_decorators.ImageDecorator;
import core.auxillary.ShapeDrawers.ShapeDrawer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawCaptureAreaImageDecorator extends ImageDecorator {
    CaptureArea area;
    ShapeDrawer drawer;

    protected DrawCaptureAreaImageDecorator(String alias, ShapeDrawer drawer, CaptureArea area) {
        super(alias);
        this.drawer = drawer;
        this.area = area;
    }

    @Override
    public void setImage(BufferedImage source) {
       drawer.init(source);
        switch (area.getKind()){
            case Deviation:
                drawer.drawLine(area.getStart().x,area.getStart().y,area.getLength()+area.getStart().x
                        ,area.getStart().y,area.getWidth(), Color.blue);
                break;
            case Meniscus:
                drawer.drawLine(area.getStart().x,area.getStart().y,area.getStart().x,area.getLength()+area.getStart().y,
                        area.getWidth(), Color.red);
                break;
        }
        drawer.dispose();
        getInnerDecorator().setImage(source);
    }
}
