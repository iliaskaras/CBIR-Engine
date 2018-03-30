package karatsin_ilias.cbir_project.DAO.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseCreation {


    public void createDatabase(Connection databaseConnection) throws SQLException {
        Statement createDatabaseStatement = databaseConnection.createStatement();

        String sql = "CREATE DATABASE imagesdatabase";

        createDatabaseStatement.executeUpdate(sql);

    }


    public void createSurfTable(Connection databaseConnection) throws SQLException {
        PreparedStatement ps = databaseConnection.prepareStatement("CREATE TABLE IF NOT EXISTS images_surf_table" +
                "(FILENAME VARCHAR NOT NULL, FILEPATH VARCHAR NOT NULL, BLOB BYTEA NOT NULL," +
                " ROWS INT NOT NULL, COLUMNS INT NOT NULL, MATTYPE INT NOT NULL, DESCRIPTOR_TYPE VARCHAR NOT NULL)");
        ps.executeUpdate();
        ps.close();

    }

    public void createHistogramTable(Connection databaseConnection) throws SQLException {
        PreparedStatement ps = databaseConnection.prepareStatement("CREATE TABLE IF NOT EXISTS images_histogram_table" +
                "(FILENAME VARCHAR NOT NULL, FILEPATH VARCHAR NOT NULL, HISTOGRAM VARCHAR NOT NULL)");
        ps.executeUpdate();
        ps.close();

    }
}
