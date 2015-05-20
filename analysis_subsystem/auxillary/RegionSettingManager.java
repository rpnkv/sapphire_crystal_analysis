package analysis_subsystem.auxillary;

import capture_subsystem.auxillary.PopupMenu;
import capture_subsystem.interfaces.ImagePanelActionListeneable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class RegionSettingManager implements MouseListener,MouseWheelListener{

    final int MENISCUS_BEGIN = 1, DEVIATION_BEGIN = 2, MENISCUS_END = 3, DEVIATION_END =4;
    final int MENISCUS_WIDTH = 5, DEVIATION_WIDTH = 6;

    Point menBegin;
    int menEnd;
    int menWidth;

    Point devBegin;
    int devEnd;
    int devWidth;

    ImagePanelActionListeneable actionListeneable;
    JPopupMenu popupMenu;

    int state = 0;

    public RegionSettingManager(ImagePanelActionListeneable actionListeneable) {
        this.actionListeneable = actionListeneable;
        actionListeneable.addMouseWheelListener(this);
        actionListeneable.addMouseListener(this);
        popupMenu = new PopupMenu(this);
        actionListeneable.setComponentPopupMenu(popupMenu);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(e.toString());
    }

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

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        System.out.println(e.toString());
    }

    public void initMeniscusCoordSetup() {
        state = MENISCUS_BEGIN;
    }

    public void initDeviationCoordSetup() {
        state = DEVIATION_BEGIN;
    }

    public void initMeniscusWidthEdit() {
        state = MENISCUS_WIDTH;
    }

    public void initDeviationWidthEdit() {
        state = DEVIATION_WIDTH;
    }
}
