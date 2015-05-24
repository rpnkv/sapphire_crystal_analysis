package analysis_subsystem.auxillary.analysis_result_visualisation;

import capture_subsystem.gui.ImagePanel;
import analysis_subsystem.interfaces.GraphDrawable;
import core.auxillary.ShapeDrawers.ShapeDrawer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

//выполняет визуализацию графиков
public class DiagramPanel extends ImagePanel implements GraphDrawable{
	BufferedImage defaultDiagramShape, diagramShape; //изображение - шаблон графика
							//координатные оси, сетка и т.п.

	private boolean drawSGrid; //флаг прорисовки мелкой сетки
	private boolean drawLGrid; //флаг прорисовки крупной сетки

	private int lGrInt,lGrLngth,sGrInt,sGrLngth;//длинна линий и интервал между ними на координатной сетке

	private int coordDelta;//величина, на которую
	ShapeDrawer drawer;

	public DiagramPanel(int width, int height, ShapeDrawer drawer) {
		super(width, height);
		Dimension size = new Dimension(width,height);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);
		setBackground(Color.gray);
		initGridValues();
		coordDelta = 2;
		this.drawer = drawer;
		redrawDiagramShape();
		setImage(defaultDiagramShape);
	}

	private void initGridValues() {
		//инициализация величин значений сетки
		drawSGrid = false;
		drawLGrid = true;
		lGrInt = 3;
		lGrLngth = 3;
		sGrInt = 5;
		sGrLngth = 2;
	}

	//region grid draw functions
	private void redrawDiagramShape() {
		defaultDiagramShape = createStub();
		if(drawLGrid)
			drawLargeGrid();
		if(drawSGrid)
			drawSmallGrid();
		drawX();
		drawY();

	}

	private void drawLargeGrid() {
		for(int y = 25; y <=250; y+=25)
			drawShpLnY(25, 475, y, lGrInt, lGrLngth, Color.white);
		for(int x = 25; x <=475; x+=25)
			drawShpLnX(20, 275, x, lGrInt, lGrLngth, Color.white);
	}

	private void drawSmallGrid() {
		for(int y = 37; y <=274; y+=25)
			drawShpLnY(25, 475, y, sGrInt, sGrLngth, Color.white);
		for(int x = 37; x <=475; x+=25)
			drawShpLnX(20, 275, x, sGrInt, sGrLngth, Color.white);
	}

	private void drawShpLnY(int x0, int x1, int y,
							int interval, int length, Color color){
		if(x0!=x1){
			int xBeg,xEnd;
			if(x0 < x1){
				xBeg = x0;
				xEnd = x1;
			}else {
				xBeg = x1;
				xEnd = x0;
			}

			Graphics2D g2d = defaultDiagramShape.createGraphics();
			g2d.setStroke(new BasicStroke(1));
			g2d.setColor(color);

			for(int i = xBeg; i <xEnd; i+=(length+interval))
				g2d.drawLine(i,y,i+length,y);

			g2d.dispose();

		}
	}

	private void drawShpLnX(int y0, int y1, int x,
							int interval, int length, Color color){
		if(y0!=y1){
			int yBeg,yEnd;
			if(y0 < y1){
				yBeg = y0;
				yEnd = y1;
			}else {
				yBeg = y1;
				yEnd = y0;
			}

			Graphics2D g2d = defaultDiagramShape.createGraphics();
			g2d.setStroke(new BasicStroke(1));
			g2d.setColor(color);


			for(int i = yBeg; i <yEnd; i+=(length+interval))
				g2d.drawLine(x,i,x,i+length);
			g2d.dispose();
		}
	}

	private void drawX(){
		//draws X line
		int dY = coordDelta;

		Graphics2D g2d = defaultDiagramShape.createGraphics();
		g2d.setColor(Color.RED);
		g2d.setStroke(new BasicStroke(2));


		g2d.drawLine(25, 275, 488, 275);
		g2d.drawLine(485, 275 + dY, 488, 275);
		g2d.drawLine(485, 275 - dY, 488, 275);

		for(int i = 25; i <=475; i+=25){
			g2d.drawLine(i,275+dY,i,275-dY);
		}
		g2d.dispose();
		drawXDescr();
	}

	private void drawY() {
		//draws Y line

		int dX = coordDelta;
		Graphics2D g2d = defaultDiagramShape.createGraphics();
		g2d.setColor(Color.RED);
		g2d.setStroke(new BasicStroke(2));
		g2d.drawLine(25, 275, 25, 10);

		g2d.drawLine(25 + dX, 13, 25, 10);
		g2d.drawLine(25 - dX, 13, 25, 10);



		for (int i = 25; i <= 250; i+=25){
			g2d.drawLine(25 + dX, i, 25 - dX, i);
		}
		g2d.dispose();
		drawYDescr();
	}

	private void drawXDescr(){
		Graphics2D g2d = defaultDiagramShape.createGraphics();
		g2d.setColor(Color.white);
		g2d.setFont(new Font("Arial", Font.PLAIN, 10));
		g2d.drawString("px.#", 475, 270);

		g2d.drawString("0", 15, 275);
		g2d.dispose();
	}



	private void drawYDescr(){
		Graphics2D g2d = defaultDiagramShape.createGraphics();
		g2d.setColor(Color.white);
		g2d.setFont(new Font("Arial", Font.PLAIN, 10));

		g2d.drawString("brightness",3, 9);
		int j = 250;
		for (Integer i = 25; i <= 250; i+=25,j-=25)
			g2d.drawString(String.valueOf(i),1, j);
		g2d.dispose();
	}

	@Override
	public void drawGraphs(ArrayList<GraphInfo> graphSInfo) {
		diagramShape = deepCopy(defaultDiagramShape);
		graphSInfo.forEach(graph -> {
			int[] brightnessValues = graph.getBrightnessValues();
			drawGraphLine(brightnessValues, graph.getColor());
			viewGraphCoord(graph.getBeginPixel(), graph.getColor(), brightnessValues.length);
		});
		setImage(diagramShape);
	}

	@Override
	public void drawGraph(GraphInfo graphInfo) {
		diagramShape = deepCopy(defaultDiagramShape);
		int[] brightnessValues = graphInfo.getBrightnessValues();
		//redrawDiagramShape();
		drawGraphLine(brightnessValues, graphInfo.getColor());
		viewGraphCoord(graphInfo.getBeginPixel(), graphInfo.getColor(),brightnessValues.length);
		setImage(diagramShape);
	}

	private void viewGraphCoord(int beginPixel, Color color, int valsNum) {
		int textCoord = 0;
		if(color.equals(Color.RED))
			textCoord = 290;
		if(color.equals(Color.BLUE))
			textCoord = 305;
				Graphics2D g2d = diagramShape.createGraphics();
		g2d.setFont(new Font("Arial", Font.PLAIN, 10));
		g2d.setColor(color);

		int v = 450, n = valsNum, f = 25, k = 18,s = beginPixel;
		double q = v/n;
		StringBuilder val = new StringBuilder();
		for(int i = 0; i <= k; i++){
			switch (i%2){
				case 0 :
					calcVal(q,i,val,f,s);
					g2d.drawString(val.toString(),i*f+f-9,textCoord);
					break;
				case 1 :
					calcVal(q,i,val,f,s);
					g2d.drawString(val.toString(),i*f+f-9,textCoord);
					break;
				default :
					System.out.println("unknown case");
			}
		}

		g2d.dispose();
	}

	private boolean calcVal(double q, int i, StringBuilder val, int s, int f){
		Double x = (s*i)/q+f;
		boolean valid = kIsValid(x);
		val.setLength(0);
		if(!valid)
			val.append("*" + String.valueOf(x.intValue()));
		else
			val.append(String.valueOf(x.intValue()));
		return valid;
	}

	private boolean kIsValid(double k) {
		double t;
		t = k - (int)k;
		if(t == 0)
			return true;
		else
			return false;
	}

	private void drawGraphLine(int[] brightnessValues, Color color){
		Point prevPoint = new Point(25,275-brightnessValues[0]);
		Point newPoint = new Point();
		Graphics2D g2d = diagramShape.createGraphics();
		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(color);


		int step = 450/brightnessValues.length,iterator = 1;

		if(brightnessValues.length <= 450)
			try{
			for(int i = 25+step; i < 475; i+=step,iterator++){
				if(iterator < brightnessValues.length)
				newPoint = new Point(i, 275-brightnessValues[iterator]);
				else
				return;
				g2d.drawLine(prevPoint.x,prevPoint.y, newPoint.x,newPoint.y);
				prevPoint = newPoint;
			}}
			catch (Exception e){
				System.out.println("X1:" + prevPoint.x + ", Y1:" + prevPoint.y +
						"; X2: " + newPoint.x + ", Y2:" + newPoint.y);
			}
		g2d.dispose();
	}

	private BufferedImage deepCopy(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
}
