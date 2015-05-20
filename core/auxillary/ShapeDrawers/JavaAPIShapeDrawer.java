package core.auxillary.ShapeDrawers;

import java.awt.*;
import java.awt.image.BufferedImage;

public class JavaAPIShapeDrawer extends ShapeDrawer {

    Graphics2D g2d;


    @Override
    public void init(BufferedImage source) {
        g2d = source.createGraphics();
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        g2d.drawLine(x1, y1, x2, y2);
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2, Color color) {
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(color);
        g2d.drawLine(x1, y1, x2, y2);
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2, int width, Color color) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(width));
        g2d.drawLine(x1, y1, x2, y2);
    }

    @Override
    public void drawText(int x, int y, String s, Color color) {
        g2d.setColor(color);
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        g2d.drawString(s, x, y);
    }

    @Override
    public void dispose() {
        g2d.dispose();
    }
}
