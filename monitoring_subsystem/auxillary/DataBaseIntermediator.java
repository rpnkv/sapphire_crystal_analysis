package monitoring_subsystem.auxillary;

import analysis_subsystem.interfaces.ConnectionStatusEditable;
import monitoring_subsystem.interfaces.RequestResultsViewable;


import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class DataBaseIntermediator {
    private ConnectionStatusEditable statusEditable;
    private boolean connected;
    private Connection connection;
    private StatementPreparer statementPreparer;
    String currentCustomer, currentProduct;
    RequestResultsViewable resultsViewer;

    public DataBaseIntermediator(ConnectionStatusEditable statusEditable, RequestResultsViewable requestResultsViewable)  {
        this.statusEditable = statusEditable;
        statementPreparer = new StatementPreparer();
        this.resultsViewer = requestResultsViewable;
        connected = false;
    }

    public boolean createConnection(String url, String user, String pass) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pass);
            connected = true;
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        } catch (SQLException e) {
            System.out.println(e);
        }
        statusEditable.setConnectionStatus(connected);
        return connected;
    }

    public void disposeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        connected = false;
        statusEditable.setConnectionStatus(connected);
    }

    public boolean isConnected() {
        return connected;
    }

    public String[] getProdusts(String customerName){
        String[] products = null;
        switch (customerName){
            case "IMI":
                products = new String[]{"IMI crystal 1", "IMI crystal 2"};
                break;
            case "Motor-Sich":
                products = new String[]{"Motor crystal 1", "Motor crystal 2"};
                break;
        }
        return products;
    }

    public String[] getCustomers(){
        try {
            ResultSet customersSet = statementPreparer.getCustomers(connection);
            LinkedList<String> customers = new LinkedList<>();
            while (customersSet.next()){
                String customer = "id: "+  customersSet.getInt(1) +", name: "+  customersSet.getString(2) +", adress:"+
                        customersSet.getString(3)+";";
                customers.add(customer);
            }
            String[] customersString = new String[customers.size()];
            for(int i = 0; i < customersString.length;i++)
                customersString[i] = customers.get(i);
            return customersString;
        } catch (SQLException e) {
            resultsViewer.append("Customers getting failed. " + e.getMessage() );
        }
        return new String[]{"no customers"};
    }

    public void setCurrentProduct(String currentProduct) {
        this.currentProduct = currentProduct;
    }

    public void setCurrentCustomer(String currentCustomer) {
        this.currentCustomer = currentCustomer;
    }


    public void addCustomer(String name, String adress) {
        try {
            statementPreparer.addCustomer(name,adress, connection);
            resultsViewer.append("Customer \'" + name + "\', with adress: " + adress + "was added.");
        } catch (SQLException e) {
            resultsViewer.append("Customer adding failed. " + e.getMessage());
        }
    }
    public void addProduct(String s){
        System.out.println("product" + s +"added.");
    }
}
