package capture_subsystem.gui;

import capture_subsystem.interfaces.CaptureGUIComponentsProvideable;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CapturePanel extends JPanel implements CaptureGUIComponentsProvideable{
    ImagePanel imagePanel;
    JTextArea outpArea;
    JScrollPane scrollPane;
    JPanel textAreaPanel;


    public CapturePanel() {
        genOutpArea();

        this.add(initImagePanel());
        this.add(textAreaPanel);
     }

    private JPanel initImagePanel() {
        JPanel imagePanelWrap = new JPanel();
        Border border = BorderFactory.createTitledBorder("Capture:");
        Dimension imagePanelDimension = new Dimension(500,375);
        imagePanel = new ImagePanel(imagePanelDimension);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.lightGray);
        imagePanel.setBackground(Color.black);
        imagePanelWrap.add(imagePanel);
        imagePanelWrap.setBorder(border);
        return imagePanelWrap;
    }

    private void genOutpArea(){
        textAreaPanel = new JPanel();
        textAreaPanel.setLayout(new BorderLayout());
        outpArea = new JTextArea(5,41);
        scrollPane = new JScrollPane(outpArea);

        outpArea.setLineWrap(true);
        Border border = BorderFactory.createTitledBorder("Capture log:");
        textAreaPanel.setBorder(border);
        textAreaPanel.add(scrollPane);
    }

    public void renewImagePanel(){
        Dimension dim = new Dimension(imagePanel.getWidth(), imagePanel.getHeight());
        imagePanel = null;
        imagePanel = new ImagePanel(dim);
    }

    @Override
    public JTextArea getOutpArea() {
        return outpArea;
    }

    @Override
    public void setImage(BufferedImage source) {
        imagePanel.setImage(source);
    }
}
