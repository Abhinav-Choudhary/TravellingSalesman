package uiHelper;
import java.util.ArrayList;

public class DataNormalizer {

    private ArrayList<dataProvider.Node> nodes;

    public DataNormalizer(ArrayList<dataProvider.Node> nodes) {
        this.nodes = nodes;
    }

    public ArrayList<dataProvider.Node> normalizeData() {
        // Normalize latitude and longitude values
        double maxLatitude = Double.MIN_VALUE;
        double minLatitude = Double.MAX_VALUE;
        double maxLongitude = Double.MIN_VALUE;
        double minLongitude = Double.MAX_VALUE;

        for (dataProvider.Node node : nodes) {
            double latitude = node.getLatitude();
            double longitude = node.getLongitude();
            if (latitude < minLatitude) {
                minLatitude = latitude;
            }
            if (latitude > maxLatitude) {
                maxLatitude = latitude;
            }
            if (longitude < minLongitude) {
                minLongitude = longitude;
            }
            if (longitude > maxLongitude) {
                maxLongitude = longitude;
            }
        }

        double latitudeRange = maxLatitude - minLatitude;
        double longitudeRange = maxLongitude - minLongitude;

        // Increase normalization factor to scatter the points more
        double normalizationFactor = 10000000.0;

        for (dataProvider.Node node : nodes) {
            double normalizedLatitude = (node.getLatitude() - minLatitude) * (normalizationFactor / latitudeRange);
            double normalizedLongitude = (node.getLongitude() - minLongitude) * (normalizationFactor / longitudeRange);
            node.setLatitude(normalizedLatitude);
            node.setLongitude(normalizedLongitude);
            
        }

        return nodes;
    }
    public ArrayList<dataProvider.Node> denormalizeData() {
        double maxLatitude = Double.MIN_VALUE;
        double minLatitude = Double.MAX_VALUE;
        double maxLongitude = Double.MIN_VALUE;
        double minLongitude = Double.MAX_VALUE;

        for (dataProvider.Node node : nodes) {
            double latitude = node.getLatitude();
            double longitude = node.getLongitude();
            if (latitude < minLatitude) {
                minLatitude = latitude;
            }
            if (latitude > maxLatitude) {
                maxLatitude = latitude;
            }
            if (longitude < minLongitude) {
                minLongitude = longitude;
            }
            if (longitude > maxLongitude) {
                maxLongitude = longitude;
            }
        }

        double latitudeRange = maxLatitude - minLatitude;
        double longitudeRange = maxLongitude - minLongitude;

        // Increase normalization factor to scatter the points more
        double normalizationFactor = 10000000.0;

        for (dataProvider.Node node : nodes) {
            double denormalizedLatitude = (node.getLatitude() * latitudeRange / normalizationFactor) + minLatitude;
            double denormalizedLongitude = (node.getLongitude() * longitudeRange / normalizationFactor) + minLongitude;
            node.setLatitude(denormalizedLatitude);
            node.setLongitude(denormalizedLongitude);
        }

        return nodes;
    }
}
