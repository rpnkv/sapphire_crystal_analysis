package analysis_subsystem.auxillary.crystalMathModel;


import capture_subsystem.auxillary.frame_sources.FrameSource;
import capture_subsystem.interfaces.FrameProvidable;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.pow;
import static java.lang.Math.sin;

public class MathModelFrameSource extends FrameSource implements FrameProvidable, ICustomizableGraphicMathModel {

	String alias = "Math model";
	CrystalMathModel mathModel;

	private short crBorderBr, crCoreBr, menBr, shprBr, bckGrndBr;

	private int menUpBlur, menDwnBlur, crystDownBlur, crystBordBlur;


	public MathModelFrameSource(int frameWidth, int frameHeight){
		super("Math model");
		mathModel = new CrystalMathModel(frameWidth,frameHeight);
		crBorderBr = 150;
		crCoreBr = 110;
		menBr = 200;
		shprBr = 180;
		bckGrndBr = 53;
		menUpBlur = menDwnBlur = crystDownBlur = crystBordBlur = 50;
	}

	@Override
	public BufferedImage getFrame() {
		return builtFrame();
	}

	@Override
	protected JPanel completeSettingsPanel() {
		return new MathModelSettingsPanel(this);
	}

	private BufferedImage builtFrame() {
		BufferedImage frame = new BufferedImage(mathModel.getFrameWidth(),
				mathModel.getFrameHeight(),BufferedImage.TYPE_3BYTE_BGR);
		int[] menUpperBorderBr =
				new int[mathModel.getFrameWidth() - (mathModel.getFrameWidth() - mathModel.getCrystWidth())];
		int[] menBottomBorderBr =
				new int[mathModel.getFrameWidth() - (mathModel.getFrameWidth() - mathModel.getShapeWidth())];

		builtBackground(frame);
		builtCrystalBody(frame, menUpperBorderBr);
		builtMeniscus(frame, menUpperBorderBr, menBottomBorderBr);
		builtShaper(frame, menBottomBorderBr);
		builtCrystalBorder(frame);
		builtMeniscusEdge(frame, menBottomBorderBr);
		return frame;
	}

	private void builtCrystalBorder(BufferedImage frame) {
		int brdXRight = mathModel.frameWidth - mathModel.crystWidth;
		int brdXLeft = (int) (brdXRight - 25*(100/(double)crystBordBlur));

		for(int i = 0; i < mathModel.getCrystHeight() + (mathModel.getMenHeight()); i++)
			drawHorizontalBlurLine(frame,brdXLeft,i,
					getBrValuesBySin(brdXLeft,brdXRight,bckGrndBr,crBorderBr));
	}

	private void builtMeniscusEdge(BufferedImage frame, int[] menBottomBorderBr) {
		int[] minYCoord = new int[mathModel.getShapeWidth() - mathModel.getCrystWidth()];
		int edgeXLeft = mathModel.getFrameWidth() - mathModel.getShapeWidth();
		int edgeXRight = mathModel.getFrameWidth() - mathModel.getCrystWidth();

		for(int i = 0; i < minYCoord.length;i++)
			minYCoord[i] = 420- (int) (pow((i), 2)  * 0.025);

		for(int i = edgeXLeft; i < edgeXRight; i++){
			drawVerticalBlurLine(frame, i, minYCoord[i - edgeXLeft],
					getBrValuesBySin(minYCoord[i - edgeXLeft], 420, menBr, menBottomBorderBr[i - edgeXLeft]));
		}


	}

	private void builtBackground(BufferedImage frame) {
		Color backgroundColor = new Color(bckGrndBr,bckGrndBr,bckGrndBr);
		for(int i = 0; i< mathModel.frameHeight ;i++)
			drawHorizontalLine(frame,0,i,mathModel.getFrameWidth(),backgroundColor);
	}

	private void builtCrystalBody(BufferedImage frame, int[] menUpperBorderBr) {
		int crystXBeg = mathModel.getFrameWidth() - mathModel.getCrystWidth();
		int crystXEnd = mathModel.getFrameWidth();
		int blurAreaHeight = (int) (mathModel.getMenHeight()/2*(double)(crystDownBlur)/100);

		int k;
		int[] coreBrValues = calcCrystalCoreBrightness(crystXBeg,crystXEnd);

		for(int i = crystXBeg; i<crystXEnd; i++)
			drawCrystalLine(frame,coreBrValues[i-crystXBeg],i, blurAreaHeight);

		for(int i = 0; i < menUpperBorderBr.length;i++)
			menUpperBorderBr[i] = menBr - (menBr - coreBrValues[i])/2;

		for(int i = crystXBeg; i<crystXEnd; i++)
			drawVerticalBlurLine(frame, i, mathModel.crystHeight - blurAreaHeight,
					getBrValuesBySin(0, blurAreaHeight + 1, coreBrValues[i - crystXBeg], menUpperBorderBr[i - crystXBeg]));

	}

	private int[] calcCrystalCoreBrightness(int crystXBeg, int crystXEnd){
		int[] brValues = new int[crystXEnd - crystXBeg];
		int crystCenterX = mathModel.frameWidth-mathModel.crystWidth/2;

		int[] half = getBrValuesBySin(crystXBeg,crystCenterX,crCoreBr,crBorderBr);

		for(int i = 0; i < brValues.length; i++)
			if(i < half.length)
				brValues[i] = half[half.length-i-1];
			else
				brValues[i] = half[i-half.length];
		return brValues;
	}

	private void drawCrystalLine(BufferedImage frame, int lineBringt, int x, int blurAreaHeight){
		Color color = new Color(lineBringt,lineBringt,lineBringt);
		int yMax = mathModel.getCrystHeight() - blurAreaHeight;
		for(int y = 0;y < yMax;y++)
			frame.setRGB(x,y,color.getRGB());
	}

	private void builtMeniscus(BufferedImage frame, int[] menUpperBorderBr, int[] menBottomBorderBr) {
		int menTopY = mathModel.getCrystHeight();
		int menBlurTopY = menTopY+ (int) (mathModel.getMenHeight()/2*(double)(menUpBlur)/100);

		int menBotY = menTopY + mathModel.getMenHeight();
		int menBlurBotY =menBotY - (int) (mathModel.getMenHeight()/2*(double)(menDwnBlur)/100);

		int menRightX = mathModel.frameWidth;
		int menLeftX = menRightX - mathModel.crystWidth;

		Color meniscusColor = new Color(menBr,menBr,menBr);
		for(int i = menBlurTopY; i < menBlurBotY;i++)
			drawHorizontalLine(frame,mathModel.frameWidth - mathModel.crystWidth,i,mathModel.getCrystWidth(),meniscusColor);

		for(int i = 0; i < menBottomBorderBr.length; i++)
			menBottomBorderBr[i] = menBr - ((menBr - shprBr)/2);

		for (int i = menLeftX; i < menRightX; i++){
			drawVerticalBlurLine(frame, i, menTopY,
					getBrValuesBySin(menTopY, menBlurTopY, menUpperBorderBr[i - menLeftX], menBr));
			drawVerticalBlurLine(frame, i, menBlurBotY, reversBrValues(
					getBrValuesBySin(menBlurBotY, menBotY, menBottomBorderBr[i - menLeftX], menBr)));
		}
	}

	private int[] reversBrValues(int[] brValues){
		int[] reversedValues = new int[brValues.length];
		for(int i = 0; i < brValues.length; i++)
			reversedValues[i] = brValues[(brValues.length-1)-i];
		return reversedValues;
	}

	private void drawHorizontalBlurLine(BufferedImage frame, int x, int y, int[] bright){
		for(int i = 0; i < bright.length;i++){
			Color color = new Color(bright[i],bright[i],bright[i]);
			frame.setRGB(x+i,y,color.getRGB());
		}
	}

	private void drawVerticalBlurLine(BufferedImage frame, int x, int y, int[] bright){
		for(int i = 0; i < bright.length;i++){
			Color color = new Color(bright[i],bright[i],bright[i]);
			frame.setRGB(x,y+i,color.getRGB());
		}
	}

	private void drawHorizontalLine(BufferedImage frame, int x, int y, int length, Color color){
		for(int i = x; i < x+length; i++)
			frame.setRGB(i,y,color.getRGB());
	}

	private void builtShaper(BufferedImage frame, int[] menBottomBorderBr) {
		int shpTopY = mathModel.getFrameHeight() - mathModel.getShapeHeight();
		int shpBlurTopY = (int) (mathModel.getFrameHeight()- mathModel.getShapeHeight()
				+ (mathModel.getMenHeight()/2*(double)(menDwnBlur)/100));

		int shpXRight = mathModel.getFrameWidth();
		int shpXLeft = shpXRight - mathModel.getShapeWidth();

		for (int i = shpXLeft; i< shpXRight;i++)
			drawVerticalBlurLine(frame, i, shpTopY,
					getBrValuesBySin(shpTopY, shpBlurTopY,
							menBottomBorderBr[i - shpXLeft], shprBr));

		Color shaperColor = new Color(shprBr,shprBr,shprBr);
		for(int i = shpBlurTopY; i < mathModel.getFrameHeight();i++)
			drawHorizontalLine(frame,mathModel.frameWidth - mathModel.shapeWidth,i,mathModel.getShapeWidth(),shaperColor);

	}

	private int[] getBrValuesBySin(int xMin,int xMax,int yMin, int yMax){
		int[] brValues = new int[xMax - xMin];

		double x1 = (double) xMin, x2 = (double)xMax, y1 = (double)yMin, y2 = (double) yMax;

		double sinStart = Math.asin(1)+Math.PI, frstSin = Math.sin(Math.asin(1)+Math.PI);
		double q = Math.PI/(x2-x1), v = 2/(y2-y1);

		for(double i = sinStart; i < sinStart+Math.PI; i+=q){
			int x = (int) ((i-sinStart)/q + x1);
			int y = (int) ((Math.sin(i)-frstSin)/v + y1);
			if(x-xMin < brValues.length)
			brValues[x-xMin] = y;
		}
		return brValues;
	}

	@Override
	public String toString() {
		return alias;
	}

	//region brightness values setters and getters
	@Override
	public void setCrystalBorderBrightness(short val) {
		crBorderBr = val;
	}

	@Override
	public void setCrystalCoreBrightness(short val) {
		crCoreBr = val;
		System.out.println("core br changed to: " + val);
	}

	@Override
	public void setMeniscusBrightness(short val) {
		menBr = val;
	}

	@Override
	public void setShaperBrightness(short val) {
		shprBr = val;
	}

	@Override
	public short getCrystalBorderBrightness() {
		return crBorderBr;
	}

	@Override
	public short getCrystalCoreBrightness() {
		return crCoreBr;
	}

	@Override
	public short getMeniscusBrightness() {
		return menBr;
	}

	@Override
	public short getShaperBrightness() {
		return shprBr;
	}

	public int getMenUpBlur() {
		return menUpBlur;
	}

	public int getMenDwnBlur() {
		return menDwnBlur;
	}

	public int getCrystBordBlur() {
		return crystBordBlur;
	}

	public int getCrystDownBlur() {
		return crystDownBlur;
	}

	public void setMenUpBlur(int menUpBlur) {
		this.menUpBlur = menUpBlur;
	}

	public void setMenDwnBlur(int menDwnBlur) {
		this.menDwnBlur = menDwnBlur;
	}

	public void setCrystDownBlur(int crystDownBlur) {
		this.crystDownBlur = crystDownBlur;
	}

	public void setCrystBordBlur(int crystBordBlur) {
		this.crystBordBlur = crystBordBlur;
	}

	//endregion
}
