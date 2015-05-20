package capture_subsystem.auxillary;

import analysis_subsystem.auxillary.RegionSettingManager;

import javax.swing.*;

public class PopupMenu extends JPopupMenu {
    RegionSettingManager regionSettingManager;


    JMenuItem setMeniscus, setDeviation, editMeniscus, editDeviation;

    public PopupMenu(RegionSettingManager regionSettingManager) {
        this.regionSettingManager = regionSettingManager;
        initMenuItems();
        addActionListeners();
        addMenuItems();
    }

    private void initMenuItems(){
        setMeniscus = new JMenuItem("Set meniscus");
        setDeviation = new JMenuItem("Set deviation");
        editMeniscus = new JMenuItem("Edit meniscus width");
        editDeviation = new JMenuItem("Edit deviation width");
    }

    private void addActionListeners(){
        setMeniscus.addActionListener(e -> regionSettingManager.initMeniscusCoordSetup());
        setDeviation.addActionListener(e -> regionSettingManager.initDeviationCoordSetup());
        editMeniscus.addActionListener(e -> regionSettingManager.initMeniscusWidthEdit());
        editDeviation.addActionListener(e -> regionSettingManager.initDeviationWidthEdit());
    }

    private void addMenuItems(){
        add(setMeniscus);
        add(setDeviation);
        add(editMeniscus);
        add(editDeviation);
    }

    void setMeniscusEditable(boolean value){
        editMeniscus.setEnabled(value);
    }

    void setDeviationEditable(boolean value){
        editDeviation.setEnabled(value);
    }
}
