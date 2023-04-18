package christofidesAlgo;

import java.util.*;

public class MST {
	 private int nodeCount;
	    private HashSet<String> addedToMST = new HashSet<>();
	    private HashMap<String, Double> distToVertex = new HashMap<>();
	    private ArrayList<Edge> edges = new ArrayList<>();

	    private ArrayList<Node> nodeList;
	    private PriorityQueue<Edge> pq = new PriorityQueue<Edge>(new Comparator<Edge>() {
	        @Override
	        public int compare(Edge a, Edge b) {
	            return a.compareWeightTo(b);
	        }
	    });

	    public MST(int nodeCount, ArrayList<Node> nodeList) {
	        this.nodeCount = nodeCount;
	        this.nodeList = nodeList;
	    }  

	    private void buildSpanningTree() {
	        Node u = nodeList.get(0);
	        distToVertex.put(u.id, 0.0);
	        while(addedToMST.size() < nodeCount) {
	            for(Node v: nodeList) {
	                if(u.equals(v) || addedToMST.contains(v.id)) continue;
	                pq.add(new Edge(u, v));
	            }
	            addedToMST.add(u.id);
	            while(!pq.isEmpty()) {
	                Edge edge = pq.poll();
	                if(addedToMST.contains(edge.v.id)) continue;
	                distToVertex.put(edge.v.id, edge.weight);
	                u = edge.v;
	                edges.add(edge);
	                break;
	            }
	        }
	    }

	    public Graph buildGraph() {
	        buildSpanningTree();
	        return new Graph(edges);
	    } 

	    public double computeDistance() {
	        buildSpanningTree();
	        double distSum = 0.0;
	        for(Double value: distToVertex.values()) distSum += value;
	        return distSum;
	    }
}