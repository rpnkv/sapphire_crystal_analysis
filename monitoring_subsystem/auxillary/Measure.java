package monitoring_subsystem.auxillary;

import analysis_subsystem.auxillary.capture_regions_management.AreaDescription;

public class Measure {

    private int menHeight,xMenisk, yTopMenisk, yBotMenisk, deviation,
            yCryst, xCrystLeft, xCrystRight, yShaper, xShaperLeft, xShaperRignt;

    public Measure(int menHeight,int xMenisk, int yTopMenisk, int yBotMenisk, int deviation,
                   int yCryst, int xCrystLeft, int xCrystRight,
                   int yShaper, int xShaperLeft, int xShaperRignt) {
        this.menHeight = menHeight;
        this.xMenisk = xMenisk;
        this.yTopMenisk = yTopMenisk;
        this.yBotMenisk = yBotMenisk;
        this.deviation = deviation;
        this.yCryst = yCryst;
        this.xCrystLeft = xCrystLeft;
        this.xCrystRight = xCrystRight;
        this.yShaper = yShaper;
        this.xShaperLeft = xShaperLeft;
        this.xShaperRignt = xShaperRignt;
    }

    public Measure(int meniscus,int deviation, AreaDescription meniskDescr,
                   AreaDescription deviationDescr, AreaDescription shaperDescr) {
        this.deviation = deviation;
        this.menHeight = meniscus;

        this.xMenisk = meniskDescr.getBegin().x;
        this.yTopMenisk = meniskDescr.getBegin().y+meniskDescr.getLenght();
        this.yBotMenisk = meniskDescr.getBegin().y;

        this.yCryst = deviationDescr.getBegin().y;
        this.xCrystLeft = deviationDescr.getBegin().x;
        this.xCrystRight = deviationDescr.getBegin().x + deviationDescr.getLenght();

        this.yShaper = shaperDescr.getBegin().y;
        this.xShaperLeft = shaperDescr.getBegin().x;
        this.xShaperRignt =  shaperDescr.getBegin().x + shaperDescr.getLenght();
    }

    public int getMenHeight() {
        return menHeight;
    }

    public int getxMenisk() {
        return xMenisk;
    }

    public int getyTopMenisk() {
        return yTopMenisk;
    }

    public int getyBotMenisk() {
        return yBotMenisk;
    }

    public int getDeviation() {
        return deviation;
    }

    public int getyCryst() {
        return yCryst;
    }

    public int getxCrystLeft() {
        return xCrystLeft;
    }

    public int getxCrystRight() {
        return xCrystRight;
    }

    public int getyShaper() {
        return yShaper;
    }

    public int getxShaperLeft() {
        return xShaperLeft;
    }

    public int getxShaperRignt() {
        return xShaperRignt;
    }
}
