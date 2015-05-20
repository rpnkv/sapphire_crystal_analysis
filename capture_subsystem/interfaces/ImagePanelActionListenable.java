package capture_subsystem.interfaces;

import javax.swing.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelListener;

public interface ImagePanelActionListenable {
    void addMouseListener(MouseListener mouseListener);
    void addMouseWheelListener(MouseWheelListener wheelListener);
    void removeMouseWheelListener(MouseWheelListener wheelListener);
    void setComponentPopupMenu(JPopupMenu popupMenu);
}
