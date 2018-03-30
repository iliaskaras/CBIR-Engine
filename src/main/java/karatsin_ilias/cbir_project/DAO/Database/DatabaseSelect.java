package karatsin_ilias.cbir_project.DAO.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseSelect {


    public Boolean imageExistInSURFTable(Connection databaseConnection, String fileName, String filePath) throws SQLException {
        Boolean result = false;
        PreparedStatement ps = databaseConnection.prepareStatement("SELECT FILENAME FROM images_surf_table WHERE filename = ? AND filepath = ?");
        ps.setString(1, fileName);
        ps.setString(2, filePath);

        ResultSet selectResult = ps.executeQuery();

        result = (selectResult.isBeforeFirst());

        ps.close();

        return result;
    }
    public Boolean imageExistInHistogramTable(Connection databaseConnection, String fileName, String filePath) throws SQLException {
        Boolean result = false;
        PreparedStatement ps = databaseConnection.prepareStatement("SELECT FILENAME FROM images_histogram_table WHERE filename = ? AND filepath = ?");
        ps.setString(1, fileName);
        ps.setString(2, filePath);

        ResultSet selectResult = ps.executeQuery();

        result = (selectResult.isBeforeFirst());

        ps.close();

        return result;
    }

    public PreparedStatement getAllSURFItemsFromDatabase(Connection databaseConnection, String descriptor) throws SQLException {
        PreparedStatement ps = databaseConnection.prepareStatement("SELECT * FROM images_surf_table WHERE descriptor_type = ?");
        ps.setString(1, descriptor);
        ps.executeQuery();

        return ps;
    }

    public PreparedStatement getAllHistogramItemsFromDatabase(Connection databaseConnection) throws SQLException {
        PreparedStatement ps = databaseConnection.prepareStatement("SELECT * FROM images_histogram_table");
        ps.executeQuery();

        return ps;
    }


}
