package monitoring_subsystem.gui;


import monitoring_subsystem.auxillary.DataBaseIntermediator;

import javax.swing.*;
import java.awt.*;

public class ConnectionSettingsFrame extends JFrame{
    public final String URL = "jdbc:mysql://localhost:3306/dist_sys_cp", username = "root", password = "04d0h";
    DataBaseIntermediator databaseIntermediator;

    JButton conenct,disconnect;
    JTextField urlField, userField, passwordField;

    JPanel buttonsPanel, mainPanel;

    public ConnectionSettingsFrame(DataBaseIntermediator intermediator) {
        super("DB connection settings");
        databaseIntermediator = intermediator;
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        initTextFields();
        initButtons();
        addTextFields();
        addButtons();
        add(mainPanel);
        setSize(320,180);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
            databaseIntermediator.createConnection(urlField.getText(), userField.getText(), passwordField.getText()));
        disconnect.addActionListener(e -> databaseIntermediator.disposeConnection());
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

