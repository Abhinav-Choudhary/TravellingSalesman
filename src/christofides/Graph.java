package christofides;

import java.util.*;

public class Graph {
	 ArrayList<Node> nodes = new ArrayList<>();
	    Map<String, ArrayList<Edge>> adj = new HashMap<String, ArrayList<Edge>>();;

	    private int edgeCount = 0;

	    public Graph(ArrayList<Edge> edges) {
	        HashSet<Node> nodeSet = new HashSet<>();
	        for(Edge edge: edges) {
	            nodeSet.add(edge.u);
	            nodeSet.add(edge.v);
	        }
	        nodes = new ArrayList<>(nodeSet);
	        for(Node node: nodeSet) adj.put(node.id, new ArrayList<Edge>());
	        for(Edge edge: edges) addEdge(edge);
	    }

	    public int getEdgeCount() {
	        return edgeCount;
	    }

	    public int getVertexCount() {
	        return nodes.size();
	    }

	    public ArrayList<Node> getNodes() {
	        return nodes;
	    }

	    public void addEdge(Edge e) {
	        Node v = e.v;
	        Node u = e.u;
	        adj.get(u.id).add(e);
	        adj.get(v.id).add(e);
	        edgeCount++;
	    }

	    public double getWeight(String uId, String vId) {
	        Node u = null, v = null;
	        for(Node node: nodes) {
	            if(node.id.equals(uId)) u = node;
	            else if(node.id.equals(vId)) v = node;
	        }
	        return new Edge(u, v).weight;
	    }

	    public ArrayList<Edge> adj(Node u) {
	        return adj.getOrDefault(u.id, new ArrayList<Edge>());
	    }

	    public int degree(Node u) {
	        return adj.getOrDefault(u.id, new ArrayList<>()).size();
	    }

	    public ArrayList<Edge> allEdges() {
	        HashSet<Edge> edgeSet = new HashSet<>();
	        for(ArrayList<Edge> list: adj.values()) {
	            edgeSet.addAll(list);
	        }
	        return new ArrayList<>(edgeSet);
	    }

	    public double totalWeight() {
	        double weight = 0.0;
	        for(Edge edge: allEdges()) weight += edge.weight;
	        return weight;
	    }
}

class Node {
    String id;
    double x;
    double y;
    public Node(String id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public boolean equals(Node that) {
        return this.id.equals(that.id);
    }

    @Override
    public int hashCode(){
        return id.hashCode();
    }

}

class Edge {
    Node u;
    Node v;
    double weight;
    public Edge(Node u, Node v) {
        this.u = u;
        this.v = v;
        weight = getWeight();
    }

    // Euclidean distance calculation
    private double getWeight() {
        // Return infinite weight if nodes are null
        if(Objects.isNull(u) || Objects.isNull(v)) return Integer.MAX_VALUE;
        return latLonDistance();
    }

    /**
     Calculate distance between two points in latitude and longitude.
     **/
    private double latLonDistance() {
        final double R = 6371.0; // Radius of the earth

        double latDistance = Math.toRadians(v.x - u.x);
        double lonDistance = Math.toRadians(v.y - u.y);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(u.x)) * Math.cos(Math.toRadians(v.x))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;
        return distance;
    }

    public int compareWeightTo(Edge that) {
        return Double.compare(this.weight, that.weight);
    }
}
