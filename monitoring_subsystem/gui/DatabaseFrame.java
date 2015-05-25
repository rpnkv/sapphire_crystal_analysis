package monitoring_subsystem.gui;


import monitoring_subsystem.auxillary.DatabaseIntermediator;

import javax.swing.*;
import java.awt.*;

public class DatabaseFrame extends JFrame{

    DatabaseIntermediator databaseIntermediator;
    JPanel customerPanel, productPanel, viewPanel, lowerPanel, upperPanel;
    JComboBox<String> customerCB, productCB;
    JButton custAddBtn, custDelBtn, custUpdBtn, prodAddBtn, prodDelBtn, prodUpdBtn, loadMenMeas, loadDevMeas, loadAllMeas;
    JTextArea outpArea;

    public DatabaseFrame(DatabaseIntermediator databaseIntermediator) throws HeadlessException {
        super("Database log");
        this.databaseIntermediator = databaseIntermediator;
        setLayout(new BorderLayout());
        initCustomerPanel();
        initProductPanel();
        initUpperPanel();
        initViewPanel();
        initLowerButtons();
        initLowerPanel();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(550,440);
        setVisible(true);
    }

    private void initCustomerPanel() {
        customerPanel = new JPanel();
        customerPanel.setBorder(BorderFactory.createTitledBorder("Customer"));
        customerPanel.setLayout(new BoxLayout(customerPanel,BoxLayout.Y_AXIS));

        JPanel customerSubpanel = new JPanel();
        customerSubpanel.add(new JLabel("Customer:"));
        customerCB = new JComboBox<>(databaseIntermediator.getCustomers());
        customerCB.setSelectedIndex(0);
        customerSubpanel.add(customerCB);
        customerPanel.add(customerSubpanel);

        JPanel customersBtnSubpanel = new JPanel();
        custAddBtn = new JButton("Add");
        custAddBtn.addActionListener(e -> System.out.println(e.getSource()));
        customersBtnSubpanel.add(custAddBtn);
        custDelBtn = new JButton("Delete");
        custDelBtn.addActionListener(e->System.out.println(e.getSource()));
        customersBtnSubpanel.add(custDelBtn);
        custUpdBtn = new JButton("Update");
        custUpdBtn.addActionListener(e ->System.out.println(e.getSource()));
        customersBtnSubpanel.add(custUpdBtn);
        customerPanel.add(customersBtnSubpanel);
    }

    private void initProductPanel() {
        productPanel = new JPanel();
        productPanel.setBorder(BorderFactory.createTitledBorder("Product:"));
        productPanel.setLayout(new BoxLayout(productPanel,BoxLayout.Y_AXIS));

        JPanel productSubpanel = new JPanel();
        productSubpanel.add(new JLabel("Product:"));
        productCB = new JComboBox<>(databaseIntermediator.getProdusts());
        productCB.setSelectedIndex(0);
        productSubpanel.add(productCB);
        productPanel.add(productSubpanel);

        JPanel productsBtnSubpanel = new JPanel();
        prodAddBtn = new JButton("Add");
        prodAddBtn.addActionListener(e ->System.out.println(e.getSource()));
        productsBtnSubpanel.add(prodAddBtn);
        prodDelBtn = new JButton("Delete");
        prodDelBtn.addActionListener(e->System.out.println(e.getSource()));
        productsBtnSubpanel.add(prodDelBtn);
        prodUpdBtn = new JButton("Update");
        prodUpdBtn.addActionListener(e-> System.out.println(e.getSource()));
        productsBtnSubpanel.add(prodUpdBtn);
        productPanel.add(productsBtnSubpanel);
    }

    private void initUpperPanel() {
        upperPanel = new JPanel();
        upperPanel.add(customerPanel);
        upperPanel.add(productPanel);
        add(upperPanel, BorderLayout.NORTH);
    }

    private void initViewPanel() {
        viewPanel = new JPanel();
        viewPanel.setBorder(BorderFactory.createTitledBorder("Output:"));
        outpArea = new JTextArea(16,46);
        outpArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(outpArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        viewPanel.add(scroll);
        add(viewPanel, BorderLayout.CENTER);
    }

    private void initLowerButtons() {
        loadMenMeas = new JButton("Menisc");
        loadMenMeas.addActionListener(e -> System.out.println(e.getActionCommand()));

        loadDevMeas = new JButton("Deviation");
        loadDevMeas.addActionListener(e-> System.out.println(e.getActionCommand()));

        loadAllMeas = new JButton("All measures");
        loadAllMeas.addActionListener(e-> System.out.println(e.getActionCommand()));
    }

    private void initLowerPanel() {
        lowerPanel = new JPanel();
        lowerPanel.setBorder(BorderFactory.createTitledBorder("Measures view:"));
        lowerPanel.add(loadMenMeas);
        lowerPanel.add(loadDevMeas);
        lowerPanel.add(loadAllMeas);
        add(lowerPanel,BorderLayout.SOUTH);
    }
}
