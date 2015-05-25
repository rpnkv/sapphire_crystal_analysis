package monitoring_subsystem.gui;


import monitoring_subsystem.auxillary.DataBaseIntermediator;

import javax.swing.*;
import java.awt.*;

public class DatabaseFrame extends JFrame{

    DataBaseIntermediator databaseIntermediator;
    JPanel customerPanel, productPanel, viewPanel, lowerPanel, upperPanel;
    JComboBox<String> customerCB, productCB;
    JButton custAddBtn, custDelBtn, custUpdBtn, prodAddBtn, prodDelBtn, prodUpdBtn,
            loadMenMeas, loadDevMeas, loadAllMeas, clearArea;
    JTextArea outpArea;
    final int ADD_CUSTOMER = 1, ADD_PRODUCT =2;

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
        setSize(550,440);
        setVisible(true);
    }

    private void initCustomerPanel() {
        customerPanel = new JPanel();
        customerPanel.setBorder(BorderFactory.createTitledBorder("Customer"));
        customerPanel.setLayout(new BoxLayout(customerPanel,BoxLayout.Y_AXIS));

        JPanel customerSubpanel = new JPanel();
        customerSubpanel.add(new JLabel("Customer:"));
        customerCB = new JComboBox<>(databaseIntermediator.getCustomersNames());
        customerCB.setSelectedIndex(0);
        customerCB.addActionListener(e-> updateProducts());
        customerSubpanel.add(customerCB);
        customerPanel.add(customerSubpanel);

        JPanel customersBtnSubpanel = new JPanel();
        custAddBtn = new JButton("Add");
        custAddBtn.addActionListener(e -> new AddingFrame(ADD_CUSTOMER));
        customersBtnSubpanel.add(custAddBtn);
        custDelBtn = new JButton("Delete");
        custDelBtn.addActionListener(e-> deleteCustomer());
        customersBtnSubpanel.add(custDelBtn);
        custUpdBtn = new JButton("View all");
        custUpdBtn.addActionListener(e ->databaseIntermediator.getCustomers());
        customersBtnSubpanel.add(custUpdBtn);
        customerPanel.add(customersBtnSubpanel);
    }

    private void initProductPanel() {
        productPanel = new JPanel();
        productPanel.setBorder(BorderFactory.createTitledBorder("Product:"));
        productPanel.setLayout(new BoxLayout(productPanel,BoxLayout.Y_AXIS));

        JPanel productSubpanel = new JPanel();
        productSubpanel.add(new JLabel("Product:"));
        productCB = new JComboBox<>(databaseIntermediator.getProducts());
        productCB.setSelectedIndex(0);
        productCB.addActionListener(e -> databaseIntermediator.setCurrentProduct((String) productCB.getSelectedItem()));

        productSubpanel.add(productCB);
        productPanel.add(productSubpanel);

        JPanel productsBtnSubpanel = new JPanel();
        prodAddBtn = new JButton("Add");
        prodAddBtn.addActionListener(e -> new AddingFrame(ADD_PRODUCT));
        productsBtnSubpanel.add(prodAddBtn);
        prodDelBtn = new JButton("Delete");
        prodDelBtn.addActionListener(e->System.out.println(e.getSource()));
        productsBtnSubpanel.add(prodDelBtn);
        prodUpdBtn = new JButton("Update");
        prodUpdBtn.addActionListener(e-> System.out.println(e.getSource()));
        productsBtnSubpanel.add(prodUpdBtn);
        productPanel.add(productsBtnSubpanel);
    }

    private void addString(String name, String adress, int code){
        switch (code){
            case ADD_CUSTOMER:
                databaseIntermediator.addCustomer(name, adress);
                updateCustomers();
                break;
            case ADD_PRODUCT:
                databaseIntermediator.addProduct(name);
                updateProducts();
                break;
        }
    }

    private void deleteCustomer() {
        databaseIntermediator.deleteCustomer((String)(customerCB.getSelectedItem()));
        updateCustomers();
        updateProducts();
    }

    private void updateCustomers(){
        DefaultComboBoxModel model = new DefaultComboBoxModel(databaseIntermediator.getCustomersNames());
        customerCB.setModel(model);
    }

    private void updateProducts(){
        databaseIntermediator.setCurrentCustomer((String) customerCB.getSelectedItem());
        String[] products = databaseIntermediator.getProducts();
        DefaultComboBoxModel model;
        if(products != null)
            model = new DefaultComboBoxModel( products );

        else
            model = new DefaultComboBoxModel(new String[] {"No products"});

        productCB.setModel(model);
        databaseIntermediator.setCurrentProduct(productCB.getItemAt(0));
    }

    public JTextArea getOutpArea() {
        return outpArea;
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
        outpArea = new JTextArea(16,46);
        outpArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(outpArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        viewPanel.add(scroll);
        add(viewPanel, BorderLayout.CENTER);
    }

    private void initLowerButtons() {
        clearArea = new JButton("Clear");
        clearArea.addActionListener(e-> outpArea.setText(""));

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
        lowerPanel.add(clearArea);
        lowerPanel.add(loadMenMeas);
        lowerPanel.add(loadDevMeas);
        lowerPanel.add(loadAllMeas);
        add(lowerPanel,BorderLayout.SOUTH);
    }
    //endregion

    class AddingFrame extends JFrame{
        JButton ok;
        JTextField newName, newAddress;
        String editingParamName;
        JLabel nameLabel, addressLabel;

        int type;
        public AddingFrame(int type) throws HeadlessException {
            defineParamName(type);
            setTitle(editingParamName + " adding.");
            initTextFields();
            initLabels();
            initButton();

            setLayout(new FlowLayout(FlowLayout.RIGHT,0,2));
            add(nameLabel);
            add(newName);
            add(addressLabel);
            add(newAddress);
            add(ok);
            setSize(370, 100);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setVisible(true);
        }

        private void defineParamName(int type) {
            this.type = type;
            switch (type){
                case ADD_CUSTOMER:
                    editingParamName = "Customer";
                    break;
                case ADD_PRODUCT:
                    editingParamName =  "Product";
                default: editingParamName =   "Unknown";
            }
        }

        private void initButton(){
            ok = new JButton("Done");
            ok.addActionListener(e -> {
                if(newName.getText().equals("") || newName.getText().length()<4){
                    JOptionPane.showMessageDialog(null,
                            editingParamName + " must have at least 4 characters.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }else
                if(newName.getText().length()>20){
                    JOptionPane.showMessageDialog(null,
                            editingParamName + " must 20 characters.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }else{
                    addString(newName.getText(), newAddress.getText(),type);
                    dispose();
                }
            });
        }

        private void initTextFields(){
            newName = new JTextField(20);
            newAddress = new JTextField(20);
            if(type == ADD_PRODUCT)
                newAddress.setEnabled(false);
        }

        private void initLabels(){
            nameLabel = new JLabel(" name:");
            addressLabel = new JLabel(" addr:");
            if(type == ADD_PRODUCT)
                addressLabel.setEnabled(false);
        }
    }
}

