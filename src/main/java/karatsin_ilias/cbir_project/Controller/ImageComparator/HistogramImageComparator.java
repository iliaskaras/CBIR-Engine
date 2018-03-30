package karatsin_ilias.cbir_project.Controller.ImageComparator;

import karatsin_ilias.cbir_project.Controller.Database.DatabaseSelectController;
import karatsin_ilias.cbir_project.DistanceEvaluators.*;
import karatsin_ilias.cbir_project.FeatureExtractor.Color_HistogramExtractor;
import karatsin_ilias.cbir_project.Model.ComparisonImage;
import karatsin_ilias.cbir_project.Model.HistogramDatabaseImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistogramImageComparator implements IImageComparator {

    public int kNeighbor = 0;

    @Override
    public void setK_Neighbor(int kNeighbor) {
        this.kNeighbor = kNeighbor;
    }

    @Override
    public List<ComparisonImage> getComparisonResult(String distanceMethod, File currentSelectedImage){
        List<ComparisonImage> comparisonResults = new ArrayList<ComparisonImage>();

        if(currentSelectedImage!=null){

                Color_HistogramExtractor color_histogramExtractor = new Color_HistogramExtractor();

                String resizedImageResourcesPath = "src//resources//images//"+currentSelectedImage.getName();
                BufferedImage img = null;
                try {
                    img = ImageIO.read(new File(resizedImageResourcesPath));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                ArrayList<int[]> histogram = color_histogramExtractor.createImageHistogram(img);
                ArrayList<Integer> histogramToCompare = color_histogramExtractor.uniteRGBtoSingleList(histogram);

                DatabaseSelectController databaseSelectController = new DatabaseSelectController();

                //2
                ResultSet rs = null;
                ArrayList<HistogramDatabaseImage> databaseImages = null;

                try {
                    rs = databaseSelectController.selectHistogramFromDatabase();
                    databaseImages = createHistogramDatabaseList(rs);

                    /** Set ArrayList<int[]> variable in each histogramDatabaseImage object based on its extracted String vector from database */
                    for(HistogramDatabaseImage databaseImage : databaseImages){
                        ArrayList<Integer> currentImageHistogram = color_histogramExtractor.getImageHistogram(databaseImage);
                        databaseImage.setFinalHistogram(currentImageHistogram);
                    }

                    rs.close();

                }catch (SQLException ex) {
                    ex.printStackTrace();
                }

                //5
                /** Sorting the images we compared based on their distance */

                if(distanceMethod==null){
                    distanceMethod = "Euclidean";
                }

                comparisonResults = getHistogramComparisonResults(databaseImages,histogramToCompare,distanceMethod);
                if(distanceMethod == "Euclidean" || distanceMethod == "Matusita" || distanceMethod == "Bhattacharyya")
                    comparisonResults.sort((o1, o2) -> o1.compareEuclidean(o1, o2));
                else
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



    private  List<ComparisonImage> getHistogramComparisonResults(ArrayList<HistogramDatabaseImage> databaseImages,
                                                                 ArrayList<Integer> selectedImageHistogram, String distanceMethod){
        List<ComparisonImage> comparisonResults = new ArrayList<ComparisonImage>();

        for(HistogramDatabaseImage databaseImage : databaseImages){
            ArrayList<Integer> currentImageHistogram = databaseImage.getFinalHistogram();

            double[] selectedImageDistancesList = new double[selectedImageHistogram.size()];
            double[] queryImageDistancesList = new double[currentImageHistogram.size()];

            /** Tranform Histograms into a list of Doubles, to be used on distance calculation methods later on */
            for (int i = 0; i < currentImageHistogram.size(); i++) {
                selectedImageDistancesList[i] = selectedImageHistogram.get(i);
                queryImageDistancesList[i] = currentImageHistogram.get(i);
            }

            double distanceResult;
            DistanceAdapter distanceAdapter = new DistanceAdapter(distanceMethod);
            distanceResult = distanceAdapter.get_distance(selectedImageDistancesList,queryImageDistancesList);
//            if(distanceMethod.equals("Euclidean")){
//                distanceResult = Euclidean_distance.get_distance(selectedImageDistancesList,queryImageDistancesList);
//            } else if(distanceMethod.equals("Bhattacharyya")){
//                distanceResult = Bhattacharyya_distance.get_distance(selectedImageDistancesList,queryImageDistancesList);
//            } else if(distanceMethod.equals("Matusita")){
//                distanceResult = Matusita_distance.get_distance(selectedImageDistancesList,queryImageDistancesList);
//            } else if(distanceMethod.equals("Histogram")){
//                distanceResult = Histogram_distance.get_distance(selectedImageDistancesList,queryImageDistancesList);
//            }

            ComparisonImage comparedDatabaseImage = new ComparisonImage(databaseImage.getFileName(),databaseImage.getFilePath(),distanceResult);
            comparisonResults.add(comparedDatabaseImage);


        }

        return comparisonResults;
    }

    private  ArrayList<HistogramDatabaseImage> createHistogramDatabaseList(ResultSet rs) throws SQLException {
        ArrayList<HistogramDatabaseImage> databaseImages = new ArrayList<>();

        while (rs.next()) {
            String fileName = rs.getString("filename");
            String filePath = rs.getString("filepath");
            String histogram = rs.getString("histogram");

            HistogramDatabaseImage databaseImage = new HistogramDatabaseImage(fileName,filePath,histogram);
            databaseImages.add(databaseImage);
        }

        return databaseImages;
    }


}
