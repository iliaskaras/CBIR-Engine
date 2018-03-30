package karatsin_ilias.cbir_project.DistanceEvaluators;

public class Divergence_distance{

    public Divergence_distance( double[] arr){

    }


    /** Σ((Hq(i)-Hx(i)*ln(Hq(i)/Hx(i)))
     *  General rule : the bigger the distance result the more similar the images are */
    public static double get_distance (double[] selectedDistanceList ,double[] queryDistanceList){

        double abstraction = 0;
        double ln = 0;
        double result = 0;

        if(selectedDistanceList.length == queryDistanceList.length){
            for(int i=0; i < selectedDistanceList.length; i++){

                abstraction = selectedDistanceList[i] - queryDistanceList[i];
                ln =  selectedDistanceList[i] / queryDistanceList[i];
                result += abstraction*ln;
            }

            return result;

        }

        else return -1;

    }
}
