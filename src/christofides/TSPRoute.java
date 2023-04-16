package christofides;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
//        for(Edge edge: mst.allEdges()) {
//            System.out.println(edge.u.id+" "+edge.v.id);
//        }
        System.out.println("MST distance: " + mstBuilder.computeDistance());
        // Make all odd degree nodes even
        makeAllNodeDegreeEven(mst);
        // Compute Euler route
        EulerTour eulerTour = new EulerTour(mst);
        Graph tour = eulerTour.buildGraph();
//        for(Edge edge: tour.allEdges()) {
//            System.out.println(edge.u.id+" "+edge.v.id);
//        }
        System.out.println(tour.totalWeight());
        
        //Genetic Algo
//        GeneticAlgoSolver GASolver = new GeneticAlgoSolver(tour);
//        Graph GATourGraph = GASolver.buildTour(1500);
//        System.out.println("New Genetic Algorithm Tour");
//        System.out.println(GATourGraph.totalWeight());
        
        //Simulated Annealing
//        SimulatedAnnealingSolver SASolver = new SimulatedAnnealingSolver(tour);
//        Graph SATourGraph = SASolver.buildTour(eulerTour);
//        System.out.println("New Simulated Annealing Tour");
//        System.out.println(SATourGraph.totalWeight());
        
      //2 Opt Optimization
        TwoOpt twoOptSolver = new TwoOpt(tour);
        Graph twoOptGraph = twoOptSolver.buildTour(eulerTour);
        System.out.println("2 Opt Optimization");
        System.out.println(twoOptGraph.totalWeight());
        
        
        //3 Opt Optimization
//        ThreeOpt threeOptSolver = new ThreeOpt(tour);
//        Graph threeOptGraph = threeOptSolver.buildTour(eulerTour);
//        System.out.println("3 Opt Optimization");
//        System.out.println(threeOptGraph.totalWeight());
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
