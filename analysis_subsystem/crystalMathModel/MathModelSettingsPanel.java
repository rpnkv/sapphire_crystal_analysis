package analysis_subsystem.crystalMathModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;
import java.awt.*;

/**
 * Created by ierus on 3/14/15.
 */
public class MathModelSettingsPanel extends JPanel {

    ICustomizableGraphicMathModel customizableGraphicMathModel;

    //JSpinner crCoreBrightSp, crBrdBrightSp, menBrightSp, shprBrightSp;

    int spinnerStep;

    public MathModelSettingsPanel(ICustomizableGraphicMathModel customizableGraphicMathModel) throws HeadlessException {
        this.customizableGraphicMathModel = customizableGraphicMathModel;
        JPanel corePanel = new JPanel();
        corePanel.setLayout(new BoxLayout(corePanel, BoxLayout.Y_AXIS));
        spinnerStep = 5;
        corePanel.add(initCrCoreBrightPanel());
        corePanel.add(initCrBrdBrightPanel());
        corePanel.add(initMenBrightPanel());
        corePanel.add(initShprBrightPanel());

        this.setSize(380,170);
        this.add(corePanel);
        this.setVisible(true);
    }

    private JPanel initCrCoreBrightPanel() {
        JPanel crCoreBrightPanel = new JPanel();
        crCoreBrightPanel.add(new JLabel("Crystal core brightness: "));
        crCoreBrightPanel.add(initCrystalCoreBrightSpinner());
        return crCoreBrightPanel;
    }

    private JPanel initCrBrdBrightPanel() {
        JPanel crBrdBrightPanel = new JPanel();
        crBrdBrightPanel.add(new JLabel("Crystal border brightness: "));
        crBrdBrightPanel.add(initCrystalBrdBrightSpinner());
        return crBrdBrightPanel;
    }

    private JPanel initMenBrightPanel() {
        JPanel menBrightPanel = new JPanel();
        menBrightPanel.add(new JLabel("Meniscus brightness: "));
        menBrightPanel.add(initMenBrghtSpinner());
        return menBrightPanel;
    }

    private JPanel initShprBrightPanel() {
        JPanel shprBrightPanel = new JPanel();
        shprBrightPanel.add(new JLabel("Shaper brightness: "));
        shprBrightPanel.add(initShprBrghtSpinner());
        return shprBrightPanel;
    }

    public JSpinner initCrystalCoreBrightSpinner() {
        JSpinner crCoreBrightSp = new JSpinner(
                new SpinnerNumberModel(customizableGraphicMathModel.getCrystalCoreBrightness(),0,255,spinnerStep));
        JFormattedTextField ampmspin=((JSpinner.DefaultEditor)crCoreBrightSp.getEditor()).getTextField();
        ampmspin.setEditable(false);

        JComponent comp = crCoreBrightSp.getEditor();
        JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
        DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
        formatter.setCommitsOnValidEdit(true);
        crCoreBrightSp.addChangeListener(e -> {
            int value = (int) crCoreBrightSp.getValue();
            customizableGraphicMathModel.setCrystalCoreBrightness((short) value);
        });

        return crCoreBrightSp ;
    }

    public JSpinner initCrystalBrdBrightSpinner() {
        JSpinner crBrdBrightSp = new JSpinner(
                new SpinnerNumberModel(customizableGraphicMathModel.getCrystalBorderBrightness(),0,255,spinnerStep));
        JFormattedTextField ampmspin=((JSpinner.DefaultEditor)crBrdBrightSp.getEditor()).getTextField(); ampmspin.setEditable(false);

        JComponent comp = crBrdBrightSp.getEditor();
        JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
        DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
        formatter.setCommitsOnValidEdit(true);
        crBrdBrightSp.addChangeListener(e -> {
            int value = (int) crBrdBrightSp.getValue();
            customizableGraphicMathModel.setCrystalBorderBrightness((short) value);
        });

        return crBrdBrightSp;
    }

    public JSpinner initMenBrghtSpinner() {
        JSpinner menBrightSp = new JSpinner(
                new SpinnerNumberModel(customizableGraphicMathModel.getMeniscuslBorderBrightness(),0,255,spinnerStep));
        JFormattedTextField ampmspin=((JSpinner.DefaultEditor)menBrightSp.getEditor()).getTextField(); ampmspin.setEditable(false);

        JComponent comp = menBrightSp.getEditor();
        JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
        DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
        formatter.setCommitsOnValidEdit(true);
        menBrightSp.addChangeListener(e -> {
            int value = (int) menBrightSp.getValue();
            customizableGraphicMathModel.setMeniscusBrightness((short) value);
        });

        return menBrightSp;
    }

    public JSpinner initShprBrghtSpinner() {
        JSpinner shprBrightSp = new JSpinner(
                new SpinnerNumberModel(customizableGraphicMathModel.getShaperBorderBrightness(),0,255,spinnerStep));
        JFormattedTextField ampmspin=((JSpinner.DefaultEditor)shprBrightSp.getEditor()).getTextField(); ampmspin.setEditable(false);

        JComponent comp = shprBrightSp.getEditor();
        JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
        DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
        formatter.setCommitsOnValidEdit(true);
        shprBrightSp.addChangeListener(e -> {
            int value = (int) shprBrightSp.getValue();
            customizableGraphicMathModel.setShaperBrightness((short) value);
        });
        return shprBrightSp;
    }
}
