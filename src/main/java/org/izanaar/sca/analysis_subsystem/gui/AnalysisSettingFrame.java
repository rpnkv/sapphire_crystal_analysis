package org.izanaar.sca.analysis_subsystem.gui;


import org.izanaar.sca.analysis_subsystem.auxillary.areas_analysis.AnalysisController;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;

public class AnalysisSettingFrame extends JFrame {
    JPanel paramPnl, anlSelPnl, dbPnl, buttonPnl;
    JTextField perTxtFld, frmsTxtFld;
    AnalysisController analysisController;

    private int defaultWidth = 430, defaultHeight = 165;

    public AnalysisSettingFrame(AnalysisController analysisController) throws HeadlessException {
        this.analysisController = analysisController;
        initParamPnl();
        initAnlSetPnl();
        initDbPnl();
        initButtonPnl();
        initComponent();
    }

    private void initParamPnl() {
        paramPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        paramPnl.add(new JLabel("Analysis period length (sec):"));
        perTxtFld = new JTextField(3);
        perTxtFld.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkoutTextForValid(e,3);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkoutTextForValid(e,4);
            }
        });
        paramPnl.add(perTxtFld);
        paramPnl.add(new JLabel("; frame amount:"));
        frmsTxtFld = new JTextField(3);
        frmsTxtFld.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkoutTextForValid(e,2);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkoutTextForValid(e,4);
            }
        });
        paramPnl.add(frmsTxtFld);
        paramPnl.add(new JLabel(";"));
    }

    private void initAnlSetPnl() {
        anlSelPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        anlSelPnl.add(new JLabel("Analysis algorithm:"));
        anlSelPnl.add(initComboBox());
    }

    private JComboBox initComboBox(){
        String[] names = new String[analysisController.getFrameAnalysers().size()];
        for (int i = 0; i < names.length;i++)
            names[i] = analysisController.getFrameAnalysers().get(i).toString();

        JComboBox<String > comboBox = new JComboBox<>(names);
        comboBox.addActionListener(e -> reDefineContentPane((String) comboBox.getSelectedItem()));

        return comboBox;
    }

    private void initDbPnl() {
        dbPnl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JCheckBox checkBox = new JCheckBox("save measures to database");
        checkBox.setSelected(analysisController.isSaveCoordToDB());
        checkBox.addActionListener(e -> checkBox.setSelected(analysisController.setSaveCoordToDB(checkBox.isSelected())));
        dbPnl.add(checkBox);
    }

    private void initButtonPnl() {
        buttonPnl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton ok = new JButton("Start analysis"), close = new JButton("Close");
        ok.addActionListener(e ->{
            if(attemptToStartAnalysis())
                dispose();
        });
        buttonPnl.add(ok);
        close.addActionListener(e -> dispose());
        buttonPnl.add(close);
    }

    private void initComponent() {
        reDefineContentPane(analysisController.getFrameAnalysers().getFirst().toString());
        setTitle("Analysis configuration");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void reDefineContentPane(String analyserName){
        analysisController.setDefaultAnalyser(analyserName);
        JPanel settingsPanel = initSettingsPanel();
        getContentPane().removeAll();
        getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        getContentPane().add(paramPnl);
        getContentPane().add(anlSelPnl);
        getContentPane().add(settingsPanel);
        getContentPane().add(dbPnl);
        getContentPane().add(buttonPnl);
        setSize(defaultWidth, defaultHeight + settingsPanel.getHeight());
        update(getGraphics());
    }

    private JPanel initSettingsPanel(){
        JPanel settingsPanel = analysisController.getFrameAnalysers().getFirst().getSettingsPanel();
        if (settingsPanel == null)
        {
            settingsPanel = new JPanel();
            settingsPanel.setSize(defaultWidth,20);
            settingsPanel.add(new JLabel("Selected analysis algorithm has no settings available."));
            settingsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        }
        return settingsPanel;
    }

    private boolean attemptToStartAnalysis(){
        if(perTxtFld.getText().equals("") || frmsTxtFld.getText().equals("")){
            analysisController.viewException("Cannot start analysis","Analysis params are invalid");
            return false;
        }

        int length = Integer.parseInt(perTxtFld.getText()), frames = Integer.parseInt(frmsTxtFld.getText());

        if(length < frames){
            analysisController.viewException("Cannot start analysis","Analysis params are invalid");
            return false;
        }
        if(analysisController.isAnalysisPerforming()){
            int n = JOptionPane.showConfirmDialog(
                    null,
                    "Would you like to restart it with new params?",
                    "Analysis is already performing",
                    JOptionPane.YES_NO_OPTION);
            if(n == 1)
                return false;
        }

        analysisController.startAnalysis(length,frames);

        return true;
    }

    private void checkoutTextForValid(DocumentEvent e, int length) {
        SwingUtilities.invokeLater(()->{
            int offset = e.getOffset();
            Document doc = e.getDocument();
            try{
                try {
                    if(doc.getLength() >length){
                        doc.remove(offset,1);
                        return;
                    }
                    String insertedChar = doc.getText(offset, 1);
                    int num = Integer.parseInt(insertedChar);
                }catch (NumberFormatException numFormExc){
                    doc.remove(offset,1);
                }
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }

        });
    }
}
