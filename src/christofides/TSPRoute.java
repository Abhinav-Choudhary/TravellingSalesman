import java.util.*;

public class TSPRoute {
    ArrayList<Node> nodes = new ArrayList<>();

    public TSPRoute() {}

    public void build(Scanner reader) {
        int n = reader.nextInt();
        while(n-- > 0) {
            Node node = new Node(String.valueOf(reader.nextInt()), reader.nextInt(), reader.nextInt());
            nodes.add(node);
        }
        buildRoute();
    }

    private void buildRoute() {
        MST mstBuilder = new MST(nodes.size(), nodes);
        // Build MST
        Graph mst = mstBuilder.buildGraph();
        for(Edge edge: mst.allEdges()) {
            System.out.println(edge.u.id+" "+edge.v.id);
        }
        // Make all odd degree nodes even
        makeAllNodeDegreeEven(mst);
        // Compute Euler route
        EulerTour eulerTour = new EulerTour(mst);
        Graph tour = eulerTour.build();
        for(Edge edge: tour.allEdges()) {
            System.out.println(edge.u.id+" "+edge.v.id);
        }
        System.out.println(tour.totalWeight());
    }

    private void makeAllNodeDegreeEven(Graph g) {
        ArrayList<Node> oddDegreeNodes = new ArrayList<>();
        HashSet<Node> completedNodes = new HashSet<>();
        for(Node node: nodes) {
            int degree = g.degree(node);
            if(isEven(degree)) continue;
            oddDegreeNodes.add(node);
        }

        for(Node u: oddDegreeNodes) {
            if(completedNodes.contains(u)) continue;
            Edge shortestEdge = new Edge(null, null);
            
            for(Node v: oddDegreeNodes) {
                if(completedNodes.contains(v)) continue;
                Edge connectingEdge = new Edge(u, v);
                if(connectingEdge.compareWeightTo(shortestEdge) < 0) shortestEdge = connectingEdge;
            }
            completedNodes.add(shortestEdge.u);
            completedNodes.add(shortestEdge.v);
            g.addEdge(shortestEdge);
        }
    }

    private boolean isEven(int n) {
        return n%2 == 0;
    }
}
