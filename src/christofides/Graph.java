package christofides;

import java.util.*;

public class Graph {
    ArrayList<Node> nodes = new ArrayList<>();
    Map<String, ArrayList<Edge>> adj = new HashMap<String, ArrayList<Edge>>();

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
    
    public Map<String, ArrayList<Edge>> getAdj() {
    	return this.adj;
    }

    public void addEdge(Edge e) {
        Node v = e.v;
        Node u = e.u;
        adj.getOrDefault(u.id, new ArrayList<Edge>()).add(e);
        adj.getOrDefault(v.id, new ArrayList<Edge>()).add(e);
        edgeCount++;
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


//class Node {
//    String id;
//    int x;
//    int y;
//    public Node(String id, int x, int y) {
//        this.id = id;
//        this.x = x;
//        this.y = y;
//    }
//
//    public boolean equals(Node that) {
//        return this.id.equals(that.id);
//    }
//
//    @Override
//    public int hashCode(){
//        return id.hashCode();
//    }
//
//}

//class Edge {
//    Node u;
//    Node v;
//    double weight;
//    public Edge(Node u, Node v) {
//        this.u = u;
//        this.v = v;
//        weight = getWeight();
//    }
//
//    // Euclidean distance calculation
//    private double getWeight() {
//        // Return infinite weight if nodes are null
//        if(Objects.isNull(u) || Objects.isNull(v)) return Integer.MAX_VALUE;
//        // return Math.sqrt((u.x-v.x)*(u.x-v.x) + (u.y-v.y)*(u.y-v.y));
//        return (Math.abs(u.x-v.x) + Math.abs(u.y-v.y));
//    }
//
//    public int compareWeightTo(Edge that) {
//        return Double.compare(this.weight, that.weight);
//    }
//}
