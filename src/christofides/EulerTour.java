import java.util.*;

public class EulerTour {
    private Graph g;
    private ArrayList<Node> tour = new ArrayList<>();

    public EulerTour(Graph g) {
        this.g = g;
    }

    public Graph build() {
        ArrayList<Node> nodes = g.getNodes();
        Map<String, Integer> degree = new HashMap<>();
        Map<String, Boolean> visited = new HashMap<>();
        for(Node node: nodes) {
            degree.put(node.id, g.degree(node));
            visited.put(node.id, false);
        }
        dfs(nodes.get(0), visited);
        ArrayList<Edge> tourEdges = new ArrayList<>();
        for(int i=1; i<nodes.size(); i++) {
            tourEdges.add(new Edge(nodes.get(i), nodes.get(i-1)));
        }
        tourEdges.add(new Edge(nodes.get(0), nodes.get(nodes.size()-1)));
        return new Graph(tourEdges);
    }

    private void dfs(Node curr, Map<String, Boolean> visited) {
        if(visited.get(curr.id)) return;
        System.out.println("dfs "+curr.id);
        visited.put(curr.id, true);
        for(Edge edge: g.adj(curr)) {
            if(!edge.u.equals(curr)) dfs(edge.u, visited);
            else dfs(edge.v, visited);
        }
        tour.add(curr);
    }
}
