package karatsin_ilias.cbir_project.DistanceEvaluators;

public class Matusita_distance{

        public Matusita_distance( double[] arr){

        }


        /** sqrt*Σ((sqrt(Hq(i))-sqrt(Hx(i))))^2)
         *  General rule : the bigger the distance result the more similar the images are */
        public static double get_distance (double[] selectedDistanceList ,double[] queryDistanceList){

            double sum =0;

            if(selectedDistanceList.length == queryDistanceList.length){
                for(int i=0; i < selectedDistanceList.length; i++){

                    sum +=Math.pow( Math.sqrt(selectedDistanceList[i]) -  Math.sqrt(queryDistanceList[i]),2);
                }
                /** Closer to 0 the sum is : means negative or positive 0,
                 *  results log to give -infinity,
                 *  that's why we multiply by negative to give infinity */
                return Math.sqrt(sum);

            }

            else return -1;

        }


}
