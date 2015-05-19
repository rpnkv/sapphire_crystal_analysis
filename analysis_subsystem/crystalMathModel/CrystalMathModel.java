package analysis_subsystem.crystalMathModel;

/**
 * Created by ierus on 3/12/15.
 */
public class CrystalMathModel {

    int frameHeight;
    int frameWidth;

    int menHeight,crystHeight,crystWidth, shapeWidth, shapeHeight;

    public CrystalMathModel(int frameWidth, int frameHeight) {
        this.frameHeight = frameHeight;
        this.frameWidth = frameWidth;

        menHeight = frameHeight/12;
        crystWidth = (int) (frameWidth - (frameWidth/5.3));

        shapeHeight = frameHeight/8;
        shapeWidth = crystWidth + menHeight;

        crystHeight = frameHeight - (menHeight+ shapeHeight);
        crystWidth = (int) (frameWidth - (frameWidth/5.3));
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public int getMenHeight() {
        return menHeight;
    }

    public int getCrystHeight() {
        return crystHeight;
    }

    public int getCrystWidth() {
        return crystWidth;
    }

    public int getShapeWidth() {
        return shapeWidth;
    }

    public int getShapeHeight() {
        return shapeHeight;
    }
}
