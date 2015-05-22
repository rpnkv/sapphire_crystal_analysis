package analysis_subsystem.auxillary;

import analysis_subsystem.interfaces.CaptureCoordEditable;
import analysis_subsystem.interfaces.CaptureRegionsViewable;
import capture_subsystem.interfaces.ImagePanelActionListenable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static analysis_subsystem.auxillary.AreaTypes.Deviation;
import static analysis_subsystem.auxillary.AreaTypes.Meniscus;

public class RegionSettingManager implements MouseListener,MouseWheelListener{

    public final int MENISCUS_BEGIN = 1, DEVIATION_BEGIN = 2, MENISCUS_END = 3, DEVIATION_END =4;
    public final int MENISCUS_WIDTH = 5, DEVIATION_WIDTH = 6;

    double widthFactor, heightFactor;
    private Point menBegin;
    private int menEnd;
    private int menWidth;

    private Point devBegin;
    private int devEnd;
    private int devWidth;

    ImagePanelActionListenable actionListenable;
    JPopupMenu popupMenu;

    ArrayList<CaptureCoordEditable> captureCoordEditables;
    CaptureRegionsViewable coordViewer;

    int state = 0;

    public RegionSettingManager(ImagePanelActionListenable actionListenable, CaptureRegionsViewable coordViewer) {
        this.actionListenable = actionListenable;
        actionListenable.addMouseListener(this);
        popupMenu = new PopupMenu(this);
        actionListenable.setComponentPopupMenu(popupMenu);
        captureCoordEditables = new ArrayList<>();
        widthFactor = 640.0/488.0;
        heightFactor = 480.0/366.0;
        this.coordViewer = coordViewer;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (state){
            case MENISCUS_BEGIN :
                menBegin = new Point((int) (e.getX()*widthFactor), (int) (e.getY()* heightFactor));
                state = MENISCUS_END;
                break;
            case DEVIATION_BEGIN :
                devBegin = new Point((int) (e.getX()*widthFactor), (int) (e.getY()*heightFactor));
                state = DEVIATION_END;
                break;
            case MENISCUS_END :
                menEnd = (int) (e.getY()*heightFactor - menBegin.y);
                state = MENISCUS_WIDTH;
                actionListenable.addMouseWheelListener(this);
                break;
            case DEVIATION_END:
                devEnd = (int) ((e.getX() * widthFactor) - devBegin.x);
                state = DEVIATION_WIDTH;
                actionListenable.addMouseWheelListener(this);
                break;
            case MENISCUS_WIDTH :
                actionListenable.removeMouseWheelListener(this);
                state = 0;
                informListeners(Meniscus,menBegin,menEnd,menWidth);
                break;
            case DEVIATION_WIDTH :
                actionListenable.removeMouseWheelListener(this);
                state = 0;
                informListeners(AreaTypes.Deviation,devBegin,devEnd,devWidth);
                break;
        }
        if(state != 0)
        updateCoordinates();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int rotation = (int)e.getPreciseWheelRotation();
        switch (state){
            case MENISCUS_WIDTH :
                if (rotation < 0 && menWidth <2 || menWidth ==30)
                    return;
                menWidth += rotation;
                break;
            case DEVIATION_WIDTH :
                if (rotation < 0 && devWidth <2|| devWidth ==30)
                    return;
                devWidth += rotation;
                break;
        }
        if(state != 0)
            updateCoordinates();
    }

    public void setState(int state) {
        this.state = state;
        updateCoordinates();
    }

    protected void informListeners(AreaTypes type, Point begin, int end, int width){
        captureCoordEditables.forEach((listener) -> listener.setCaptureCoord(type,begin,end,width));
    }

    public void addCaptureCoordEditable(CaptureCoordEditable captureCoordEditable) {
        this.captureCoordEditables.add(captureCoordEditable);
    }

    private void updateCoordinates(){
        String text = "Capture coordinates: ";

        text += "meniscus " + collectCoord(Meniscus,true) + "--" + collectCoord(Meniscus,false) + ", ";
        text += collectWidth(Meniscus) +"; ";

        text += "deviation " + collectCoord(Deviation,true) + "--" + collectCoord(Deviation,false) + ", ";
        text += collectWidth(Deviation) +".";

        text += " Now editing: " + getEditableState() +".";

        coordViewer.updateCaptureRegions(text);
    }

    private String collectCoord(AreaTypes type, boolean edge){
        Point currentPoint;
        switch (type){
            case Meniscus :
                if(edge)
                    currentPoint = menBegin;
                else
                try {
                    currentPoint = new Point(menBegin.x,menEnd);
                }catch (NullPointerException e){
                    currentPoint = null;
                }
                break;
            case Deviation:
                if(edge)
                    currentPoint = devBegin;
                else
                    try {
                        currentPoint = new Point(devBegin.x,devEnd);
                    }catch (NullPointerException e){
                        currentPoint = null;
                    }
                break;
            default: currentPoint = null;
        }

        DecimalFormat coordinatesFormatter = new DecimalFormat("000");
        String coord = "[";
        if (currentPoint == null)
            coord = "[xxx,xxx]";
        else {
            coord += coordinatesFormatter.format(currentPoint.x)+ ",";
            coord += coordinatesFormatter.format(currentPoint.y)+"]";
        }

        return coord;
    }

    private String collectWidth(AreaTypes type){
        String width = "width:";
        DecimalFormat cwidthFormatter = new DecimalFormat("00");
        switch (type){
            case Meniscus:
                if(menWidth == 0)
                    width +="xx";
                else
                    width +=cwidthFormatter.format(menWidth);
                break;
            case Deviation:
                if(devWidth == 0)
                    width +="xx";
                else
                    width +=cwidthFormatter.format(devWidth);
                break;
        }
        return width;
    }

    private String getEditableState(){
        String text;
        switch (state){
            case MENISCUS_BEGIN : text = "meniscus begin";
                break;
            case MENISCUS_END :text = "meniscus end";
                break;
            case DEVIATION_BEGIN :text = "deviation begin";
                break;
            case DEVIATION_END :text = "deviation end";
                break;
            case MENISCUS_WIDTH :text = "meniscus width";
                break;
            case DEVIATION_WIDTH :text = "deviation width";
                break;
            default: text = "null";
        }
        return text;
    }
    //region unused implemented methods
    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    //endregion
}
