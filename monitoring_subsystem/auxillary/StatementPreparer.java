package monitoring_subsystem.auxillary;

import com.sun.istack.internal.NotNull;

import java.sql.*;

class StatementPreparer {
    private final String ADD_CUSTOMER = "insert into dist_sys_cp.customer(customer.description, customer.adress)\n" +
            "values (?,?);";
    private final String GET_CUSTOMERS = "SELECT * FROM dist_sys_cp.customer;";
    private final String REMOVE_CUSTOMER = "delete from dist_sys_cp.customer where customer.description=?;";
    private final String GET_CUSTOMER_ID = "select id_customer from dist_sys_cp.customer \n" +
            "where description = ?;";
    private final String GET_CUSTOMER_NAME = "select description from dist_sys_cp.customer" +
            " where customer.id_customer = ?;";
    private final String ADD_PRODUCT = "insert into \n" +
            "dist_sys_cp.product(product.shape,product.kind,product.id_customer,product.date)\n" +
            " values (?,?,?,?);";
    private final String GET_PRODUCTS_FULL = "SELECT * FROM dist_sys_cp.product;";
    private final String GET_PRODUCTS = "SELECT kind FROM dist_sys_cp.product\n" +
            "where id_customer = ?;";
    private final String DELETE_PRODUCT = "delete from dist_sys_cp.product where product.kind = ?;";

    public void addCustomer(@NotNull String name, String adress, Connection connection) throws SQLException {
            PreparedStatement statement = connection.prepareStatement(ADD_CUSTOMER);
            statement.setString(1, name);
            statement.setString(2, adress);
            statement.execute();
    }

    public ResultSet getCustomers(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(GET_CUSTOMERS);
        return statement.executeQuery();
    }

    public void deleteCustomer(Connection connection, String name) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(REMOVE_CUSTOMER);
        statement.setString(1,name);
        statement.execute();
    }

    public int getCustomerID (Connection connection, String name) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(GET_CUSTOMER_ID);
        statement.setString(1, name);
        ResultSet result = statement.executeQuery();
        while (result.next())
            return result.getInt(1);
        return 0;
    }

    public String getCustomerName(Connection connection, int id) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(GET_CUSTOMER_NAME);
        statement.setInt(1,id);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next())
            return resultSet.getString(1);
        return "unknown.";
    }

    public void addProduct(Connection connection,@NotNull String kind,
                           String shape, String customerName) throws SQLException {
        int customerID = getCustomerID(connection,customerName);
        PreparedStatement statement = connection.prepareStatement(ADD_PRODUCT);
        statement.setString(1,shape);
        statement.setString(2,kind);
        statement.setInt(3,customerID);
        statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
        statement.execute();
    }

    public ResultSet getProductsFull(Connection connection) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(GET_PRODUCTS_FULL);
        return statement.executeQuery();
    }

    public ResultSet getProducts(Connection connection, String customer) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(GET_PRODUCTS);
        statement.setInt(1,getCustomerID(connection,customer));
        return statement.executeQuery();
    }

    public void deleteProduct(Connection connection, String shape) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT);
        statement.setString(1,shape);
        statement.execute();
    }
}
