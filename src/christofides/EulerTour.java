package christofides;

import java.util.*;

public class EulerTour {
	private Graph g;
    private ArrayList<Node> tour = new ArrayList<>();

    public EulerTour(Graph g) {
        this.g = g;
    }

    public Graph buildGraph() {
    	build();
        ArrayList<Edge> tourEdges = new ArrayList<>();
        for(int i=0; i<tour.size(); i++) {
            tourEdges.add(new Edge(tour.get(i), tour.get((i+1) % tour.size())));
        }
        return new Graph(tourEdges);
    }

    private void dfs(Node curr, Map<String, Boolean> visited) {
        if(visited.get(curr.id)) return;
        visited.put(curr.id, true);
        for(Edge edge: g.adj(curr)) {
            if(!edge.u.equals(curr)) dfs(edge.u, visited);
            else dfs(edge.v, visited);
        }
//        System.out.println("dfs "+curr.id);
        tour.add(curr);
    }
    
    private void build() {
    	this.tour = new ArrayList<>();
    	ArrayList<Node> nodes = g.getNodes();
        Map<String, Boolean> visited = new HashMap<>();
        for(Node node: nodes) {
            visited.put(node.id, false);
        }
        dfs(nodes.get(0), visited);
    }
    
    public ArrayList<Node> buildVertexTour() {
    	build();
    	return tour;
    }
}
