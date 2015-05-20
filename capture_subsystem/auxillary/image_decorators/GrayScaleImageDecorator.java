package capture_subsystem.auxillary.image_decorators;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GrayScaleImageDecorator extends ImageDecorator {
    public GrayScaleImageDecorator() {
        super("gray scale image decorator");
    }

    @Override
    public void setImage(BufferedImage source) {
        convertImageToGrayScale(source);
        innerDecorator.setImage(source);
    }

    private void convertImageToGrayScale(BufferedImage source) {
        int width = source.getWidth();
        int height = source.getHeight();


        double gmRed = 0.299, gmGreen = 0.587, gmBlue = 0.014;

        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                Color c = new Color(source.getRGB(j, i));
                int red = (int)(c.getRed() * gmRed);
                int green = (int)(c.getGreen() * gmGreen);
                int blue = (int)(c.getBlue() * gmBlue);
                Color newColor = new Color(red+green+blue,
                        red+green+blue,red+green+blue);
                source.setRGB(j,i,newColor.getRGB());
            }
        }
    }
}
