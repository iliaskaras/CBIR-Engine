package karatsin_ilias.cbir_project.FeatureExtractor;


import karatsin_ilias.cbir_project.Model.DatabaseImage;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.FeatureDetector;
import org.opencv.highgui.Highgui;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class SURF_DescriptorExtractor {

    private byte[] blob;
    private int rows;
    private int columns;
    private int mattype;

    public SURF_DescriptorExtractor(){
    }


    public MatOfKeyPoint getImageDescriptorMatFromFilename(String imageName){
        MatOfKeyPoint imgDescriptor = new MatOfKeyPoint();

        if(imageName!=null ){
            Mat image = Highgui.imread(imageName, Highgui.CV_LOAD_IMAGE_COLOR);

            MatOfKeyPoint objectKeyPoints = new MatOfKeyPoint();
            FeatureDetector featureDetector = FeatureDetector.create(FeatureDetector.SURF);
            featureDetector.detect(image, objectKeyPoints);

            imgDescriptor = new MatOfKeyPoint();
            DescriptorExtractor descriptorExtractor = DescriptorExtractor.create(DescriptorExtractor.SURF);
            descriptorExtractor.compute(image, objectKeyPoints, imgDescriptor);

        }

        return imgDescriptor;
    }

    public void initDataForDatabase(MatOfKeyPoint keyPoints) {
        float[] data = new float[(int)keyPoints.total() * keyPoints.channels()]; // make a spot to save the data
        keyPoints.get(0,0,data); // load the data;
        ByteBuffer buffer = ByteBuffer.allocate(data.length * 4);
        for (int i = 0; i < data.length; i++){
            buffer.putFloat(data[i]);
        }
        this.blob = buffer.array();
        this.columns = keyPoints.cols();
        this.rows = keyPoints.rows();
        this.mattype = keyPoints.type();

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

    public byte[] getBlob() {
        return blob;
    }

    public MatOfKeyPoint getMatFromDatabase(DatabaseImage databaseImage) {
        MatOfKeyPoint keyPoints = null;

        int rows = databaseImage.getRows();
        int columns = databaseImage.getColumns();
        byte[] blob = databaseImage.getDescriptorVector();
        int mattype = databaseImage.getMattype();


        keyPoints = new MatOfKeyPoint();
        keyPoints.create(rows, columns, mattype);
        ByteBuffer buffer = ByteBuffer.wrap(blob);
        FloatBuffer floatBuffer = buffer.asFloatBuffer();
        float[] floatArray = new float[floatBuffer.limit()];
        floatBuffer.get(floatArray);
        keyPoints.put(0, 0, floatArray);

        return keyPoints;
    }
}
