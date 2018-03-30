package karatsin_ilias.cbir_project.Model;

import java.util.ArrayList;

public class HistogramDatabaseImage {

    private String filePath;
    private String fileName;
    private String histogram;
    private ArrayList<Integer> finalHistogram;

    public HistogramDatabaseImage(String fileName, String filePath, String histogram ){
        this.fileName = fileName;
        this.filePath = filePath;
        this.histogram = histogram;


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

    public String getHistogram() {
        return histogram;
    }

    public void setFinalHistogram(ArrayList<Integer> finalHistogram) {
        this.finalHistogram = finalHistogram;
    }

    public ArrayList<Integer> getFinalHistogram() {
        return finalHistogram;
    }
}
