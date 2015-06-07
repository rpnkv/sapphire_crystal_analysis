package analysis_subsystem.auxillary.crystalMathModel;

public interface MathModelCustomizable {

    void setCrystalBorderBrightness(short val);
    void setCrystalCoreBrightness(short val);
    void setMeniscusBrightness(short val);
    void setShaperBrightness(short val);
    void setMenUpBlur(int menUpBlur);
    void setMenDwnBlur(int menDwnBlur);
    void setCrystDownBlur(int crystDownBlur);
    void setCrystBordBlur(int crystBordBlur);
    void setMenHeight(int menHeight);
    void setFramesAmountBeforeChange(int framesAmountBeforeChange);
    void setChange(boolean change);

    short getCrystalBorderBrightness();
    short getCrystalCoreBrightness();
    short getMeniscusBrightness();
    short getShaperBrightness();
    int getMenUpBlur();
    int getMenDwnBlur();
    int getCrystBordBlur();
    int getCrystDownBlur();
    int getMenHeight();
    int getFramesAmountBeforeChange();
    boolean getChange();
}
