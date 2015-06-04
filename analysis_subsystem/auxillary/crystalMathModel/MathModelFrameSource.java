package analysis_subsystem.auxillary.crystalMathModel;


import capture_subsystem.auxillary.frame_sources.FrameSource;
import capture_subsystem.interfaces.FrameProvidable;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.pow;

public class MathModelFrameSource extends FrameSource implements FrameProvidable, ICustomizableGraphicMathModel {

	String alias = "Math model";
	CrystalMathModel mathModel;

	private short crBorderBr, crCoreBr, menBr, shprBr;

	private int menUpBlur, menDwnBlur, crystDownBlur, crystBordBlur;


	public MathModelFrameSource(int frameWidth, int frameHeight){
		super("Math model");
		mathModel = new CrystalMathModel(frameWidth,frameHeight);
		crBorderBr = 150;
		crCoreBr = 110;
		menBr = 200;
		shprBr = 180;

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
		builtCrystalBody(frame);
		builtMeniscus(frame);
		builtShaper(frame);
		return frame;
	}

	private void builtCrystalBody(BufferedImage frame) {
		int height = mathModel.getCrystHeight();
		for (int i = 0; i < height; i++)
				drawCrystalLine(frame,i);
	}

	private void drawCrystalLine(BufferedImage frame, int yCoord){
		int leftBorder = mathModel.getFrameWidth() - mathModel.getCrystWidth();
		int rightBorder = mathModel.getFrameWidth();
		int center = (rightBorder-leftBorder) /2 + 120;



		Color color;
		for (int i = leftBorder; i <rightBorder; i++) {
			double q = (crBorderBr - crCoreBr)/(pow(i-center,2));
			int br = (int) (pow(i-379,2)*0.0019 +120);
			if (br > 255)
				br = 255;
			color = new Color(br,br,br);
			frame.setRGB(i,yCoord,color.getRGB());
		}
	}

	private void builtMeniscus(BufferedImage frame) {
		int menWidthBeg = mathModel.getFrameWidth() - mathModel.getShapeWidth();
		int menWidthEnd = mathModel.getFrameWidth();

		for (int i = menWidthBeg; i < menWidthEnd; i++)
			drawMeniscusLine(frame, i);
	}


	private void drawMeniscusLine(BufferedImage frame, int x){
		int menUpBorder = mathModel.getFrameHeight() - mathModel.getShapeHeight() - mathModel.getMenHeight();
		int menDownBorder = menUpBorder + mathModel.getMenHeight();


		int i = 0;
		int z = (int) -(pow(x-80,2)*0.021 -420);
		if (z > menUpBorder)
			menUpBorder = z;

		Color color = new Color(menBr,menBr,menBr);
		try {
			for (i = menUpBorder; i < menDownBorder; i++)
					frame.setRGB(x,i,color.getRGB());
		}catch (RuntimeException e){
			System.out.println( i + ", " + x);
		}
	}


	private void builtShaper(BufferedImage frame) {
		Color color = new Color(shprBr,shprBr,shprBr);


		int shapeYbegin = mathModel.getFrameHeight() - mathModel.getShapeHeight();
		int shapeYend = mathModel.getFrameHeight();

		int shapeXbegin = mathModel.getFrameWidth() - mathModel.getShapeWidth();
		int shapeXend = mathModel.getFrameWidth();

		int i = 0, j = 0;
		try{
			for(i = shapeYbegin; i< shapeYend; i++)
				for(j = shapeXbegin; j< shapeXend; j++)
					frame.setRGB(j,i,color.getRGB());
		}catch (RuntimeException e){
			System.out.println( i + ", " + j);
		}

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
	public short getMeniscuslBorderBrightness() {
		return menBr;
	}

	@Override
	public short getShaperBorderBrightness() {
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
