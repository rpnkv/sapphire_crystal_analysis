package analysis_subsystem.auxillary;

import javax.swing.*;

public class PopupMenu extends JPopupMenu {
    RegionSettingManager regionSettingManager;


    JMenuItem setMeniscus, setDeviation, setShaper, editMeniscus, editDeviation, editSpaher;

    public PopupMenu(RegionSettingManager regionSettingManager) {
        this.regionSettingManager = regionSettingManager;
        initMenuItems();
        addActionListeners();
        addMenuItems();
    }

    private void initMenuItems(){
        setMeniscus = new JMenuItem("Set meniscus");
        setDeviation = new JMenuItem("Set deviation");
        setShaper = new JMenuItem("Set shaper");
        editMeniscus = new JMenuItem("Edit meniscus width");
        editDeviation = new JMenuItem("Edit deviation width");
        editSpaher = new JMenuItem("Edit shaper width");
        setShaperEditable(false);
        setMeniscusEditable(false);
        setDeviationEditable(false);
    }

    private void addActionListeners(){
        setMeniscus.addActionListener(e -> regionSettingManager.setState(regionSettingManager.MENISCUS_BEGIN));
        setDeviation.addActionListener(e -> regionSettingManager.setState(regionSettingManager.DEVIATION_BEGIN));
        setShaper.addActionListener(e -> regionSettingManager.setState(regionSettingManager.SHAPER_BEGIN));
        editMeniscus.addActionListener(e -> regionSettingManager.setState(regionSettingManager.MENISCUS_WIDTH));
        editDeviation.addActionListener(e -> regionSettingManager.setState(regionSettingManager.DEVIATION_WIDTH));
        editSpaher.addActionListener(e -> regionSettingManager.setState(regionSettingManager.SHAPER_WIDTH));
    }

    private void addMenuItems(){
        add(setMeniscus);
        add(setDeviation);
        add(setShaper);
        add(editMeniscus);
        add(editDeviation);
        add(editSpaher);
    }

    void setMeniscusEditable(boolean value){
        editMeniscus.setEnabled(value);
    }

    void setDeviationEditable(boolean value){
        editDeviation.setEnabled(value);
    }

    void setShaperEditable(boolean value){ editSpaher.setEnabled(value);}
}
