package karatsin_ilias.cbir_project.FeatureExtractor;

import karatsin_ilias.cbir_project.Model.HistogramDatabaseImage;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class Color_HistogramExtractor {


    public Color_HistogramExtractor() {

    }

    public ArrayList<Integer> getImageHistogram(HistogramDatabaseImage histogramDatabaseImage){
        String databaseHistogram = histogramDatabaseImage.getHistogram();

        ArrayList<String> arrayList = new ArrayList<String> (Arrays.asList(databaseHistogram.split(" ")));
        ArrayList<Integer> favList = new ArrayList<Integer>();
        for(String fav:arrayList){
            favList.add(Integer.parseInt(fav.trim()));
        }

        return favList;
    }

    public ArrayList<int[]> createImageHistogram(BufferedImage input){


        int[] rhistogram = new int[256];
        int[] ghistogram = new int[256];
        int[] bhistogram = new int[256];

        for(int i=0; i<rhistogram.length; i++) rhistogram[i] = 0;
        for(int i=0; i<ghistogram.length; i++) ghistogram[i] = 0;
        for(int i=0; i<bhistogram.length; i++) bhistogram[i] = 0;

        for(int i=0; i<input.getWidth(); i++) {
            for(int j=0; j<input.getHeight(); j++) {

                int red = new Color(input.getRGB (i, j)).getRed();
                int green = new Color(input.getRGB (i, j)).getGreen();
                int blue = new Color(input.getRGB (i, j)).getBlue();

                rhistogram[red]++; ghistogram[green]++; bhistogram[blue]++;

            }
        }

        ArrayList<int[]> histogram = new ArrayList<int[]>();
        histogram.add(rhistogram);
        histogram.add(ghistogram);
        histogram.add(bhistogram);


        return histogram;
    }

    public ArrayList<Integer> uniteRGBtoSingleList(ArrayList<int[]> arrayList){
        StringBuilder sb = new StringBuilder();
        for (int[] s : arrayList)
        {
            for (int s1 : s) {
                sb.append(Integer.toString(s1));
                sb.append(" ");
            }
        }

        String listString = sb.toString();
        ArrayList<String> stringArrayList = new ArrayList<String>    (Arrays.asList(listString.split(" ")));

        ArrayList<Integer> resultArrayList = new ArrayList<Integer>();

        for(String fav:stringArrayList){
            resultArrayList.add(Integer.parseInt(fav.trim()));
        }

        return resultArrayList;
    }


}
