package org.izanaar.sca.monitoring_subsystem.auxillary;


import javax.swing.*;

public class PopupMenu extends JPopupMenu{

    JMenuItem clearArea;
    JTextArea operableArea;

    public PopupMenu(JTextArea operableArea) {
        this.operableArea = operableArea;
        clearArea = new JMenuItem("Clear area");
        clearArea.addActionListener(e -> operableArea.setText(""));
        add(clearArea);
    }
}
