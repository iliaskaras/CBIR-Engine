package karatsin_ilias.cbir_project.Controller.ImageComparator;

import karatsin_ilias.cbir_project.Model.ComparisonImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/** =======================================================================================
 *  1) create the descriptor vector of selected Image
 *  2) get all images and their descriptor vectors from database
 *  3) transform those byte vectors into real MatOfKeyPoint vectors
 *  4) create a list of DatabaseImage objects, containing FileName,FilePath,DescriptorVector
 *  5) finally loop into that list of objects, comparing 1) with DescriptorVectors of 4)
 *  ======================================================================================= */

public class ImageComparatorController {

    private File currentSelectedImage = null;
    private String selectedDescriptor = null;
    private int kNeighbor;
    private IImageComparator iImageComparator = null;

    public ImageComparatorController(File currentSelectedImage, String selectedDescriptor, int kNeighbor){

        this.currentSelectedImage = currentSelectedImage;
        this.selectedDescriptor = selectedDescriptor;

        if(this.selectedDescriptor.equals("SURF"))
            iImageComparator = new SurfImageComparator();
        else if (this.selectedDescriptor.equals("RGB"))
            iImageComparator = new HistogramImageComparator();

        this.kNeighbor = kNeighbor;
        iImageComparator.setK_Neighbor(this.kNeighbor);
    }


    public List<ComparisonImage> getComparisonResult(String distanceMethod){
        List<ComparisonImage> comparisonResults = new ArrayList<ComparisonImage>();
        comparisonResults = iImageComparator.getComparisonResult(distanceMethod, this.currentSelectedImage);


        return comparisonResults;
    }


}
