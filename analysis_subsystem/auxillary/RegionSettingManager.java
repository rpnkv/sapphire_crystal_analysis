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

    public final int MENISCUS_BEGIN = 1, DEVIATION_BEGIN = 2, MENISCUS_END = 3, DEVIATION_END =4, SHAPER_BEGIN = 5;
    public final int SHAPER_END = 6, MENISCUS_WIDTH = 7, DEVIATION_WIDTH = 8, SHAPER_WIDTH = 9;

    private double widthFactor, heightFactor;
    private AreaDescription meniscusInf;
    private AreaDescription deviationInf;
    private AreaDescription shaperInf;

    private ImagePanelActionListenable actionListenable;
    private JPopupMenu popupMenu;

    private ArrayList<CaptureCoordEditable> captureCoordEditables;
    private CaptureRegionsViewable coordViewer;

    private int state = 0;

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
                meniscusInf = new AreaDescription(AreaTypes.Meniscus, new Point(
                        (int) (e.getX()*widthFactor), (int) (e.getY()* heightFactor)));
                state = MENISCUS_END;
                break;
            case DEVIATION_BEGIN :
                deviationInf = new AreaDescription(AreaTypes.Deviation,
                        new Point((int) (e.getX()*widthFactor), (int) (e.getY()*heightFactor)));
                state = DEVIATION_END;
                break;
            case SHAPER_BEGIN :
                shaperInf = new AreaDescription(AreaTypes.Shaper,
                        new Point((int) (e.getX()*widthFactor), (int) (e.getY()*heightFactor)));
                state = DEVIATION_END;
                break;
            case MENISCUS_END :
                meniscusInf.lenght = (int) (e.getY()*heightFactor - meniscusInf.begin.y);
                state = MENISCUS_WIDTH;
                actionListenable.addMouseWheelListener(this);
                break;
            case DEVIATION_END:
                deviationInf.lenght = (int) ((e.getX() * widthFactor) - deviationInf.begin.x);
                state = DEVIATION_WIDTH;
                actionListenable.addMouseWheelListener(this);
                break;
            case SHAPER_END :
                shaperInf.lenght = (int) ((e.getX() * widthFactor) - shaperInf.begin.x);
                state =SHAPER_WIDTH;
                actionListenable.addMouseWheelListener(this);
                break;
            case MENISCUS_WIDTH :
                actionListenable.removeMouseWheelListener(this);
                state = 0;
                informListeners(meniscusInf);
                break;
            case DEVIATION_WIDTH :
                actionListenable.removeMouseWheelListener(this);
                state = 0;
                informListeners(deviationInf);
                break;
            case SHAPER_WIDTH:
                actionListenable.removeMouseWheelListener(this);
                state = 0;
                informListeners(shaperInf);
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
                if (rotation < 0 && meniscusInf.width <2 || meniscusInf.width ==30)
                    return;
                meniscusInf.width += rotation;
                break;
            case DEVIATION_WIDTH :
                if (rotation < 0 && deviationInf.width <2|| deviationInf.width ==30)
                    return;
                deviationInf.width += rotation;
                break;
            case SHAPER_WIDTH :
                if (rotation < 0 && shaperInf.width <2|| shaperInf.width ==30)
                    return;
                shaperInf.width += rotation;
                break;
        }
        if(state != 0)
            updateCoordinates();
    }

    public void setState(int state) {
        this.state = state;
        updateCoordinates();
    }

    protected void informListeners(AreaDescription areaDescription){
        captureCoordEditables.forEach((listener) -> listener.setCaptureCoord(areaDescription));
    }

    public void addCaptureCoordEditable(CaptureCoordEditable captureCoordEditable) {
        this.captureCoordEditables.add(captureCoordEditable);
    }

    private void updateCoordinates(){
        String text = "Capture coordinates: ";

        text += "meniscus" + collectCoord(Meniscus) +": ";
        text += collectWidth(Meniscus) +"; ";

        text += "deviation" + collectCoord(Deviation) + ": ";
        text += collectWidth(Deviation) +";";

        text += "shaper" + collectCoord(Deviation) + ": ";
        text += collectWidth(Deviation) +";";

        text +=  getEditableState() +".";

        coordViewer.updateCaptureRegions(text);
    }

    private String collectCoord(AreaTypes type){
        Point start = null;
        Point end = null;
        switch (type){
            case Meniscus :
                try {
                    start = new Point(meniscusInf.begin);
                }catch (NullPointerException ignored){}
                try{
                    end = new Point(meniscusInf.begin.x,meniscusInf.lenght);
                }catch (NullPointerException ignored){}
                break;
            case Deviation:
                try {
                    start = new Point(deviationInf.begin);
                }catch (NullPointerException ignored){}
                try{
                    end = new Point(deviationInf.begin.y,deviationInf.lenght);
                }catch (NullPointerException ignored){}
                break;
            case Shaper:
                try {
                    start = new Point(shaperInf.begin);
                }catch (NullPointerException ignored){}
                try{
                    end = new Point(shaperInf.begin.y,shaperInf.lenght);
                }catch (NullPointerException ignored){}
                break;
        }

        DecimalFormat coordinatesFormatter = new DecimalFormat("000");
        String coord = "[";
        switch (type){
            case Meniscus:
                if(start == null)
                    coord+="xxx;xxx:xxx]";
                else
                if (end == null)
                    coord+= coordinatesFormatter.format(start.x)+";"+coordinatesFormatter.format(start.y) +":xxx]";
                else
                coord+= coordinatesFormatter.format(start.x)+";"+coordinatesFormatter.format(start.y) +":" +
                        coordinatesFormatter.format(end.y) +"]";
                break;
            case Deviation :
                if(start == null)
                    coord+="xxx:xxx;xxx]";
                else
                if (end == null)
                    coord+= coordinatesFormatter.format(start.x)+":xxx;" + coordinatesFormatter.format(start.y);
                else
                    coord+= coordinatesFormatter.format(start.x)+":"+coordinatesFormatter.format(end.x) +";" +
                            coordinatesFormatter.format(start.x) +"]";
                break;
            case Shaper:
                if(start == null)
                    coord+="xxx:xxx;xxx]";
                else
                if (end == null)
                    coord+= coordinatesFormatter.format(start.x)+":xxx;" + coordinatesFormatter.format(start.y);
                else
                    coord+= coordinatesFormatter.format(start.x)+":"+coordinatesFormatter.format(end.x) +";" +
                            coordinatesFormatter.format(start.x) +"]";
                break;
        }

        return coord;
    }

    private String collectWidth(AreaTypes type){
        String width = "";
        DecimalFormat widthFormatter = new DecimalFormat("00");
        switch (type){
            case Meniscus:
                if(meniscusInf.width == 0)
                    width +="xx";
                else
                    width +=widthFormatter.format(meniscusInf.width);
                break;
            case Deviation:
                if(deviationInf.width == 0)
                    width +="xx";
                else
                    width +=widthFormatter.format(meniscusInf.width);
                break;
            case Shaper:
                if(shaperInf.width == 0)
                    width +="xx";
                else
                    width +=widthFormatter.format(shaperInf.width);
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
