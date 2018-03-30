
package karatsin_ilias.cbir_project.DistanceEvaluators;



public class Euclidean_distance {

    double[] array_a = null;

    public Euclidean_distance( double[] arr){

        array_a = arr;
    }


    /**  General rule : closer to 0 more non similar the images are,
     *  the bigger the result the more similar the images are */
    public static double get_distance (double[] selectedDistanceList ,double[] queryDistanceList){

        double sum =0;

        if(selectedDistanceList.length == queryDistanceList.length){
            for(int i=0; i < selectedDistanceList.length; i++){

                sum += Math.pow(Math.abs(selectedDistanceList[i] - queryDistanceList[i]),2);
            }

            return Math.sqrt(sum);

        }

        else return -1;

    }


}
