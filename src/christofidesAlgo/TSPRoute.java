package christofidesAlgo;

import java.util.*;

import UI.DataNormalizer;
import UI.UICreator;
import christofidesAlgo.optimizations.GeneticAlgoSolver;
import christofidesAlgo.optimizations.SimulatedAnnealingSolver;
import christofidesAlgo.optimizations.ThreeOpt;
import christofidesAlgo.optimizations.TwoOpt;
import javafx.application.Application;
import javafx.stage.Stage;;

public class TSPRoute {
	ArrayList<Node> nodes = new ArrayList<>();
	public static TSPRoute shared = new TSPRoute();
	
    private TSPRoute() {
    	
    }

    public void build(Scanner reader) {
    	reader.nextLine();
    	int idLength = 6;
        while(reader.hasNextLine()) {
        	String line = reader.nextLine();
        	String[] data = line.split(",");
        	String id = data[0].substring(data[0].length() - idLength, data[0].length());
            Node node = new Node(id, Double.valueOf(data[1]), Double.valueOf(data[2]));
            nodes.add(node);
        }
    }
    
    public Graph computeMST() {
    	MST mstBuilder = new MST(nodes.size(), nodes);
        // Build MST
        Graph mst = mstBuilder.buildGraph();
        System.out.println("MST total Weight: " + mst.totalWeight());
        return mst;
    }
    
    public Graph computeEulerTour(Graph mst) {
    	makeAllNodeDegreeEven(mst);
        // Compute Euler route
        EulerTour eulerTour = new EulerTour(mst);
        Graph tour = eulerTour.buildGraph();
        System.out.println(tour.totalWeight());
        return tour;
    }

//    public Graph buildChristofides() {
//        MST mstBuilder = new MST(nodes.size(), nodes);
//        // Build MST
//        Graph mst = mstBuilder.buildGraph();
//        System.out.println(mst.totalWeight());
//        // Make all odd degree nodes even
//        makeAllNodeDegreeEven(mst);
//        // Compute Euler route
//        EulerTour eulerTour = new EulerTour(mst);
//        Graph tour = eulerTour.buildGraph();
//        System.out.println(tour.totalWeight());
//        return tour;
//    }
    
    public Graph buildGeneticAlgoTour() {
      // Genetic Algo
//      Graph tour = buildChristofides();
    	Graph mst = computeMST();
    	Graph tour = computeEulerTour(mst);
    	GeneticAlgoSolver gaSolver = new GeneticAlgoSolver(tour);
    	Graph gaTourGraph = gaSolver.buildTour(800);
    	System.out.println("New Genetic Algorithm Tour");
    	System.out.println(gaTourGraph.totalWeight());
    	return gaTourGraph;
    }
    
    public Graph runSimulatedAnnealing() {
    	MST mstBuilder = new MST(nodes.size(), nodes);
        // Build MST
        Graph mst = mstBuilder.buildGraph();
        // Make all odd degree nodes even
        makeAllNodeDegreeEven(mst);
        // Compute Euler route
        EulerTour eulerTour = new EulerTour(mst);
        Graph tour = eulerTour.buildGraph();

        //Simulated Annealing
        SimulatedAnnealingSolver saSolver = new SimulatedAnnealingSolver(tour);
        Graph saTourGraph = saSolver.buildTour(eulerTour);
        System.out.println("New Simulated Annealing Tour");
        System.out.println(saTourGraph.totalWeight());
        return saTourGraph;
    }
    
    public Graph run2OPT() {
    	// 2 Opt Optimization
    	MST mstBuilder = new MST(nodes.size(), nodes);
        // Build MST
        Graph mst = mstBuilder.buildGraph();
        System.out.println("MST Distance: " + mst.totalWeight());
        // Make all odd degree nodes even
        makeAllNodeDegreeEven(mst);
        // Compute Euler route
        EulerTour eulerTour = new EulerTour(mst);
//    	Graph saTourGraph = runSimulatedAnnealing();
        Graph tour = eulerTour.buildGraph();
        TwoOpt twoOptSolver = new TwoOpt(tour);
        Graph twoOptGraph = twoOptSolver.buildTour(eulerTour);
        System.out.println("2 Opt Optimization");
        System.out.println(twoOptGraph.totalWeight());
        return twoOptGraph;
    }
    
    public Graph run3OPT() {
    	 MST mstBuilder = new MST(nodes.size(), nodes);
         // Build MST
         Graph mst = mstBuilder.buildGraph();
         System.out.println(mst.totalWeight());
         // Make all odd degree nodes even
         makeAllNodeDegreeEven(mst);
         // Compute Euler route
         EulerTour eulerTour = new EulerTour(mst);
         Graph tour = eulerTour.buildGraph();
         //3 Opt Optimization
         ThreeOpt threeOptSolver = new ThreeOpt(tour);
         Graph threeOptGraph = threeOptSolver.buildTour(eulerTour);
         System.out.println("3 Opt Optimization");
         System.out.println(threeOptGraph.totalWeight());
         return threeOptGraph;
    }

    public void makeAllNodeDegreeEven(Graph g) {
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
