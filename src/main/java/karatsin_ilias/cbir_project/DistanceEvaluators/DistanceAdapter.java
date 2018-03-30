package karatsin_ilias.cbir_project.DistanceEvaluators;

public class DistanceAdapter implements IDistance{

    private String distanceMethod = null;

    public DistanceAdapter() {
    }

    public DistanceAdapter(String distanceMethod) {
        this.distanceMethod = distanceMethod;
    }

    @Override
    public double get_distance(double[] selectedDistanceList, double[] queryDistanceList) {
        double distanceResult = 0;

        if( this.distanceMethod.equals("Euclidean")){
            distanceResult = Euclidean_distance.get_distance(selectedDistanceList,queryDistanceList);
        } else if( this.distanceMethod.equals("Bhattacharyya")){
            distanceResult = Bhattacharyya_distance.get_distance(selectedDistanceList,queryDistanceList);
        } else if( this.distanceMethod.equals("Matusita")){
            distanceResult = Matusita_distance.get_distance(selectedDistanceList,queryDistanceList);
        } else if( this.distanceMethod.equals("Histogram")){
            distanceResult = Histogram_distance.get_distance(selectedDistanceList,queryDistanceList);
        }

        return distanceResult;
    }
}
