package UI;
import christofidesAlgo.Graph;
import christofidesAlgo.Node;
import christofidesAlgo.Edge;
import java.util.ArrayList;

public class DataNormalizer {

    private Graph graph;
    
    
    
    public DataNormalizer() {
    }

    public DataNormalizer(Graph graph) {
    	normalizeData(graph);
        this.graph = graph;
    }

    public Graph normalizeData(Graph graph) {
        // Normalize latitude and longitude values
        double maxLatitude = Double.MIN_VALUE;
        double minLatitude = Double.MAX_VALUE;
        double maxLongitude = Double.MIN_VALUE;
        double minLongitude = Double.MAX_VALUE;

        ArrayList<Node> nodes = graph.getNodes();
        ArrayList<Edge> edges = graph.allEdges();

        for (Node node : nodes) {
            double latitude = node.x;
            double longitude = node.y;
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
        double normalizationFactor = 1000000.0;

        for (Node node : nodes) {
            double normalizedLatitude = (node.x - minLatitude) * (normalizationFactor / latitudeRange);
            double normalizedLongitude = (node.y - minLongitude) * (normalizationFactor / longitudeRange);
            node.x = normalizedLatitude;
            node.y = normalizedLongitude;
        }

        for (Edge edge : edges) {
            Node u = edge.u;
            Node v = edge.v;
            double normalizedWeight = (edge.weight - minLatitude) * (normalizationFactor / latitudeRange);
            edge.weight = normalizedWeight;
        }

        return graph;
    }

    public Graph denormalizeData() {
        double maxLatitude = Double.MIN_VALUE;
        double minLatitude = Double.MAX_VALUE;
        double maxLongitude = Double.MIN_VALUE;
        double minLongitude = Double.MAX_VALUE;

        ArrayList<Node> nodes = graph.getNodes();
        ArrayList<Edge> edges = graph.allEdges();

        for (Node node : nodes) {
            double latitude = node.x;
            double longitude = node.y;
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
        double normalizationFactor = 1000000.0;

        for (Node node : nodes) {
            double denormalizedLatitude = (node.x * latitudeRange / normalizationFactor) + minLatitude;
            double denormalizedLongitude = (node.y * longitudeRange / normalizationFactor) + minLongitude;
            node.x =  denormalizedLatitude;
            node.y = denormalizedLongitude;
        }

        for (Edge edge : edges) {
            Node u = edge.u;
            Node v = edge.v;
            double denormalizedWeight = (edge.weight * latitudeRange / normalizationFactor) + minLatitude;
            edge.weight = denormalizedWeight;
        }    return graph;
    }
}
