package analysis_subsystem.auxillary;

import java.awt.*;

public class AreaDescription {

    private AreaTypes areaType;
    protected Point begin;
    protected int lenght;
    protected int width;

    public AreaDescription(AreaTypes areaType, Point begin, int lenght, int width) {
        this.areaType = areaType;
        this.begin = begin;
        this.lenght = lenght;
        this.width = width;
    }

    public AreaDescription(AreaTypes areaType, Point begin) {
        this.areaType = areaType;
        this.begin = begin;
    }

    public AreaTypes getAreaType() {
        return areaType;
    }

    public Point getBegin() {
        return begin;
    }

    public int getLenght() {
        return lenght;
    }

    public int getWidth() {
        return width;
    }
}
