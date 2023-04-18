//package christofides.optimizations;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Random;
//
//import christofides.Edge;
//import christofides.EulerTour;
//import christofides.Graph;
//import christofides.Node;
//
//public class SimulatedAnnealingSolver {
//	Graph route;
//	int numVertices = 0;
//	ArrayList<Node> nodes;
//	// Cooling rate for the temperature
//    private static final double COOLING_RATE = 0.095;
//    // The initial temperature
//    private static final double INITIAL_TEMPERATURE = 1000;
//    // Random number generator
//    private static final Random random = new Random();
//	
//	public SimulatedAnnealingSolver(Graph tour) {
//		this.route = tour;
//		this.numVertices = this.route.getVertexCount();
//		this.nodes = this.route.getNodes();
//	}
//	
//	public Graph buildTour(EulerTour eulerTour) {
//		String[] originalTour = Helper.getStringTourFromVertexTour(eulerTour);
//		HashMap<String, Double> allDistances = Helper.calculateAllDistances(numVertices, nodes);
//		HashMap<String, Node> mappedNodes = Helper.mapNodes(originalTour, numVertices, nodes);
//		String[] modifiedTour = simulatedAnnealing(originalTour, allDistances, mappedNodes);
//		ArrayList<Edge> newSATour = Helper.generateArrayListOfEdgesFromTour(modifiedTour, mappedNodes);
//		return new Graph(newSATour);
//	}
//	
//	// Implementation of the Simulated Annealing algorithm
//    public String[] simulatedAnnealing(String[] initialRoute, HashMap<String, Double> allDistances, HashMap<String, Node> nodesMap) {
//    	String[] currentRoute = initialRoute.clone();
//    	Double currentCost = calculateCost(allDistances, nodesMap, currentRoute);
//    	String[] bestRoute = currentRoute.clone();
//        Double bestCost = currentCost;
//        double temperature = INITIAL_TEMPERATURE;
//        
//        while (temperature > 1) {
//            String[] newRoute = getNeighbour(currentRoute);
//            HashMap<String, Double> newDistances = Helper.calculateDistances(newRoute, allDistances, nodesMap);
//            Double newCost = calculateCost(newDistances, nodesMap, newRoute);
//
//            if (acceptanceProbability(currentCost, newCost, temperature) > Math.random()) {
//                currentRoute = newRoute;
//                currentCost = newCost;
//            }
//
//            if (currentCost < bestCost) {
//                bestRoute = currentRoute.clone();
//                bestCost = currentCost;
//            }
//
//            temperature *= 1 - COOLING_RATE;
//        }
//        
//    	return bestRoute;
//    }
//    
// // Calculate the cost of a given route
//    private static Double calculateCost(HashMap<String, Double> distances, HashMap<String, Node> nodesMap, String[] route) {
//    	Double cost = 0.0;
//        for (int i = 0; i < route.length - 1; i++) {
//        	cost += Helper.getDistanceBetweenNodes(nodesMap.get(route[i]), nodesMap.get(route[i+1]), distances);
//        }
//        cost += Helper.getDistanceBetweenNodes(nodesMap.get(route[route.length - 1]), nodesMap.get(route[0]), distances);
//        return cost;
//    }
//    
// // Get a neighbour of a given route by swapping two random cities
//    private static String[] getNeighbour(String[] route) {
//        String[] newRoute = route.clone();
//        int index1 = random.nextInt(newRoute.length);
//        int index2 = random.nextInt(newRoute.length);
//        String temp = newRoute[index1];
//        newRoute[index1] = newRoute[index2];
//        newRoute[index2] = temp;
//        return newRoute;
//    }
//    
//    // Calculate the acceptance probability for the Simulated Annealing algorithm
//    private static double acceptanceProbability(Double currentCost, Double newCost, double temperature) {
//        if (newCost < currentCost) {
//            return 1.0;
//        }
//        return Math.exp((currentCost - newCost) / temperature);
//    }
//	
//}
