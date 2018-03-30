package karatsin_ilias.cbir_project.Controller.ImageComparator;

import karatsin_ilias.cbir_project.Controller.Database.DatabaseSelectController;
import karatsin_ilias.cbir_project.DistanceEvaluators.*;
import karatsin_ilias.cbir_project.FeatureExtractor.SURF_DescriptorExtractor;
import karatsin_ilias.cbir_project.Model.ComparisonImage;
import karatsin_ilias.cbir_project.Model.DatabaseImage;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorMatcher;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SurfImageComparator implements IImageComparator {

    private int kNeighbor = 0;

    @Override
    public void setK_Neighbor(int kNeighbor) {
        this.kNeighbor = kNeighbor;
    }

    @Override
    public List<ComparisonImage> getComparisonResult(String distanceMethod, File currentSelectedImage){
        List<ComparisonImage> comparisonResults = new ArrayList<ComparisonImage>();

        if(currentSelectedImage!=null){
            System.out.println(" Selected image : "+ currentSelectedImage.getAbsolutePath());

            //1
            SURF_DescriptorExtractor surf_descriptorExtractor = new SURF_DescriptorExtractor();
            String resizedImageResourcesPath = "src//resources//images//"+currentSelectedImage.getName();
            MatOfKeyPoint choosedFileMatKeyPoint = surf_descriptorExtractor.getImageDescriptorMatFromFilename(resizedImageResourcesPath);

            //2
            DatabaseSelectController databaseSelectController = new DatabaseSelectController();
            ResultSet rs = null;
            ArrayList<DatabaseImage> databaseImages = null;

            try {
                rs = databaseSelectController.selectDescriptorFromDatabase();
                databaseImages = getSurfDatabaseImages(rs);
                //3 + 4
                /** Set MatOfKeyPoint variable in each databaseImage object based on its extracted byte[] vector from database */
                for(DatabaseImage databaseImage : databaseImages){
                    MatOfKeyPoint currentFileMatKeyPoint = surf_descriptorExtractor.getMatFromDatabase(databaseImage);
                    databaseImage.setMatOfKeyPoint(currentFileMatKeyPoint);
                }

                rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            //5
            /** Sorting the images we compared based on their distance */
            comparisonResults = getSurfComparisonResults(databaseImages,choosedFileMatKeyPoint);
            comparisonResults.sort((o1, o2) -> o1.compare(o1, o2));
            System.out.println("=========================================================================");
            System.out.println("Images sorted based on Similarity!");

            /** Keep K first similar images */
            comparisonResults = comparisonResults.subList(0, this.kNeighbor);
            comparisonResults.forEach(ComparisonImage::printFileName);
        } else {
            return null;
        }

        return comparisonResults;
    }


    private  List<ComparisonImage> getSurfComparisonResults(ArrayList<DatabaseImage> databaseImages, MatOfKeyPoint selectedImageMatOfKeyPoint){
        List<ComparisonImage> comparisonResults = new ArrayList<ComparisonImage>();
        /** If its surf then the distances are calculated only with Divergence method
         *  If its RGB, then the distance method that will be used is the selected one from the list in UI */
        for(DatabaseImage databaseImage : databaseImages){
            MatOfKeyPoint currentFileMatKeyPoint = databaseImage.getMatOfKeyPoint();
            List<MatOfDMatch> matches = new LinkedList<MatOfDMatch>();
            DescriptorMatcher descriptorMatcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);
            descriptorMatcher.knnMatch(selectedImageMatOfKeyPoint, currentFileMatKeyPoint, matches, 2);

//            LinkedList<DMatch> goodMatchesList = new LinkedList<DMatch>();
//
//            float nndrRatio = 0.7f;

            double[] selectedImageDistancesList = new double[matches.size()];
            double[] queryImageDistancesList = new double[matches.size()];

            /** Matches contains the most similar points in the images we compare
             * (size of matches is the size of vector from selected image)
             *  Then we load this points in new lists that we will use for calculating the distances later on*/
            for (int i = 0; i < matches.size(); i++) {
                MatOfDMatch matofDMatch = matches.get(i);
                DMatch[] dmatcharray = matofDMatch.toArray();
                DMatch m1 = dmatcharray[0];
                DMatch m2 = dmatcharray[1];

                selectedImageDistancesList[i] = m1.distance;
                queryImageDistancesList[i] = m2.distance;

            }

            double distanceDivergenceResult = Divergence_distance.get_distance(selectedImageDistancesList,queryImageDistancesList);

            ComparisonImage comparedDatabaseImage = new ComparisonImage(databaseImage.getFileName(),databaseImage.getFilePath(),distanceDivergenceResult);
            comparisonResults.add(comparedDatabaseImage);

        }

        return comparisonResults;
    }

    private  ArrayList<DatabaseImage> getSurfDatabaseImages(ResultSet rs) throws SQLException {
        ArrayList<DatabaseImage> databaseImages = new ArrayList<>();

        while (rs.next()) {
            String fileName = rs.getString("filename");
            String filePath = rs.getString("filepath");
            byte[] descriptorVector = rs.getBytes("blob");
            int rows = rs.getInt("rows");
            int columns = rs.getInt("columns");
            int mattype = rs.getInt("mattype");
            String descriptor_type = rs.getString("descriptor_type");

            DatabaseImage databaseImage = new DatabaseImage(fileName,filePath,descriptorVector,rows,columns,mattype,descriptor_type);
            databaseImages.add(databaseImage);
        }

        return databaseImages;
    }

}
