package karatsin_ilias.cbir_project.Controller.Database;

import karatsin_ilias.cbir_project.DAO.Database.DatabaseCreation;
import karatsin_ilias.cbir_project.DAO.Database.PostGreSQLConn;
import java.sql.Connection;
import java.sql.SQLException;

/** Our Singleton DatabaseConnector Class.*/
public class DatabaseConnector {

    private static PostGreSQLConn postGreSQLConn = new PostGreSQLConn();
    private static DatabaseCreation databaseCreation = null;
    private static Connection _connection = null;

    public static void connectToDatabase(){
        Boolean databaseConnected = false;

        try {
            if(_connection == null){
                _connection = postGreSQLConn.createDatabaseConnection();
                databaseConnected = true;
            }
        } catch (SQLException e) {
            databaseConnected = false;
            e.printStackTrace();
        }

        if(databaseConnected){
            databaseCreation = new DatabaseCreation();

            try {
                databaseCreation.createSurfTable(_connection);
                databaseCreation.createHistogramTable(_connection);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        closeConnectionToDatabase();
    }

    public static void closeConnectionToDatabase(){
        try {
            _connection.close();
            System.out.print("Connection Closed\n\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getPostGreSqlConn() throws SQLException {

        if(_connection.isClosed())
            return postGreSQLConn.connectToDatabaseImagesDatabase();
        else
            return _connection;

    }


}
