package org.izanaar.sca.core.auxillary.ShapeDrawers;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class ShapeDrawer{
    public abstract void init(BufferedImage source);
    public abstract void drawLine(int x1, int y1, int x2, int y2);
    public abstract void drawLine(int x1, int y1, int x2, int y2, Color color);
    public abstract void drawLine(int x1, int y1, int x2, int y2, int width, Color color);
    public abstract void drawText(int x, int y, String s, Color color);
    public abstract void dispose();
}
