package karatsin_ilias.cbir_project.Model;

import java.util.Comparator;

public class ComparisonImage implements Comparator<ComparisonImage>{

    private String filePath;
    private String fileName;
    private double vectorDistanceResult;

    public ComparisonImage(String fileName, String filePath, double vectorDistanceResult){
        this.fileName = fileName;
        this.filePath = filePath;
        this.vectorDistanceResult = vectorDistanceResult;
    }


    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public double getVectorDistanceResult() {
        return vectorDistanceResult;
    }


    @Override
    public int compare(ComparisonImage o1, ComparisonImage o2) {
        if(o1.getVectorDistanceResult() < o2.getVectorDistanceResult()){
            return 1;
        }
        if(o1.getVectorDistanceResult() > o2.getVectorDistanceResult()){
            return -1;
        }
        return 0;
    }


    public int compareEuclidean(ComparisonImage o1, ComparisonImage o2) {
        if(o1.getVectorDistanceResult() > o2.getVectorDistanceResult()){
            return 1;
        }
        if(o1.getVectorDistanceResult() < o2.getVectorDistanceResult()){
            return -1;
        }
        return 0;
    }


    public void printFileName(){
        System.out.println(this.fileName);
    }
}
