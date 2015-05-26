package analysis_subsystem.gui;

import analysis_subsystem.interfaces.ControliableAnalysisPerformable;

import javax.swing.*;
import java.awt.*;

public class AnalysisParamSetupFrame extends JFrame{
    ControliableAnalysisPerformable analysisPerformable;

    JPanel upperPanel;
    JPanel lowerPanel;

    JButton acceptBtn;
    JTextField lengthTxtFld, framesTxtFld;

    int a,b;

    public AnalysisParamSetupFrame(ControliableAnalysisPerformable analysisPerformable) throws HeadlessException {
        super("Analysis params config frame");
        this.analysisPerformable = analysisPerformable;
        setLayout(new BorderLayout(2,2));
        initUpperPanel();
        initLowerPanel();
        addComponents();
        initFrame();
    }

    private void initUpperPanel() {
        upperPanel = new JPanel();
        upperPanel.add(new JLabel("Iteration lenght:"));
        lengthTxtFld = new JTextField(3);
        upperPanel.add(lengthTxtFld);
        upperPanel.add(new JLabel("Frames amount:"));
        framesTxtFld = new JTextField(3);
        upperPanel.add(framesTxtFld);
        upperPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
    }

    private void initLowerPanel() {
        lowerPanel = new JPanel();
        acceptBtn = new JButton("Start");
        acceptBtn.addActionListener(e -> {
            if(checkoutParams())
                analysisPerformable.performAnalysis(a,b);
        });
        lowerPanel.add(acceptBtn);
        lowerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
    }

    private void addComponents() {
        add(upperPanel,BorderLayout.NORTH);
        add(lowerPanel,BorderLayout.SOUTH);
    }

    private void initFrame() {
        setSize(335,87);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private boolean checkoutParams(){
        int x,y;
        try{
            x = Integer.valueOf(lengthTxtFld.getText());
            y = Integer.valueOf(framesTxtFld.getText());
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null,
                    e.getMessage(),
                    "Invalid number format",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }


        if (x < 10){
            JOptionPane.showMessageDialog(null,
                    "Iteration's length must be at least 10 seconds.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }if(x > 999){
            JOptionPane.showMessageDialog(null,
                    "Iteration's length must less than 900 seconds.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }if (y > x){
            JOptionPane.showMessageDialog(null,
                    "Frame amount must me less than iteration length.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }if(y < 1){
            JOptionPane.showMessageDialog(null,
                    "Frame amount must be at least 1",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        a = x;
        b = y;
        return true;
    }
}
