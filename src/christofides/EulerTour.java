package christofides;

import java.util.*;

public class EulerTour {
   private Graph g;

    public EulerTour(Graph g) {
        this.g = g;
    }

    private ArrayList<Node> build() {
        ArrayList<Node> nodes = g.getNodes();
        HashSet<Edge> visited = new HashSet<>();
        ArrayList<Node> eulerPath = new ArrayList<>();
        

        dfs(nodes.get(0), eulerPath, visited);

        ArrayList<Node> hamiltonianCycle = new ArrayList<>();
        HashSet<Node> visitedNodes = new HashSet<>();

        for(Node node: eulerPath) {
            if(visitedNodes.contains(node)) continue;
            hamiltonianCycle.add(node);
            visitedNodes.add(node);
        }
        return hamiltonianCycle;
        
    }
    
    public Graph buildGraph() {
    	ArrayList<Edge> tourEdges = new ArrayList<>();
    	ArrayList<Node> hamiltonianCycle = build();
    	for(int i=0; i<hamiltonianCycle.size(); i++) {
            tourEdges.add(new Edge(hamiltonianCycle.get(i), hamiltonianCycle.get((i+1) % hamiltonianCycle.size())));
        }
        return new Graph(tourEdges);
    }
    
    public ArrayList<Node> buildVertexTour() {
    	ArrayList<Node> hamiltonianCycle = build();
    	return hamiltonianCycle;
    }

    private void dfs(Node curr, ArrayList<Node> eulerPath, HashSet<Edge> visitedEdges) {
        for(Edge edge: g.adj(curr)) {
            if(visitedEdges.contains(edge)) continue;
            visitedEdges.add(edge);
            Node nextNode = edge.v;
            if (curr.equals(nextNode)) nextNode = edge.u;
            dfs(nextNode, eulerPath, visitedEdges);
        }
        System.out.println("dfs "+curr.id);
        eulerPath.add(curr);
    }
}
