package karatsin_ilias.cbir_project.Controller.ImageComparator;

import karatsin_ilias.cbir_project.Model.ComparisonImage;
import java.io.File;
import java.util.List;

public interface IImageComparator {

    void setK_Neighbor(int kNeighbor);
    List<ComparisonImage> getComparisonResult(String distanceMethod, File currentSelectedImage);
}
