package karatsin_ilias.cbir_project.Controller.Database;

import karatsin_ilias.cbir_project.DAO.Database.DatabaseSelect;
import karatsin_ilias.cbir_project.Model.HistogramDatabaseImage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseSelectController {
    DatabaseSelect selectFromDatabase = null;

    public ResultSet selectDescriptorFromDatabase(){
        Connection connection = null;
        try {
            connection = DatabaseConnector.getPostGreSqlConn();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<HistogramDatabaseImage> databaseImages = null;
        selectFromDatabase = new DatabaseSelect();

        try {
            connection = DatabaseConnector.getPostGreSqlConn();
            ps = selectFromDatabase.getAllSURFItemsFromDatabase(connection,"surf_descriptor");
            rs = ps.getResultSet();

            DatabaseConnector.closeConnectionToDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;

    }


    public ResultSet selectHistogramFromDatabase(){
        Connection connection = null;
        try {
            connection = DatabaseConnector.getPostGreSqlConn();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<HistogramDatabaseImage> databaseImages = null;
        selectFromDatabase = new DatabaseSelect();

        try {
            connection = DatabaseConnector.getPostGreSqlConn();
            ps = selectFromDatabase.getAllHistogramItemsFromDatabase(connection);
            rs = ps.getResultSet();

            DatabaseConnector.closeConnectionToDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }
}
