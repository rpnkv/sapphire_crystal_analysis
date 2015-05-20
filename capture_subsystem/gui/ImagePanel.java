package capture_subsystem.gui;

import capture_subsystem.interfaces.ImageSetable;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static org.bytedeco.javacpp.opencv_core.CvSize;

/**
 * Created by ierus on 3/11/15.
 */
public class ImagePanel extends JPanel implements ImageSetable{

    private BufferedImage image;

    public ImagePanel(int width, int height) {
        Dimension size = new Dimension(width,height);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
        fillByTrash(width,height);
    }

    private void fillByTrash(int width, int height) {
        CvSize size = new CvSize();
        size.height(height);
        size.width(width);
        setImage(new BufferedImage(640, 480, BufferedImage.TYPE_BYTE_BINARY));
    }

    public ImagePanel(Dimension panelDimension) {
        this(panelDimension.width, panelDimension.height);
    }

    public void setImage(BufferedImage source) {
        Image tmp = source.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
        BufferedImage dimg = createStub();

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        image = dimg;
        repaint();
    }

    protected BufferedImage createStub(){
        return new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image,0,0,null);
    }
}
