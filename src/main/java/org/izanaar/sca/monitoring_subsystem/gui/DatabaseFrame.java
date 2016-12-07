package org.izanaar.sca.monitoring_subsystem.gui;


import org.izanaar.sca.monitoring_subsystem.auxillary.DataBaseIntermediator;

import javax.swing.*;
import java.awt.*;

public class DatabaseFrame extends JFrame {
    AddingFrame addingFrame;
    DataBaseIntermediator databaseIntermediator;
    JPanel customerPanel, productPanel, viewPanel, lowerPanel, upperPanel;
    JComboBox<String> customerCB, productCB;
    JButton custAddBtn, custDelBtn, custUpdBtn, prodAddBtn, prodDelBtn, prodUpdBtn,
            loadMenMeas, loadDevMeas, loadAllMeas, clearMeasures;
    JCheckBox detailedMeasChkBx;
    JTextArea outpArea;
    final int ADD_CUSTOMER = 1, ADD_PRODUCT = 2;

    public DatabaseFrame(DataBaseIntermediator databaseIntermediator) throws HeadlessException {
        super("Database log");
        this.databaseIntermediator = databaseIntermediator;
        databaseIntermediator.initDefaultValues();
        setLayout(new BorderLayout());
        initCustomerPanel();
        initProductPanel();
        initUpperPanel();
        initViewPanel();
        initLowerButtons();
        initLowerPanel();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(550, 440);
        setVisible(true);
    }

    private void initCustomerPanel() {
        customerPanel = new JPanel();
        customerPanel.setBorder(BorderFactory.createTitledBorder("Customer"));
        customerPanel.setLayout(new BoxLayout(customerPanel, BoxLayout.Y_AXIS));

        JPanel customerSubpanel = new JPanel();
        customerSubpanel.add(new JLabel("Customer:"));
        customerCB = new JComboBox<>(databaseIntermediator.getCustomersNames());
        customerCB.setSelectedIndex(0);
        customerCB.addActionListener(e -> updateProducts());
        customerSubpanel.add(customerCB);
        customerPanel.add(customerSubpanel);

        JPanel customersBtnSubpanel = new JPanel();
        custAddBtn = new JButton("Add");
        custAddBtn.addActionListener(e -> {
            if (addingFrame == null)
                addingFrame = new AddingFrame(ADD_CUSTOMER);
            else {
                addingFrame.dispose();
                addingFrame = new AddingFrame(ADD_CUSTOMER);
            }
        });
        customersBtnSubpanel.add(custAddBtn);
        custDelBtn = new JButton("Delete");
        custDelBtn.addActionListener(e -> deleteCustomer());
        customersBtnSubpanel.add(custDelBtn);
        custUpdBtn = new JButton("View all");
        custUpdBtn.addActionListener(e -> databaseIntermediator.getCustomers());
        customersBtnSubpanel.add(custUpdBtn);
        customerPanel.add(customersBtnSubpanel);
    }

    private void initProductPanel() {
        productPanel = new JPanel();
        productPanel.setBorder(BorderFactory.createTitledBorder("Product:"));
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));

        JPanel productSubpanel = new JPanel();
        productSubpanel.add(new JLabel("Product:"));
        productCB = new JComboBox<>(databaseIntermediator.getProducts());
        productCB.setSelectedIndex(0);
        productCB.addActionListener(e -> databaseIntermediator.setCurrentProduct((String) productCB.getSelectedItem()));

        productSubpanel.add(productCB);
        productPanel.add(productSubpanel);

        JPanel productsBtnSubpanel = new JPanel();
        prodAddBtn = new JButton("Add");
        prodAddBtn.addActionListener(e -> {
            if (addingFrame == null)
                addingFrame = new AddingFrame(ADD_PRODUCT);
            else {
                addingFrame.dispose();
                addingFrame = new AddingFrame(ADD_PRODUCT);
            }
        });
        productsBtnSubpanel.add(prodAddBtn);
        prodDelBtn = new JButton("Delete");
        prodDelBtn.addActionListener(e -> deleteProduct());
        productsBtnSubpanel.add(prodDelBtn);
        prodUpdBtn = new JButton("View all");
        prodUpdBtn.addActionListener(e -> databaseIntermediator.viewProducts());
        productsBtnSubpanel.add(prodUpdBtn);
        productPanel.add(productsBtnSubpanel);
    }

    private void addString(String name, String adress, int code) {
        switch (code) {
            case ADD_CUSTOMER:
                databaseIntermediator.addCustomer(name, adress);
                updateCustomers();
                break;
            case ADD_PRODUCT:
                databaseIntermediator.addProduct(name, adress);
                updateProducts();
                break;
        }
        addingFrame = null;
    }

    private void deleteCustomer() {
        databaseIntermediator.deleteCustomer((String) (customerCB.getSelectedItem()));
        updateCustomers();
        updateProducts();
    }

    private void deleteProduct() {
        databaseIntermediator.deleteProduct();
        updateProducts();
    }

    private void updateCustomers() {
        DefaultComboBoxModel model = new DefaultComboBoxModel(databaseIntermediator.getCustomersNames());
        customerCB.setModel(model);
    }

    private void updateProducts() {
        databaseIntermediator.setCurrentCustomer((String) customerCB.getSelectedItem());
        String[] products = databaseIntermediator.getProducts();
        DefaultComboBoxModel model;
        if (products != null)
            model = new DefaultComboBoxModel(products);

        else
            model = new DefaultComboBoxModel(new String[]{"No products"});

        productCB.setModel(model);
        databaseIntermediator.setCurrentProduct(productCB.getItemAt(0));
    }

    public JTextArea getOutpArea() {
        return outpArea;
    }

    @Override
    public void dispose() {
        super.dispose();
        if (addingFrame != null)
            addingFrame.dispose();
    }

    //region components intializing
    private void initUpperPanel() {
        upperPanel = new JPanel();
        upperPanel.add(customerPanel);
        upperPanel.add(productPanel);
        add(upperPanel, BorderLayout.NORTH);
    }

    private void initViewPanel() {
        viewPanel = new JPanel();
        viewPanel.setBorder(BorderFactory.createTitledBorder("Output:"));
        outpArea = new JTextArea(15, 46);
        outpArea.setEditable(false);

        JPopupMenu popupMenu = new javax.swing.JPopupMenu(outpArea.toString());

        outpArea.setComponentPopupMenu(popupMenu);
        JScrollPane scroll = new JScrollPane(outpArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        viewPanel.add(scroll);
        add(viewPanel, BorderLayout.CENTER);
    }

    private void initLowerButtons() {
        loadMenMeas = new JButton("Menisc");
        loadMenMeas.addActionListener(e -> databaseIntermediator.showMeniscusMeasures(detailedMeasChkBx.isSelected()));

        loadDevMeas = new JButton("Deviation");
        loadDevMeas.addActionListener(e -> databaseIntermediator.showDeviationMeasures(detailedMeasChkBx.isSelected()));

        loadAllMeas = new JButton("All measures");
        loadAllMeas.addActionListener(e -> databaseIntermediator.showAllMeasures());

        clearMeasures = new JButton("Clear all");
        clearMeasures.addActionListener(e -> databaseIntermediator.deleteMeasures(detailedMeasChkBx.isSelected()));

        detailedMeasChkBx = new JCheckBox("Detailed inf");
    }

    private void initLowerPanel() {
        lowerPanel = new JPanel();
        lowerPanel.setBorder(BorderFactory.createTitledBorder("Measures view:"));
        lowerPanel.add(loadMenMeas);
        lowerPanel.add(loadDevMeas);
        lowerPanel.add(loadAllMeas);
        lowerPanel.add(detailedMeasChkBx);
        lowerPanel.add(clearMeasures);
        add(lowerPanel, BorderLayout.SOUTH);
    }
    //endregion

    class AddingFrame extends JFrame {
        JTextField field1, field2;
        JLabel label1, label2;
        JButton ok;

        String editingParamName;
        int type;

        public AddingFrame(int type) throws HeadlessException {
            defineParamName(type);
            setTitle(editingParamName + " adding");
            initTextFields();
            initLabels();
            initButton();
            addComponents();
            initFrameParams();
        }

        private void defineParamName(int type) {
            this.type = type;
            switch (type) {
                case ADD_CUSTOMER:
                    editingParamName = "Customer";
                    break;
                case ADD_PRODUCT:
                    editingParamName = "Product";
                    break;
                default:
                    editingParamName = "Unknown";
            }
        }

        private void initButton() {
            ok = new JButton("Done");

            ok.addActionListener(e -> {
                if (field1.getText().equals("") || field1.getText().length() < 4) {
                    JOptionPane.showMessageDialog(null,
                            editingParamName + " must have at least 4 characters.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (field1.getText().length() > 20) {
                    JOptionPane.showMessageDialog(null,
                            editingParamName + " must 20 characters.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    addString(field1.getText(), field2.getText(), type);
                    dispose();
                }
            });
        }

        private void initTextFields() {
            field1 = new JTextField(20);
            field2 = new JTextField(20);
        }

        private void initLabels() {
            switch (type) {
                case ADD_CUSTOMER:
                    label1 = new JLabel("Name:");
                    label2 = new JLabel("Address:");
                    break;
                case ADD_PRODUCT:
                    label1 = new JLabel("Shape:");
                    label2 = new JLabel("Kind:");
                    break;
            }
        }

        private void addComponents() {
            add(label1);
            add(field1);
            add(label2);
            add(field2);
            add(ok);
        }

        private void initFrameParams() {
            setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 2));
            setSize(300, 100);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setVisible(true);
        }
    }
}

