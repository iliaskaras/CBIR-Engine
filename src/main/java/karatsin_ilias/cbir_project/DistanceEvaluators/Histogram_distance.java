package karatsin_ilias.cbir_project.DistanceEvaluators;

public class Histogram_distance {


    public Histogram_distance( double[] arr){
    }


    /** sqrt*Σ((sqrt(Hq(i))-sqrt(Hx(i))))^2)
     *  General rule : the bigger the distance result the more similar the images are */
    public static double get_distance (double[] selectedDistanceList ,double[] queryDistanceList){

        double sum1 =0;
        double sum2 =0;
        double arithmitis =0;

        if(selectedDistanceList.length == queryDistanceList.length){
            for(int i=0; i < selectedDistanceList.length; i++){

                arithmitis += Math.min(selectedDistanceList[i],queryDistanceList[i]);
                sum1 += selectedDistanceList[i];
                sum2 += queryDistanceList[i];
            }

            double paranomastis = Math.min(sum1,sum2);
            return arithmitis/paranomastis;

        }

        else return -1;

    }


}
