package org.izanaar.sca.capture_subsystem.auxillary.frame_sources.image;

import javax.swing.*;

public class ImgSettingsPanel extends JPanel {
    JTextField txtPath;
    JButton btnLoad;
    public ImgSettingsPanel(ImageFrameSource frameSource) {
        txtPath = new JTextField(21);
        txtPath.setText(frameSource.getPath());
        btnLoad = new JButton("load");
        btnLoad.addActionListener(e -> frameSource.setPath(txtPath.getText()));
        add(new JLabel("Path to image:"));
        add(txtPath);
        add(btnLoad);
    }
}
