package karatsin_ilias.cbir_project.Model;

import java.awt.*;

public class SimilarImage {

    private String imageName;
    private Image fileName;

    public SimilarImage(String imageName, Image fileName) {
        this.imageName = imageName;
        this.fileName = fileName;
    }

    public String getImageName() {
        return imageName;
    }

    public Image getFileName() {
        return fileName;
    }

    public void setFileName(Image fileName) {
        this.fileName = fileName;
    }

}
