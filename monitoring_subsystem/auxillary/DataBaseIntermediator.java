package monitoring_subsystem.auxillary;

import analysis_subsystem.interfaces.ConnectionStatusEditable;
import monitoring_subsystem.interfaces.MeasureSavable;
import monitoring_subsystem.interfaces.RequestResultsViewable;


import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class DataBaseIntermediator implements MeasureSavable {
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
            JOptionPane.showMessageDialog(null, e.getMessage(),"Connection failed", JOptionPane.ERROR_MESSAGE);
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

    @Override
    public boolean saveMeasure(Measure measure) {
        try {
            statementPreparer.saveMeasure(connection,currentProduct,measure);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isReadyToAnalysisLogging(){
        assert currentProduct != null;
        boolean productIsSelected, customerIsSelected;
        try {
            productIsSelected = !currentProduct.equals("No products");
            customerIsSelected = currentCustomer != null;
        }catch (NullPointerException e){
            return false;
        }
        return (connected && productIsSelected && customerIsSelected);
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

    public void showMeniscusMeasures(Boolean detailed) {
        if(detailed)
            showMeniscusMeasuresTotal();
        else
            showMeniscusMeasuresSimple();
    }

    private void showMeniscusMeasuresSimple(){
        try {
            ResultSet result = statementPreparer.getMeniscusSimple(connection,currentProduct);
            resultsViewer.clearArea();
            resultsViewer.append(padLeft("ID",3) +padLeft("time",15) + padLeft("height",15) + "\n");
            while (result.next())
                resultsViewer.append(padLeft(String.valueOf(result.getInt(1)),3) +
                padLeft(String.valueOf(result.getTime(2)),15) + padLeft(String.valueOf(result.getInt(3)),15) + "\n");
        } catch (SQLException e) {
            resultsViewer.append("Cannot view meniscus simple measures.\n" + e.getMessage());
        }
    }

    private void showMeniscusMeasuresTotal(){
        try {
            ResultSet result = statementPreparer.getMeniscusTotal(connection, currentProduct);
            resultsViewer.clearArea();
            resultsViewer.append(padLeft("ID",3) +padLeft("time",15) + padLeft("height",12)  + padLeft("x_menisk",11)
                    + padLeft("y_top",11)  + padLeft("y_bot",11)+ "\n");
            while (result.next())
                resultsViewer.append(padLeft(String.valueOf(result.getInt(1)),3) +//id
                        padLeft(String.valueOf(result.getTime(2)),10) +
                        padLeft(String.valueOf(result.getInt(3)),10) +
                        padLeft(String.valueOf(result.getInt(4)),14) +
                        padLeft(String.valueOf(result.getInt(5)),12) +
                        padLeft(String.valueOf(result.getInt(6)),12) + "\n");
        } catch (SQLException e) {
            resultsViewer.append("Cannot view meniscus total measures.\n" + e.getMessage());
        }
    }

    public void showDeviationMeasures(Boolean detailed) {
        if(detailed)
            showDeviationMeasuresTotal();
        else
            showDeviationMeasuresSimple();
    }

    private void showDeviationMeasuresSimple(){
        try {
            ResultSet result = statementPreparer.getDeviationSimple(connection, currentProduct);
            resultsViewer.clearArea();
            resultsViewer.append(padLeft("ID",3) +padLeft("time",15) + padLeft("deviation",15) + "\n");
            while (result.next())
                resultsViewer.append(padLeft(String.valueOf(result.getInt(1)),3) +
                        padLeft(String.valueOf(result.getTime(2)),15) + padLeft(String.valueOf(result.getInt(3)),15) + "\n");
        } catch (SQLException e) {
            resultsViewer.append("Cannot view deviation simple measures.\n" + e.getMessage());
        }
    }

    private void showDeviationMeasuresTotal(){
        try {
            ResultSet result = statementPreparer.getDeviationTotal(connection, currentProduct);
            resultsViewer.clearArea();
            resultsViewer.append(padLeft("ID",3) +padLeft("time",15) + padLeft("deviation",12)  + padLeft("y_cryst",11)
                    + padLeft("x_cr_l",11) + padLeft("x_cr_r",11) + padLeft("y_shp",11) + padLeft("y_shp_l",11)
                    + padLeft("y_shp_r",11) + "\n");
            while (result.next())
                resultsViewer.append(padLeft(String.valueOf(result.getInt(1)),3) +//id
                        padLeft(String.valueOf(result.getTime(2)),10) +
                        padLeft(String.valueOf(result.getInt(3)),10) +
                        padLeft(String.valueOf(result.getInt(4)),10) +
                        padLeft(String.valueOf(result.getInt(5)),15) +
                        padLeft(String.valueOf(result.getInt(6)),12) +
                        padLeft(String.valueOf(result.getInt(7)),12) +
                        padLeft(String.valueOf(result.getInt(8)),12)+
                        padLeft(String.valueOf(result.getInt(9)),12) + "\n");
        } catch (SQLException e) {
            resultsViewer.append("Cannot view deviation total measures.\n" + e.getMessage());
        }
    }

    public void showAllMeasures() {
        try {
            ResultSet result = statementPreparer.getAllSimple(connection, currentProduct);
            resultsViewer.clearArea();
            resultsViewer.append(padLeft("ID",3) +padLeft("time",15) +
                    padLeft("height",15) + padLeft("deviation",15) + "\n");
            while (result.next())
                resultsViewer.append(padLeft(String.valueOf(result.getInt(1)),3) +
                        padLeft(String.valueOf(result.getTime(2)),15) +
                        padLeft(String.valueOf(result.getInt(3)),15)+
                        padLeft(String.valueOf(result.getInt(4)),15) + "\n");
        } catch (SQLException e) {
            resultsViewer.append("Cannot view all simple measures.\n" + e.getMessage());
        }
    }

    public void deleteMeasures(Boolean detailed) {
        try{
            statementPreparer.deleteAllMeasures(connection,currentProduct);
        } catch (SQLException e) {
            System.out.println("I don't wanna mess with measures.\n" + e.getMessage());
        }
    }

    private String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

    private static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }

}
