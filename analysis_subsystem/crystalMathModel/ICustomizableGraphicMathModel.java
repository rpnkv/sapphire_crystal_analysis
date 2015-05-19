package analysis_subsystem.crystalMathModel;

/**
 * Created by ierus on 4/3/15.
 */
public interface ICustomizableGraphicMathModel {

    void setCrystalBorderBrightness(short val);
    void setCrystalCoreBrightness(short val);
    void setMeniscusBrightness(short val);
    void setShaperBrightness(short val);

    short getCrystalBorderBrightness();
    short getCrystalCoreBrightness();
    short getMeniscuslBorderBrightness();
    short getShaperBorderBrightness();
}
