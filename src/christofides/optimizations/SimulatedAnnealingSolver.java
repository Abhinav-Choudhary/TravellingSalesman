package christofides.optimizations;

import java.util.ArrayList;
import java.util.Random;

import christofides.Edge;
import christofides.Graph;
import christofides.Node;

public class SimulatedAnnealingSolver {
	Graph route;
	int numVertices = 0;
	ArrayList<Node> nodes;
	// Cooling rate for the temperature
    private static final double COOLING_RATE = 0.003;
    // The initial temperature
    private static final double INITIAL_TEMPERATURE = 10000;
    // Random number generator
    private static final Random random = new Random();
	
	public SimulatedAnnealingSolver(Graph tour) {
		this.route = tour;
		this.numVertices = this.route.getVertexCount();
		this.nodes = this.route.getNodes();
	}
	
	// Implementation of the Simulated Annealing algorithm
    public String[] simulatedAnnealing(String[] initialRoute) {
    	String[] currentRoute = initialRoute.clone();
    	Double[][] distances = calculateDistances(initialRoute);
    	Double currentCost = calculateCost(distances, currentRoute);
    	
    	String[] bestRoute = currentRoute.clone();
        Double bestCost = currentCost;
        
        double temperature = INITIAL_TEMPERATURE;
        
        while (temperature > 1) {
            String[] newRoute = getNeighbour(currentRoute);
            Double[][] newDistances = calculateDistances(newRoute);
            Double newCost = calculateCost(newDistances, newRoute);

            if (acceptanceProbability(currentCost, newCost, temperature) > Math.random()) {
                currentRoute = newRoute;
                currentCost = newCost;
            }

            if (currentCost < bestCost) {
                bestRoute = currentRoute.clone();
                bestCost = currentCost;
            }

            temperature *= 1 - COOLING_RATE;
        }
        
    	return bestRoute;
    }
    
//    for(Edge edge: SATourGraph.allEdges()) {
//        System.out.println(edge.u.id+" "+edge.v.id);
//    }
    
    public String[] createStringTourFromGraphTour(Graph Tour) {
    	String[] newTour = new String[this.numVertices];
    	ArrayList<Edge> edges = Tour.allEdges();
    	newTour[0] = edges.get(0).getUEdge().getID();
    	String initialVEdge = edges.get(0).getVEdge().getID();
    	for (int i=1; i<newTour.length; i++) {
    		int edgeIndex = findEdgeIndex(edges, initialVEdge);
    		Edge nextEdge = edges.get(edgeIndex);
    		Node nextEdgeU = nextEdge.getUEdge();
    		Node nextEdgeV = nextEdge.getVEdge();
    		newTour[i] = nextEdgeU.getID();
    		initialVEdge = nextEdgeV.getID();
    	}
    	
    	return newTour;
    }
    
    private int findEdgeIndex(ArrayList<Edge> edges, String vEdgeID) {
    	int edgeIndex = -1;
    	for (int i=1; i<edges.size(); i++) {
    		if (edges.get(i).getUEdge().getID() == vEdgeID) {
    			edgeIndex = i;
    			break;
    		}
    	}
    	return edgeIndex;
    }
    
    private Double[][] calculateDistances(String[] initialRoute) {
    	int n = initialRoute.length;
    	Double[][] distances = new Double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Double distance = calculateDistance(initialRoute[i], initialRoute[j]);
                distances[i][j] = distance;
                distances[j][i] = distance;
            }
        }
        return distances;
    }
    
    private Double calculateDistance(String city1, String city2) {
    	int city1Index = findNodeIndex(city1);
    	int city2Index = findNodeIndex(city2);
    	Node city1Node = nodes.get(city1Index);
    	Node city2Node = nodes.get(city2Index);
    	Edge city1Edge = new Edge(city1Node, city2Node);
    	return city1Edge.getEdgeWeight();
    }
    
    private int findNodeIndex(String ID) {
		int length = nodes.size();
		int foundIndex = -1;
		for (int i=0; i < length; i++) {
			if (nodes.get(i).getID() == ID) {
				foundIndex = i;
				break;
			}
		}
		return foundIndex;
	}
    
 // Calculate the cost of a given route
    private static Double calculateCost(Double[][] distances, String[] route) {
    	Double cost = 0.0;
        for (int i = 0; i < route.length - 1; i++) {
            cost += distances[i][i+1];
        }
        cost += distances[route.length - 1][0];
        return cost;
    }
    
 // Get a neighbour of a given route by swapping two random cities
    private static String[] getNeighbour(String[] route) {
        String[] newRoute = route.clone();
        int index1 = random.nextInt(newRoute.length);
        int index2 = random.nextInt(newRoute.length);
        String temp = newRoute[index1];
        newRoute[index1] = newRoute[index2];
        newRoute[index2] = temp;
        return newRoute;
    }
    
    // Calculate the acceptance probability for the Simulated Annealing algorithm
    private static double acceptanceProbability(Double currentCost, Double newCost, double temperature) {
        if (newCost < currentCost) {
            return 1.0;
        }
        return Math.exp((currentCost - newCost) / temperature);
    }
    
    public ArrayList<Edge> generateArrayListOfEdgesFromTour(String[] tour) {
		ArrayList<Edge> newTour = new ArrayList<>();
		int n = tour.length;
		for (int i=0; i < n; i++) {
			int j = (i + 1) % n;
	        Edge newEdge = new Edge(nodes.get(findNodeIndex(tour[i])), nodes.get(findNodeIndex(tour[j])));
	        newTour.add(newEdge);
		}
		
		return newTour;
	}
	
}
