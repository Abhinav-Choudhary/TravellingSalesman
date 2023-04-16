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