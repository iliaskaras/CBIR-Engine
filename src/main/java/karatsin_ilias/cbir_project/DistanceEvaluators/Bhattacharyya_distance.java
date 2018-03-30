package karatsin_ilias.cbir_project.DistanceEvaluators;

public class Bhattacharyya_distance {


    public Bhattacharyya_distance( double[] arr){

    }


    /** -ln*Σ(sqrt(Hq(i)*Hx(i)))
     *  General rule : closer to 0 more similar images (infinity = equal images)*/
    public static double get_distance (double[] selectedDistanceList ,double[] queryDistanceList){

        double sum =0;

        if(selectedDistanceList.length == queryDistanceList.length){
            for(int i=0; i < selectedDistanceList.length; i++){

                sum += Math.sqrt((selectedDistanceList[i] * queryDistanceList[i]));
            }
            /** Closer to 0 the sum is : means negative or positive 0,
             *  results log to give -infinity,
             *  that's why we multiply by negative to give infinity */
            return -Math.log(sum);

        }

        else return -1;

    }


}
