package org.izanaar.sca.analysis_subsystem.auxillary.capture_regions_management;


import org.izanaar.sca.capture_subsystem.auxillary.image_decorators.ImageDecorator;
import org.izanaar.sca.core.auxillary.ShapeDrawers.ShapeDrawer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawCaptureAreaImageDecorator extends ImageDecorator {
    AreaDescription areaDescription;
    ShapeDrawer drawer;

    protected DrawCaptureAreaImageDecorator(String alias, ShapeDrawer drawer, AreaDescription areaDescription) {
        super(alias);
        this.drawer = drawer;
        this.areaDescription = areaDescription;
    }

    @Override
    public void setImage(BufferedImage source) {
       drawer.init(source);
        switch (areaDescription.getAreaType()){
            case Deviation:
                drawer.drawLine(areaDescription.getBegin().x,areaDescription.getBegin().y,areaDescription.getLenght()+areaDescription.getBegin().x
                        ,areaDescription.getBegin().y,areaDescription.getWidth(), Color.blue);
                break;
            case Meniscus:
                drawer.drawLine(areaDescription.getBegin().x,areaDescription.getBegin().y,areaDescription.getBegin().x,areaDescription.getLenght()+areaDescription.getBegin().y,
                        areaDescription.getWidth(), Color.red);
                break;
            case Shaper:
                drawer.drawLine(areaDescription.getBegin().x,areaDescription.getBegin().y,areaDescription.getLenght()+areaDescription.getBegin().x
                        ,areaDescription.getBegin().y,areaDescription.getWidth(), Color.green);
                break;
        }
        drawer.dispose();
        getInnerDecorator().setImage(source);
    }
}
