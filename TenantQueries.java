import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class TenantQueries {
    private static final String URL = "jdbc:derby:properties";
    private static final String USERNAME = "test";
    private static final String PASSWORD = "test";

    private Connection connection; // manages connection
    private PreparedStatement selectAllTenants;
    private PreparedStatement selectTenantsByLastName;
    private PreparedStatement insertNewTenant;
    private PreparedStatement removeTenant;

    // constructor
    public TenantQueries(){
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // query that selects all entries in the tenant table of the properties db
            selectAllTenants = connection.prepareStatement("SELECT * FROM tenant ORDER BY lastName, firstName");

            // query that selects entries with last names that begin with the specified characters
            selectTenantsByLastName = connection.prepareStatement("SELECT * FROM tenant WHERE lastName LIKE ? ORDER BY lastName, firstName");

            // query that adds a new entry to the db
            insertNewTenant = connection.prepareStatement("INSERT INTO tenant (firstName, lastName, phoneNumber, email) VALUES (?, ?, ?, ?)");

            // query that removes the tenant of the entered tenantID
            removeTenant = connection.prepareStatement("DELETE FROM tenant WHERE tenantID = ?");
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }

    // select all of the tenants in the db
    public List<Tenant> getAllTenants() {
        // executeQuery returns ResultSet containing matching entries
        try (ResultSet resultSet = selectAllTenants.executeQuery()){
            List<Tenant> results = new ArrayList<Tenant>();

            while (resultSet.next()){
                results.add(new Tenant(
                        resultSet.getInt("tenantID"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("phoneNumber"),
                        resultSet.getString("email")));
            }

            return results;
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }

        return null;
    }

    // select all of the tenants ordered by last name
    public List<Tenant> getTenantsByLastName(String lastName) {
        try {
            selectTenantsByLastName.setString(1, lastName); // set last name
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }

        // executeQuery returns ResultSet containing matching entries
        try (ResultSet resultSet = selectTenantsByLastName.executeQuery()) {
            List<Tenant> results = new ArrayList<Tenant>();

            while (resultSet.next()) {
                results.add(new Tenant(
                        resultSet.getInt("tenantID"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("phoneNumber"),
                        resultSet.getString("email")));
            }
            
            return results;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    // add an entry
    public int addTenant(String firstName, String lastName, String phoneNumber, String email) {
        // insert the new entry
        try {
            // set parameters
            insertNewTenant.setString(1, firstName);
            insertNewTenant.setString(2, lastName);
            insertNewTenant.setString(4, phoneNumber);
            insertNewTenant.setString(3, email);
            // returns # of rows updated
            return insertNewTenant.executeUpdate();
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return 0;
        }
    }

    // remove an entry
    public int removeTenant(int tenantID) {
        // remove the entry
        try {
            // set parameters
            removeTenant.setInt(1, tenantID);
            // returns # of rows updated
            return removeTenant.executeUpdate();
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return 0;
        }
    }

    // close the database connection
    public void close() {
        try {
            connection.close();
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}