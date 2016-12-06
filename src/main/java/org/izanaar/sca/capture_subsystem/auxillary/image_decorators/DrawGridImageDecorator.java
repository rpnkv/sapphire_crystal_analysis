package org.izanaar.sca.capture_subsystem.auxillary.image_decorators;


import org.izanaar.sca.core.auxillary.ShapeDrawers.ShapeDrawer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawGridImageDecorator extends ImageDecorator {

    ShapeDrawer drawer;

    public DrawGridImageDecorator(ShapeDrawer shapeDrawer) {
        super("draw grid image decorator");
        drawer = shapeDrawer;
    }

    @Override
    public void setImage(BufferedImage source) {
        drawer.init(source);
        drawXGrid(source);
        drawYGrid(source);
        drawer.drawText(5,15,"0", Color.red);
        drawer.dispose();
        innerDecorator.setImage(source);
    }

    private void drawXGrid(BufferedImage source) {

        drawer.drawLine(15, 15, source.getWidth() - 15, 15,2,Color.red);
        drawer.drawLine(source.getWidth() - 18, 18, source.getWidth() - 15, 15);
        drawer.drawLine(source.getWidth() - 18, 12, source.getWidth() - 15, 15);
        for (int i = 50; i < source.getWidth(); i += 50) {
            drawShpLnX(15, source.getHeight() - 15, i, 8, 3, Color.black);
            drawer.drawText(i - 17, 14, String.valueOf(i), Color.red);
        }
        drawer.drawText(source.getWidth() - 14, 14, "X", Color.red);
    }


    private void drawYGrid(BufferedImage cpdImg) {

        drawer.drawLine(15, 15, 15, cpdImg.getHeight() - 15,2,Color.red);
        drawer.drawLine(18, cpdImg.getHeight() - 18, 15, cpdImg.getHeight() - 15);
        drawer.drawLine(12, cpdImg.getHeight() - 18, 15, cpdImg.getHeight() - 15);
        for (int i = 50; i < cpdImg.getHeight(); i += 50) {
            drawer.drawText(1, i, String.valueOf(i), Color.red);
            drawShpLnY(15, cpdImg.getWidth() - 15, i, 8, 3, Color.black);
        }
        drawer. drawText(2, cpdImg.getHeight() - 14, "Y", Color.red);
    }

    private void drawShpLnY(int x0, int x1, int y,
                            int interval, int length, Color color){
        if(x0!=x1){
            int xBeg,xEnd;
            if(x0 < x1){
                xBeg = x0;
                xEnd = x1;
            }else {
                xBeg = x1;
                xEnd = x0;
            }
            for(int i = xBeg; i <xEnd; i+=(length+interval))
                drawer.drawLine(i,y,i+length,y,color);
        }
    }

    private void drawShpLnX(int y0, int y1, int x,
                            int interval, int length, Color color){
        if(y0!=y1) {
            int yBeg, yEnd;
            if (y0 < y1) {
                yBeg = y0;
                yEnd = y1;
            } else {
                yBeg = y1;
                yEnd = y0;
            }
            for(int i = yBeg; i <yEnd; i+=(length+interval))
                drawer.drawLine(x,i,x,i+length,color);
        }
    }
}
