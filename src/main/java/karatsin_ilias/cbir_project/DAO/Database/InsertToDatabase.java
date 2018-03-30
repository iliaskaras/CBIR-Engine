package karatsin_ilias.cbir_project.DAO.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class InsertToDatabase {


    public void insertNewDescriptorVectorToDB(Connection databaseConnection, String descriptor, String fileName, String filePath, byte[] blob, int rows, int columns, int mattype) throws SQLException {
        PreparedStatement ps = databaseConnection.prepareStatement("INSERT INTO images_surf_table (filename, filepath, blob, rows, columns, mattype, descriptor_type)" +
                "VALUES (?,?,?,?,?,?,?)");
        ps.setString(1,fileName);
        ps.setString(2,filePath);
        ps.setBytes(3,blob);
        ps.setInt(4,rows);
        ps.setInt(5,columns);
        ps.setInt(6,mattype);
        ps.setString(7,descriptor);

        ps.executeUpdate();
        ps.close();

    }

    public void insertNewHistogramToDB(Connection databaseConnection, String fileName, String filePath, ArrayList<int[]> histogram)
        throws SQLException {

        StringBuilder sb = new StringBuilder();
        for (int[] s : histogram)
        {
            for (int s1 : s) {
//                   count+=1;
                sb.append(Integer.toString(s1));
                sb.append(" ");
            }
        }

        PreparedStatement ps = databaseConnection.prepareStatement("INSERT INTO images_histogram_table (filename, filepath, histogram)" +
                "VALUES (?,?,?)");
        ps.setString(1,fileName);
        ps.setString(2,filePath);
        ps.setString(3,sb.toString());

        ps.executeUpdate();
        ps.close();

    }
}
