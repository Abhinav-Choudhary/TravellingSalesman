package christofides;

import java.util.Objects;

public class Edge {
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
        // return Math.sqrt((u.x-v.x)*(u.x-v.x) + (u.y-v.y)*(u.y-v.y));
        return (Math.abs(u.x-v.x) + Math.abs(u.y-v.y));
    }
    
    public double getEdgeWeight() {
    	return this.weight;
    }

    public int compareWeightTo(Edge that) {
        return Double.compare(this.weight, that.weight);
    }
}
