package org.izanaar.sca.analysis_subsystem.auxillary.crystalMathModel;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;
import java.awt.*;

public class MathModelSettingsPanel extends JPanel {

    MathModelCustomizable custMdl;
    JPanel brTtlPnl, brValPnl, blrTtlPnl, blrValPnl, mdlCnfPnl;

    int spinnerStep;

    public MathModelSettingsPanel(MathModelCustomizable customizableGraphicMathModel) throws HeadlessException {
        this.custMdl = customizableGraphicMathModel;
        JPanel corePanel = new JPanel();
        corePanel.setLayout(new BoxLayout(corePanel, BoxLayout.Y_AXIS));
        spinnerStep = 5;
        initBrTtlPnl();
        initbrValPnl();
        initBlrTtlPnl();
        initBlrValPnl();
        intMdlCnfPnl();
        initPanel();
    }

    private void intMdlCnfPnl() {
        mdlCnfPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        mdlCnfPnl.add(new JLabel("Meniscus height: "));
        mdlCnfPnl.add(initNewSpinner(10, 60, custMdl.getMenHeight(), 1, e ->
                custMdl.setMenHeight((short) (int) ((JSpinner) e.getSource()).getValue())));
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
        spinnersPanel.add(initNewSpinner(0, 255, custMdl.getCrystalCoreBrightness(), 5, e ->
                custMdl.setCrystalCoreBrightness((short) (int) ((JSpinner) e.getSource()).getValue())));
        spinnersPanel.add(initNewSpinner(0, 255, custMdl.getCrystalBorderBrightness(), 5, e ->
                custMdl.setCrystalBorderBrightness((short) (int) ((JSpinner) e.getSource()).getValue())));
        spinnersPanel.add(initNewSpinner(0, 255, custMdl.getMeniscusBrightness(), 5, e ->
                custMdl.setMeniscusBrightness((short) (int) ((JSpinner) e.getSource()).getValue())));
        spinnersPanel.add(initNewSpinner(0, 255, custMdl.getShaperBrightness(), 5, e ->
                custMdl.setShaperBrightness((short) (int) ((JSpinner) e.getSource()).getValue())));
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
        JPanel spinnersPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,33,0));
        blrValPnl.add(new JLabel("Blur:"));
        spinnersPanel.add(initNewSpinner(5, 100, custMdl.getMenUpBlur(), 10, e ->
                custMdl.setMenUpBlur((int) ((JSpinner) e.getSource()).getValue())));
        spinnersPanel.add(initNewSpinner(5, 100, custMdl.getMenDwnBlur(), 10, e ->
                custMdl.setMenDwnBlur((int) ((JSpinner) e.getSource()).getValue())));
        spinnersPanel.add(initNewSpinner(5, 100, custMdl.getCrystDownBlur(), 5, e ->
                custMdl.setCrystDownBlur((int) ((JSpinner) e.getSource()).getValue())));
        spinnersPanel.add(initNewSpinner(5, 100, custMdl.getCrystBordBlur(), 5, e ->
                custMdl.setCrystBordBlur((int) ((JSpinner) e.getSource()).getValue())));

        blrValPnl.add(spinnersPanel);
    }

    private void initPanel() {
        add(brTtlPnl);
        add(brValPnl);
        add(blrTtlPnl);
        add(blrValPnl);
        add(mdlCnfPnl);
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
