package monitoring_subsystem.auxillary;

import com.sun.istack.internal.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class StatementPreparer {
    private final String ADD_CUSTOMER = "insert into dist_sys_cp.customer(customer.description, customer.adress)\n" +
            "values (?,?);";
    private final String GET_CUSTOMERS = "SELECT * FROM dist_sys_cp.customer;";
    private final String REMOVE_CUSTOMER = "delete from dist_sys_cp.customer where customer.description=?;";

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
}
