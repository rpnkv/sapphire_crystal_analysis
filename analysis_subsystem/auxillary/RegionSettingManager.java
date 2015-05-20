package analysis_subsystem.auxillary;

import analysis_subsystem.interfaces.CaptureCoordEditable;
import capture_subsystem.interfaces.ImagePanelActionListenable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

public class RegionSettingManager implements MouseListener,MouseWheelListener{

    final int MENISCUS_BEGIN = 1, DEVIATION_BEGIN = 2, MENISCUS_END = 3, DEVIATION_END =4;
    final int MENISCUS_WIDTH = 5, DEVIATION_WIDTH = 6;

    private Point menBegin;
    private int menEnd;
    private int menWidth;

    private Point devBegin;
    private int devEnd;
    private int devWidth;

    ImagePanelActionListenable actionListenable;
    JPopupMenu popupMenu;

    ArrayList<CaptureCoordEditable> captureCoordEditables;

    int state = 0;

    public RegionSettingManager(ImagePanelActionListenable actionListenable) {
        this.actionListenable = actionListenable;
        actionListenable.addMouseListener(this);
        popupMenu = new PopupMenu(this);
        actionListenable.setComponentPopupMenu(popupMenu);
        captureCoordEditables = new ArrayList<>();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(e.toString());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        System.out.println(e.toString());
    }

    void setState(int state) {
        this.state = state;
    }

    public void addCaptureCoordEditable(CaptureCoordEditable captureCoordEditable) {
        this.captureCoordEditables.add(captureCoordEditable);
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
