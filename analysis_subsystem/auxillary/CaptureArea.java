package analysis_subsystem.auxillary;

import java.awt.*;

public class CaptureArea {
    public enum Kind{
        Meniscus, Deviation
    }

    Point start;
    int length, width;
    Kind kind;

    public CaptureArea(Point start, int length, int width, Kind kind) {
        this.start = start;
        this.length = length;
        this.width = width;
        this.kind = kind;
    }

    public Point getStart() {
        return start;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public Kind getKind() {
        return kind;
    }
}
