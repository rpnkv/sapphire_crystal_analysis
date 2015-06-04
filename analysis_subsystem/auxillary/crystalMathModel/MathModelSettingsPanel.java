package analysis_subsystem.auxillary.crystalMathModel;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;
import java.awt.*;
import java.awt.event.ActionListener;

public class MathModelSettingsPanel extends JPanel {

    ICustomizableGraphicMathModel customizableGraphicMathModel;
    JPanel brTtlPnl, brValPnl, blrTtlPnl, blrValPnl;

    int spinnerStep;

    public MathModelSettingsPanel(ICustomizableGraphicMathModel customizableGraphicMathModel) throws HeadlessException {
        this.customizableGraphicMathModel = customizableGraphicMathModel;
        JPanel corePanel = new JPanel();
        corePanel.setLayout(new BoxLayout(corePanel, BoxLayout.Y_AXIS));
        spinnerStep = 5;
        initBrTtlPnl();
        initbrValPnl();
        initBlrTtlPnl();
        initBlrValPnl();
        initPanel();
    }

    private void initBrTtlPnl() {
        brTtlPnl = new JPanel();
        brTtlPnl.setLayout(new FlowLayout(FlowLayout.RIGHT));
        brTtlPnl.add(new JLabel("           Core"));
        brTtlPnl.add(new JLabel("         Border"));
        brTtlPnl.add(new JLabel("       Meniscus"));
        brTtlPnl.add(new JLabel("         Shaper"));

    }

    private void initbrValPnl() {
        brValPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel spinnersPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,20,0));

        brValPnl.add(new JLabel("Brightness:"));
        spinnersPanel.add(initNewSpinner(0,255,customizableGraphicMathModel.getCrystalCoreBrightness(),5,e ->
                customizableGraphicMathModel.setCrystalCoreBrightness((short) (int)((JSpinner) e.getSource()).getValue())));
        spinnersPanel.add(initNewSpinner(0,255,customizableGraphicMathModel.getCrystalBorderBrightness(),5,e ->
                customizableGraphicMathModel.setCrystalBorderBrightness((short) (int)((JSpinner) e.getSource()).getValue())));
        spinnersPanel.add(initNewSpinner(0,255,customizableGraphicMathModel.getMeniscuslBorderBrightness(),5,e ->
                customizableGraphicMathModel.setMeniscusBrightness((short) (int)((JSpinner) e.getSource()).getValue())));
        spinnersPanel.add(initNewSpinner(0, 255, customizableGraphicMathModel.getShaperBorderBrightness(), 5, e ->
                customizableGraphicMathModel.setShaperBrightness((short) (int) ((JSpinner) e.getSource()).getValue())));
        brValPnl.add(spinnersPanel);
    }

    private void initBlrTtlPnl() {
        blrTtlPnl = new JPanel();
        blrTtlPnl.setLayout(new FlowLayout(FlowLayout.RIGHT));
        blrTtlPnl.add(new JLabel("       Men up"));
        blrTtlPnl.add(new JLabel("     Men down"));
        blrTtlPnl.add(new JLabel("   Cryst down"));
        blrTtlPnl.add(new JLabel("    Cryst brd"));

    }

    private void initBlrValPnl() {
        blrValPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel spinnersPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,20,0));
        blrValPnl.add(new JLabel("Blur:"));
        spinnersPanel.add(initNewSpinner(0, 100,customizableGraphicMathModel.getMenUpBlur(),10,e ->
                customizableGraphicMathModel.setMenUpBlur((int) ((JSpinner) e.getSource()).getValue())));
        spinnersPanel.add(initNewSpinner(0, 100,customizableGraphicMathModel.getMenDwnBlur(),10,e ->
                customizableGraphicMathModel.setMenDwnBlur((int) ((JSpinner) e.getSource()).getValue())));
        spinnersPanel.add(initNewSpinner(0, 100,customizableGraphicMathModel.getCrystBordBlur(),5,e ->
                customizableGraphicMathModel.setCrystBordBlur((int) ((JSpinner) e.getSource()).getValue())));
        spinnersPanel.add(initNewSpinner(0, 100, customizableGraphicMathModel.getCrystBordBlur(), 5, e ->
                customizableGraphicMathModel.setCrystBordBlur((int) ((JSpinner) e.getSource()).getValue())));
        blrValPnl.add(spinnersPanel);
    }

    private void initPanel() {
        add(brTtlPnl);
        add(brValPnl);
        add(blrTtlPnl);
        add(blrValPnl);
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.setSize(380,150);
        this.setVisible(true);
    }

    private JSpinner initNewSpinner(int minVal, int maxVal,int currVal, int step, ChangeListener changeListener){
        JSpinner newSpinner = new JSpinner(
                new SpinnerNumberModel(currVal,minVal,maxVal,step));
        JFormattedTextField ampmspin=((JSpinner.DefaultEditor)newSpinner.getEditor()).getTextField(); ampmspin.setEditable(false);
        JComponent comp = newSpinner.getEditor();
        JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
        DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
        formatter.setCommitsOnValidEdit(true);
        newSpinner.addChangeListener(changeListener);
        return newSpinner;
    }
}
