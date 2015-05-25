package monitoring_subsystem.auxillary;

import analysis_subsystem.interfaces.ConnectionStatusEditable;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseIntermediator {
    private ConnectionStatusEditable statusEditable;
    private boolean connected;
    private Connection connection;
    private StatementPreparer statementPreparer;
    String currentCustomer, currentProduct;

    public DatabaseIntermediator(ConnectionStatusEditable statusEditable) {
        this.statusEditable = statusEditable;
        statementPreparer = new StatementPreparer();
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

    public String[] getProdusts(){
        return new String[] {"Crystal xq01", "Crystal xp02"};
    }

    public String[] getCustomers(){
        return new String[] {"IMI", "Motor-Sich"};
    }

    public void setCurrentProduct(String currentProduct) {
        this.currentProduct = currentProduct;
    }

    public void setCurrentCustomer(String currentCustomer) {
        this.currentCustomer = currentCustomer;
    }


    public void addCustomer(String s) {
        System.out.println("customer " + s + " added.");
    }
    public void addProduct(String s){
        System.out.println("product" + s +"added.");
    }
}
