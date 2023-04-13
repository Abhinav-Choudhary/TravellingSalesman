package uiHelper;

import java.util.ArrayList;

public class Normalizer {

    public static ArrayList<Node> normalizeNodes(Graph graph) {
        ArrayList<Node> normalizedNodes = new ArrayList<>();
        ArrayList<Node> nodes = graph.getNodes();
        for (Node node : nodes) {
            Node normalizedNode = new Node(node.id, normalizeLongitude(node.longitude), normalizeLatitude(node.latitude));
            normalizedNodes.add(normalizedNode);
        }
        return normalizedNodes;
    }

    public static ArrayList<Edge> normalizeEdges(Graph graph) {
        ArrayList<Edge> normalizedEdges = new ArrayList<>();
        ArrayList<Edge> edges = graph.allEdges();
        for (Edge edge : edges) {
            Node u = edge.u;
            Node v = edge.v;
            Edge normalizedEdge = new Edge(u, v, edge.weight);
            normalizedEdges.add(normalizedEdge);
        }
        return normalizedEdges;
    }

    // Example methods to normalize longitude and latitude values
    private static double normalizeLongitude(double longitude) {
        // Normalize longitude value here
        // Example: return normalized value
        return longitude * 2;
    }

    private static double normalizeLatitude(double latitude) {
        // Normalize latitude value here
        // Example: return normalized value
        return latitude / 2;
    }
}

