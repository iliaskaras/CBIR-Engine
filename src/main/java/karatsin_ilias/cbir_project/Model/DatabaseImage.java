package karatsin_ilias.cbir_project.Model;

import org.opencv.core.MatOfKeyPoint;

public class DatabaseImage {

    private String filePath;
    private String fileName;
    private MatOfKeyPoint matOfKeyPoint;
    private byte[] descriptorVector ;
    private int rows;
    private int columns;
    private int mattype;
    private String descriptor_type;

    public DatabaseImage(String fileName, String filePath, byte[] descriptorVector,
                         int rows, int columns, int mattype, String descriptor_type ){
        this.fileName = fileName;
        this.filePath = filePath;
        this.descriptorVector = descriptorVector;
        this.rows = rows;
        this.columns = columns;
        this.mattype = mattype;
        this.descriptor_type = descriptor_type;


    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getDescriptorVector() {
        return descriptorVector;
    }

    public MatOfKeyPoint getMatOfKeyPoint() {
        return matOfKeyPoint;
    }

    public void setMatOfKeyPoint(MatOfKeyPoint matOfKeyPoint) {
        this.matOfKeyPoint = matOfKeyPoint;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getMattype() {
        return mattype;
    }
}
