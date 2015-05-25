package monitoring_subsystem.auxillary;

import analysis_subsystem.interfaces.ConnectionStatusEditable;
import monitoring_subsystem.interfaces.RequestResultsViewable;


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

    public void initDefaultValues(){
        setCurrentCustomer(getCustomersNames()[0]);
        setCurrentProduct(getProducts()[0]);
    }

    public boolean createConnection(String url, String user, String pass) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pass);
            connected = true;

        } catch (SQLException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
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

    public boolean isReadyToAnalysisLogging(){
        assert currentProduct != null;
        return  ((currentProduct != null|| !currentProduct.equals("No products")) && currentCustomer != null && connected);
    }

    public String[] getProducts(){
        try {
            ResultSet productsSet = statementPreparer.getProducts(connection, currentCustomer);
            LinkedList<String> products = new LinkedList<>();
            while (productsSet.next())
                products.add(productsSet.getString(1));
            return products.toArray(new String[products.size()]);
        } catch (SQLException e) {
            resultsViewer.append("Products kinds for \'" + currentCustomer +"\' failed. \n" + e.getMessage() + "\n" );
        }
        return  new String[]{"No products"};
    }

    public void viewProducts(){
        try {
            ResultSet productsSet = statementPreparer.getProductsFull(connection);
            while (productsSet.next())
                resultsViewer.append("id: " + productsSet.getInt(1) + ", shape: " + productsSet.getString(2) +
                        ", kind: " + productsSet.getString(3) + ",\n customer: " +
                        statementPreparer.getCustomerName(connection,productsSet.getInt(4)) + ", date: " +
                        productsSet.getDate(5).toString() +".\n");
        } catch (SQLException e) {
            resultsViewer.append("Products view failed. \n" + e.getMessage() + "\n" );
        }

    }

    public void getCustomers(){
        try {
            ResultSet customersSet = statementPreparer.getCustomers(connection);
            while (customersSet.next()) {
                String customer = "id: " + customersSet.getInt(1) + ", name: " + customersSet.getString(2) + ", adress:" +
                        customersSet.getString(3) + ";";
                resultsViewer.append(customer+"\n");
            }
        } catch (SQLException e) {
            resultsViewer.append("Customers getting failed. " + e.getMessage()+"\n" );
        }
    }

    public String[] getCustomersNames(){
        try {
            ResultSet customersSet = statementPreparer.getCustomers(connection);
            LinkedList<String> customers = new LinkedList<>();
            while (customersSet.next()){
                String customer = customersSet.getString(2);
                customers.add(customer);
            }
            String[] customersString = new String[customers.size()];
            return customers.toArray(customersString);
        } catch (SQLException e) {
            resultsViewer.append("Customers getting failed. " + e.getMessage()+"\n" );
        }
        return new String[]{"no customers"};
    }

    public void setCurrentProduct(String currentProduct) {
        this.currentProduct = currentProduct;
        if(resultsViewer != null)
        resultsViewer.append("Current product updated: " + currentProduct+ "\n");
    }

    public void setCurrentCustomer(String currentCustomer) {
        this.currentCustomer = currentCustomer;
        if(resultsViewer != null)
        resultsViewer.append("Current customer updated: " + currentCustomer + "\n");
    }

    public void addCustomer(String name, String adress) {
        try {
            statementPreparer.addCustomer(name,adress, connection);
            resultsViewer.append("Customer \'" + name + "\', with adress: " + adress + "was added."+"\n");
            setCurrentCustomer(name);
        } catch (SQLException e) {
            resultsViewer.append("Customer adding failed. " + e.getMessage()+"\n");
        }
    }

    public void addProduct(String shape, String kind){
        try {
            statementPreparer.addProduct(connection,kind,shape,currentCustomer);
        } catch (SQLException e) {
            resultsViewer.append("Product adding failed. " + e.getMessage()+ "\n");
        }
    }

    public void deleteCustomer(String selectedItem) {
        try{
            statementPreparer.deleteCustomer(connection,selectedItem);
            resultsViewer.append("Customer \'" + selectedItem +"\' removed."+"\n");
            setCurrentCustomer(getCustomersNames()[0]);
        } catch (SQLException e) {
            resultsViewer.append("Customer removing failed. " + e.getMessage());
        }
    }

    public void deleteProduct(){
        try {
            statementPreparer.deleteProduct(connection, currentProduct);
            resultsViewer.append("Product \'" + currentProduct +"\' deleted. \n");
        } catch (SQLException e) {
            resultsViewer.append("Deleting product \'" + currentProduct +"\' wasn't performed. \n" + e.getMessage());
        }
    }

}
