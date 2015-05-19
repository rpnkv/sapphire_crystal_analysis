package analysis_subsystem.crystalMathModel;

/**
 * Created by ierus on 4/3/15.
 */
public class MathModelCustomizer implements Runnable {

    ICustomizableGraphicMathModel mathModelVideoSource;

    public MathModelCustomizer(ICustomizableGraphicMathModel mathModelVideoSource) {
        this.mathModelVideoSource = mathModelVideoSource;
    }

    @Override
    public void run() {
        new MathModelSettingsPanel(mathModelVideoSource);
    }
}
