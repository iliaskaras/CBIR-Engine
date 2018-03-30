package karatsin_ilias.cbir_project.Controller.Database;

import karatsin_ilias.cbir_project.DAO.Database.InsertToDatabase;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseInsertController {

    InsertToDatabase insertToDatabase = null;

    public void insertDescriptorToDatabase(Connection connection, String descriptor, String fileName, String filePath, byte[] blob, int rows, int columns, int mattype){

        insertToDatabase = new InsertToDatabase();
        try {
            insertToDatabase.insertNewDescriptorVectorToDB(connection, descriptor, fileName, filePath, blob, rows, columns, mattype);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void insertHistogramToDatabase(Connection connection, String fileName, String filePath, ArrayList<int[]> histogram){

        insertToDatabase = new InsertToDatabase();
        try {
            insertToDatabase.insertNewHistogramToDB(connection, fileName, filePath, histogram);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
