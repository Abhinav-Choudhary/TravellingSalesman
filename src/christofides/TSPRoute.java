package christofides;

import java.util.*;

import christofides.optimizations.GeneticAlgoSolver;
import christofides.optimizations.SimulatedAnnealingSolver;
import christofides.optimizations.ThreeOpt;
import christofides.optimizations.TwoOpt;

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
        System.out.println("MST Distance: " + mst.totalWeight());
        // Make all odd degree nodes even
        makeAllNodeDegreeEven(mst);
        // Compute Euler route
        EulerTour eulerTour = new EulerTour(mst);
        Graph tour = eulerTour.buildGraph();
        System.out.println("Tour length: "+tour.totalWeight());
        
        //Genetic Algo
        GeneticAlgoSolver GASolver = new GeneticAlgoSolver(tour);
        Graph GATourGraph = GASolver.buildTour(1000);
        System.out.println("Genetic Algorithm Tour: " + GATourGraph.totalWeight());
        
        //Simulated Annealing
        SimulatedAnnealingSolver SASolver = new SimulatedAnnealingSolver(tour);
        Graph SATourGraph = SASolver.buildTour(eulerTour);
        System.out.println("Simulated Annealing Tour: " + SATourGraph.totalWeight());
        
      //2 Opt Optimization
        TwoOpt twoOptSolver = new TwoOpt(tour);
        Graph twoOptGraph = twoOptSolver.buildTour(eulerTour);
        System.out.println("2 Opt Optimization: " + twoOptGraph.totalWeight());
        
        
        //3 Opt Optimization
        ThreeOpt threeOptSolver = new ThreeOpt(tour);
        Graph threeOptGraph = threeOptSolver.buildTour(eulerTour);
        System.out.println("3 Opt Optimization: " + threeOptGraph.totalWeight());
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
