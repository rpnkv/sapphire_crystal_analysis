package analysis_subsystem.auxillary.analysis_result_visualisation;

import java.awt.*;


public class GraphInfo {

    int[] brightnessValues;
    int beginPixel;
    Color color;
    short width;

    public GraphInfo(int[] brightnessValues, int beginPixel, Color color) {
        this.brightnessValues = brightnessValues;
        this.beginPixel = beginPixel;
        this.color = color;
    }

    public GraphInfo(int[] brightnessValues, int beginPixel, Color color, short width) {
        this.brightnessValues = brightnessValues;
        this.beginPixel = beginPixel;
        this.color = color;
        this.width = width;
    }

    public int[] getBrightnessValues() {
        return brightnessValues;
    }

    public int getBeginPixel() {
        return beginPixel;
    }

    public Color getColor() {
        return color;
    }

    public short getWidth() {
        return width;
    }

    public void setBrightnessValues(int[] brightnessValues) {
        this.brightnessValues = brightnessValues;
    }

    public void setBeginPixel(short beginPixel) {
        this.beginPixel = beginPixel;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setWidth(short width) {
        this.width = width;
    }
}

