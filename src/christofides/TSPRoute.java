package christofides;

import java.util.*;

//import christofides.optimizations.GeneticAlgoSolver;
//import christofides.optimizations.SimulatedAnnealingSolver;
//import christofides.optimizations.ThreeOpt;
//import christofides.optimizations.TwoOpt;

public class TSPRoute {
	ArrayList<Node> nodes = new ArrayList<>();

    public TSPRoute() {}

    public void build(Scanner reader) {
        int n = reader.nextInt();
        int pos = 1;
        while(n-- > 0) {
            reader.next();
            Node node = new Node(String.valueOf(pos++), reader.nextDouble(), reader.nextDouble());
            nodes.add(node);
        }
        buildRoute();
    }

    private void buildRoute() {
        MST mstBuilder = new MST(nodes.size(), nodes);
        // Build MST
        Graph mst = mstBuilder.buildGraph();
        System.out.println(mst.totalWeight());
        // Make all odd degree nodes even
        makeAllNodeDegreeEven(mst);
        // Compute Euler route
        EulerTour eulerTour = new EulerTour(mst);
        Graph tour = eulerTour.build();
//        for(Edge edge: tour.allEdges()) {
//            System.out.println(edge.u.id+" "+edge.v.id);
//        }
        System.out.println("Total tour length: "+tour.totalWeight());
    }

    private void makeAllNodeDegreeEven(Graph g) {
        ArrayList<Node> oddDegreeNodes = new ArrayList<>();
        HashSet<Node> completedNodes = new HashSet<>();
        for(Node node: nodes) {
            int degree = g.degree(node);
            if(isEven(degree)) continue;
            oddDegreeNodes.add(node);
        }

        ArrayList<Edge> stronglyConnectedEdges = new ArrayList<>();
        for(int i=0; i<oddDegreeNodes.size(); i++) {
            for(int j=i+1; j<oddDegreeNodes.size(); j++) {
                Edge newEdge = new Edge(oddDegreeNodes.get(i), oddDegreeNodes.get(j));
                stronglyConnectedEdges.add(newEdge);
//                System.out.println(newEdge.u.id+" "+newEdge.v.id+" "+(int)newEdge.weight);
            }
        }
        BlossomGraph blossomGraph = new BlossomGraph(stronglyConnectedEdges);



        BlossomMatching maxMatching = new BlossomMatching(blossomGraph);
        ArrayList<Edge> matchingEdges = new ArrayList<>();
        try {
            matchingEdges = maxMatching.solveMinimumCostPerfectMatching();
        } catch (Exception e) {
        	for(StackTraceElement ele: e.getStackTrace()) {
        		System.out.println(ele.toString());
        	}
        }
        for(Edge edge: matchingEdges) {
            g.addEdge(edge);
        }
//        for(Node u: oddDegreeNodes) {
//            if(completedNodes.contains(u)) continue;
//            Edge shortestEdge = new Edge(null, null);
//
//            for(Node v: oddDegreeNodes) {
//                if(completedNodes.contains(v) || u.equals(v)) continue;
//                Edge connectingEdge = new Edge(u, v);
//                if(connectingEdge.compareWeightTo(shortestEdge) < 0) shortestEdge = connectingEdge;
//            }
//            completedNodes.add(shortestEdge.u);
//            completedNodes.add(shortestEdge.v);
//            System.out.println("Add "+shortestEdge.u.id+" "+shortestEdge.v.id);
//            g.addEdge(shortestEdge);
//        }
    }

    private boolean isEven(int n) {
        return n%2 == 0;
    }
}
