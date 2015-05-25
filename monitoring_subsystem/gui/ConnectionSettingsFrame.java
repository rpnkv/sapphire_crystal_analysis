package monitoring_subsystem.gui;


import analysis_subsystem.interfaces.ConnectionStatusEditable;
import monitoring_subsystem.auxillary.DatabaseIntermediator;

import javax.swing.*;
import java.awt.*;

public class ConnectionSettingsFrame extends JFrame{
    public final String URL = "jdbc:mysql://localhost:3306/dist_sys_cp", username = "root", password = "04d0h";
    DatabaseIntermediator intermediator;
    ConnectionStatusEditable statusEditable;

    JButton conenct,disconnect;
    JTextField urlField, userField, passwordField;

    JPanel buttonsPanel, mainPanel;

    public ConnectionSettingsFrame(DatabaseIntermediator intermediator, ConnectionStatusEditable statusEditable) {
        super("DB connection settings");
        intermediator = new DatabaseIntermediator();
        this.intermediator = intermediator;
        this.statusEditable = statusEditable;
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        initTextFields();
        initButtons();
        addTextFields();
        addButtons();
        add(mainPanel);
        setSize(320,180);
        setVisible(true);
    }

    private void initTextFields() {
        urlField = new JTextField(22);
        urlField.setText(URL);
        userField = new JTextField(20);
        userField.setText(username);
        passwordField = new JTextField(20);
        passwordField.setText(password);
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
        mainPanel.add(urlPanel);

        JPanel usernamePanel = new JPanel();
        usernamePanel.add(new JLabel("username:"));
        usernamePanel.add(userField);
        mainPanel.add(usernamePanel);

        JPanel passPanel = new JPanel();
        passPanel.add(new JLabel("password"));
        passPanel.add(passwordField);
        mainPanel.add(passPanel);
    }

    private void addButtons() {
        buttonsPanel.add(conenct);
        buttonsPanel.add(disconnect);
        mainPanel.add(buttonsPanel);
    }
}
