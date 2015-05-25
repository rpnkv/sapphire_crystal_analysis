package monitoring_subsystem.gui;


import analysis_subsystem.interfaces.ConnectionStatusEditable;
import monitoring_subsystem.auxillary.DatabaseIntermediator;

import javax.swing.*;

public class ConnectionSettingsFrame extends JFrame{
    public final String URL = "jdbc:mysql://localhost:3306/dist_sys_cp", username = "root", password = "04d0h";
    DatabaseIntermediator intermediator;
    ConnectionStatusEditable statusEditable;

    JButton conenct,disconnect;
    JTextField urlField, userField, passwordField;

    JPanel buttonsPanel;

    public ConnectionSettingsFrame(DatabaseIntermediator intermediator, ConnectionStatusEditable statusEditable) {
        super("DB connection settings");
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        intermediator = new DatabaseIntermediator();
        this.intermediator = intermediator;
        this.statusEditable = statusEditable;
        initTextFields();
        initButtons();
        addTextFields();
        addButtons();
        setSize(400,200);
        setVisible(true);
    }

    private void initTextFields() {
        urlField = new JTextField(URL);
        userField = new JTextField(username);
        passwordField = new JTextField(password);
    }

    private void initButtons() {
        conenct = new JButton("Connect");
        disconnect = new JButton("Disconnect");
        buttonsPanel = new JPanel();
        buttonsPanel.add(conenct);
        buttonsPanel.add(disconnect);
        conenct.addActionListener(e ->
            statusEditable.setConnectionStatus(intermediator.createConnection(urlField.getText(),
                    userField.getText(), passwordField.getText()))
        );
        conenct.addActionListener(e -> {intermediator.disposeConnection(); intermediator = null;});
    }

    private void addTextFields() {
        JPanel urlPanel = new JPanel();
        urlPanel.add(new JLabel("URL:"));
        urlPanel.add(urlField);
        add(urlPanel);

        JPanel usernamePanel = new JPanel();
        usernamePanel.add(new JLabel("username:"));
        usernamePanel.add(userField);
        add(usernamePanel);

        JPanel passPanel = new JPanel();
        passPanel.add(new JLabel("password"));
        passPanel.add(passwordField);
        add(passPanel);
    }

    private void addButtons() {
        buttonsPanel.add(conenct);
        buttonsPanel.add(disconnect);
    }
}
