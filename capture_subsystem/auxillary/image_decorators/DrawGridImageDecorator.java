package capture_subsystem.auxillary.image_decorators;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by ierus on 5/20/15.
 */
public class DrawGridImageDecorator extends ImageDecorator {

    public DrawGridImageDecorator() {
        super("draw grid image decorator");
    }

    @Override
    public void setImage(BufferedImage source) {
        drawXGrid(source);
        drawYGrid(source);
        drawText(5,15,"0", Color.red,source);
        innerDecorator.setImage(source);
    }

    private void drawXGrid(BufferedImage cpdImg) {
        Graphics2D g2d = cpdImg.createGraphics();
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.red);

        g2d.drawLine(15, 15, cpdImg.getWidth() - 15, 15);
        g2d.drawLine(cpdImg.getWidth() - 18, 18, cpdImg.getWidth() - 15, 15);
        g2d.drawLine(cpdImg.getWidth() - 18, 12, cpdImg.getWidth() - 15, 15);
        g2d.dispose();
        for (int i = 50; i < cpdImg.getWidth(); i += 50) {
            drawShpLnX(15, cpdImg.getHeight() - 15, i, 8, 3, Color.black, cpdImg);
            drawText(i - 17, 14, String.valueOf(i), Color.red, cpdImg);
        }
        drawText(cpdImg.getWidth() - 14, 14, "X", Color.red, cpdImg);

    }


    private void drawYGrid(BufferedImage cpdImg) {
        Graphics2D g2d = cpdImg.createGraphics();
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.red);


        g2d.drawLine(15, 15, 15, cpdImg.getHeight() - 15);
        g2d.drawLine(18, cpdImg.getHeight() - 18, 15, cpdImg.getHeight() - 15);
        g2d.drawLine(12, cpdImg.getHeight() - 18, 15, cpdImg.getHeight() - 15);
        g2d.dispose();
        for (int i = 50; i < cpdImg.getHeight(); i += 50) {
            drawText(1, i, String.valueOf(i), Color.red, cpdImg);
            drawShpLnY(15, cpdImg.getWidth() - 15, i, 8, 3, Color.black, cpdImg);
        }
        drawText(2, cpdImg.getHeight() - 14, "Y", Color.red, cpdImg);
    }

    private void drawText(int x, int y, String s, Color color, BufferedImage cpdImg) {
        Graphics2D g2d = cpdImg.createGraphics();
        g2d.setColor(color);
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        g2d.drawString(s,x,y);
        g2d.dispose();

    }

    private void drawShpLnY(int x0, int x1, int y,
                            int interval, int length, Color color,
                            BufferedImage diagramShape){
        if(x0!=x1){
            int xBeg,xEnd;
            if(x0 < x1){
                xBeg = x0;
                xEnd = x1;
            }else {
                xBeg = x1;
                xEnd = x0;
            }

            Graphics2D g2d = diagramShape.createGraphics();
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(color);

            for(int i = xBeg; i <xEnd; i+=(length+interval))
                g2d.drawLine(i,y,i+length,y);

            g2d.dispose();
        }
    }

    private void drawShpLnX(int y0, int y1, int x,
                            int interval, int length, Color color,
                            BufferedImage diagramShape){
        if(y0!=y1) {
            int yBeg, yEnd;
            if (y0 < y1) {
                yBeg = y0;
                yEnd = y1;
            } else {
                yBeg = y1;
                yEnd = y0;
            }

            Graphics2D g2d = diagramShape.createGraphics();
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(color);


            for(int i = yBeg; i <yEnd; i+=(length+interval))
                g2d.drawLine(x,i,x,i+length);
            g2d.dispose();
        }
    }
}
