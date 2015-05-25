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
        customerCB.addActionListener(e-> updateCustomer((String) customerCB.getSelectedItem()));
        customerSubpanel.add(customerCB);
        customerPanel.add(customerSubpanel);

        JPanel customersBtnSubpanel = new JPanel();
        custAddBtn = new JButton("Add");
        custAddBtn.addActionListener(e -> new AddingFrame(ADD_CUSTOMER));
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
        productCB = new JComboBox<>(databaseIntermediator.getProdusts((String) customerCB.getSelectedItem()));
        productCB.setSelectedIndex(0);
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

    private void addString(String name, String adress, int code){
        switch (code){
            case ADD_CUSTOMER:
                databaseIntermediator.addCustomer(name, adress);
                customerCB.addItem(name);
                break;
            case ADD_PRODUCT:
                databaseIntermediator.addProduct(name);
                productCB.addItem(name);
                break;
        }
    }

    private void updateCustomer(String customerName){
        DefaultComboBoxModel model = new DefaultComboBoxModel( databaseIntermediator.getProdusts(customerName) );
        productCB.setModel( model );
        databaseIntermediator.setCurrentProduct(productCB.getItemAt(0));
    }

    public JTextArea getOutpArea() {
        return outpArea;
    }

    class AddingFrame extends JFrame{
        JButton ok;
        JTextField newName, newAdress;
        int type;
        String editParamName = "";
        public AddingFrame(int type) throws HeadlessException {
            switch (type){
                case ADD_CUSTOMER:
                    editParamName = "Customer";
                    break;
                case ADD_PRODUCT:
                    editParamName = "Product";
                    break;
            }
            setTitle(editParamName + " adding.");
            this.type = type;
            newName = new JTextField(20);
            newAdress = new JTextField(20);
            if(type == ADD_PRODUCT)
                newAdress.setEnabled(false);
            ok = new JButton("Done");
            ok.addActionListener(e -> {
                if(newName.getText().equals("") || newName.getText().length()<4){
                    JOptionPane.showMessageDialog(null,
                            editParamName + "must have at least 4 characters.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }else
                if(newName.getText().length()>20){
                    JOptionPane.showMessageDialog(null,
                            editParamName + "must 20 characters.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }else{
                    addString(newName.getText(),newAdress.getText(),type);
                    dispose();
                }
            });
            setLayout(new FlowLayout(FlowLayout.RIGHT,0,2));
            add(new JLabel(editParamName + " name:"));
            add(newName);
            add(new JLabel(editParamName + " addr:"));
            add(newAdress);
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BorderLayout());
            buttonPanel.add(ok,BorderLayout.EAST);
            add(buttonPanel);
            setSize(370, 100);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setVisible(true);
        }
    }
}

