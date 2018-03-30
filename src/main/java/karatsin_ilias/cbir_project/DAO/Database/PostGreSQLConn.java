package karatsin_ilias.cbir_project.DAO.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostGreSQLConn {

    private final String databaseUrl = "jdbc:postgresql://localhost/imagesdatabase";
    private final String serverUrl = "jdbc:postgresql://localhost/";
    private final String user = "postgres";
    private final String password = "karatsin";

    private Connection databaseConnection = null;
    private DatabaseCreation databaseCreation =null;

    public Connection createDatabaseConnection() throws SQLException {
        Boolean databaseExistance = false;

        try {
            databaseConnection = DriverManager.getConnection(databaseUrl, user, password);
            System.out.println("Connected to the "+ databaseUrl +" server successfully.");
            databaseExistance = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            databaseExistance = false;
        }

        if(!databaseExistance){
            databaseCreation = new DatabaseCreation();
            databaseConnection = createServerConnection();
            databaseCreation.createDatabase(databaseConnection);
            databaseConnection.close();
            databaseConnection = null;
            databaseConnection = connectToDatabaseImagesDatabase();

        }

        return databaseConnection;
    }


    private Connection createServerConnection() {

        try {
            databaseConnection = DriverManager.getConnection(serverUrl, user, password);
            System.out.println("Connected to the "+ serverUrl +" server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return databaseConnection;
    }


    public Connection connectToDatabaseImagesDatabase() {

        try {
            databaseConnection = DriverManager.getConnection(databaseUrl, user, password);
            System.out.println("Connected to the "+ databaseUrl +" database successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return databaseConnection;
    }


}
